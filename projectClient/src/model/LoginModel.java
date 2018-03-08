package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import control.LoginController;
import net.Command;
import net.CommandType;
import net.Response;
import view.AdminView;
import view.ClientView;
import view.EmitterView;
import view.LoginDialog;

public class LoginModel {
	private ClientSocket clientSocket;

	public LoginModel(ClientSocket connection) {
		this.clientSocket = connection;
	}

	public void checkLogin(User user, LoginDialog login) {
		Command command = new Command();
		command.setCommandType(CommandType.LOGIN);
		List<Object> users = new ArrayList<>();
		users.add(user);
		command.setCommandData(users);
		Response response = clientSocket.executeCommand(command);
		
		if (response.getInfo().equals("admin")) {
			AdminModel adminModel = new AdminModel();
			AdminView adminView = new AdminView(adminModel);
			login.closeDialog();
			adminView.openWindow();
		} else if (response.getInfo().equals("client")) {
			ClientModel clientModel = new ClientModel();
			ClientView clientView = new ClientView(clientModel, login.getUsername());
			login.closeDialog();
			clientView.openWindow();
		} else if (response.getInfo().equals("emitter")) {
			EmitterModel emitterModel = new EmitterModel();
			EmitterView emitterView = new EmitterView(emitterModel, login.getUsername());
			login.closeDialog();
			emitterView.openWindow();
		} else if (response.getInfo().equals("banned")) {
			JOptionPane.showMessageDialog(null, "You were banned permanently!");
		} else {
			JOptionPane.showMessageDialog(null, "Wrong user details inserted!");
		}
	}
	
	public void register(User user, LoginDialog login) {
		Command command = new Command();
		command.setCommandType(CommandType.LOGIN);
		List<Object> users = new ArrayList<>();
		users.add(user);
		command.setCommandData(users);
		Response response = clientSocket.executeCommand(command);
		
		
		if (response.getInfo().equals("wrongPassword") || response.getInfo().equals("admin") || response.getInfo().equals("client") || response.getInfo().equals("emitter")) {
			JOptionPane.showMessageDialog(null, "Username already used!");
		} else if (response.getInfo().equals("nonExistent")) {
			Command commandz = new Command();
			commandz.setCommandType(CommandType.CREATE_USER);
			List<Object> usersz = new ArrayList<>();
			User userz = new User();
			userz.setUsername(user.getUsername());
			userz.setPassword(user.getPassword());
			userz.setType("client");
			userz.setEmail("");
			userz.setEmailStatus("");
			usersz.add(userz);
			commandz.setCommandData(usersz);
			ClientSocket.getConnection().executeCommand(commandz);
			
			ClientModel clientModel = new ClientModel();
			ClientView clientView = new ClientView(clientModel, login.getUsername());
			login.closeDialog();
			clientView.openWindow();
		} else {
			JOptionPane.showMessageDialog(null, "Internal error!");
		}
	}
	
	public User getUser(String username) {
		List<Object> usernames = new ArrayList<>();
		Command command = new Command();
		command.setCommandType(CommandType.GET_USER);
		usernames.add(username);
		command.setCommandData(usernames);
		Response response = ClientSocket.getConnection().executeCommand(command);
		for (Object obj : response.getResponseData()) {
			return ((User) obj);
		}
		return null;
	}

	public void closeConnection() {
		Command command = new Command();
		command.setCommandType(CommandType.FINISH);
		clientSocket.executeCommand(command);
		clientSocket.closeConnection();
	}

	public static void main(String args[]) {
		LoginDialog login = new LoginDialog();
		LoginModel loginModel = new LoginModel(ClientSocket.getConnection());
		new LoginController(login, loginModel);
		login.openDialog();	
	}
}
