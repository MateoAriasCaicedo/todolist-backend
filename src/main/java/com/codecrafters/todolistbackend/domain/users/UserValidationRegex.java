package com.codecrafters.todolistbackend.domain.users;

enum UserValidationRegex {
  PASSWORD_VALIDATION("^[A-Za-z0-9]{8,}$"),

  EMAIL_VALIDATION("^[A-Za-zd.-]{1,}@[A-Za-zd.-]{1,}[.]{1}[A-Za-zd.-]{1,}$");
  public final String validationString;

  UserValidationRegex(String validationString) {
    this.validationString = validationString;
  }
}
