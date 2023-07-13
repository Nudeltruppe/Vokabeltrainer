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
import datenspeicherung.Database;
import gq.glowman554.starlight.StarlightEventManager;
import gq.glowman554.starlight.annotations.StarlightEventTarget;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Settings extends JFrame
{

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtNumVoc;
	private JButton btnStart;
	private JButton btnEditor;
	private JButton btnNewButton;
	private JComboBox categorySelection;
	
	// TODO:
	// Als Lerner möchte ich zu Vokabeln Audioaufnahmen verwalten, um mir die Aussprache anzuhören und mit mehreren Sinnen zu lernen.
	// Als Lerner möchte ich zu Vokabeln Abbildungen verwalten, um mit mehreren Sinnen zu lernen.
	// Als Lerner möchte ich Vokabeln suchen und finden, um diese gezielt anzusehen, zu ändern oder zu löschen.
	// Als Lerner möchte ich zu Vokabeln Bemerkungen hinzufügen, um Hinweise zur Verwendung oder Aussprache festzuhalten.
	
	// (x) Als Lerner möchte ich sehen, wann ich das jeweilige Thema / Kapitel zuletzt geübt habe, um schon länger nicht geübte Vokabeln vorrangig zu üben.
	// Als Lerner möchte ich eine Vokabel, die ich gerade nicht gewusst habe, in der aktuellen Runde so oft präsentiert bekommen, bis ich die Vokabel gekonnt habe, um diese am Ende doch zu können.
	// Als Lerner möchte ich zu Vokabeln vorhandene Audioaufnahmen abspielen, um mir die Aussprache anzuhören und mit mehreren Sinnen zu lernen.
	// Als Lerner möchte ich zu Vokabeln vorhandene Abbildungen anzeigen, um mit mehreren Sinnen zu lernen.

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

	public Settings() throws SQLException, IOException
	{
		setResizable(false);
		StarlightEventManager.register(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		contentPane.setLayout(null);

		this.lblNewLabel = new JLabel("Vokabeln zum lernen:");
		lblNewLabel.setBounds(15, 34, 115, 38);
		this.contentPane.add(this.lblNewLabel);

		this.btnEditor = new JButton("Vokabel Editor Öffnen");
		btnEditor.setBounds(10, 135, 183, 87);
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
		txtNumVoc.setBounds(122, 38, 108, 30);
		this.txtNumVoc.setText("30");
		this.contentPane.add(this.txtNumVoc);
		this.txtNumVoc.setColumns(10);
		this.contentPane.add(this.btnEditor);

		this.btnStart = new JButton("START!");
		btnStart.setBounds(262, 10, 145, 87);
		this.btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					new BenutzerschnittstelleV2(Integer.parseInt(txtNumVoc.getText()), (String) ((DefaultComboBoxModel) categorySelection.getModel()).getSelectedItem()).setVisible(true);
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
		btnNewButton.setBounds(203, 135, 220, 87);
		this.btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new FortschrittViewer().setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnNewButton);
		
		this.categorySelection = new JComboBox();
		categorySelection.setBounds(132, 75, 82, 22);
		this.categorySelection.setModel(new DefaultComboBoxModel(new String[] {}));
		this.contentPane.add(this.categorySelection);
		
		for (var c : Database.getInstance().loadCategories())
		{
			((DefaultComboBoxModel) this.categorySelection.getModel()).addElement(c);
		}
	}

	@StarlightEventTarget
	public void onFinishTrain(FinishTrainEvent e)
	{
		setVisible(true);
	}
}
