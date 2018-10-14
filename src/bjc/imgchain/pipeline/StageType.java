package bjc.imgchain.pipeline;

/**
 * The type of stage a given {@link PipelineStage} is.
 * 
 * @author acm
 *
 */
public enum StageType {
	/**
	 * No image -> Image
	 */
	IMGSOURCE,
	/**
	 * Image -> Image
	 */
	IMGTRANS,
	/**
	 * Image -> No image
	 */
	IMGSINK
}
