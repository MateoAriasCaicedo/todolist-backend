package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.db.DBCollections;
import com.codecrafters.todolistbackend.db.DBNames;
import com.codecrafters.todolistbackend.db.collections.fields.TaskFields;
import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
class TaskRepository {

  private final MongoClient mongoClient;

  TaskRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  private static Bson equalTaskIDFilter(ObjectId userID) {
    return Filters.eq(TaskFields.ID, userID);
  }

  private static Bson equalUserIDFilter(ObjectId userID) {
    return Filters.eq(UserFields.ID, userID);
  }

  private MongoCollection<Document> getUsersCollection() {
    return mongoClient.getDatabase(DBNames.TODO).getCollection(DBCollections.USERS);
  }

  <T> void updateField(ObjectId userID, ObjectId taskID, String field, T value) {
    var user = getUsersCollection().find(equalUserIDFilter(userID)).first();

    if (user == null) {
      throw new UserDoesNotExistsException(taskID);
    }

    Predicate<Document> isExpected = task -> task.getObjectId(TaskFields.ID).equals(taskID);

    var task =
        user.getList(UserFields.TASKS, Document.class).stream().filter(isExpected).findFirst();

    if (task.isEmpty()) {
      throw new TaskDoesNotExistsException(taskID);
    }

    deleteUserTask(userID, taskID);

    var taskDocument = task.get();
    taskDocument.remove(field);
    taskDocument.append(field, value);
    createUserTask(userID, Task.fromDocument(taskDocument).asCompletedTaskCreationDTO());
  }

  void createUserTask(ObjectId userID, TaskCreationDTO task) throws UserDoesNotExistsException {

    var createdTasks =
        getUsersCollection()
            .updateOne(
                equalTaskIDFilter(userID), Updates.push(UserFields.TASKS, task.asMongoDocument()));

    if (createdTasks.getModifiedCount() < 1) {
      throw new UserDoesNotExistsException(userID);
    }
  }

  void deleteUserTask(ObjectId userID, ObjectId taskID) {
    var updatedTasks =
        getUsersCollection()
            .updateOne(
                equalTaskIDFilter(userID), Updates.pull("tasks", new Document("_id", taskID)));

    if (updatedTasks.getModifiedCount() < 1) {
      throw new UserDoesNotExistsException(userID);
    }
  }

  List<Task> findAllUserTasks(ObjectId userID) throws UserDoesNotExistsException {
    var user = getUsersCollection().find(equalTaskIDFilter(userID)).first();

    if (user == null) {
      throw new UserDoesNotExistsException(userID);
    }

    var tasks = new LinkedList<Task>();

    for (Object document : user.get(UserFields.TASKS, List.class)) {
      tasks.add(Task.fromDocument((Document) document));
    }

    return tasks;
  }
}
