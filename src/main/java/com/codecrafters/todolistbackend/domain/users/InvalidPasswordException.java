package com.codecrafters.todolistbackend.domain.users;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String password) {
        super("The given password is invalid: " + password);
    }
}
