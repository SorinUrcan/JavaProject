package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.EmitterModel;
import model.Tax;

public class EmitterView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -11125527705786953L;
	private JPanel contentPane;
	private JTextField txtTaxes;
	private JTable tableTax;
	private JTextField txtId;
	private JTextField txtClient;
	private JTextField txtEmitter;
	private JTextField txtAmount;
	private JTextField txtType;
	private JTextField txtStatus;
	private JTextField txtDescription;
	private JTextField tID;
	private JTextField tClient;
	private JTextField tEmitter;
	private JTextField tAmount;
	private JTextField tType;
	private JTextField tStatus;
	private JTextField tDescription;
	private JTextField txtClients;
	private JTable tableClients;
	private EmitterModel emitterModel;
	private String username;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public EmitterView(EmitterModel emitterModel, String username) {
		this.emitterModel = emitterModel;
		this.username = username;
		
		
		setTitle("Emitter Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 871, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtTaxes = new JTextField();
		txtTaxes.setEditable(false);
		txtTaxes.setText("Taxes:");
		txtTaxes.setBounds(10, 11, 68, 20);
		contentPane.add(txtTaxes);
		txtTaxes.setColumns(10);
		
		tableTax = new JTable();
		tableTax.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value = tableTax.getModel().getValueAt(tableTax.getSelectedRow(), 0).toString();
				Tax tax = (emitterModel.getAllTaxes(value, "")).get(0);
				if (!value.equals(""))
						select(tax);
			}
		});
		tableTax.setBounds(10, 42, 703, 300);
		contentPane.add(tableTax);
		tableTax.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		"id", "client", "emitter", "amount", "type", "status", "description"
	            }
	        ) {
	            @SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
	                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
	            };

	            @SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }
	        });
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("ID:");
		txtId.setBounds(10, 353, 68, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtClient = new JTextField();
		txtClient.setEditable(false);
		txtClient.setText("Client:");
		txtClient.setBounds(88, 353, 68, 20);
		contentPane.add(txtClient);
		txtClient.setColumns(10);
		
		txtEmitter = new JTextField();
		txtEmitter.setEditable(false);
		txtEmitter.setText("Emitter:");
		txtEmitter.setBounds(166, 353, 68, 20);
		contentPane.add(txtEmitter);
		txtEmitter.setColumns(10);
		
		txtAmount = new JTextField();
		txtAmount.setEditable(false);
		txtAmount.setText("Amount:");
		txtAmount.setBounds(244, 353, 68, 20);
		contentPane.add(txtAmount);
		txtAmount.setColumns(10);
		
		txtType = new JTextField();
		txtType.setEditable(false);
		txtType.setText("Type:");
		txtType.setBounds(322, 353, 68, 20);
		contentPane.add(txtType);
		txtType.setColumns(10);
		
		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		txtStatus.setText("Status:");
		txtStatus.setBounds(400, 353, 68, 20);
		contentPane.add(txtStatus);
		txtStatus.setColumns(10);
		
		txtDescription = new JTextField();
		txtDescription.setEditable(false);
		txtDescription.setText("Description:");
		txtDescription.setBounds(478, 353, 235, 20);
		contentPane.add(txtDescription);
		txtDescription.setColumns(10);
		
		tID = new JTextField();
		tID.setEditable(false);
		tID.setBounds(10, 384, 68, 20);
		contentPane.add(tID);
		tID.setColumns(10);
		
		tClient = new JTextField();
		tClient.setEditable(false);
		tClient.setBounds(85, 384, 73, 20);
		contentPane.add(tClient);
		tClient.setColumns(10);
		
		tEmitter = new JTextField();
		tEmitter.setEditable(false);
		tEmitter.setBounds(166, 384, 68, 20);
		contentPane.add(tEmitter);
		tEmitter.setColumns(10);
		
		tAmount = new JTextField();
		tAmount.setBounds(244, 384, 68, 20);
		contentPane.add(tAmount);
		tAmount.setColumns(10);
		
		tType = new JTextField();
		tType.setBounds(322, 384, 68, 20);
		contentPane.add(tType);
		tType.setColumns(10);
		
		tStatus = new JTextField();
		tStatus.setEditable(false);
		tStatus.setBounds(400, 384, 68, 20);
		contentPane.add(tStatus);
		tStatus.setColumns(10);
		
		tDescription = new JTextField();
		tDescription.setBounds(478, 384, 235, 65);
		contentPane.add(tDescription);
		tDescription.setColumns(10);
		
		JButton btnCreateTax = new JButton("Create Tax");
		btnCreateTax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tClient.getText().equals("") || tAmount.getText().equals("") || tType.getText().equals("")) {
					if (tClient.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please select a client from the right panel!");
					if (tAmount.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a valid amount!");
					if (tType.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a tax type!");
				} else {
					Tax newTax = new Tax(tClient.getText(), username, tAmount.getText(), tType.getText(), "unpaid", tDescription.getText());
					emitterModel.createTax(newTax);
					print();
				}
			}
		});
		btnCreateTax.setBounds(10, 415, 109, 23);
		contentPane.add(btnCreateTax);
		
		JButton btnUpdateTax = new JButton("Update Tax");
		btnUpdateTax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tID.getText().equals("") || tAmount.getText().equals("") || tType.getText().equals("")) {
					if (tID.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please select a tax from the above panel!");
					if (tAmount.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a valid amount!");
					if (tType.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a tax type!");
				} else {
					Tax newTax = new Tax(tClient.getText(), username, tAmount.getText(), tType.getText(), tStatus.getText(), tDescription.getText());
					newTax.setId(tID.getText());
					emitterModel.updateTax(newTax);
					print();
				}
			}
		});
		btnUpdateTax.setBounds(129, 415, 109, 23);
		contentPane.add(btnUpdateTax);
		
		JButton btnDeleteTax = new JButton("Delete Tax");
		btnDeleteTax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tID.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please select a tax from the above panel!");
				else {
					Tax newTax = new Tax();
					newTax.setId(tID.getText());
					emitterModel.deleteTax(newTax);
					print();
				}
			}
		});
		btnDeleteTax.setBounds(254, 415, 109, 23);
		contentPane.add(btnDeleteTax);
		
		txtClients = new JTextField();
		txtClients.setEditable(false);
		txtClients.setText("Clients:");
		txtClients.setBounds(724, 11, 86, 20);
		contentPane.add(txtClients);
		txtClients.setColumns(10);
		
		tableClients = new JTable();
		tableClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//String value = tableClients.getModel().getValueAt(tableClients.getSelectedRow(), 0).toString();
				tClient.setText((String) tableClients.getValueAt(tableClients.getSelectedRow(), 0));
			}
		});
		tableClients.setBounds(723, 42, 122, 408);
		contentPane.add(tableClients);
		tableClients.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	                "username"
	            }
	        ) {
	            @SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
	                java.lang.String.class
	            };

	            @SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }
	        });
		
		tEmitter.setText(this.username);
		
		textField = new JTextField();
		textField.setText("Client:");
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(108, 11, 68, 20);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText("Emitter:");
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(207, 11, 68, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setText("Amount:");
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(307, 11, 68, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("Type:");
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(517, 11, 68, 20);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setText("Status:");
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(419, 11, 68, 20);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setText("Description:");
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(614, 11, 99, 20);
		contentPane.add(textField_5);
		print();
	}
	
	public void displayUsers(List<String> users) {
		DefaultTableModel usersTable = (DefaultTableModel) tableClients.getModel();
		usersTable.setRowCount(0);
		for (String user : users) {
			String username = user;
			usersTable.addRow(new Object[] { username });
		}
	}
	
	private void displayTaxes(List<Tax> allTaxes) {
		DefaultTableModel usersTax = (DefaultTableModel) tableTax.getModel();
		usersTax.setRowCount(0);
		for (Tax tax : allTaxes) {
			String id = tax.getId();
			String client = tax.getClient();
			String emitter = tax.getEmitter();
			String amount = tax.getAmount();
			String type = tax.getType();
			String status = tax.getStatus();
			String description = tax.getDescription();
			usersTax.addRow(new Object[] { id, client, emitter, amount, type, status, description });
		}
	}
	
	private void select(Tax tax) {
		tID.setText(tax.getId());
		tClient.setText(tax.getClient());
		tEmitter.setText(tax.getEmitter());
		tType.setText(tax.getType());
		tAmount.setText(tax.getAmount());
		tStatus.setText(tax.getStatus());
		tDescription.setText(tax.getDescription());
	}
	
	public void openWindow() {
		this.setVisible(true);
	}
	
	public void print() {
		displayUsers(emitterModel.getAllClients());
		displayTaxes(emitterModel.getAllTaxes("", username));
	}
}
