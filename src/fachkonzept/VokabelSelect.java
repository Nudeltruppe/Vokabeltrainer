package fachkonzept;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import datenspeicherung.Vokabel;
import gq.glowman554.pipeline.CoputeStep;

public class VokabelSelect implements CoputeStep<ArrayList<ArrayList<Vokabel>>, ArrayList<Vokabel>>
{
	private double multiplier(double score)
	{
		return Math.min(1, -0.4 * Math.log(score + 5) + 1);
	}

	private double avg_score(ArrayList<Vokabel> voc)
	{
		double score = 0;

		for (Vokabel v : voc)
		{
			score += v.getScore();
		}

		return score / voc.size();
	}

	@Override
	public ArrayList<Vokabel> compute(ArrayList<ArrayList<Vokabel>> input)
	{
		ArrayList<Vokabel> ret = new ArrayList<>();

		for (ArrayList<Vokabel> partition : input)
		{
			double a = avg_score(partition);
			double m = multiplier(a);
			double ammount = Math.max(1, partition.size() * m);
			System.out.println("avg: " + a + ", mul: " + m + ", am: " + ammount);

			for (int i = 0; i < ammount; i++)
			{
				int p = ThreadLocalRandom.current().nextInt(partition.size());
				ret.add(partition.get(p));
			}
		}

		return ret;
	}

}
