package fachkonzept;

import java.util.ArrayList;

import datenspeicherung.Vokabel;
import gq.glowman554.pipeline.CoputeStep;

public class VokabelPartition implements CoputeStep<ArrayList<Vokabel>, ArrayList<ArrayList<Vokabel>>>
{
	private final int partitions = 5;

	@Override
	public ArrayList<ArrayList<Vokabel>> compute(ArrayList<Vokabel> input)
	{
		ArrayList<ArrayList<Vokabel>> ret = new ArrayList<>();

		int partition_size = input.size() / partitions;

		for (int i = 0; i < partitions; i++)
		{
			ArrayList<Vokabel> partition = new ArrayList<>();
			for (int j = 0; j < partition_size; j++)
			{
				partition.add(input.get(0));
				input.remove(0);
			}
			ret.add(partition);
		}

		// TODO check if array is actually empty

		return ret;
	}

}
