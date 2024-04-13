package com.codecrafters.todolistbackend.db.indexes;

public enum UsersIndex {
  UNIQUE_USERNAME,
  UNIQUE_EMAIL;

  public static final String UNIQUE_USERNAME_INDEX = "uniqueUsernameIndex";

  public static final String UNIQUE_EMAIL_INDEX = "uniquePasswordIndex";

  public static UsersIndex findViolatedIndex(String exceptionMessage) {
    return exceptionMessage.contains(UNIQUE_USERNAME_INDEX) ? UNIQUE_USERNAME : UNIQUE_EMAIL;
  }
}
