package steuerung;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import benutzerschnittstelle.BenutzerschnittstelleV2;
import benutzerschnittstelle.event.FinishTrainEvent;
import datenspeicherung.Database;
import datenspeicherung.Vokabel;
import fachkonzept.VokabelPartition;
import fachkonzept.VokabelPartitionShuffle;
import fachkonzept.VokabelSchuffle;
import fachkonzept.VokabelSelect;
import gq.glowman554.pipeline.ComputePipe;
import gq.glowman554.starlight.annotations.StarlightEventTarget;

public class Steuerung
{

	private final BenutzerschnittstelleV2 benutzerschnittstelle;
	private ComputePipe<ArrayList<Vokabel>, ArrayList<Vokabel>> vokabel_pipeline = new ComputePipe<>();

	private ArrayList<Vokabel> current;
	private int idx;

	public Steuerung(BenutzerschnittstelleV2 benutzerschnittstelle) throws IOException, SQLException
	{
		this.benutzerschnittstelle = benutzerschnittstelle;

		vokabel_pipeline.addStep(new VokabelPartition());
		vokabel_pipeline.addStep(new VokabelPartitionShuffle());
		vokabel_pipeline.addStep(new VokabelSelect());
		vokabel_pipeline.addStep(new VokabelSchuffle());
	}

	@StarlightEventTarget
	public void onSubmit(Vokabel c, String a) throws IOException
	{
		// TODO implement & fine tune this

		int score = c.getScore();
		if (a.strip().equals(c.getAnswer().strip()))
		{
			score += 1;
			benutzerschnittstelle.onMessage("Richtig!", true);
		}
		else
		{
			score -= 3;
			benutzerschnittstelle.onMessage(c.getAnswer(), false);
		}

		System.out.println("New score " + score);

		try
		{
			Database.getInstance().updateScore(c.getId(), score);
			update();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

	public void fillVokabeln(int batch, String category) throws SQLException, IOException
	{
		current = new ArrayList<>();
		idx = 0;

		while (true)
		{
			ArrayList<Vokabel> voc = Database.getInstance().loadVokabeln(-1000, 1000, category);
			var idk = vokabel_pipeline.compute(voc);

			for (var v : idk)
			{
				System.out.println("Selected " + v.getQuestion() + " to learn with answer " + v.getAnswer());
				current.add(v);
				if (current.size() >= batch)
				{
					return;
				}
			}
		}
	}

	public void update() throws SQLException
	{
		if (idx >= current.size())
		{
			new FinishTrainEvent().call();
		}
		else
		{
			benutzerschnittstelle.onTrain(current.get(idx++));
		}
	}
}
