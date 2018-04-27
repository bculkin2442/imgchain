package bjc.imgchain.pipeline;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import bjc.imgchain.ImgChain;
import bjc.imgchain.utils.Utils;

/**
 * An editable {@link Pipeline}
 * 
 * @author acm
 *
 */
public class MutablePipeline implements Pipeline {
	private final List<PipelineStage> stages;

	private String name;

	/**
	 * Create a new unnamed mutable pipeline.
	 */
	public MutablePipeline() {
		stages = new ArrayList<>();

		name = "Unnamed Pipeline";
	}

	/**
	 * Create a new named mutable pipeline.
	 * 
	 * @param name
	 *                The name of the pipeline.
	 */
	public MutablePipeline(String name) {
		stages = new ArrayList<>();

		this.name = name;
	}

	@Override
	public Image process(Image input) {
		Image proc = input;

		int i = 1;

		for (PipelineStage stage : stages) {
			System.out.println("Applying stage " + stage.name() + "(" + stage.toString() + ")");

			proc = stage.process(proc);

			Utils.displayImage(proc, "Pipeline Results - " + stage.name() + " - #" + i);

			System.out.println("Applied stage " + stage.name());
		}

		return proc;
	}

	@Override
	public List<PipelineStage> stages() {
		return stages;
	}

	@Override
	public String name() {
		return name;
	}

	/**
	 * Set the name of the pipeline.
	 * 
	 * @param nam
	 *                The name of the pipeline.
	 */
	public void name(String nam) {
		name = nam;
	}

	/**
	 * Append a pipeline stage to the end of this pipeline.
	 * 
	 * @param stag
	 *                The stage to add.
	 */
	public void addStage(PipelineStage stag) {
		System.out.println("Adding stage " + stag);
		stages.add(stag);
	}

	/**
	 * Remove a pipeline stage.
	 * 
	 * @param stag
	 *                The stage to remove.
	 */
	public void removeStage(PipelineStage stag) {
		System.out.println("Removing stage " + stag);
		stages.remove(stag);
	}

	/**
	 * Remove a pipeline stage by index.
	 * 
	 * @param idx
	 *                The index of the stage to remove.
	 */
	public void removeStage(int idx) {
		System.out.println("Removing stage # " + idx);
		stages.remove(idx);
		System.out.println("Pipeline contains " + stages.size() + " stages");
	}

}
