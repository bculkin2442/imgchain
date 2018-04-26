package bjc.imgchain.pipeline.stages;

import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JLabel;

import bjc.imgchain.pipeline.StageType;

/**
 * A pipeline stage that does nothing.
 * 
 * @author acm
 *
 */
public class IDStage extends AbstractPipelineStage {

	/**
	 * Create a new identity stage.
	 */
	public IDStage() {
		super(StageType.IMGTRANS);
	}

	@Override
	public String name() {
		return "Identity";
	}

	@Override
	public Image process(Image inp) {
		return inp;
	}

	@Override
	public String description() {
		return "Passes an image straight through.";
	}

	@Override
	public JComponent getEditor() {
		return new JLabel("Nothing to edit");
	}
}
