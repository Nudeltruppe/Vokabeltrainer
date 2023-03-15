package datenspeicherung;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			try (InputStream inputStream = Database.class.getResourceAsStream("/vokabeln_testdata.sqlite"); OutputStream outputStream = new FileOutputStream(db_path))
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

	public ArrayList<Vokabel> loadVokabeln(int smin, int smax) throws SQLException
	{
		ArrayList<Vokabel> vokabeln = new ArrayList<>();
		
		PreparedStatement statement = connect.prepareStatement("select * from vokabeln where score <= ? and score >= ? order by score");
		statement.setInt(1, smax);
		statement.setInt(2, smin);
		
		ResultSet rs = statement.executeQuery();
		while (rs.next())
		{
			vokabeln.add(new Vokabel(rs.getInt("id"), rs.getString("question"), rs.getString("answer"), rs.getInt("score"), rs.getInt("audio_id"), rs.getInt("image_id"), rs.getString("notes")));
		}
		
		rs.close();
		statement.close();
		
		return vokabeln;
	}

	public void updateScore(int id, int score) throws SQLException
	{
		PreparedStatement statement = connect.prepareStatement("update vokabeln set score = ? where id = ?");

		statement.setInt(1, score);
		statement.setInt(2, id);
		
		statement.executeUpdate();
		statement.close();
	}
}
