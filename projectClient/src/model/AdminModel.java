package model;

import java.util.ArrayList;
import java.util.List;

import net.Command;
import net.CommandType;
import net.Response;

public class AdminModel {
	
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
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		Command command = new Command();
		command.setCommandType(CommandType.READ_USERS);
		Response response = ClientSocket.getConnection().executeCommand(command);
		for (Object obj : response.getResponseData()) {
			users.add((User) obj);
		}
		return users;
	}

	public void createUser(User user) {
		Command command = new Command();
		command.setCommandType(CommandType.CREATE_USER);
		List<Object> users = new ArrayList<>();
		users.add(user);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}

	public void updateUser(User oldUser, User newUser) {
		Command command = new Command();
		command.setCommandType(CommandType.UPDATE_USER);
		List<Object> users = new ArrayList<>();
		users.add(oldUser);
		users.add(newUser);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}

	public void deleteUser(User user) {
		Command command = new Command();
		command.setCommandType(CommandType.DELETE_USER);
		List<Object> users = new ArrayList<>();
		users.add(user);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}
	
	public void closeConnection() {
		Command command = new Command();
		command.setCommandType(CommandType.FINISH);
		ClientSocket.getConnection().executeCommand(command);
		ClientSocket.getConnection().closeConnection();
	}
}
