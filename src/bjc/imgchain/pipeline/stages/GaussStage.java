package bjc.imgchain.pipeline.stages;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.JComponent;
import javax.swing.JPanel;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.LabeledInputPanel;

public class GaussStage extends AbstractPipelineStage {
	private int	m;
	private double	sig, k;

	public GaussStage() {
		super(StageType.IMGTRANS);
	}

	@Override
	public Image process(Image inp) {
		BufferedImage buf = (BufferedImage) inp;

		ConvolveOp cop = new ConvolveOp(genKern());

		BufferedImage ret = cop.createCompatibleDestImage(buf, null);

		cop.filter(buf, ret);

		return ret;
	}

	private Kernel genKern() {
		float[][] w = new float[m][m];

		float sum = 0.0f;

		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < m - 1; j++) {
				double s = i - (m / 2.0);
				double t = j - (m / 2.0);

				double r2 = (s * s) + (t * t);

				double frac = r2 / (2.0 * sig * sig);

				double val = k * Math.exp(-frac);

				int idx1 = m - i;
				int idx2 = m - j;

				float fval = (float) val;

				w[idx1 - 1][idx2 - 1] = fval;
				sum += fval;
			}
		}

		float invsum = 1 / sum;

		float[] dat = new float[m * m];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				dat[(i * m) + j] = w[i][j] * sum;
			}
		}

		return new Kernel(m, m, dat);
	}

	@Override
	public String name() {
		return "Gaussian";
	}

	@Override
	public String description() {
		return "Perform a gaussian blur";
	}

	@Override
	public JComponent getEditor() {
		JPanel holder = new JPanel();
		holder.setLayout(new GridLayout(3, 1));

		LabeledInputPanel mField = new LabeledInputPanel("Size of kernel", 3);
		mField.field.addPropertyChangeListener("value", (ev) -> {
			m = (Integer) mField.field.getValue();
		});
		LabeledInputPanel sigField = new LabeledInputPanel("Value for sigma", 3.0);
		sigField.field.addPropertyChangeListener("value", (ev) -> {
			sig = (Double) sigField.field.getValue();
		});
		LabeledInputPanel kField = new LabeledInputPanel("Value for k", 1.0);
		kField.field.addPropertyChangeListener("value", (ev) -> {
			k = (Double) kField.field.getValue();
		});

		holder.add(mField);
		holder.add(sigField);
		holder.add(kField);

		return holder;
	}
}
