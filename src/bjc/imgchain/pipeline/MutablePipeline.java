package bjc.imgchain.pipeline;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

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

		for (PipelineStage stage : stages) {
			System.out.println("Applying stage " + stage.name());
			proc = stage.process(proc);
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
		stages.add(stag);
	}

	/**
	 * Remove a pipeline stage.
	 * 
	 * @param stag
	 *                The stage to remove.
	 */
	public void removeStage(PipelineStage stag) {
		stages.remove(stag);
	}

	/**
	 * Remove a pipeline stage by index.
	 * 
	 * @param idx
	 *                The index of the stage to remove.
	 */
	public void removeStage(int idx) {
		stages.remove(idx);
	}

}
