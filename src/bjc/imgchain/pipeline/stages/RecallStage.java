package bjc.imgchain.pipeline.stages;

import java.awt.Image;

import javax.swing.JComponent;

import bjc.imgchain.ImgChain;
import bjc.imgchain.ImgPickerPanel;
import bjc.imgchain.pipeline.StageType;

/**
 * Stage which recalls an image from memory.
 * @author Ben Culkin
 *
 */
public class RecallStage extends AbstractPipelineStage {
	private String stashName;

	/**
	 * Create a new recall stage.
	 */
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

		pan.imgField.field.addPropertyChangeListener("value", (ev) -> {
			stashName = pan.imgField.field.getText();
		});

		return pan;
	}
}
