package benutzerschnittstelle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Rectangle;

class Slice {
	double value;
	Color color;

	public Slice(double value, Color color) {
		this.value = value;
		this.color = color;
	}
}

class MyComponent extends JComponent {
	public Slice[] slices;
	public double curValue;
	
	public MyComponent(double percentage_learned, double _curVal) {
		this.curValue = _curVal;
		slices = new Slice[]{new Slice(percentage_learned, Color.GREEN), new Slice(((double)100)-percentage_learned, Color.RED)};
	}

	public void paint(Graphics g) {
		drawPie((Graphics2D) g, getBounds(), slices);
	}

	void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
		double total = 0.0D;

		for (int i = 0; i < slices.length; i++) {
			total += slices[i].value;
		}
		int startAngle = 0;
		for (int i = 0; i < slices.length; i++) {
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (slices[i].value * 360 / total);
			g.setColor(slices[i].color);
			g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
			curValue += slices[i].value;
		}
	}
}

public class FortschrittViewer extends JFrame {
	public FortschrittViewer() {
	}

	public static void main(String[] argv) {
		JFrame frame = new JFrame();
		MyComponent c = new MyComponent(50, 1D);
		frame.getContentPane().add(c);
		frame.setSize(300, 200);
		frame.setVisible(true);
		
		while (true) {
			try {
				Thread.sleep(50);
				c.curValue += 0.5D;
				frame.repaint();;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}