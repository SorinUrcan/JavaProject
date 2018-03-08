package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import model.LoginModel;
import model.User;
import view.LoginDialog;

public class LoginController {
	private LoginDialog login;
	private LoginModel loginModel;

	public LoginController(LoginDialog login, LoginModel loginModel) {
		this.login = login;
		this.loginModel = loginModel;
		this.login.addOKListener(new OKListener());
		this.login.addRegListener(new RegListener());
		this.login.addWindowCloseListener(new WindowClose());
	}

	private class OKListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			User user = new User();
			user.setUsername(login.getUsername());
			user.setPassword(login.getPassword());
			loginModel.checkLogin(user, login);
		}
	}
	
	private class RegListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			User user = new User();
			user.setUsername(login.getUsername());
			user.setPassword(login.getPassword());
			if (user.getUsername().equals("") || user.getPassword().equals(""))
				JOptionPane.showMessageDialog(null, "Please fill all fields!");
			else loginModel.register(user, login);
		}
	}

	private class WindowClose extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent a) {
			try {
				loginModel.closeConnection();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.exit(0);
		}
	}
}
