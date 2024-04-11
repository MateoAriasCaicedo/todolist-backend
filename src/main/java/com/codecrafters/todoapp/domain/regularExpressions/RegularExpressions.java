package com.codecrafters.todoapp.domain.regularExpressions;

public enum RegularExpressions {
  PASSWORD_VALIDATION("^[A-Za-z0-9]{8,}$"),

  EMAIL_VALIDATION("^[A-Za-zd.-]{1,}@[A-Za-zd.-]{1,}[.]{1}[A-Za-zd.-]{1,}$"),

  DATE_VALIDATION("^(?!0000)[0-9]{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12][0-9]|3[01])$");

  public final String validationString;

  RegularExpressions(String validationString) {
    this.validationString = validationString;
  }
}
