package benutzerschnittstelle;

import javax.swing.JFrame;
import javax.swing.JLabel;

import benutzerschnittstelle.components.PieChart;
import datenspeicherung.Database;
import fachkonzept.VokabelSelect;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SpringLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;

public class FortschrittViewer extends JFrame
{
	public FortschrittViewer()
	{
		int num_vokabeln;
		double percentage_learned = 0;
		try
		{
			num_vokabeln = Database.getInstance().loadVokabeln(-10000, 10000).size();

			double sum = 0;
			for (var i : Database.getInstance().loadVokabeln(-10000, 10000))
			{
				sum += (double) i.getPercentage();
			}

			percentage_learned = sum / num_vokabeln;
		}
		catch (SQLException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// double x = 0;
		// PieChart c = new PieChart(x, 100-x);
		// getContentPane().add(c);

		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JLabel txtpnKategorieAuswhlen = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, txtpnKategorieAuswhlen, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtpnKategorieAuswhlen, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtpnKategorieAuswhlen, 57, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtpnKategorieAuswhlen, 300, SpringLayout.WEST, getContentPane());
		txtpnKategorieAuswhlen.setText("Kategorie Auswählen");
		getContentPane().add(txtpnKategorieAuswhlen);

		JComboBox comboBox = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 57, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, comboBox, 114, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comboBox, 300, SpringLayout.WEST, getContentPane());
		getContentPane().add(comboBox);

		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, 114, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, 171, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, 300, SpringLayout.WEST, getContentPane());
		getContentPane().add(progressBar);

		((DefaultComboBoxModel) comboBox.getModel()).addElement("All Categories");
		progressBar.setMinimum(0);
		progressBar.setMaximum(10000);
		progressBar.setValue((int) (percentage_learned * 100));
		progressBar.setIndeterminate(false);

		JLabel txtLastLearned = new JLabel("");
		getContentPane().add(txtLastLearned);

		try
		{
			for (var c : Database.getInstance().loadCategories())
			{
				((DefaultComboBoxModel) comboBox.getModel()).addElement(c);
			}
		}
		catch (SQLException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		comboBox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				txtLastLearned.setText("");

				if (comboBox.getSelectedIndex() == 0)
				{
					int num_vokabeln;
					double percentage_learned = 0;
					try
					{
						num_vokabeln = Database.getInstance().loadVokabeln(-10000, 10000).size();

						double sum = 0;
						for (var i : Database.getInstance().loadVokabeln(-10000, 10000))
						{
							sum += (double) i.getPercentage();
						}

						percentage_learned = sum / num_vokabeln;
						percentage_learned *= 100;
						System.out.printf("%f\n", percentage_learned);
						progressBar.setValue((int) percentage_learned);
						System.out.println(progressBar.getValue());
						System.out.println(progressBar.getMaximum());
						System.out.println(progressBar.getMinimum());
						return;
					}
					catch (SQLException | IOException e1)
					{
						e1.printStackTrace();
					}
				}

				try
				{
					int num = Database.getInstance().loadVokabeln(-10000, 10000, comboBox.getSelectedItem().toString()).size();
					double sum = 0;
					for (var i : Database.getInstance().loadVokabeln(-10000, 10000, comboBox.getSelectedItem().toString()))
					{
						sum += (double) i.getPercentage();
					}

					progressBar.setValue(((int) ((sum / num) * 100)));

					try
					{
						txtLastLearned.setText("Zuletz gelernt: " + Database.getInstance().lastLearned(comboBox.getSelectedItem().toString()).toLocaleString());
					}
					catch (Exception e1)
					{
						txtLastLearned.setText("Zuletz gelernt: Niemals");

					}
				}
				catch (SQLException | IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		setVisible(true);
	}
}