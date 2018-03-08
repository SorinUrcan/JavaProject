package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.Tax;
import model.TaxMapper;
import model.User;
import model.UserMapper;
import net.*;

public class ServerModel extends Observable {

	TaxMapper taxMapper = new TaxMapper();
	UserMapper userMapper = new UserMapper();
	
	public ServerModel(ServerObserver serverObserver) {
		this.addObserver(serverObserver);
	}

	public Response execute(Command command) {
		Response response = null;
		CommandType type = command.getCommandType();
		switch (type) {
		case LOGIN:
			response = login((User) command.getCommandData().get(0));
			break;
		case READ_USERS:
			response = readUsers();
			break;
		case CREATE_USER:
			createUser((User) command.getCommandData().get(0));
			break;
		case DELETE_USER:
			deleteUser((User) command.getCommandData().get(0));
			break;
		case UPDATE_USER:
			updateUser((User) command.getCommandData().get(0), (User) command.getCommandData().get(1));
			break;
		case GET_USER:
			response = getUser((String) command.getCommandData().get(0));
			break;
		case ACTIVATE_EMAIL:
			response = activateEmail((String) command.getCommandData().get(0), (String) command.getCommandData().get(1));
			break;
		case UPDATE_EMAIL:
			updateMail((String) command.getCommandData().get(0), (String) command.getCommandData().get(1));
			break;
		case CHECK_EMAIL:
			response = checkMail((String) command.getCommandData().get(0));
			break;
		case READ_CLIENTS:
			response = readClients();
			break;
		case READ_TAXES:
			response = getAllTaxes();
			break;
		case CREATE_TAX: 
			taxMapper.createTax((Tax) command.getCommandData().get(0));
			notiftyClientViaEmail((Tax) command.getCommandData().get(0));
			break;
		case UPDATE_TAX:
			taxMapper.updateTax((Tax) command.getCommandData().get(0));
			break;
		case UPDATE_TAX_STATUS:
			taxMapper.updateTaxStatus((String) command.getCommandData().get(0), (String) command.getCommandData().get(1));
			break;
		case DELETE_TAX:
			taxMapper.deleteTax((Tax) command.getCommandData().get(0));
			break;		
		case CHECK_UPDATE:
			response = checkUpdate();
			break;
		case FINISH:
			break;
		default:
			System.out.println("Unknown command received!");
			break;
		}
		return response;
	}

	private void notiftyClientViaEmail(Tax tax) {
		if (userMapper.getUser(tax.getClient()).getEmailStatus().equals("activated")) {
			String email = userMapper.getUser(tax.getClient()).getEmail();
			String title = "New tax issued for you";
			String message = "Hello mister " + userMapper.getUser(tax.getClient()).getUsername() + "! We are here to inform you than a new Tax from " + tax.getEmitter() + " has been issued.";
			userMapper.sendEmail(email, title, message);
		}
	}

	private Response readClients() {
		UserMapper userMap = new UserMapper();
		List<User> users = userMap.getAllUsers();
		Response response = new Response();
		List<Object> usersList = new ArrayList<>();
		for (User user : users) {
			if (user.getType().equals("client"))
				usersList.add(user.getUsername());
		}
		response.setResponseData(usersList);
		return response;
	}

	//CONSULTATIONS
	private Response checkUpdate() {
		Response response = null;
		if (ServerObserver.isUpdate()) {
			if (ServerObserver.getNrOfClients() > 0) {
				ServerObserver.decreaseNrOfClients();
				response = new Response();
				response.setInfo(ServerObserver.whatChanged().toString());
				//System.out.println("SM check update: " + ServerObserver.whatChanged().toString());
			} else {
				ServerObserver.setUpdate(false);
			}
		}
		return response;
	}

