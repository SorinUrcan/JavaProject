package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import net.*;

public class ServerClient implements Runnable {

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket client;
	private Server server;
	private ServerModel serverModel;

	public ServerClient(Socket client, Server server, ServerModel serverModel) {
		this.client = client;
		this.server = server;
		this.serverModel = serverModel;
		try {
			input = new ObjectInputStream(this.client.getInputStream());
			output = new ObjectOutputStream(this.client.getOutputStream());
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getClient() {
		return client;
	}

	@Override
	public void run() {
		try {
			Command command = (Command) input.readObject();
			Response response = serverModel.execute(command);
			output.writeObject(response);
			while (command.getCommandType() != CommandType.FINISH) {
				command = (Command) input.readObject();
				response = serverModel.execute(command);
				output.writeObject(response);
			}
			server.closeClient(this);
			input.close();
			output.close();
			client.close();
		} catch (ClassNotFoundException | IOException e) {
			server.closeClient(this);
			try {
				output.close();
				input.close();
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Socket closed: " + e.getMessage());
		}
	}
}
