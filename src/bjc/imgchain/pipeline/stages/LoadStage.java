package bjc.imgchain.pipeline.stages;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bjc.imgchain.pipeline.StageType;

public class LoadStage extends AbstractPipelineStage {
	private String fileName;

	public LoadStage() {
		super(StageType.IMGSOURCE);
	}

	@Override
	public Image process(Image inp) {
		try {
			return ImageIO.read(new URL("file://" + fileName));
		} catch (IOException e) {
			String msg = String.format("Error: Could not load image %s", fileName);

			System.out.printf("%s\n", msg);

			e.printStackTrace();

			JOptionPane.showInternalMessageDialog(null, msg, "Error loading image",
					JOptionPane.ERROR_MESSAGE);
		}

		return inp;
	}

	@Override
	public String name() {
		return "Load Image";
	}

	@Override
	public String description() {
		return "Load an image from a file";
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

			int res = jfc.showOpenDialog(holder);

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
