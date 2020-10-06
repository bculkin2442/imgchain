package bjc.imgchain.pipeline.stages;

import java.awt.Image;

import javax.swing.JComponent;

import bjc.imgchain.ImgChain;
import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.LabeledInputPanel;

/**
 * Stage which stashes an image.
 * 
 * @author Ben Culkin
 *
 */
public class StashStage extends AbstractPipelineStage {
	private String stashName;

	/**
	 * Create a new image-stashing stage.
	 */
	public StashStage() {
		super(StageType.IMGSOURCE);
	}

	@Override
	public Image process(Image inp) {
		ImgChain.chan.addImage(stashName, inp);

		return inp;
	}

	@Override
	public String name() {
		return "Stash Image";
	}

	@Override
	public String description() {
		return "Stash image to memory";
	}

	@Override
	public JComponent getEditor() {
		LabeledInputPanel imgName = new LabeledInputPanel("Image name", "");
		imgName.field.addPropertyChangeListener("value", (ev) -> {
			stashName = imgName.field.getText();
		});

		return imgName;
	}
}
