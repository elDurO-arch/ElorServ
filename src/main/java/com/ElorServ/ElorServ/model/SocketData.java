package com.ElorServ.ElorServ.model;

import lombok.Data;

@Data
public class SocketData {

	private String type;
	private Object data;
	
	public SocketData() {
	}
	
	public SocketData(String type, Object data) {
		this.type = type;
		this.data = data;
	}
	
}
