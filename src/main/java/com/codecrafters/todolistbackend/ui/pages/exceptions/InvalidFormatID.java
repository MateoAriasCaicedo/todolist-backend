package com.codecrafters.todolistbackend.ui.pages.exceptions;

public class InvalidFormatID extends Exception{
  public InvalidFormatID() {
    super("The given ID does not meet the format.");
  }
}
