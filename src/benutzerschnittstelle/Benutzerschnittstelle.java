package benutzerschnittstelle;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import datenspeicherung.Vokabel;
import steuerung.Steuerung;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Benutzerschnittstelle extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Vokabel current = null;
	
	private Steuerung steuerung;
	private JLabel questionLabel;
	private JTextField answerField;
	private JButton submitButton;
	private JLabel messageLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Benutzerschnittstelle frame = new Benutzerschnittstelle();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public Benutzerschnittstelle() throws IOException, SQLException
	{
		setTitle("Vokabeltrainer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(506, 328);

		// Fenster anzeigen
		setVisible(true);

		getContentPane().setLayout(null);
		
		this.questionLabel = new JLabel("New label");
		this.questionLabel.setBounds(33, 25, 368, 14);
		getContentPane().add(this.questionLabel);
		
		this.answerField = new JTextField();
		this.answerField.setBounds(99, 95, 86, 20);
		getContentPane().add(this.answerField);
		this.answerField.setColumns(10);
		
		this.submitButton = new JButton("New button");
		this.submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				steuerung.onSubmit(current, answerField.getText());
				
			}
		});
		this.submitButton.setBounds(323, 159, 89, 23);
		getContentPane().add(this.submitButton);
		
		this.messageLabel = new JLabel("New label");
		this.messageLabel.setBounds(53, 231, 359, 14);
		getContentPane().add(this.messageLabel);
		
		steuerung = new Steuerung(this);
	}

	public void onTrain(Vokabel voc)
	{
		current = voc;
		update();
	}
	
	public void onMessage(String m)
	{
		messageLabel.setText(m);
	}
	
	private void update()
	{
		questionLabel.setText(current.getQuestion());
		answerField.setText("");
	}
}
