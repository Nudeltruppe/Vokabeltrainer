package datenspeicherung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.FileUtils;

public class Database extends Thread
{
	private String db_path = "vokabeln.sqlit";
	private Connection connect = null;

	public Database() throws IOException, SQLException
	{
		createIfNecesary();

		connect = DriverManager.getConnection(String.format("jdbc:sqlite:%s", db_path));

		Runtime.getRuntime().addShutdownHook(this);
	}

	@Override
	public void run()
	{
		System.out.println("Closing database.");
		try
		{
			connect.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void createIfNecesary() throws IOException
	{
		if (!new File(db_path).exists())
		{
			try (InputStream inputStream = Database.class.getResourceAsStream("/vokabeln.sqlite"); OutputStream outputStream = new FileOutputStream(db_path))
			{

				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0)
				{
					outputStream.write(buffer, 0, length);
				}

				System.out.println("File copied successfully!");

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			System.out.println("Wrote " + db_path + ".");
		}
	}

	public void insertVokabel(String answer, String question) throws SQLException
	{
		insertVokabel(answer, question, -1, -1, null);
	}

	public void insertVokabel(String answer, String question, int audio_id, int image_id, String notes) throws SQLException
	{
		PreparedStatement statement = connect.prepareStatement("insert into vokabeln (answer, question, audio_id, image_id, notes, score) values (?, ?, ?, ?, ?, 0)");

		statement.setString(1, answer);
		statement.setString(2, question);
		statement.setInt(3, audio_id);
		statement.setInt(4, image_id);
		statement.setString(5, notes);

		statement.executeUpdate();
		statement.close();
	}
}
