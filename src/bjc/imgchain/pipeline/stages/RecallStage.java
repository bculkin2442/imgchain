package bjc.imgchain.pipeline.stages;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import bjc.imgchain.ImgChain;
import bjc.imgchain.ImgPicker;
import bjc.imgchain.ImgPickerPanel;
import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.LabeledInputPanel;

public class RecallStage extends AbstractPipelineStage {
	private String stashName;

	public RecallStage() {
		super(StageType.IMGSOURCE);
	}

	@Override
	public Image process(Image inp) {
		return ImgChain.chan.imageRepo.get(stashName);
	}

	@Override
	public String name() {
		return "Recall Image";
	}

	@Override
	public String description() {
		return "Recall image from memory";
	}

	@Override
	public JComponent getEditor() {
		ImgPickerPanel pan = new ImgPickerPanel();

		pan.imgField.addPropertyChangeListener("value", (ev) -> {
			stashName = pan.imgField.field.getText();
		});

		return pan;
	}

}
