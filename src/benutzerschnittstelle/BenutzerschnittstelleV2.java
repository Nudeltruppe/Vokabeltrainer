package benutzerschnittstelle;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import benutzerschnittstelle.event.FinishTrainEvent;
import datenspeicherung.Database;
import datenspeicherung.Vokabel;
import gq.glowman554.starlight.StarlightEventManager;
import gq.glowman554.starlight.annotations.StarlightEventTarget;
import steuerung.Steuerung;
import java.awt.Color;

public class BenutzerschnittstelleV2 extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTextField txtAntwort;
	private JButton nextButton;
	private JLabel question, answer;
	private Steuerung steuerung;
	private Vokabel current = null;

	public BenutzerschnittstelleV2(int ammount, String category) throws IOException, SQLException
	{
		Database.getInstance().setLastLearned(category);
		setResizable(false);

		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.setBounds(0, 0, 384, 24);

		JPanel studyPanel = new JPanel(new GridLayout(2, 1));
		studyPanel.setBounds(0, 24, 384, 104);
		JPanel wordPanel = new JPanel(new FlowLayout());
		question = new JLabel();
		wordPanel.add(question);
		JPanel definitionPanel = new JPanel(new FlowLayout());
		txtAntwort = new JTextField(20);
		definitionPanel.add(txtAntwort);
		txtAntwort.setToolTipText("Antwort");
		answer = new JLabel();
		definitionPanel.add(answer);
		studyPanel.add(wordPanel);
		studyPanel.add(definitionPanel);

		JPanel controlPanel = new JPanel(new FlowLayout());
		controlPanel.setBounds(0, 128, 384, 33);
		nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		controlPanel.add(nextButton);
		getContentPane().setLayout(null);

		getContentPane().add(inputPanel);
		JLabel label = new JLabel("Question: ");
		inputPanel.add(label);
		getContentPane().add(studyPanel);
		getContentPane().add(controlPanel);
		
		JButton stopButton = new JButton("Abbruch");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FinishTrainEvent().call();
				dispose();
			}
		});
		controlPanel.add(stopButton);

		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		StarlightEventManager.register(this);

		steuerung = new Steuerung(this);
		steuerung.fillVokabeln(ammount, category);
		steuerung.update();
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			steuerung.onSubmit(current, txtAntwort.getText());
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void onTrain(Vokabel voc)
	{
		current = voc;
		update();
	}

	public void onMessage(String m, boolean known)
	{
		answer.setForeground(known ? Color.GREEN : Color.RED);
		answer.setText(m);
	}

	@StarlightEventTarget
	public void onFinishTrain(FinishTrainEvent e)
	{
		dispose();
	}

	private void update()
	{
		question.setText(current.getQuestion());
		txtAntwort.setText("");
	}
}