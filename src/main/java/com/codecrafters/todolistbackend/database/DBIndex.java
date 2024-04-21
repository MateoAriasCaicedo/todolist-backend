package com.codecrafters.todolistbackend.database;

import com.mongodb.MongoWriteException;
import java.util.Optional;

public enum DBIndex {
  UNIQUE_USERNAME,
  UNIQUE_EMAIL;

  public static final String UNIQUE_USERNAME_INDEX = "uniqueUsernameIndex";

  public static final String UNIQUE_EMAIL_INDEX = "uniqueEmailIndex";

  public static Optional<DBIndex> findViolatedIndex(MongoWriteException exception) {
    var exceptionMessage = exception.getMessage();

    if (exceptionMessage.contains(UNIQUE_USERNAME_INDEX)) {
      return Optional.of(UNIQUE_USERNAME);
    }

    if (exceptionMessage.contains(UNIQUE_EMAIL_INDEX)) {
      return Optional.of(UNIQUE_EMAIL);
    }

    return Optional.empty();
  }
}
