package gq.glowman554.pipeline;

public interface CoputeStep<In, Out>
{
	Out compute(In input);
}
