package bjc.imgchain;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import bjc.imgchain.utils.SimpleInputPanel;

public class ImgViewer extends JInternalFrame {
	private final class ScaleImageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {
			int height = Integer.parseInt(JOptionPane.showInputDialog("Enter new image height"));
			int width = Integer.parseInt(JOptionPane.showInputDialog("Enter new image width"));
			
			icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
			
			repaint();
		}
	}

	private final class ReloadImageListener implements ActionListener {
		private final File img;

		private ReloadImageListener(File img) {
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
	
	public ImgViewer(ImgChain desk, File img) {
		super("Image Viewer - " + img.getName(), true, true, true, true);
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

		JLabel lab = loadLabel(img);
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

		JMenuItem changeImage = new JMenuItem("Change Image");
		changeImage.setMnemonic('C');
		changeImage.addActionListener(new ChangeImageListener());

		JMenuItem reloadImage = new JMenuItem("Reload Image");
		reloadImage.setMnemonic('R');
		reloadImage.addActionListener(new ReloadImageListener(img));
		
		JMenuItem storeImage = new JMenuItem("Store Image");
		storeImage.setMnemonic('S');
		storeImage.addActionListener((ev) -> {
			String inp = JOptionPane.showInternalInputDialog(this, "Enter name to store image under");
			
			if(inp == null) return;
			
			inp = inp.trim();
			
			if(inp.equals("")) {
				return;
			}
			
			desktop.addImage(inp, icon.getImage());
		});
		
		fileMenu.add(changeImage);
		fileMenu.add(reloadImage);
		fileMenu.addSeparator();
		fileMenu.add(storeImage);
		
		JMenu editMenu = new JMenu("Edit Image");
		editMenu.setMnemonic('E');
		
		JMenuItem scaleImage = new JMenuItem("Scale Image");
		scaleImage.setMnemonic('S');
		scaleImage.addActionListener(new ScaleImageListener());
		
		editMenu.add(scaleImage);
		
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
