package com.relics.backend.security.model;

public enum LoginResult {
	INVALID_PASSWORD, NO_SUCH_USER, USER_BLOCKED, USER_NOT_VERYFIED, SUCCESS, INNER_SERVER_ERROR;
}
