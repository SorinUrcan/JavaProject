package model;

import java.util.ArrayList;
import java.util.List;

import net.Command;
import net.CommandType;
import net.Response;

public class EmitterModel {

	public List<String> getAllClients() {
		List<String> users = new ArrayList<>();
		Command command = new Command();
		command.setCommandType(CommandType.READ_CLIENTS);
		Response response = ClientSocket.getConnection().executeCommand(command);
		for (Object obj : response.getResponseData()) {
			users.add((String) obj);
		}
		return users;
	}

	public List<Tax> getAllTaxes(String id, String emitter) {
		List<Tax> users = new ArrayList<>();
		Command command = new Command();
		command.setCommandType(CommandType.READ_TAXES);
		
		List<Object> param = new ArrayList<>();
		command.setCommandData(param);
		
		Response response = ClientSocket.getConnection().executeCommand(command);
		for (Object obj : response.getResponseData()) {
			if (id.equals("")) {
				if (emitter.equals(((Tax) obj).getEmitter()))
					users.add((Tax) obj);
			} else {
				if (id.equals(((Tax) obj).getId())) {
					users.add((Tax) obj);
				}
			}
		}
		return users;
	}

	public void createTax(Tax newTax) {
		Command command = new Command();
		command.setCommandType(CommandType.CREATE_TAX);
		List<Object> users = new ArrayList<>();
		users.add(newTax);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}

	public void updateTax(Tax newTax) {
		Command command = new Command();
		command.setCommandType(CommandType.UPDATE_TAX);
		List<Object> users = new ArrayList<>();
		users.add(newTax);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}

	public void deleteTax(Tax newTax) {
		Command command = new Command();
		command.setCommandType(CommandType.DELETE_TAX);
		List<Object> users = new ArrayList<>();
		users.add(newTax);
		command.setCommandData(users);
		ClientSocket.getConnection().executeCommand(command);
	}
}
