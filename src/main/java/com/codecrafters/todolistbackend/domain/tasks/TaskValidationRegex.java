package com.codecrafters.todolistbackend.domain.tasks;

public enum TaskValidationRegex {

  DATE_VALIDATION("^(?!0000)[0-9]{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12][0-9]|3[01])$");

  public final String validationString;

  TaskValidationRegex(String validationString) {
    this.validationString = validationString;
  }

}
