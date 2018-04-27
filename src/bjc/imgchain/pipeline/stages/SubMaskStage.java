package bjc.imgchain.pipeline.stages;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import bjc.imgchain.ImgChain;
import bjc.imgchain.ImgPickerPanel;
import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.Utils;

public class SubMaskStage extends AbstractPipelineStage {
	private String masqueName;

	public SubMaskStage() {
		super(StageType.IMGTRANS);
	}

	@Override
	public Image process(Image inp) {
		BufferedImage buf = (BufferedImage) inp;
		BufferedImage masque = (BufferedImage) ImgChain.chan.imageRepo.get(masqueName);

		for (int y = 0; y < buf.getHeight(); y++) {
			for (int x = 0; x < buf.getWidth(); x++) {
				int[] pix = Utils.toARGBQuad(buf.getRGB(x, y));
				int[] msq = Utils.toARGBQuad(masque.getRGB(x, y));

				pix[1] -= msq[1];
				pix[2] -= msq[2];
				pix[3] -= msq[3];

				buf.setRGB(x, y, Utils.fromARGBQuad(pix));
			}
		}

		return buf;
	}

	@Override
	public String name() {
		return "Subtractive Mask";
	}

	@Override
	public String description() {
		return "Subtract an image from this one";
	}

	@Override
	public JComponent getEditor() {
		ImgPickerPanel pan = new ImgPickerPanel("Mask name");

		pan.imgField.field.addPropertyChangeListener("value", (ev) -> {
			masqueName = pan.imgField.field.getText();
		});

		return pan;
	}	
}
