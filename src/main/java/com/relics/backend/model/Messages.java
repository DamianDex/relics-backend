package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Messages {
	/**
	 * Common
	 */
	INNER_SERVER_ERROR(Result.ERROR ,"Wystpił problem z serwerem. Spróbuj później."),
	
	/**
	 * User connected
	 */
	USER_CREATED(Result.SUCCESS, "Pomyślnie utworzono użytkownika."),
	USERNAME_CONFLICT(Result.ERROR, "Uzytkownik o podanym adresie email jest już zarejestrowany!");
	
	private Result result;
	private String description;
	
	enum Result {
		ERROR, SUCCESS;
	}
	
	private Messages(Result result, String description) {
		this.result = result;
		this.description = description;
	}

	public Result getResult() {
		return result;
	}
	
	public String getTag() {
		return name();
	}
	
	public String getDescription() {
		return description;
	}
	
}
