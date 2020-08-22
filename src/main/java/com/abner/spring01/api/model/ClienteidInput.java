package com.abner.spring01.api.model;

import javax.validation.constraints.NotNull;

public class ClienteidInput {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@NotNull
	private String id;

}
