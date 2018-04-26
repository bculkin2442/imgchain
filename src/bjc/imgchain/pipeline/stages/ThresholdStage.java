package bjc.imgchain.pipeline.stages;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.LabeledInputPanel;

public class ThresholdStage extends AbstractPixelStage {
	public ThresholdStage() {
		this(0, 0, 0);
	}

	public ThresholdStage(int rr, int gg, int bb) {
		super(StageType.IMGTRANS);

		this.redThreshold = rr;
		this.greenThreshold = gg;
		this.blueThreshold = bb;
	}

	private int redThreshold;
	private int greenThreshold;
	private int blueThreshold;

	@Override
	public int[] processPixel(int[] pix) {
		int[] ret = new int[4];

		ret[0] = pix[0];
		ret[1] = pix[1] > redThreshold ? 255 : 0;
		ret[2] = pix[2] > greenThreshold ? 255 : 0;
		ret[3] = pix[3] > blueThreshold ? 255 : 0;

		ret[1] = Math.max(0, Math.min(255, ret[1]));
		ret[2] = Math.max(0, Math.min(255, ret[2]));
		ret[3] = Math.max(0, Math.min(255, ret[3]));

		return ret;
	}

	@Override
	public String name() {
		return "Tint";
	}

	@Override
	public String description() {
		return "Add/remove colors";
	}

	@Override
	public JComponent getEditor() {
		JPanel holder = new JPanel();
		holder.setLayout(new GridLayout(3, 1));

		JPanel rSkew = new JPanel();
		rSkew.setLayout(new GridLayout(1, 3));
		rSkew.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Red Threshold"));

		JPanel gSkew = new JPanel();
		gSkew.setLayout(new GridLayout(1, 3));
		gSkew.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Green Threshold"));

		JPanel bSkew = new JPanel();
		bSkew.setLayout(new GridLayout(1, 3));
		bSkew.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Blue Threshold"));

		LabeledInputPanel rpercRed = new LabeledInputPanel("Red", redThreshold);
		rSkew.add(rpercRed);
		;

		LabeledInputPanel gpercGreen = new LabeledInputPanel("Green", greenThreshold);
		gSkew.add(gpercGreen);

		LabeledInputPanel bpercBlue = new LabeledInputPanel("Blue", blueThreshold);
		bSkew.add(bpercBlue);

		rpercRed.addPropertyChangeListener("value", (ev) -> {
			redThreshold = (Integer) rpercRed.field.getValue();
		});

		gpercGreen.addPropertyChangeListener("value", (ev) -> {
			greenThreshold = (Integer) gpercGreen.field.getValue();
		});

		bpercBlue.addPropertyChangeListener("value", (ev) -> {
			blueThreshold = (Integer) bpercBlue.field.getValue();
		});

		holder.add(rSkew);
		holder.add(gSkew);
		holder.add(bSkew);

		return holder;
	}

}