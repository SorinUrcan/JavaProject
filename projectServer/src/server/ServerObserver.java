package server;

import java.util.Observable;
import java.util.Observer;

public class ServerObserver implements Observer {

	private static boolean isUpdate;
	private static String whatChanged;
	private static int nrOfClients;
	private static Server server;

	public ServerObserver(Server server) {
		ServerObserver.server = server;
	}

	@Override
	public void update(Observable o, Object arg) {
		ServerObserver.isUpdate = true;
		ServerObserver.whatChanged = (String) arg;
		ServerObserver.nrOfClients = ServerObserver.server.getClients().size();
	}

	public static String whatChanged() {
		return whatChanged;
	}

	public static boolean isUpdate() {
		return isUpdate;
	}

	public static void setUpdate(boolean isUpdate) {
		ServerObserver.isUpdate = isUpdate;
	}

	public static int getNrOfClients() {
		return nrOfClients;
	}

	public static void decreaseNrOfClients() {
		ServerObserver.nrOfClients--;
	}

}
