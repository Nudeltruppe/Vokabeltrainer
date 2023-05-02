package fachkonzept;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import datenspeicherung.Vokabel;
import gq.glowman554.pipeline.CoputeStep;

public class VokabelPartitionShuffle implements CoputeStep<ArrayList<ArrayList<Vokabel>>, ArrayList<ArrayList<Vokabel>>>
{

	@Override
	public ArrayList<ArrayList<Vokabel>> compute(ArrayList<ArrayList<Vokabel>> input)
	{
		for (ArrayList<Vokabel> partition : input)
		{
			for (int i = 0; i < (partition.size() * 2); i++)
			{
				int p1 = ThreadLocalRandom.current().nextInt(partition.size());
				int p2 = ThreadLocalRandom.current().nextInt(partition.size());

				Vokabel tmp = partition.get(p1);
				partition.set(p1, partition.get(p2));
				partition.set(p2, tmp);

			}
		}

		return input;
	}

}
