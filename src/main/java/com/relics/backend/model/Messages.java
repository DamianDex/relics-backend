package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Messages {
	/**
	 * Common
	 */
	INNER_SERVER_ERROR(Result.ERROR ,"Wystpił problem z serwerem. Spróbuj później."),
	
	/**
	 * ApplicationUser connected
	 */
	USER_CREATED(Result.SUCCESS, "Pomyślnie utworzono użytkownika."),
	USERNAME_CONFLICT(Result.ERROR, "Uzytkownik o podanym adresie email jest już zarejestrowany!"),
	BAD_USERNAME_FORMAT(Result.ERROR, "Podany login nie jest właściwym adresem email!"),
	INVALID_PASSWORD(Result.ERROR, "Podano niewłaściwe hasło!"),
	NO_SUCH_USER(Result.ERROR, "Podany użytkownik nie istnieje!"),
	USER_NOT_VERIFIED(Result.ERROR, "Konto dla tego użytkownika nie zostało aktywowane."),
	LOGGED_OUT(Result.SUCCESS, "Zostałeś pomyślnie wylogowany.");

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
