package bjc.imgchain.pipeline.stages;

import java.awt.Image;
import java.awt.image.BufferedImage;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.Utils;

public abstract class AbstractPixelStage extends AbstractPipelineStage {

	protected AbstractPixelStage(StageType type) {
		super(type);
	}

	@Override
	public Image process(Image inp) {
		BufferedImage buf = (BufferedImage) inp;

		//BufferedImage res = new BufferedImage(buf.getWidth(), buf.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < buf.getHeight(); y++) {
			for (int x = 0; x < buf.getWidth(); x++) {
				int[] pix = Utils.toARGBQuad(buf.getRGB(x, y));

				int[] processedPixel = processPixel(pix);

				buf.setRGB(x, y, Utils.fromARGBQuad(processedPixel));
			}
		}

		return buf;
	}

	public abstract int[] processPixel(int[] pix);
}
