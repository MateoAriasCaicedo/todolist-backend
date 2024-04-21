package com.codecrafters.todolistbackend.database;

import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.util.Assert;

public class PullsProvider {

  private PullsProvider() {}

  public static Bson pullUserCategory(String category) {
    Assert.notNull(category, "The pull category cannot be null");
    return Updates.pull(UserFields.CATEGORIES, category);
  }
}
