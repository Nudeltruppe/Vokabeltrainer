package benutzerschnittstelle;

import javax.swing.JFrame;
import benutzerschnittstelle.components.PieChart;

public class FortschrittViewer extends JFrame
{
	public FortschrittViewer()
	{
		PieChart c = new PieChart(50, 1D);
		getContentPane().add(c);
		setSize(300, 200);
		setVisible(true);

		new Thread(() -> {
			while (true)
			{
				try
				{
					Thread.sleep(50);
					c.curValue += 0.5D;
					repaint();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}