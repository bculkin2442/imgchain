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
