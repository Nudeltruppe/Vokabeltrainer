package datenspeicherung;

import gq.glowman554.starlight.StarlightAutoToString;

public class Vokabel extends StarlightAutoToString
{
	private final int id;
	private final String question;
	private final String answer;
	private final int score;
	
	private final int audio_id;
	private final int image_id;
	
	private final String notes;
	
	public Vokabel(int id, String question, String answer, int score, int audio_id, int image_id, String notes)
	{
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.score = score;
		this.audio_id = audio_id;
		this.image_id = image_id;
		this.notes = notes;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getAudio_id()
	{
		return audio_id;
	}
	
	public int getImage_id()
	{
		return image_id;
	}
	
	public String getNotes()
	{
		return notes;
	}
	
	public double getPercentage()
	{
		return Math.min(Math.max(0.0, llsf(this.score)*2), 100.0);
	}
	
	// linear logarithmic scaling function. caps at 50
	private double llsf(int score) {
		if (score <= 40) {
			return (double)score;
		} else {
			return 3 * Math.log(((double)score)-40.3333) + 41;
		}
	}
}
