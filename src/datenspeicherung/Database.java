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
import java.util.Date;

public class Database extends Thread
{
	private static Database instance = null;

	public static Database getInstance() throws IOException, SQLException
	{
		if (instance == null)
		{
			instance = new Database();
		}
		return instance;
	}

	private String db_path = "vokabeln.sqlit";
	private Connection connect = null;

	private Database() throws IOException, SQLException
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

	public void insertVokabel(String answer, String question, String category) throws SQLException
	{
		insertVokabel(answer, question, category, -1, -1, null);
	}

	public void insertCategory(String category) throws SQLException
	{
		PreparedStatement statement = connect.prepareStatement("insert or ignore into category (category) values (?)");
		statement.setString(1, category);
		statement.executeUpdate();
		statement.close();
	}

	public void insertVokabel(String answer, String question, String category, int audio_id, int image_id, String notes) throws SQLException
	{
		insertCategory(category);
		PreparedStatement statement = connect.prepareStatement("insert into vokabeln (answer, question, category, audio_id, image_id, notes, score) values (?, ?, ?, ?, ?, ?, 0)");

		statement.setString(1, answer);
		statement.setString(2, question);
		statement.setString(3, category);
		statement.setInt(4, audio_id);
		statement.setInt(5, image_id);
		statement.setString(6, notes);

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
			vokabeln.add(new Vokabel(rs.getInt("id"), rs.getString("question"), rs.getString("answer"), rs.getString("category"), rs.getInt("score"), rs.getInt("audio_id"), rs.getInt("image_id"), rs.getString("notes")));
		}

		rs.close();
		statement.close();

		return vokabeln;
	}

	public ArrayList<String> loadCategories() throws SQLException
	{
		ArrayList<String> categories = new ArrayList<>();

		PreparedStatement statement = connect.prepareStatement("select category from category");

		ResultSet rs = statement.executeQuery();
		while (rs.next())
		{
			categories.add(rs.getString("category"));
		}

		rs.close();
		statement.close();

		return categories;
	}

	public void updateScore(int id, int score) throws SQLException
	{
		PreparedStatement statement = connect.prepareStatement("update vokabeln set score = ? where id = ?");

		statement.setInt(1, score);
		statement.setInt(2, id);

		statement.executeUpdate();
		statement.close();
	}

	public void update(int id, String question, String answer, String category) throws SQLException
	{
		insertCategory(category);
		PreparedStatement statement = connect.prepareStatement("update vokabeln set question = ?, answer = ?, category = ? where id = ?");

		statement.setString(1, question);
		statement.setString(2, answer);
		statement.setString(3, category);
		statement.setInt(4, id);

		statement.executeUpdate();
		statement.close();
	}

	public void delete(int id) throws SQLException
	{
		PreparedStatement statement = connect.prepareStatement("delete from vokabeln where id = ?");

		statement.setInt(1, id);

		statement.executeUpdate();
		statement.close();
	}

	public ArrayList<Vokabel> loadVokabeln(int smin, int smax, String category) throws SQLException
	{
		ArrayList<Vokabel> vokabeln = new ArrayList<>();

		PreparedStatement statement = connect.prepareStatement("select * from vokabeln where score <= ? and score >= ? and category = ? order by score");
		statement.setInt(1, smax);
		statement.setInt(2, smin);
		statement.setString(3, category);

		ResultSet rs = statement.executeQuery();
		while (rs.next())
		{
			vokabeln.add(new Vokabel(rs.getInt("id"), rs.getString("question"), rs.getString("answer"), rs.getString("category"), rs.getInt("score"), rs.getInt("audio_id"), rs.getInt("image_id"), rs.getString("notes")));
		}

		rs.close();
		statement.close();

		return vokabeln;
	}

	public Date lastLearned(String category) throws SQLException
	{
		PreparedStatement statement = connect.prepareStatement("select last_learned from category where category = ?");
		statement.setString(1, category);
		ResultSet rs = statement.executeQuery();
		rs.next();
		Date ret = rs.getDate("last_learned");
		rs.close();
		statement.close();
		return ret;
	}

	public void setLastLearned(String category) throws SQLException
	{
		PreparedStatement statement = connect.prepareStatement("update category set last_learned = datetime('now') where category = ?");
		statement.setString(1, category);
		statement.executeUpdate();
		statement.close();
	}
}
