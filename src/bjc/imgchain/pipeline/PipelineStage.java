package bjc.imgchain.pipeline;

import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A stage in an image processing pipeline.
 * 
 * @author acm
 *
 */
public interface PipelineStage {
	/**
	 * Get the input/output type of this stage.
	 * 
	 * @return The type of the stage.
	 * 
	 */
	StageType getType();

	/**
	 * Pass an image through the stage.
	 * 
	 * @param inp
	 *                The input image, if one is needed
	 * @return The output image, if one is provided.
	 */
	Image process(Image inp);

	/**
	 * Get the name of this stage.
	 * 
	 * @return The name of this stage.
	 */
	String name();

	/**
	 * Get a brief description of what this stage does.
	 * 
	 * @return A brief description of what the stage does.
	 */
	String description();

	/**
	 * Get an editor for this stage.
	 * 
	 * @return The editor for the stage
	 */
	JComponent getEditor();
}
