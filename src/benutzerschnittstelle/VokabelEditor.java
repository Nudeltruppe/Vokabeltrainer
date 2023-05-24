package benutzerschnittstelle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import datenspeicherung.Database;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VokabelEditor extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtQuestion;
	private JTextField txtAnswer;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnDelete;
	private int selectedId = -1;
	private JComboBox comboBox;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new Runnable() { public void run() { try { VokabelEditor frame = new VokabelEditor(); frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	public VokabelEditor() throws SQLException, IOException
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.txtQuestion = new JTextField();
		this.txtQuestion.setBounds(66, 14, 253, 20);
		this.contentPane.add(this.txtQuestion);
		this.txtQuestion.setColumns(10);

		this.txtAnswer = new JTextField();
		this.txtAnswer.setBounds(66, 45, 253, 20);
		this.contentPane.add(this.txtAnswer);
		this.txtAnswer.setColumns(10);

		this.lblNewLabel = new JLabel("Question");
		this.lblNewLabel.setBounds(10, 14, 46, 14);
		this.contentPane.add(this.lblNewLabel);

		this.lblNewLabel_1 = new JLabel("Answer");
		this.lblNewLabel_1.setBounds(10, 45, 46, 14);
		this.contentPane.add(this.lblNewLabel_1);

		this.btnInsert = new JButton("Insert");
		this.btnInsert.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					// TODO implement
					Database.getInstance().insertVokabel(txtAnswer.getText(), txtQuestion.getText(), getSelectedCategory());
					update();
				}
				catch (SQLException | IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		this.btnInsert.setBounds(335, 13, 89, 23);
		this.contentPane.add(this.btnInsert);

		this.btnUpdate = new JButton("Update");
		this.btnUpdate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Database.getInstance().update(selectedId, txtQuestion.getText(), txtAnswer.getText(), getSelectedCategory());
					update();
				}
				catch (SQLException | IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		this.btnUpdate.setBounds(335, 41, 89, 23);
		this.contentPane.add(this.btnUpdate);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(10, 101, 414, 149);
		this.contentPane.add(this.scrollPane);

		this.table = new JTable();
		/*
		 * this.table.setModel(new DefaultTableModel( new Object[][] {}, new String[] { "Question", "Answer", "Id" } ));
		 */
		this.table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] {"Question", "Answer", "Category", "Id"})
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] {String.class, String.class, String.class, int.class};

			public Class<?> getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] {false, false, false};

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		this.table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				selectedId = (int) table.getValueAt(table.getSelectedRow(), 3);
				String question = (String) table.getValueAt(table.getSelectedRow(), 0);
				String answer = (String) table.getValueAt(table.getSelectedRow(), 1);
				String category = (String) table.getValueAt(table.getSelectedRow(), 2);
				
				((DefaultComboBoxModel) comboBox.getModel()).setSelectedItem(category);

				txtQuestion.setText(question);
				txtAnswer.setText(answer);
			}
		});
		this.scrollPane.setViewportView(this.table);

		this.btnDelete = new JButton("Delete");
		this.btnDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Database.getInstance().delete(selectedId);
					update();
				}
				catch (SQLException | IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		this.btnDelete.setBounds(335, 67, 89, 23);
		this.contentPane.add(this.btnDelete);
		
		this.comboBox = new JComboBox();
		this.comboBox.setModel(new DefaultComboBoxModel(new String[] {}));
		this.comboBox.setBounds(66, 68, 253, 22);
		this.contentPane.add(this.comboBox);
		
		this.lblNewLabel_2 = new JLabel("Category");
		this.lblNewLabel_2.setBounds(10, 71, 46, 14);
		this.contentPane.add(this.lblNewLabel_2);

		update();
	}

	private String getSelectedCategory()
	{
		String selected = (String) ((DefaultComboBoxModel) comboBox.getModel()).getSelectedItem();
		
		if (selected.equals("--new--"))
		{
			return JOptionPane.showInputDialog(this, "New category name");
		}
		return selected;
	}
	
	private void update() throws SQLException, IOException
	{
		while (((DefaultTableModel) this.table.getModel()).getRowCount() != 0)
		{
			((DefaultTableModel) this.table.getModel()).removeRow(0);
		}

		var voc = Database.getInstance().loadVokabeln(-1000, 1000);

		for (int i = 0; i < voc.size(); i++)
		{
			((DefaultTableModel) this.table.getModel()).addRow(new Object[] {voc.get(i).getQuestion(), voc.get(i).getAnswer(),  voc.get(i).getCategory(), voc.get(i).getId()});
		}
		
		((DefaultComboBoxModel) this.comboBox.getModel()).removeAllElements();
		((DefaultComboBoxModel) this.comboBox.getModel()).addElement("--new--");
		for (var c : Database.getInstance().loadCategories())
		{
			((DefaultComboBoxModel) this.comboBox.getModel()).addElement(c);
		}
	}
}
