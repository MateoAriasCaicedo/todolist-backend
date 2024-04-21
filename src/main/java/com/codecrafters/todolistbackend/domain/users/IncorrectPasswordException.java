package com.codecrafters.todolistbackend.domain.users;

public class IncorrectPasswordException extends RuntimeException {

  public IncorrectPasswordException(String password, String userName) {
    super("This password " + password + " is incorrect for the user: " + userName);
  }
}
