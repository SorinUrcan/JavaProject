package model;

import java.io.Serializable;

public class Tax implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8494855731306358536L;
	private String id;
	private String client;
	private String emitter;
	private String amount;
	private String type;
	private String status;
	private String description;
	
	public Tax() {
	}

	public Tax(String client, String emitter, String amount, String type, String status, String description) {
		this.client = client;
		this.emitter = emitter;
		this.amount = amount;
		this.type = type;
		this.status = status;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getEmitter() {
		return emitter;
	}
	public void setEmitter(String emitter) {
		this.emitter = emitter;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String ammount) {
		this.amount = ammount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
