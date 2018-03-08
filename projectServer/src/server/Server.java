package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

	private static final int PORT = 9001;
	private ServerSocket serverSocket;
	private ServerModel serverModel;
	private static List<ServerClient> clients;

	public Server() {
		try {
			clients = new ArrayList<>();
			serverModel = new ServerModel(new ServerObserver(this));
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Socket> getClients() {
		List<Socket> sockets = new ArrayList<>();
		for (ServerClient serverClient : clients) {
			Socket socket = serverClient.getClient();
			sockets.add(socket);
		}
		return sockets;
	}

	public void closeClient(ServerClient closing) {
		clients.remove(closing);
	}

	@Override
	public void run() {
		while (!serverSocket.isClosed()) {
			try {
				Socket client = serverSocket.accept();
				ServerClient serverClient = new ServerClient(client, this, serverModel);
				new Thread(serverClient).start();
				clients.add(serverClient);
				System.out.println("Client connected: " + client.toString());
			} catch (IOException e) {
				System.out.println("Server closed " + e.getMessage());
			}
		}
	}

}