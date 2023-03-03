package gq.glowman554.pipeline;

public interface PipelineStep<In, Out>
{
	Out compute(In input);
}
