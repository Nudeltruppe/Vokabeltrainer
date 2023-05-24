package benutzerschnittstelle;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import benutzerschnittstelle.event.FinishTrainEvent;
import gq.glowman554.starlight.StarlightEventManager;
import gq.glowman554.starlight.annotations.StarlightEventTarget;

public class Settings extends JFrame
{

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtNumVoc;
	private JButton btnStart;
	private JButton btnEditor;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Settings frame = new Settings();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Settings()
	{
		setResizable(false);
		StarlightEventManager.register(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.lblNewLabel = new JLabel("Vokabeln zum lernen:");
		this.lblNewLabel.setBounds(10, 11, 146, 14);
		this.contentPane.add(this.lblNewLabel);

		this.txtNumVoc = new JTextField();
		this.txtNumVoc.setText("30");
		this.txtNumVoc.setBounds(153, 8, 86, 20);
		this.contentPane.add(this.txtNumVoc);
		this.txtNumVoc.setColumns(10);

		this.btnStart = new JButton("START!");
		this.btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					new BenutzerschnittstelleV2(Integer.parseInt(txtNumVoc.getText())).setVisible(true);
					setVisible(false);
				}
				catch (NumberFormatException | IOException | SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		this.btnStart.setBounds(10, 36, 414, 214);
		this.contentPane.add(this.btnStart);

		this.btnEditor = new JButton("Vokabel Editor Öffnen");
		this.btnEditor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					new VokabelEditor().setVisible(true);
					setVisible(false);
				}
				catch (SQLException | IOException e1)
				{
					e1.printStackTrace();
				}

			}
		});
		this.btnEditor.setBounds(249, 7, 175, 23);
		this.contentPane.add(this.btnEditor);
	}

	@StarlightEventTarget
	public void onFinishTrain(FinishTrainEvent e)
	{
		setVisible(true);
	}
}
