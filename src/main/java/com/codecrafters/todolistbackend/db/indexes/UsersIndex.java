package com.codecrafters.todolistbackend.db.indexes;

import java.util.Optional;

public enum UsersIndex {
  UNIQUE_USERNAME,
  UNIQUE_EMAIL;

  public static final String UNIQUE_USERNAME_INDEX = "uniqueUsernameIndex";

  public static final String UNIQUE_EMAIL_INDEX = "uniqueEmailIndex";

  public static Optional<UsersIndex> findViolatedIndex(String exceptionMessage) {
    if (exceptionMessage.contains(UNIQUE_USERNAME_INDEX)) {
      return Optional.of(UNIQUE_USERNAME);
    }

    if (exceptionMessage.contains(UNIQUE_EMAIL_INDEX)) {
      return Optional.of(UNIQUE_EMAIL);
    }

    return Optional.empty();
  }
}
