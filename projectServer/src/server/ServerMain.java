package server;

import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ServerMain {
	public static void main(String args[]) {
		Server server = new Server();
		Thread serverThread = null;
		Scanner sc = new Scanner(System.in);
		boolean running = false;
		String command;
		System.out
				.println("Commands:\nstart - start the server, and wait for client connections\nclients - show all the clients connected\nstop - shut down the server\n");
		while (!(command = sc.nextLine()).equalsIgnoreCase("stop")) {
			switch (command) {
			case "start":
				if (!running) {
					serverThread = new Thread(server);
					serverThread.start();
					running = true;
					System.out.println("Server started!\n");
				} else {
					System.out.println("Server already running\n");
				}
				break;
			case "clients":
				List<Socket> clients = server.getClients();
				if (clients.size() > 0) {
					System.out.println("clients:\n");
					for (Socket client : clients) {
						System.out.print(client.toString() + "\n");
					}
				} else {
					System.out.println("No clients connected.");
				}
				System.out.print("\n");
				break;
			case "h":
			case "help":
				System.out
						.println("Commands:\nstart - start the server, and wait for client connections\nclients - show all the clients connected\nstop - shut down the server\n");
				break;
			default:
				System.out.println("Unknown command received!\n");
				break;
			}
		}
		server.closeServer();
		serverThread.interrupt();
		sc.close();
		System.out.println("Server shut down!");
		System.exit(0);
	}
}
