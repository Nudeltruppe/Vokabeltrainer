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
import java.awt.GridLayout;
import javax.swing.SpringLayout;

public class Settings extends JFrame
{

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtNumVoc;
	private JButton btnStart;
	private JButton btnEditor;
	private JButton btnNewButton;

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
		StarlightEventManager.register(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		this.lblNewLabel = new JLabel("Vokabeln zum lernen:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblNewLabel, 92, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, 225, SpringLayout.WEST, contentPane);
		this.contentPane.add(this.lblNewLabel);

		this.btnEditor = new JButton("Vokabel Editor Öffnen");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnEditor, 92, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEditor, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnEditor, 179, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnEditor, 225, SpringLayout.WEST, contentPane);
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

		this.txtNumVoc = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtNumVoc, 5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtNumVoc, 225, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtNumVoc, 92, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtNumVoc, 445, SpringLayout.WEST, contentPane);
		this.txtNumVoc.setText("30");
		this.contentPane.add(this.txtNumVoc);
		this.txtNumVoc.setColumns(10);
		this.contentPane.add(this.btnEditor);

		this.btnStart = new JButton("START!");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnStart, 92, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnStart, 225, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnStart, 179, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnStart, 445, SpringLayout.WEST, contentPane);
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
		this.contentPane.add(this.btnStart);

		btnNewButton = new JButton("Fortschritt");
		this.btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new FortschrittViewer().setVisible(true);
				setVisible(false);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnNewButton, 179, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewButton, 0, SpringLayout.WEST, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewButton, 266, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewButton, 439, SpringLayout.WEST, lblNewLabel);
		contentPane.add(btnNewButton);
	}

	@StarlightEventTarget
	public void onFinishTrain(FinishTrainEvent e)
	{
		setVisible(true);
	}
}
