package benutzerschnittstelle.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class PieChart extends JComponent
{
	public Slice[] slices;
	public double curValue;

	public PieChart(double percentage_learned, double _curVal)
	{
		this.curValue = _curVal;
		slices = new Slice[] {new Slice(percentage_learned, Color.GREEN), new Slice(((double) 100) - percentage_learned, Color.RED)};
	}

	public void paint(Graphics g)
	{
		drawPie((Graphics2D) g, getBounds(), slices);
	}

	void drawPie(Graphics2D g, Rectangle area, Slice[] slices)
	{
		double total = 0.0D;

		for (int i = 0; i < slices.length; i++)
		{
			total += slices[i].value;
		}
		int startAngle = 0;
		for (int i = 0; i < slices.length; i++)
		{
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (slices[i].value * 360 / total);
			g.setColor(slices[i].color);
			g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
			curValue += slices[i].value;
		}
	}

	public static class Slice
	{
		double value;
		Color color;

		public Slice(double value, Color color)
		{
			this.value = value;
			this.color = color;
		}
	}
}
