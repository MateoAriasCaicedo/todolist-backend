package com.codecrafters.todolistbackend.database;

import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;

public class PullsProvider {

  private PullsProvider() {}

  public static Bson pullUserCategory(String category) {
    if (category == null) {
      throw new IllegalArgumentException("The pull category cannot be null");
    }
    return Updates.pull(UserFields.CATEGORIES, category);
  }
}
