package com.codecrafters.todolistbackend.database;

import com.codecrafters.todolistbackend.database.fields.TaskFields;
import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;

public class FiltersProvider {

  private FiltersProvider() {}

  public static Bson equalUserID(ObjectId userID) {
    Assert.notNull(userID, "The user ID must not be null.");
    return Filters.eq(UserFields.ID, userID);
  }

  public static Bson equalUsernameFilter(String username) {
    Assert.notNull(username, "The username must not be null.");
    return Filters.eq(UserFields.USERNAME, username);
  }

  public static Bson tasksCategoryFilter(String category) {
    Assert.notNull(category, "The category must not be null.");
    return Filters.eq(TaskFields.FULLY_QUALIFIED_CATEGORY, category);
  }

  public static Bson equalTaskID(Object taskID) {
    Assert.notNull(taskID, "The task ID must not be null.");
    return Filters.eq(TaskFields.ID, taskID);
  }
}
