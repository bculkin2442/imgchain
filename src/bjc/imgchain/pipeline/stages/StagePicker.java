package bjc.imgchain.pipeline.stages;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bjc.imgchain.utils.ImmutableTableModel;

public class StagePicker extends JDialog {
	private static final long serialVersionUID = 1L;

	public String stageName;

	public StagePicker() {
		super();

		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Add a Stage");

		setupGUI();
	}

	private void setupGUI() {
		setLayout(new BorderLayout());

		String[] columnNames = new String[] { "Stage Name", "Stage Description" };

		/*
		 * :AddStage
		 */
		String[][] data = new String[][] { { "Identity", "Pass through image unchanged" },
				{ "Greyscale", "Convert a color image into greyscale" },
				{ "Color Skew", "Adjust color balance" }, { "Sepia", "Make your image sepia toned" },
				{ "Negative", "Invert your images colors" }, { "Gaussian Blur", "Blur images" },
				{ "Tint", "Add/remove colors" },
				{ "Colorized Threshold", "Convert the image to three-tone" },
				{ "Sub-pipeline", "Execute another pipeline" },
				{ "Load Image", "Load an image from a file" },
				{ "Save Image", "Save an image to a file" },
				{ "Stash Image", "Stash an image to memory" },
				{ "Recall Image", "Recall an image from memory" },
				{ "Additive Mask", "Add two images together" },
				{ "Subtractive Mask", "Subtract one image from another" } };

		JTable stageTable = new JTable(new ImmutableTableModel(data, columnNames));

		JScrollPane tableScroll = new JScrollPane(stageTable);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));

		JButton addStage = new JButton("Add Stage");
		addStage.addActionListener((ev) -> {
			stageName = (String) stageTable.getModel().getValueAt(stageTable.getSelectedRow(), 0);

			setVisible(false);
			dispose();
		});

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((ev) -> {
			setVisible(false);
			dispose();
		});

		buttonPanel.add(addStage);
		buttonPanel.add(cancel);

		add(tableScroll, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
	}
}
