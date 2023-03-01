package benutzerschnittstelle;

import java.io.IOException;
import java.sql.SQLException;

import datenspeicherung.Database;

public class Konsole
{
	public static void main(String[] args) throws IOException, SQLException
	{
		Database db = new Database();
		db.insertVokabel("test", "test123");
	}
}
