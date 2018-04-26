package bjc.imgchain.pipeline.stages;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JLabel;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.Utils;

public class GreyscaleStage extends AbstractPixelStage {

	public GreyscaleStage() {
		super(StageType.IMGTRANS);
	}

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
