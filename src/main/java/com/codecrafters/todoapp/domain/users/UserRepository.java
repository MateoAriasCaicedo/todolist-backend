package com.codecrafters.todoapp.domain.users;

import static com.mongodb.client.model.Filters.eq;

import com.codecrafters.todoapp.db.DBCollections;
import com.codecrafters.todoapp.db.DBNames;
import com.codecrafters.todoapp.db.collections.fields.UserFields;
import com.codecrafters.todoapp.exceptions.UserDoesNotExistsException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  private final MongoClient mongoClient;

  public UserRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  private MongoCollection<Document> getUsersCollection() {
    return mongoClient.getDatabase(DBNames.TODO).getCollection(DBCollections.USERS);
  }

  String createUser(UserCreationDTO user) throws UserDoesNotExistsException {
    MongoCollection<Document> userCollection = getUsersCollection();
    InsertOneResult result = userCollection.insertOne(user.asMongoDocument());

    if (result.getInsertedId() == null) {
      throw new UserAlreadyExistsException(user.email(), user.username());
    }
    return result.getInsertedId().asObjectId().getValue().toHexString();
  }

  String findUser(String username, String password) throws UserDoesNotExistsException {
    MongoCollection<Document> userCollection = getUsersCollection();
    Document user = userCollection.find(eq(UserFields.USERNAME, username)).first();

    if (user == null) {
      throw new UserDoesNotExistsException(username);
    } else if (!user.getString(UserFields.PASSWORD).equals(password)) {
      throw new IncorrectPasswordException(password, user.getString(UserFields.USERNAME));
    }
    return user.getString(UserFields.ID);
  }
}
