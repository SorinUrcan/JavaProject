package view;

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

import model.AdminModel;
import model.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -732016771382746047L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField jUsername;
	private JTextField txtPassword;
	private JTextField jPassword;
	private JTextField txtType;
	private JTable textArea = new JTable();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private AdminModel adminModel;
	private String userType = "";
	JRadioButton rclient = new JRadioButton("client");
	JRadioButton remitter = new JRadioButton("emitter");
	JRadioButton rdbtnAdmin = new JRadioButton("admin");

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public AdminView(AdminModel adminModel) {
		setResizable(false);
		setTitle("Administrator Panel");
		this.adminModel = adminModel;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				String value = textArea.getModel().getValueAt(textArea.getSelectedRow(), 0).toString();
				User user = adminModel.getUser(value);
				jUsername.setText(user.getUsername());
				jPassword.setText(user.getPassword());
				switch (user.getType()) {
				case "admin":
					rdbtnAdmin.setSelected(true);
					break;
				case "emitter":
					remitter.setSelected(true);
					break;
				default:
					rclient.setSelected(true);
					break;
				}
			}
		});
		
		textArea.setBounds(10, 11, 544, 366);
		contentPane.add(textArea);
		textArea.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	                "username", "password", "type"
	            }
	        ) {
	            @SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
	                java.lang.String.class, java.lang.String.class, java.lang.String.class
	            };

	            @SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }
	        });
		
		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setText("Username:");
		txtUsername.setBounds(10, 384, 86, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		jUsername = new JTextField();
		jUsername.setBounds(106, 385, 86, 20);
		contentPane.add(jUsername);
		jUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setEditable(false);
		txtPassword.setText("Password:");
		txtPassword.setBounds(10, 415, 86, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		jPassword = new JTextField();
		jPassword.setBounds(106, 415, 86, 20);
		contentPane.add(jPassword);
		jPassword.setColumns(10);
		
		txtType = new JTextField();
		txtType.setEditable(false);
		txtType.setText("Type:");
		txtType.setBounds(10, 446, 86, 20);
		contentPane.add(txtType);
		txtType.setColumns(10);
		
		rclient.setSelected(true);
		rclient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userType = "client";
			}
		});
		buttonGroup.add(rclient);
		rclient.setBounds(106, 445, 73, 23);
		contentPane.add(rclient);
		rclient.setActionCommand("client");
		

		remitter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userType = "emitter";
			}
		});
		buttonGroup.add(remitter);
		remitter.setBounds(181, 445, 73, 23);
		contentPane.add(remitter);

		rdbtnAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userType = "admin";
			}
		});
		buttonGroup.add(rdbtnAdmin);
		rdbtnAdmin.setBounds(259, 445, 65, 23);
		contentPane.add(rdbtnAdmin);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jUsername.getText().equals("") || jPassword.getText().equals("") || (userType.equals(""))) {
					JOptionPane.showMessageDialog(null, "Fill all fields!");
				} else {
					User user = new User();
					user.setUsername(jUsername.getText());
					user.setPassword(jPassword.getText());
					user.setType(userType);
					adminModel.createUser(user);
					print();
				}
			}
		});

		btnCreate.setBounds(10, 479, 89, 23);
		contentPane.add(btnCreate);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jUsername.getText().equals("") || jPassword.getText().equals("") || (userType.equals(""))) {
					JOptionPane.showMessageDialog(null, "Fill all fields!");
				} else {
					User user = new User();
					user.setUsername(jUsername.getText());
					user.setPassword(jPassword.getText());
					user.setType(userType);
					
					User old = new User();
					old.setUsername(jUsername.getText());
					adminModel.updateUser(old, user);
					print();
				}
			}
		});
		btnUpdate.setBounds(116, 479, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jUsername.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Fill username field!");
				} else {
					User user = new User();
					user.setUsername(jUsername.getText());
					//user.setPassword(jPassword.getText());
					//user.setType(userType);
					//System.out.println(userType);
					adminModel.deleteUser(user);
					print();
				}
			}
		});
		btnDelete.setBounds(215, 479, 89, 23);
		contentPane.add(btnDelete);
		
		displayUsers(this.adminModel.getAllUsers());
	}
	
	public void displayUsers(List<User> users) {
		DefaultTableModel usersTable = (DefaultTableModel) textArea.getModel();
		usersTable.setRowCount(0);
		for (User user : users) {
			String username = user.getUsername();
			String password = user.getPassword();
			String type     = user.getType();
			usersTable.addRow(new Object[] { username, password, type });
		}
	}

	public void openWindow() {
		this.setVisible(true);
	}
	
	public void print() {
		displayUsers(this.adminModel.getAllUsers());
	}
}
