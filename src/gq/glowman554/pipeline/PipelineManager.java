package gq.glowman554.pipeline;

import java.util.ArrayList;

public class PipelineManager<In, Out>
{
	private ArrayList<PipelineStep<?, ?>> steps = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public Out compute(In input)
	{
		Object tmp = input;
		for (PipelineStep<?, ?> step : steps)
		{
			tmp = ((PipelineStep<Object, ?>) step).compute(tmp);
		}

		return (Out) tmp;
	}

	public void addStep(PipelineStep<?, ?> step)
	{
		steps.add(step);
	}
}
