package net;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8867289652866595145L;
	private String info;
	private List<Object> responseData;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<Object> getResponseData() {
		return responseData;
	}

	public void setResponseData(List<Object> responseData) {
		this.responseData = responseData;
	}

}
