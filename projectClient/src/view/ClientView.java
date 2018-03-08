package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.ClientModel;
import model.ClientSocket;
import model.Tax;
import model.User;
import net.Command;
import net.CommandType;
import net.Response;

public class ClientView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1986012756604020175L;
	private JPanel contentPane;
	private JTextField txtUserDetails;
	private JTextField txtUsername;
	private JTextField tUsername = new JTextField();
	private JTextField txtPassword;
	private JTextField tPassword = new JTextField();
	private JTextField txtType;
	private JTextField tType = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTextField tEmail = new JTextField();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private ClientModel clientModel;
	private User currentUser = new User();
	private JTextField tCode;
	private JButton btnActivateEmail;
	private JTextField txtCode;
	private String currentUsername;
	private JTextField txtTaxes;
	JTable tTaxes = new JTable();
	private String selectedTaxId = null;


	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public ClientView(ClientModel clientModel, String username) {
		this.clientModel = clientModel;
		this.currentUsername = username;
		
		setTitle("Client Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUserDetails = new JTextField();
		txtUserDetails.setEditable(false);
		txtUserDetails.setText("User details:");
		txtUserDetails.setBounds(10, 11, 86, 20);
		contentPane.add(txtUserDetails);
		txtUserDetails.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setText("Username:");
		txtUsername.setBounds(10, 42, 86, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		tUsername.setEditable(false);
		tUsername.setBounds(106, 42, 164, 20);
		contentPane.add(tUsername);
		tUsername.setColumns(10);
		tUsername.setText(currentUsername);
		
		txtPassword = new JTextField();
		txtPassword.setEditable(false);
		txtPassword.setText("Password:");
		txtPassword.setBounds(10, 73, 86, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		tPassword.setBounds(106, 73, 164, 20);
		contentPane.add(tPassword);
		tPassword.setColumns(10);
		
		txtType = new JTextField();
		txtType.setEditable(false);
		txtType.setText("Type:");
		txtType.setBounds(10, 102, 86, 20);
		contentPane.add(txtType);
		txtType.setColumns(10);
		
		tType.setEditable(false);
		tType.setBounds(106, 104, 164, 20);
		contentPane.add(tType);
		tType.setColumns(10);
		
		JButton btnChangetype = new JButton("ChangeType");
		btnChangetype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnChangetype.setBounds(280, 101, 118, 23);
		contentPane.add(btnChangetype);
		
		JButton bChangePassword = new JButton("Change Password");
		bChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentUser.getEmailStatus().equals("activated")) {
					User newUser = new User(currentUser.getUsername(), tPassword.getText(), currentUser.getType(), currentUser.getEmail(), currentUser.getEmailStatus());
					clientModel.updateUser(currentUser, newUser);
					updateCurrentUser();
				} else JOptionPane.showMessageDialog(null, "You must activate your email in order to change your password!");
			}
		});
		bChangePassword.setBounds(280, 72, 118, 23);
		contentPane.add(bChangePassword);
		
		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setText("Email:");
		txtEmail.setBounds(10, 133, 86, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		tEmail.setBounds(106, 135, 164, 20);
		contentPane.add(tEmail);
		tEmail.setColumns(10);
		
		JButton btnUpdateEmail = new JButton("Update Email");
		btnUpdateEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String result = clientModel.checkEmail(tEmail.getText());
				switch (tEmail.getText()) {
					case "" :
						JOptionPane.showMessageDialog(null, "Please fill the email field!");
						break;
					default:
						switch (result) {
						case "used":
							JOptionPane.showMessageDialog(null, "Email already used!");
							break;
						case "notUsed":
							clientModel.updateEmail(currentUser.getUsername(), tEmail.getText());
							JOptionPane.showMessageDialog(null, "Email updated! Please activate it!");
							setEmailActivationVisibility(true);
							break;
						case "wrong":
							JOptionPane.showMessageDialog(null, "Wrong email format!");
							break;
						default:
							JOptionPane.showMessageDialog(null, "Internal error!");
							break;
						}
						break;
				}
				updateCurrentUser();
			}
		});
		btnUpdateEmail.setBounds(280, 132, 118, 23);
		contentPane.add(btnUpdateEmail);
		
		JRadioButton rdbtnClient = new JRadioButton("Client");
		rdbtnClient.setSelected(true);
		buttonGroup.add(rdbtnClient);
		rdbtnClient.setBounds(404, 101, 68, 23);
		contentPane.add(rdbtnClient);
		
		JRadioButton rdbtnEmitter = new JRadioButton("Emitter");
		buttonGroup.add(rdbtnEmitter);
		rdbtnEmitter.setBounds(474, 101, 68, 23);
		contentPane.add(rdbtnEmitter);
		
		JRadioButton rdbtnAdmin = new JRadioButton("Admin");
		buttonGroup.add(rdbtnAdmin);
		rdbtnAdmin.setBounds(544, 101, 68, 23);
		contentPane.add(rdbtnAdmin);
		
		tCode = new JTextField();
		tCode.setBounds(547, 133, 86, 20);
		contentPane.add(tCode);
		tCode.setColumns(10);
		
		btnActivateEmail = new JButton("Activate Email");
		btnActivateEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (tCode.getText()) {
				case "":
					JOptionPane.showMessageDialog(null, "Please fill Code fields with code you've got through your email!");
					break;
				default:
					switch(clientModel.activateEmail(currentUser.getUsername(), tCode.getText())) {
					case "activated":
						JOptionPane.showMessageDialog(null, "Email was activated!");
						updateCurrentUser();
						break;
					case "wrongCode":
						JOptionPane.showMessageDialog(null, "Error! Wrong code!");
						break;
					default:
						JOptionPane.showMessageDialog(null, "Internal error!");
						break;
					}
					break;
				}
			}
		});
		btnActivateEmail.setBounds(643, 132, 134, 23);
		contentPane.add(btnActivateEmail);
		
		txtCode = new JTextField();
		txtCode.setEditable(false);
		txtCode.setText("Code:");
		txtCode.setBounds(474, 133, 68, 20);
		contentPane.add(txtCode);
		txtCode.setColumns(10);
		
		txtTaxes = new JTextField();
		txtTaxes.setEditable(false);
		txtTaxes.setText("Taxes:");
		txtTaxes.setBounds(10, 197, 86, 20);
		contentPane.add(txtTaxes);
		txtTaxes.setColumns(10);
		tTaxes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedTaxId = ((String) tTaxes.getValueAt(tTaxes.getSelectedRow(), 0));
			}
		});
		
		tTaxes.setBounds(10, 228, 416, 315);
		contentPane.add(tTaxes);
		tTaxes.setModel(new javax.swing.table.DefaultTableModel(
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
		
		JButton bPay = new JButton("Pay Tax");
		bPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedTaxId.equals(""))
					JOptionPane.showMessageDialog(null, "Please select a Tax!");
				else { 
					clientModel.updateTax(selectedTaxId, "paid");
					print();
				}
			}
		});
		bPay.setBounds(436, 224, 89, 23);
		contentPane.add(bPay);
		
		JButton bDeny = new JButton("Deny Tax Payment");
		bDeny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (selectedTaxId.equals(""))
					JOptionPane.showMessageDialog(null, "Please select a Tax!");
				else {
					clientModel.updateTax(selectedTaxId, "denied");
					print();
				}
			}
		});
		bDeny.setBounds(436, 258, 146, 23);
		contentPane.add(bDeny);
		
		JButton bFlag = new JButton("Flag Tax");
		bFlag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedTaxId.equals(""))
					JOptionPane.showMessageDialog(null, "Please select a Tax!");
				else {
					clientModel.updateTax(selectedTaxId, "flagged");
					print();
				}
			}
		});
		bFlag.setBounds(436, 292, 89, 23);
		contentPane.add(bFlag);
		
		updateCurrentUser();
		print();
	}
	
	private void displayTaxes(List<Tax> allTaxes) {
		DefaultTableModel usersTax = (DefaultTableModel) tTaxes.getModel();
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
	
	private void print() {
		displayTaxes(clientModel.getTaxes(currentUsername));
	}

	private void updateCurrentUser() {
		getUser(this.currentUsername);

		System.out.println(currentUser.getUsername() + " " + currentUser.getPassword() + " " + currentUser.getType() + " " + currentUser.getEmail() + " " + currentUser.getEmailStatus());
		
		tPassword.setText("");
		tEmail.setText(currentUser.getEmail());
		tType.setText(currentUser.getType());
		
		if (currentUser.getEmailStatus().equals("") || currentUser.getEmailStatus().equals("activated")) {
			setEmailActivationVisibility(false);
		}
		
	}
	//TODOZ
	public String getUser(String username) {
		List<Object> usernames = new ArrayList<>();
		Command command = new Command();
		command.setCommandType(CommandType.GET_USER);
		usernames.add(username);
		command.setCommandData(usernames);
		Response response = ClientSocket.getConnection().executeCommand(command);
		
		List<User> users = new ArrayList<>();
		for (Object obj : response.getResponseData()) {
			users.add((User) obj);
		}
		currentUser = users.get(0);
		return null;
	}
	
	public void setEmailActivationVisibility(boolean is) {
		btnActivateEmail.setVisible(is);
		txtCode.setVisible(is);
		tCode.setVisible(is);
	}
	
	public void openWindow() {
		this.setVisible(true);
	}
}
