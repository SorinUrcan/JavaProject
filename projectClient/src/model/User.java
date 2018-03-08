package model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3720516041314663124L;
	private String username;
	private String password;
	private String type;
	private String email;
	private String emailStatus;
	
	public User() {
	}
	
	public User(String username, String password, String type, String email, String emailStatus) {
		this.username    = username;
		this.password    = password;
		this.type        = type;
		this.email       = email;
		this.emailStatus = emailStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}
}