	//USERS
	private Response login(User userCheck) {
		Response response = new Response();
		UserMapper userMap = new UserMapper();
		List<User> users = userMap.getAllUsers();
		for (User user : users) {
			if (user.getUsername().equals(userCheck.getUsername()) && user.getPassword().equals(userCheck.getPassword())) {
				response.setInfo(user.getType());
				return response;
			}
			
			if (user.getUsername().equals(userCheck.getUsername()) && !user.getPassword().equals(userCheck.getPassword())) {
				response.setInfo("wrongPassword");
				return response;
			}
		}
		response.setInfo("nonExistent");
		return response;
	}
	
	private Response readUsers() {
		UserMapper userMap = new UserMapper();
		List<User> users = userMap.getAllUsers();
		Response response = new Response();
		List<Object> usersList = new ArrayList<>();
		for (User user : users) {
			usersList.add(user);
		}
		response.setResponseData(usersList);
		return response;
	}
	
	private Response getAllTaxes() {
		List<Tax> users = taxMapper.getAllTaxes();
		Response response = new Response();
		List<Object> usersList = new ArrayList<>();
		for (Tax user : users) {
			usersList.add(user);
		}
		response.setResponseData(usersList);
		return response;
	}
	
	private Response getUser(String username) {
		UserMapper userMap = new UserMapper();
		Response response = new Response();
		List<Object> usersList = new ArrayList<>();
		usersList.add(userMap.getUser(username));
		response.setResponseData(usersList);
		return response;
	}
	
	private void createUser(User user) {
		UserMapper userMap = new UserMapper();
		userMap.createUser(user);
	}
	
	private void updateUser(User oldUser, User newUser) {
		UserMapper userMap = new UserMapper();
		userMap.updateUser(oldUser, newUser);
		setChanged();
		//notifyObservers(UpdateType.UPDATED_USER);
	}

	private void deleteUser(User user) {
		UserMapper userMap = new UserMapper();
		userMap.deleteUser(user);
		setChanged();
		//notifyObservers(UpdateType.DELETED_USER);
	}
	
	//EMAILS
	private Response activateEmail(String string, String string2) {
		UserMapper userMap = new UserMapper();
		Response response = new Response();
		response.setInfo(userMap.activateEmail(string, string2));
		return response;
	}

	private Response checkMail(String email) {
		UserMapper userMap = new UserMapper();
		Response response = new Response();
		response.setInfo(userMap.checkMail(email));
		return response;
	}
	
