package bjc.imgchain.pipeline.stages;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import bjc.imgchain.pipeline.StageType;
import bjc.imgchain.utils.LabeledInputPanel;

public class ColorSkewStage extends AbstractPixelStage {
	public ColorSkewStage() {
		this(1, 0, 0, 0, 1, 0, 0, 0, 1);
	}

	public ColorSkewStage(double rr, double rg, double rb, double gr, double gg, double gb, double br, double bg,
			double bb) {
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

	private double rr, rg, rb;
	private double gr, gg, gb;
	private double br, bg, bb;

	@Override
	public int[] processPixel(int[] pix) {
		int[] ret = new int[4];

		ret[0] = pix[0];
		ret[1] = (int) ((pix[1] * rr) + (pix[2] * rg) + (pix[3] * rb));
		ret[2] = (int) ((pix[1] * gr) + (pix[2] * gg) + (pix[3] * gb));
		ret[3] = (int) ((pix[1] * br) + (pix[2] * bg) + (pix[3] * bb));

		ret[1] = Math.max(Math.min(255, ret[1]), 0);
		ret[2] = Math.max(Math.min(255, ret[2]), 0);
		ret[3] = Math.max(Math.min(255, ret[3]), 0);

		return ret;
	}

	@Override
	public String name() {
		return "Color Skew";
	}

	@Override
	public String description() {
		return "Adjust color balance";
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

		rpercRed.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			rr = (Double) rpercRed.field.getValue();
		});
		gpercRed.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			gr = (Double) gpercRed.field.getValue();
		});
		bpercRed.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			br = (Double) bpercRed.field.getValue();
		});

		rpercGreen.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			rg = (Double) rpercGreen.field.getValue();
		});
		gpercGreen.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			gg = (Double) gpercGreen.field.getValue();
		});
		bpercGreen.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			bg = (Double) bpercGreen.field.getValue();
		});

		rpercBlue.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			rb = (Double) rpercBlue.field.getValue();
		});
		gpercBlue.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			gb = (Double) gpercBlue.field.getValue();
		});
		bpercBlue.field.addPropertyChangeListener("value", (ev) -> {
			System.out.println("TRIGGERED rr");
			bb = (Double) bpercBlue.field.getValue();
		});

		holder.add(rSkew);
		holder.add(gSkew);
		holder.add(bSkew);

		return holder;
	}
}
