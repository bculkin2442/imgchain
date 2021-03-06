package bjc.imgchain.pipeline.stages;

import bjc.imgchain.pipeline.PipelineStage;
import bjc.imgchain.pipeline.StageType;

/**
 * Abstract base class for stages.
 * 
 * @author acm
 *
 */
public abstract class AbstractPipelineStage implements PipelineStage {
	private final StageType type;

	/**
	 * Create a new abstract pipeline stage
	 * 
	 * @param type
	 *             The type of this stage.
	 */
	protected AbstractPipelineStage(StageType type) {
		this.type = type;
	}

	@Override
	public StageType getType() {
		return type;
	}

	@Override
	public String description() {
		return name();
	}

	@Override
	public String toString() {
		return name();
	}
}
