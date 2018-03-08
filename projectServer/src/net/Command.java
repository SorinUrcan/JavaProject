package net;

import java.io.Serializable;
import java.util.List;

public class Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7635457457024523020L;
	private CommandType commandType;
	private List<Object> commandData;

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public List<Object> getCommandData() {
		return commandData;
	}

	public void setCommandData(List<Object> commandData) {
		this.commandData = commandData;
	}

}
