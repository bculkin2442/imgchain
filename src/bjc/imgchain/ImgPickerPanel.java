package bjc.imgchain;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import bjc.imgchain.utils.LabeledInputPanel;

public class ImgPickerPanel extends JPanel {
	public String			stashName;
	public LabeledInputPanel	imgField;

	public ImgPickerPanel() {
		setLayout(new BorderLayout());

		setupGUI("Image name");
	}

	public ImgPickerPanel(String lab) {
		setLayout(new BorderLayout());

		setupGUI(lab);
	}

	private void setupGUI(String lab) {
		imgField = new LabeledInputPanel(lab, "");
		imgField.addPropertyChangeListener("value", (ev) -> {
			stashName = imgField.field.getText();
		});

		JButton pickImg = new JButton("Pick Image");
		pickImg.addActionListener((ev) -> {
			ImgPicker pick = new ImgPicker();

			pick.pack();
			pick.setVisible(true);

			if (pick.imageName == null) {
				System.out.println("WARN: picked null image");
				return;
			}

			imgField.field.setText(stashName);
		});

		add(imgField, BorderLayout.CENTER);
	}
}
