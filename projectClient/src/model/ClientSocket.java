package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import net.Command;
import net.Response;

public class ClientSocket {

	private static final int PORT = 9001;
	private static final String HOST = "127.0.0.1";
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private static ClientSocket conn;
	private Socket socket;

	private ClientSocket() {
		try {
			socket = new Socket(HOST, PORT);
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Server offline or not reachable!\nPlease try again later or contact the system administrator.", "Connection error",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

	public static ClientSocket getConnection() {
		if (conn == null) {
			conn = new ClientSocket();
		}
		return conn;
	}

	public void closeConnection() {
		try {
			output.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized Response executeCommand(Command command) {
		Response response = null;
		try {
			output.writeObject(command);
			response = (Response) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}
}
