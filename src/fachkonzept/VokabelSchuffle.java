package fachkonzept;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import datenspeicherung.Vokabel;
import gq.glowman554.pipeline.CoputeStep;

public class VokabelSchuffle implements CoputeStep<ArrayList<Vokabel>, ArrayList<Vokabel>>
{

	@Override
	public ArrayList<Vokabel> compute(ArrayList<Vokabel> input)
	{
		for (int i = 0; i < (input.size() * 2); i++)
		{
			int p1 = ThreadLocalRandom.current().nextInt(input.size());
			int p2 = ThreadLocalRandom.current().nextInt(input.size());

			Vokabel tmp = input.get(p1);
			input.set(p1, input.get(p2));
			input.set(p2, tmp);

		}
		
		return input;
	}

}
