package bjc.imgchain.pipeline.stages;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.Utils;

/**
 * Stage which saves an image.
 * 
 * @author Ben Culkin
 *
 */
public class SaveStage extends AbstractPipelineStage {
	private String fileName;

	/**
	 * Create a stage that saves an image.
	 */
	public SaveStage() {
		super(StageType.IMGSINK);
	}

	@Override
	public Image process(Image inp) {
		try {
			ImageIO.write(Utils.toBuffered(inp), "PNG", new File(fileName));
		} catch (IOException e) {
			String msg = String.format("Error: Could not save image %s", fileName);

			System.out.printf("%s\n", msg);

			e.printStackTrace();

			JOptionPane.showInternalMessageDialog(null, msg, "Error saving image",
					JOptionPane.ERROR_MESSAGE);
		}

		return inp;
	}

	@Override
	public String name() {
		return "Save Image";
	}

	@Override
	public String description() {
		return "Save an image to a file";
	}

	@Override
	public JComponent getEditor() {
		JPanel holder = new JPanel();
		holder.setLayout(new BorderLayout());

		JLabel fileLabel = new JLabel("File");

		JTextField fileField = new JTextField(80);
		fileField.addPropertyChangeListener("value", (ev) -> {
			fileName = fileField.getText();
		});

		JButton fileButton = new JButton("Select File");
		fileButton.addActionListener((ev) -> {
			JFileChooser jfc = new JFileChooser();
			jfc.setMultiSelectionEnabled(false);

			int res = jfc.showSaveDialog(holder);

			if (res != JFileChooser.APPROVE_OPTION) {
				return;
			}

			fileField.setText(jfc.getSelectedFile().getAbsolutePath());
		});

		holder.add(fileLabel, BorderLayout.LINE_START);
		holder.add(fileField, BorderLayout.CENTER);
		holder.add(fileButton, BorderLayout.LINE_END);

		return holder;
	}
}
