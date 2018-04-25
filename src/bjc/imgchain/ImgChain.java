package bjc.imgchain;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ImgChain {
	private final class LoadImageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {
			JFileChooser jfc = new JFileChooser();
			jfc.setMultiSelectionEnabled(true);

			int res = jfc.showOpenDialog(desktop);

			if (res != JFileChooser.APPROVE_OPTION) {
				return;
			}

			for (File fle : jfc.getSelectedFiles()) {
				ImgViewer view = new ImgViewer(ImgChain.this, fle);

				if (view.isInitialized()) {
					desktop.add(view);
					view.setVisible(true);
				}
			}
		}
	}

	private JDesktopPane desktop;

	private Map<String, Image> imageRepo;

	public static void main(String[] args) {
		System.out.println("ImgChain Loading...");

		ImgChain chn = new ImgChain();

		chn.setupGUI();
	}

	public ImgChain() {
		imageRepo = new HashMap<>();
	}

	private void setupGUI() {
		JFrame frame = new JFrame("ImgChain v1");
		frame.setLayout(new GridLayout(1, 1));

		desktop = new JDesktopPane();

		JMenuBar menu = setupMenubar(frame);

		frame.setJMenuBar(menu);

		frame.add(desktop);

		frame.setSize(640, 480);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setVisible(true);
	}

	private JMenuBar setupMenubar(JFrame frame) {
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic('A');
		aboutItem.addActionListener((ev) -> {
			JOptionPane.showMessageDialog(frame, "ImgChain v1\nDeveloped by Benjamin Culkin",
					"About ImgChain", JOptionPane.INFORMATION_MESSAGE);
		});

		JMenuItem closeItem = new JMenuItem("Close");
		closeItem.setMnemonic('C');
		closeItem.addActionListener((ev) -> {
			frame.dispose();
		});

		fileMenu.add(aboutItem);
		fileMenu.addSeparator();
		fileMenu.add(closeItem);

		JMenu imageMenu = new JMenu("Images");
		imageMenu.setMnemonic('I');

		JMenuItem loadImage = new JMenuItem("Load Images...");
		loadImage.setMnemonic('L');
		loadImage.addActionListener(new LoadImageListener());

		imageMenu.add(loadImage);

		menu.add(fileMenu);
		menu.add(imageMenu);
		return menu;
	}

	public void addImage(String name, Image img) {
		if (imageRepo.containsKey(name)) {
			String msg = String.format("Are you sure you want to overwrite stored image '%s'?", name);

			if (JOptionPane.showInternalConfirmDialog(desktop, msg) != JOptionPane.OK_OPTION) {
				return;
			}
		}

		imageRepo.put(name, img);
	}
}
