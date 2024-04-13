package com.codecrafters.todolistbackend.db.collections;

import org.bson.BsonInvalidOperationException;
import org.bson.types.ObjectId;

public class CollectionsUtils {

  private CollectionsUtils() {}

  public static String getHexString(ObjectId id) {
    try {
      return id.toHexString();
    } catch (BsonInvalidOperationException invalidTypeException) {
      throw new IllegalArgumentException(
          "The given bson type cannot be converted to a hex string: " + id.getClass());
    }
  }
}
