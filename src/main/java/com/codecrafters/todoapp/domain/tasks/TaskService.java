package com.codecrafters.todoapp.domain.tasks;

import com.codecrafters.todoapp.db.collections.fields.TaskFields;
import com.codecrafters.todoapp.exceptions.UserDoesNotExistsException;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
class TaskService {

  private final TaskRepository taskRepository;

  TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  void addUserTask(ObjectId userID, TaskCreationDTO task) throws UserDoesNotExistsException {
    taskRepository.createUserTask(userID, task);
  }

  void deleteUserTask(ObjectId userID, ObjectId taskID) throws UserDoesNotExistsException {
    taskRepository.deleteUserTask(userID, taskID);
  }

  void completeUserTask(ObjectId userID, ObjectId taskID) throws UserDoesNotExistsException {
    taskRepository.updateField(userID, taskID, TaskFields.COMPLETED, Boolean.TRUE);
  }

  List<Task> getAllUserTasks(ObjectId userID) throws UserDoesNotExistsException {
    return taskRepository.findAllUserTasks(userID);
  }
}
