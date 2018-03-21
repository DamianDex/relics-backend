package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessages {
	/**
	 * Common
	 */
	INNER_SERVER_ERROR("Wystpił problem z serwerem. Spróbuj później."),
	
	/**
	 * User connected
	 */
	USER_CREATED("Pomyślnie utworzono użytkownika."),
	USERNAME_CONFLICT("Uzytkownik o podanym adresie email jest już zarejestrowany!");
	
	private String description;

	private ErrorMessages(String description) {
		this.description = description;
	}

	public String getType() {
		return "ERROR";
	}
	
	public String getTag() {
		return name();
	}
	
	public String getDescription() {
		return description;
	}
	
}
