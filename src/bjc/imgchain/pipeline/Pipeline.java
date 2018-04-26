package bjc.imgchain.pipeline;

import java.awt.Image;
import java.util.List;

/**
 * Represents a pipeline for processing images.
 * 
 * @author acm
 *
 */
public interface Pipeline {
	/**
	 * Process an image using the stages.
	 * 
	 * @param input
	 *                The input image, or null if no image is input.
	 * @return The output image, or null if no image is output.
	 */
	Image process(Image input);

	/**
	 * Get the stages of the pipeline.
	 * 
	 * @return The stages of the pipeline.
	 */
	List<PipelineStage> stages();

	/**
	 * Get the name of the pipeline.
	 * 
	 * @return The name of the pipeline.
	 */
	default String name() {
		return "Unnamed Pipeline";
	}
}
