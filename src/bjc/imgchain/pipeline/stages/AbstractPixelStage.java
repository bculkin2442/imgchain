package bjc.imgchain.pipeline.stages;

import java.awt.Image;
import java.awt.image.BufferedImage;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.Utils;

/**
 * An abstract stage that processes images pixel-by-pixel.
 * 
 * @author bjculkin
 *
 */
public abstract class AbstractPixelStage extends AbstractPipelineStage {

	/**
	 * Create a new abstract pixel stage.
	 * 
	 * @param type
	 *             The type of this stage.
	 */
	protected AbstractPixelStage(StageType type) {
		super(type);
	}

	@Override
	public Image process(Image inp) {
		BufferedImage buf = (BufferedImage) inp;

		BufferedImage res = new BufferedImage(buf.getWidth(), buf.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < buf.getHeight(); y++) {
			for (int x = 0; x < buf.getWidth(); x++) {
				int[] pix = Utils.toARGBQuad(buf.getRGB(x, y));

				int[] processedPixel = processPixel(pix);

				res.setRGB(x, y, Utils.fromARGBQuad(processedPixel));
			}
		}

		return res;
	}

	/**
	 * Process a pixel of data.
	 * 
	 * @param pix
	 *            The pixel, as an array in the form (A, R, G, B)
	 * 
	 * @return The new pixel, as an array in the form (A, R, G, B)
	 */
	public abstract int[] processPixel(int[] pix);
}
