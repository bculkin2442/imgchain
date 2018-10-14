package bjc.imgchain;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import bjc.imgchain.pipeline.Pipeline;
import bjc.imgchain.pipeline.PipelinePicker;
import bjc.imgchain.utils.Utils;

public class ImgViewer extends JInternalFrame {
	private final class ReloadImageListener implements ActionListener {
		private final File img;

		ReloadImageListener(File img) {
			this.img = img;
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			try {
				icon.setImage(ImageIO.read(img));
			} catch (IOException e) {
				String msg = String.format("Error: Could not load image %s", img.getPath());

				System.out.printf("%s\n", msg);

				e.printStackTrace();

				JOptionPane.showInternalMessageDialog(ImgViewer.this, msg, "Error loading image",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private final class ChangeImageListener implements ActionListener {
		public ChangeImageListener() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			JFileChooser jfc = new JFileChooser();
			jfc.setMultiSelectionEnabled(false);

			int res = jfc.showOpenDialog(ImgViewer.this);

			if (res != JFileChooser.APPROVE_OPTION) {
				return;
			}

			try {
				File tmp = jfc.getSelectedFile();

				icon.setImage(ImageIO.read(tmp));

				img = tmp;

				setTitle("Image Viewer - " + img.getName());
			} catch (IOException e) {
				String msg = String.format("Error: Could not load image %s", img.getPath());

				System.out.printf("%s\n", msg);

				e.printStackTrace();

				JOptionPane.showInternalMessageDialog(null, msg, "Error loading image",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static final long serialVersionUID = -4727584150861526435L;

	private File img;

	private boolean initted;

	private ImageIcon icon;

	private ImgChain desktop;

	private JLabel lab;

	public ImgViewer(ImgChain desk, File img) {
		super("Image Viewer - " + img.getName(), false, true, true, true);
		initted = false;

		this.img = img;
		this.desktop = desk;

		initted = setupGUI(img);
	}

	private boolean setupGUI(File img) {
		setSize(320, 240);
		setLayout(new GridLayout(1, 1));

		JMenuBar bar = setupMenubar(img);

		setJMenuBar(bar);

		lab = loadLabel(img);
		if (lab == null) {
			return false;
		}

		add(lab);

		return true;
	}

	private JMenuBar setupMenubar(File img) {
		JMenuBar bar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		JMenuItem changeImage = new JMenuItem("Load Image from Disk");
		changeImage.setMnemonic('L');
		changeImage.addActionListener(new ChangeImageListener());

		JMenuItem reloadImage = new JMenuItem("Reload Image");
		reloadImage.setMnemonic('R');
		reloadImage.addActionListener(new ReloadImageListener(img));

		JMenuItem saveImage = new JMenuItem("Save Image");
		saveImage.setMnemonic('S');
		saveImage.addActionListener((ev) -> {
			JFileChooser jfc = new JFileChooser();
			jfc.setMultiSelectionEnabled(false);

			int res = jfc.showSaveDialog(ImgViewer.this);

			if (res != JFileChooser.APPROVE_OPTION) {
				return;
			}

			try {
				File tmp = jfc.getSelectedFile();

				ImageIO.write(Utils.toBuffered(icon.getImage()), "PNG", tmp);
			} catch (IOException e) {
				String msg = String.format("Error: Could not save image %s", img.getPath());

				System.out.printf("%s\n", msg);

				e.printStackTrace();

				JOptionPane.showInternalMessageDialog(null, msg, "Error saving image",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		JMenuItem storeImage = new JMenuItem("Stash Image to Memory");
		storeImage.setMnemonic('T');
		storeImage.addActionListener((ev) -> {
			String inp = JOptionPane.showInternalInputDialog(this, "Enter name to store image under");

			if (inp == null) return;

			inp = inp.trim();

			if (inp.equals("")) {
				return;
			}

			desktop.addImage(inp, icon.getImage());
		});

		JMenuItem recallImage = new JMenuItem("Recall Image from Memory");
		recallImage.setMnemonic('F');
		recallImage.addActionListener((ev) -> {
			ImgPicker pick = new ImgPicker();

			pick.pack();
			pick.setVisible(true);

			if (pick.imageName == null) {
				System.out.println("WARN: picked null image");
				return;
			}

			Image imag = ImgChain.chan.imageRepo.get(pick.imageName);

			icon.setImage(imag);
			lab.repaint();
		});

		fileMenu.add(changeImage);
		fileMenu.add(reloadImage);
		fileMenu.addSeparator();
		fileMenu.add(storeImage);

		JMenu editMenu = new JMenu("Edit Image");
		editMenu.setMnemonic('E');

		JMenuItem applyPipe = new JMenuItem("Apply Pipeline");
		applyPipe.setMnemonic('A');
		applyPipe.addActionListener((ev) -> {
			PipelinePicker pick = new PipelinePicker();

			pick.pack();
			pick.setVisible(true);

			if (pick.pipeName == null) {
				System.out.println("WARN: picked null pipeline");
				return;
			}

			Pipeline pipeline = ImgChain.chan.pipelineRepo.get(pick.pipeName);

			BufferedImage bufimg = Utils.toBuffered(icon.getImage());

			Image processed = pipeline.process(bufimg);

			Utils.displayImage(processed, "Pipeline Results");

		});

		editMenu.addSeparator();
		editMenu.add(applyPipe);

		bar.add(fileMenu);
		bar.add(editMenu);

		return bar;
	}

	private JLabel loadLabel(File img) {
		JLabel lab = null;

		try {
			URL imgURL = img.toURI().toURL();

			icon = new ImageIcon(imgURL);

			setSize(icon.getIconWidth(), icon.getIconHeight());

			lab = new JLabel(icon);
		} catch (MalformedURLException e) {
			String msg = String.format("Error: Could not load image %s", img.getPath());

			System.out.printf("%s\n", msg);

			e.printStackTrace();

			JOptionPane.showInternalMessageDialog(this, msg, "Error loading image",
					JOptionPane.ERROR_MESSAGE);
		}

		return lab;
	}

	public boolean isInitialized() {
		return initted;
	}
}
