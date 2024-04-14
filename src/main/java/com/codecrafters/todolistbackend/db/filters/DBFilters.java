package com.codecrafters.todolistbackend.db.filters;

import com.codecrafters.todolistbackend.db.collections.fields.TaskFields;
import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;

public class DBFilters {

  private DBFilters() {}

  public static Bson equalUserID(ObjectId userID) {
    return Filters.eq(UserFields.ID, userID);
  }

  public static Bson equalUsernameFilter(String userName) {
    return Filters.eq(UserFields.USERNAME, userName);
  }

  public static Bson tasksCategoryFilter(String category) {
    return Filters.eq(TaskFields.FULLY_QUALIFIED_CATEGORY, category);
  }

  public static Bson equalTaskID(Object taskID) {
    return Filters.eq(TaskFields.ID, taskID);
  }
}
