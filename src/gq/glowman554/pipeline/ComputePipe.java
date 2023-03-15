package gq.glowman554.pipeline;

import java.util.ArrayList;

public class ComputePipe<In, Out>
{
	private ArrayList<CoputeStep<?, ?>> steps = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public Out compute(In input)
	{
		Object tmp = input;
		for (CoputeStep<?, ?> step : steps)
		{
			tmp = ((CoputeStep<Object, ?>) step).compute(tmp);
		}

		return (Out) tmp;
	}

	public void addStep(CoputeStep<?, ?> step)
	{
		steps.add(step);
	}
}
