package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserMapper {
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		ResultSet res = DB.getDBInstance().executeQuery("select * from users;");
		try {
			while (res.next()) {
				User user = new User();
				user.setUsername(res.getString("username"));
				user.setPassword(res.getString("password"));
				user.setType(res.getString("type"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public void updateUser(User oldUser, User newUser) {
		StringBuilder query = new StringBuilder();
		query.append("update users set username='");
		query.append(newUser.getUsername());
		query.append("',password='");
		query.append(newUser.getPassword());
		query.append("',type='");
		query.append(newUser.getType());
		query.append("' where username='");
		query.append(oldUser.getUsername());
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}

	public void createUser(User user) {
		StringBuilder query = new StringBuilder();
		query.append("insert into users (username,password,type,email,status) values ('");
		query.append(user.getUsername());
		query.append("','");
		query.append(user.getPassword());
		query.append("','");
		query.append(user.getType());
		query.append("','");
		query.append(user.getEmail());
		query.append("','");
		query.append(user.getEmailStatus());
		query.append("');");
		DB.getDBInstance().executeUpdate(query.toString());
	}
	
	public User getUser(String username) {
		StringBuilder query = new StringBuilder();
		query.append("select * from users ;");
		//query.append(username);
		//query.append("';");
		ResultSet res = DB.getDBInstance().executeQuery(query.toString());
		try {
			while (res.next()) {
				if (res.getString("username").equals(username)) {
					User user = new User();
					user.setUsername(res.getString("username"));
					user.setPassword(res.getString("password"));
					user.setType(res.getString("type"));
					user.setEmail(res.getString("email"));
					user.setEmailStatus(res.getString("status"));
					return user; 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteUser(User user) {
		StringBuilder query = new StringBuilder();
		query.append("delete from users where username='");
		query.append(user.getUsername());
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}

	public void updateMail(String username, String email) {
		StringBuilder query = new StringBuilder();
		query.append("update users set email='");
		query.append(email);
		query.append("',status='");
		Random ran = new Random();
		int x = ran.nextInt(9000) + 1000;
		String code = Integer.toString(x);
		sendActivationEmail(email, code);
		query.append(code);
		query.append("' where username='");
		query.append(username);
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}

	private void sendActivationEmail(String email, String code) {
		StringBuilder query = new StringBuilder();
		query.append("select * from email;");
		ResultSet res = DB.getDBInstance().executeQuery(query.toString());
		String senderEmail = null, password = null;

		try {
			while (res.next()) {
				senderEmail = res.getString("email");
				password    = res.getString("password");
				System.out.println("PASS : " + senderEmail + " " + password);
				//break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MailService mailService = new MailService(senderEmail, password);
		try {
		     mailService.sendMail(email, "Activation Email", "Your activation CODE for project account is: " + code);
		 } catch (Exception e) {
			System.out.println(e);
		 }
	}
	
	public void sendEmail(String email, String title, String message) {
		StringBuilder query = new StringBuilder();
		query.append("select * from email;");
		ResultSet res = DB.getDBInstance().executeQuery(query.toString());
		String senderEmail = null, password = null;

		try {
			while (res.next()) {
				senderEmail = res.getString("email");
				password    = res.getString("password");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MailService mailService = new MailService(senderEmail, password);
		try {
		     mailService.sendMail(email, title, message);
		 } catch (Exception e) {
			System.out.println(e);
		 }
	}

	public String checkMail(String email) {
		ResultSet res = DB.getDBInstance().executeQuery("select * from users;");
		try {
			while (res.next()) {
				if (res.getString("email").equals(email))
					return "used";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notUsed";
	}

	public String activateEmail(String username, String code) {
		StringBuilder query = new StringBuilder();
		query.append("select * from users;");
		ResultSet res = DB.getDBInstance().executeQuery(query.toString());
		try {
			while (res.next()) {
				if (res.getString("username").equals(username) && res.getString("status").equals(code)) {
					updateStatus(username, "activated");
					return "activated";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "wrongCode";
	}

	private void updateStatus(String username, String status) {
		StringBuilder query = new StringBuilder();
		query.append("update users set status='");
		query.append(status);
		query.append("' where username='");
		query.append(username);
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}
}
