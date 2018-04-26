package bjc.imgchain.pipeline.stages;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.LabeledInputPanel;

public class BrightnessStage extends AbstractPixelStage {
	public BrightnessStage() {
		this(0, 0, 0);
	}

	public BrightnessStage(int rr, int gg, int bb) {
		super(StageType.IMGTRANS);
		
		this.rr = rr;
		this.gg = gg;
		this.bb = bb;
	}


	private int rr;
	private int gg;
	private int bb;

	@Override
	public int[] processPixel(int[] pix) {
		int[] ret = new int[4];

		ret[0] = pix[0];
		ret[1] = pix[1] + rr;
		ret[2] = pix[2] + gg;
		ret[3] = pix[3] + bb;
		
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
		rSkew.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Red Balance"));
		
		JPanel gSkew = new JPanel();
		gSkew.setLayout(new GridLayout(1, 3));
		gSkew.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Green Balance"));
		
		JPanel bSkew = new JPanel();
		bSkew.setLayout(new GridLayout(1, 3));
		bSkew.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Blue Balance"));
		

		LabeledInputPanel rpercRed = new LabeledInputPanel("+/- Red", rr);
		rSkew.add(rpercRed);;

		LabeledInputPanel gpercGreen = new LabeledInputPanel("+/- Green", gg);
		gSkew.add(gpercGreen);

		LabeledInputPanel bpercBlue = new LabeledInputPanel("+/- Blue", bb);
		bSkew.add(bpercBlue);

		rpercRed.addPropertyChangeListener("value", (ev) -> {
			rr = (Integer)rpercRed.field.getValue();
		});
		
		gpercGreen.addPropertyChangeListener("value", (ev) -> {
			gg = (Integer)gpercGreen.field.getValue();
		});
		
		bpercBlue.addPropertyChangeListener("value", (ev) -> {
			bb = (Integer)bpercBlue.field.getValue();
		});
		
		holder.add(rSkew);
		holder.add(gSkew);
		holder.add(bSkew);
		
		return holder;
	}

}