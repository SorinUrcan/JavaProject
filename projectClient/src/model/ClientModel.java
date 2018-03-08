package model;

import java.util.ArrayList;
import java.util.List;

import net.Command;
import net.CommandType;
import net.Response;
import model.Tax;

public class ClientModel {
	
	public void updateUser(User oldUser, User newUser) {
		Command command = new Command();
		command.setCommandType(CommandType.UPDATE_USER);
		List<Object> users = new ArrayList<>();
		users.add(oldUser);
		users.add(newUser);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}
	
	public String checkEmail(String email) {
		Command command = new Command();
		command.setCommandType(CommandType.CHECK_EMAIL);
		List<Object> objects = new ArrayList<>();
		objects.add(email);
		command.setCommandData(objects);
		Response response = ClientSocket.getConnection().executeCommand(command);
		return response.getInfo();
	}
	
	public String activateEmail(String username, String code) {
		Command command = new Command();
		command.setCommandType(CommandType.ACTIVATE_EMAIL);
		List<Object> objects = new ArrayList<>();
		objects.add(username);
		objects.add(code);
		command.setCommandData(objects);
		Response response = ClientSocket.getConnection().executeCommand(command);
		return response.getInfo();
	}

	public void closeConnection() {
		Command command = new Command();
		command.setCommandType(CommandType.FINISH);
		ClientSocket.getConnection().executeCommand(command);
		ClientSocket.getConnection().closeConnection();
	}

	public void updateEmail(String username, String mail) {
		Command command = new Command();
		command.setCommandType(CommandType.UPDATE_EMAIL);
		List<Object> users = new ArrayList<>();
		users.add(username);
		users.add(mail);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}

	public List<Tax> getTaxes(String client) {
		List<Tax> taxes = new ArrayList<>();
		Command command = new Command();
		command.setCommandType(CommandType.READ_TAXES);
		
		List<Object> param = new ArrayList<>();
		command.setCommandData(param);
		
		Response response = ClientSocket.getConnection().executeCommand(command);
		for (Object obj : response.getResponseData()) {
				if (client.equals(((Tax) obj).getClient()))
					taxes.add((Tax) obj);
			} 
		
		return taxes;
	}

	public void updateTax(String taxID, String newStatus) {
		Command command = new Command();
		command.setCommandType(CommandType.UPDATE_TAX_STATUS);
		List<Object> users = new ArrayList<>();
		users.add(taxID);
		users.add(newStatus);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}
}
