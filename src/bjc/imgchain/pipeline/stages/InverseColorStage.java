package bjc.imgchain.pipeline.stages;

import javax.swing.JComponent;
import javax.swing.JLabel;

import bjc.imgchain.pipeline.StageType;

/**
 * Stage which inverts image colors.
 * 
 * @author Ben Culkin
 *
 */
public class InverseColorStage extends AbstractPixelStage {
	/**
	 * Create a new inversion stage.
	 */
	public InverseColorStage() {
		super(StageType.IMGTRANS);
	}

	@Override
	public String name() {
		return "Negative";
	}

	@Override
	public String description() {
		return "Invert image colors";
	}

	@Override
	public JComponent getEditor() {
		return new JLabel("No configuration available");
	}

	@Override
	public int[] processPixel(int[] pix) {
		return new int[] { pix[0], 255 - pix[1], 255 - pix[2], 255 - pix[3] };
	}
}
