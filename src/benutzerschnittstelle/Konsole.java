package benutzerschnittstelle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import datenspeicherung.Database;
import datenspeicherung.Vokabel;

public class Konsole
{
	public static void main(String[] args) throws IOException, SQLException
	{
		Database db = new Database();
		// db.insertVokabel("test", "test123");
		
		ArrayList<Vokabel> vokabeln = db.loadVokabeln(-1, 1);
		for (Vokabel v: vokabeln)
		{
			System.out.println(v);
		}
	}
}
