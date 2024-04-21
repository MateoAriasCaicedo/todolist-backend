package com.codecrafters.todolistbackend.exceptions;

public class ServerSideErrorException extends RuntimeException {

  public ServerSideErrorException(Exception exception) {
    super("There occurred an error on the server logic", exception);
  }
}
