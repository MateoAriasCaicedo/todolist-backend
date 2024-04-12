package com.codecrafters.todolistbackend.domain.users;

class UserValidationRegex {
  static final String PASSWORD_VALIDATION = "^[A-Za-z0-9]{8,}$";

  static final String EMAIL_VALIDATION = "^[A-Za-zd.-]{1,}@[A-Za-zd.-]{1,}[.]{1}[A-Za-zd.-]{1,}$";
}
