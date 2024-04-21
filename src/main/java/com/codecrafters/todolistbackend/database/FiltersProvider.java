package com.codecrafters.todolistbackend.database;

import com.codecrafters.todolistbackend.database.fields.TaskFields;
import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class FiltersProvider {

  private FiltersProvider() {}

  public static Bson equalUserID(ObjectId userID) throws IllegalArgumentException {

    if (userID == null) {
      throw new IllegalArgumentException("The user ID must not be null.");
    }

    return Filters.eq(UserFields.ID, userID);
  }

  public static Bson equalUsernameFilter(String username) throws IllegalArgumentException {
    if (username == null) {
      throw new IllegalArgumentException("The username must not be null.");
    }

    return Filters.eq(UserFields.USERNAME, username);
  }

  public static Bson tasksCategoryFilter(String category) {
    if (category == null) {
      throw new IllegalArgumentException("The category must not be null.");
    }

    return Filters.eq(TaskFields.FULLY_QUALIFIED_CATEGORY, category);
  }

  public static Bson equalTaskID(Object taskID) {
    if (taskID == null) {
      throw new IllegalArgumentException("The task ID must not be null.");
    }
    return Filters.eq(TaskFields.ID, taskID);
  }
}
