package com.codecrafters.todolistbackend.db.filters;

import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class DBFilters {

  private DBFilters() {}

  public static Bson equalUserIDFilter(ObjectId userID) {
    return Filters.eq(UserFields.ID, userID);
  }
}
