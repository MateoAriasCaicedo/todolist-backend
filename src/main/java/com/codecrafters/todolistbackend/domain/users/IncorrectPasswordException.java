package com.codecrafters.todolistbackend.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class IncorrectPasswordException extends RuntimeException {

  IncorrectPasswordException(String password, String userName) {
    super("This password " + password + " is incorrect for the user: " + userName);
  }
}