	private void updateMail(String username, String email) {
		UserMapper userMap = new UserMapper();
		userMap.updateMail(username, email);
	}
	
	
	//OLD
	/*
	private Response report(Date fromDate, Date toDate) {
		Response response = new Response();
		List<Object> respObj = new ArrayList<>();
		BookingMapper bookingMap = new BookingMapper();
		List<Booking> bookings = bookingMap.report(fromDate, toDate);
		respObj.add(bookings.size());
		for (Booking booking : bookings) {
			respObj.add(booking);
		}
		List<Client> clients = fidelityClients(bookings);
		respObj.add(clients.size());
		for (Client client : clients) {
			respObj.add(client);
		}
		response.setResponseData(respObj);
		return response;
	}

	private List<Client> fidelityClients(List<Booking> bookings) {
		List<Client> clients = new ArrayList<>();
		List<Integer> ids = new ArrayList<>();
		List<Integer> freq = new ArrayList<>();
		ClientMapper clientMap = new ClientMapper();
		for (Booking booking : bookings) {
			checkIDandFreq(booking.getClient().getClientID(), ids, freq);
		}
		orderIDsAndFreq(ids, freq);
		int i = 0;
		for (Integer nr : ids) {
			if (i < 5) {
				Client client = clientMap.read(nr);
				clients.add(client);
			}
			i++;
		}
		return clients;
	}

	private void orderIDsAndFreq(List<Integer> ids, List<Integer> freq) {
		int i, j, iMax;
		for (j = 0; j < ids.size() - 1; j++) {
			iMax = j;
			for (i = j + 1; i < ids.size(); i++) {
				if (freq.get(i) > freq.get(j))
					iMax = j;
			}
			if (iMax != j) {
				int id, fr;
				id = ids.get(iMax);
				fr = freq.get(iMax);
				ids.set(iMax, ids.get(j));
				freq.set(iMax, freq.get(j));
				ids.set(j, id);
				freq.set(j, fr);
			}
		}
	}

	private void checkIDandFreq(int clientID, List<Integer> ids, List<Integer> freq) {
		if (ids.contains(clientID)) {
			freq.set(ids.indexOf(clientID), freq.get(ids.indexOf(clientID)) + 1);
		} else {
			ids.add(clientID);
			freq.add(1);
		}
	}

	private Response readClientById(int id) {
		Response response = new Response();
		ClientMapper clientMapper = new ClientMapper();
		Client client = clientMapper.read(id);
		List<Object> clients = new ArrayList<>();
		clients.add(client);
		response.setResponseData(clients);
		return response;
	}

	private Response readBookings() {
		Response response = new Response();
		BookingMapper bookingMap = new BookingMapper();
		List<Booking> bookings = bookingMap.readAll();
		List<Object> bookingsList = new ArrayList<>();
		for (Booking booking : bookings) {
			bookingsList.add(booking);
		}
		response.setResponseData(bookingsList);
		return response;
	}

	private Response readBookingsByClient(Client client) {
		Response response = new Response();
		BookingMapper bookingMapper = new BookingMapper();
		List<Booking> bookings = bookingMapper.read(client);
		List<Object> allBookings = new ArrayList<>();
		for (Booking booking : bookings) {
			allBookings.add(booking);
		}
		response.setResponseData(allBookings);
		return response;
	}

	private Response readClients() {
		Response response = new Response();
		ClientMapper clientMap = new ClientMapper();
		List<Client> clients = clientMap.readAll();
		List<Object> clientsList = new ArrayList<>();
		for (Client client : clients) {
			clientsList.add(client);
		}
		response.setResponseData(clientsList);
		return response;
	}

	private void createBooking(Booking booking) {
		BookingMapper bookingMap = new BookingMapper();
		bookingMap.create(booking);
		setChanged();
		notifyObservers(UpdateType.NEW_BOOKING);
	}

	private void deleteBooking(Booking booking) {
		BookingMapper bookingMap = new BookingMapper();
		bookingMap.delete(booking);
		setChanged();
		notifyObservers(UpdateType.DELETED_BOOKING);
	}

	private void updateBooking(Booking oldBooking, Booking newBooking) {
		BookingMapper bookingMap = new BookingMapper();
		bookingMap.update(oldBooking, newBooking);
		setChanged();
		notifyObservers(UpdateType.UPDATED_BOOKING);
	}

	private void createClient(Client client) {
		ClientMapper clientMap = new ClientMapper();
		clientMap.create(client);
		setChanged();
		notifyObservers(UpdateType.NEW_CLIENT);
	}

	private void updateClient(Client oldClient, Client newClient) {
		ClientMapper clientMap = new ClientMapper();
		clientMap.update(oldClient, newClient);
		setChanged();
		notifyObservers(UpdateType.UPDATED_CLIENT);
	}

	private void deleteClient(Client client) {
		ClientMapper clientMap = new ClientMapper();
		clientMap.delete(client);
		setChanged();
		notifyObservers(UpdateType.DELETED_CLIENT);
	}

	private Response calculatePrice(int clientID, Booking booking) {
		Response response = new Response();
		PriceCalculator priceCalc = new PriceCalculator();
		List<Object> resp = new ArrayList<>();
		double price = priceCalc.calculatePrice(clientID, booking);
		resp.add(price);
		response.setResponseData(resp);
		return response;
	}

	private Response getAvailableRooms(Date from, Date to) {
		Response response = new Response();
		RoomMapper roomMapper = new RoomMapper();
		List<Room> rooms = roomMapper.getAvailableRooms(from, to);
		List<Object> allRooms = new ArrayList<>();
		for (Room room : rooms)
			allRooms.add(room);
		response.setResponseData(allRooms);
		return response;
	}

	 */


}
