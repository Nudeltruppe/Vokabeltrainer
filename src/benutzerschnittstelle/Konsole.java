package benutzerschnittstelle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import datenspeicherung.Database;
import datenspeicherung.Vokabel;
import fachkonzept.VokabelPartition;
import fachkonzept.VokabelPartitionShuffle;
import fachkonzept.VokabelSchuffle;
import fachkonzept.VokabelSelect;
import gq.glowman554.pipeline.PipelineManager;
import gq.glowman554.pipeline.PipelineStep;

public class Konsole
{
	public static void main(String[] args) throws IOException, SQLException
	{
		Database db = new Database();
		// db.insertVokabel("test", "test123");
		/*
		ArrayList<Vokabel> vokabeln = db.loadVokabeln(-1, 1);
		for (Vokabel v: vokabeln)
		{
			System.out.println(v);
		}
		*/
		
		PipelineManager<ArrayList<Vokabel>, ArrayList<Vokabel>> vokabel_pipeline = new PipelineManager<>();

		vokabel_pipeline.addStep(new VokabelPartition());
		vokabel_pipeline.addStep(new VokabelPartitionShuffle());
		vokabel_pipeline.addStep(new VokabelSelect());
		vokabel_pipeline.addStep(new VokabelSchuffle());
		
		ArrayList<Vokabel> voc = db.loadVokabeln(-1000, 1000);
		
		for (Vokabel v : vokabel_pipeline.compute(voc))
		{
			System.out.println(v);
			System.out.println(v.getPercentage());
		}
		
		Vokabel vc = new Vokabel(0, null, null, 50, 0, 0, null);
		System.out.println(vc.getPercentage());
	}
}
