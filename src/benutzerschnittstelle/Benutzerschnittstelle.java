package benutzerschnittstelle;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import benutzerschnittstelle.event.FinishTrainEvent;
import datenspeicherung.Vokabel;
import gq.glowman554.starlight.StarlightEventManager;
import gq.glowman554.starlight.annotations.StarlightEventTarget;
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
	 * Create the frame.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public Benutzerschnittstelle(int ammount) throws IOException, SQLException
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
				try
				{
					steuerung.onSubmit(current, answerField.getText());
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		this.submitButton.setBounds(323, 159, 89, 23);
		getContentPane().add(this.submitButton);
		
		this.messageLabel = new JLabel("New label");
		this.messageLabel.setBounds(53, 231, 359, 14);
		getContentPane().add(this.messageLabel);
		
		StarlightEventManager.register(this);
		
		steuerung = new Steuerung(this);
		steuerung.fillVokabeln(ammount);
		steuerung.update();
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
	
	@StarlightEventTarget
	public void onFinishTrain(FinishTrainEvent e)
	{
		dispose();
	}
	
	private void update()
	{
		questionLabel.setText(current.getQuestion());
		answerField.setText("");
	}
}
