package benutzerschnittstelle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JButton;


public class guiedit extends JFrame implements ActionListener{
	private JTextField textField;
	public guiedit() {
		getContentPane().setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 127, 684, 243);
		getContentPane().add(list);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(677, 127, 17, 243);
		getContentPane().add(scrollBar);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 419, 32);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton AddButton = new JButton("Add");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		AddButton.setBounds(10, 93, 89, 23);
		getContentPane().add(AddButton);
		
		JButton DeleteButton = new JButton("Delete");
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		DeleteButton.setBounds(140, 93, 89, 23);
		getContentPane().add(DeleteButton);
		
		JButton EditButton = new JButton("Edit");
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		EditButton.setBounds(277, 93, 89, 23);
		getContentPane().add(EditButton);
		
		JButton CategoryButton = new JButton("Categories");
		CategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		CategoryButton.setBounds(483, 16, 184, 86);
		getContentPane().add(CategoryButton);
	}

	public static void main(String[] args) {
		new guiedit().setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		
	}
}
		


