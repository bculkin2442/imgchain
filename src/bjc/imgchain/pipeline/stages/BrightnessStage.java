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
		this(1, 0, 0, 0, 1, 0, 0, 0, 1);
	}

	public BrightnessStage(int rr, int rg, int rb, int gr, int gg, int gb,
			int br, int bg, int bb) {
		super(StageType.IMGTRANS);
		
		this.rr = rr;
		this.rg = rg;
		this.rb = rb;
		this.gr = gr;
		this.gg = gg;
		this.gb = gb;
		this.br = br;
		this.bg = bg;
		this.bb = bb;
	}


	private int rr, rg, rb;
	private int gr, gg, gb;
	private int br, bg, bb;

	@Override
	public int[] processPixel(int[] pix) {
		int[] ret = new int[4];

		ret[0] = pix[0];
		ret[1] = (int) ((pix[1] + rr) + (pix[2] + rg) + (pix[3] + rb));
		ret[2] = (int) ((pix[1] + gr) + (pix[2] + gg) + (pix[3] + gb));
		ret[3] = (int) ((pix[1] + br) + (pix[2] + bg) + (pix[3] + bb));

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
		

		LabeledInputPanel rpercRed = new LabeledInputPanel("% Red", rr);
		LabeledInputPanel rpercGreen = new LabeledInputPanel("% Green", rg);
		LabeledInputPanel rpercBlue = new LabeledInputPanel("% Blue", rb);
		rSkew.add(rpercRed);
		rSkew.add(rpercGreen);
		rSkew.add(rpercBlue);

		LabeledInputPanel gpercRed = new LabeledInputPanel("% Red", gr);
		LabeledInputPanel gpercGreen = new LabeledInputPanel("% Green", gg);
		LabeledInputPanel gpercBlue = new LabeledInputPanel("% Blue", gb);
		gSkew.add(gpercRed);
		gSkew.add(gpercGreen);
		gSkew.add(gpercBlue);

		LabeledInputPanel bpercRed = new LabeledInputPanel("% Red", br);
		LabeledInputPanel bpercGreen = new LabeledInputPanel("% Green", bg);
		LabeledInputPanel bpercBlue = new LabeledInputPanel("% Blue", bb);
		bSkew.add(bpercRed);
		bSkew.add(bpercGreen);
		bSkew.add(bpercBlue);

		rpercRed.addPropertyChangeListener("value", (ev) -> {
			rr = (Integer)rpercRed.field.getValue();
		});
		gpercRed.addPropertyChangeListener("value", (ev) -> {
			gr = (Integer)gpercRed.field.getValue();
		});
		bpercRed.addPropertyChangeListener("value", (ev) -> {
			br = (Integer)bpercRed.field.getValue();
		});
		
		rpercGreen.addPropertyChangeListener("value", (ev) -> {
			rg = (Integer)rpercGreen.field.getValue();
		});
		gpercGreen.addPropertyChangeListener("value", (ev) -> {
			gg = (Integer)gpercGreen.field.getValue();
		});
		bpercGreen.addPropertyChangeListener("value", (ev) -> {
			bg = (Integer)bpercGreen.field.getValue();
		});
		
		rpercBlue.addPropertyChangeListener("value", (ev) -> {
			rb = (Integer)rpercBlue.field.getValue();
		});
		gpercBlue.addPropertyChangeListener("value", (ev) -> {
			gb = (Integer)gpercBlue.field.getValue();
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