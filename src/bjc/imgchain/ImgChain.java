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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bjc.imgchain.pipeline.Pipeline;

/**
 * Main class for ImgChain
 * 
 * @author acm
 *
 */
public class ImgChain {
	/*
	 * Action to perform when loading an image.
	 */
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

	/**
	 * The desktop everything is attached to.
	 */
	public JDesktopPane desktop;

	/*
	 * The storage for images.
	 */
	public final Map<String, Image> imageRepo;

	/*
	 * The storage for images.
	 */
	public final Map<String, Pipeline> pipelineRepo;

	/**
	 * The image chain instance.
	 */
	public static ImgChain chan;

	public JFrame frame;

	/**
	 * Main method
	 * 
	 * @param args
	 *                Unused CLI args
	 */
	public static void main(String[] args) {
		System.out.println("ImgChain Loading...");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		chan = new ImgChain();

		chan.setupGUI();
	}

	/**
	 * Initialize image repo
	 */
	public ImgChain() {
		imageRepo = new HashMap<>();
		pipelineRepo = new HashMap<>();
	}

	/*
	 * Setup the GUI
	 */
	private void setupGUI() {
		frame = new JFrame("ImgChain v1");
		frame.setLayout(new GridLayout(1, 1));

		desktop = new JDesktopPane();

		JMenuBar menu = setupMenubar(frame);

		frame.setJMenuBar(menu);

		frame.add(desktop);

		frame.setSize(640, 480);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setVisible(true);
	}

	/*
	 * Setup the menubar.
	 */
	private JMenuBar setupMenubar(JFrame frame) {
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic('A');
		aboutItem.addActionListener((ev) -> {
			JOptionPane.showMessageDialog(frame, "ImgChain v2\nDeveloped by Benjamin Culkin",
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

		JMenu pipelineMenu = new JMenu("Pipelines");
		JMenuItem createPipe = new JMenuItem("Create Pipeline");
		createPipe.setMnemonic('C');
		createPipe.addActionListener((ev) -> {
			ImgPipeline pip = new ImgPipeline();

			desktop.add(pip);
			pip.setVisible(true);
		});

		pipelineMenu.add(createPipe);

		menu.add(fileMenu);
		menu.add(imageMenu);
		menu.add(pipelineMenu);

		return menu;
	}

	/**
	 * Add an image to the image repository.
	 * 
	 * @param name
	 *                The name of the image
	 * @param img
	 *                The image to add.
	 */
	public void addImage(String name, Image img) {
		if (imageRepo.containsKey(name)) {
			String msg = String.format("Are you sure you want to overwrite stored image '%s'?", name);

			if (JOptionPane.showInternalConfirmDialog(desktop, msg) != JOptionPane.OK_OPTION) {
				return;
			}
		}

		imageRepo.put(name, img);
	}

	/**
	 * Add an pipeline to the pipeline repository.
	 * 
	 * @param pipe
	 *                The pipeline to add.
	 */
	public void addPipe(Pipeline pipe) {
		String name = pipe.name();

		if (pipelineRepo.containsKey(name)) {
			String msg = String.format("Are you sure you want to overwrite stored pipeline '%s'?", name);

			if (JOptionPane.showInternalConfirmDialog(desktop, msg) != JOptionPane.OK_OPTION) {
				return;
			}
		}

		pipelineRepo.put(name, pipe);
	}
}
