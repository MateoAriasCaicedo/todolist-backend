package com.codecrafters.todolistbackend.db.filters;

import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class DBFilter {

  private DBFilter() {}

  public static Bson equalUserIDFilter(ObjectId userID) {
    return Filters.eq(UserFields.ID, userID);
  }

  public static Bson equalUsernameFilter(String userName) {
    return Filters.eq(UserFields.USERNAME, userName);
  }
}
