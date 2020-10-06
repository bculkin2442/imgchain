package bjc.imgchain.pipeline.stages;

import javax.swing.JComponent;
import javax.swing.JLabel;

import bjc.imgchain.pipeline.StageType;

/**
 * Transforms an image to a greyscale version.
 * 
 * @author Ben Culkin
 *
 */
public class GreyscaleStage extends AbstractPixelStage {

	/**
	 * Create a new greyscale stage.
	 */
	public GreyscaleStage() {
		super(StageType.IMGTRANS);
	}

	@Override
	public int[] processPixel(int[] pix) {
		int[] ret = new int[4];

		int avg = (pix[1] + pix[2] + pix[3]) / 3;

		ret[0] = pix[0];
		ret[1] = avg;
		ret[2] = avg;
		ret[3] = avg;

		return ret;
	}

	@Override
	public String name() {
		return "Greyscale";
	}

	@Override
	public String description() {
		return "Convert an image into greyscale color";
	}

	@Override
	public JComponent getEditor() {
		return new JLabel("No configuration possible");
	}
}
