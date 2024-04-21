package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.domain.validations.TodoValidator;
import java.util.List;
import org.bson.types.ObjectId;

public class TaskController {

  private final TaskService taskService;

  public TaskController() {
    this.taskService = new TaskService(new TaskRepository(), new TodoValidator());
  }

  public String addUserTask(ObjectId userID, TaskCreationDTO task) {
    return taskService.addUserTask(userID, task);
  }

  public void completeUserTask(ObjectId userID, ObjectId taskID) {
    taskService.completeUserTask(userID, taskID);
  }

  void updateUserTaskTitle(ObjectId userID, ObjectId taskID, String title) {
    taskService.updateTitleUserTask(userID, taskID, title);
  }

  void updateUserTaskDescription(ObjectId userID, ObjectId taskID, String description) {
    taskService.updateDescriptionUserTask(userID, taskID, description);
  }

  public void updateUserTaskDueDate(ObjectId userID, ObjectId taskID, String dueDate) {
    taskService.updateDescriptionUserTask(userID, taskID, dueDate);
  }

  public void updateUserTaskCategory(ObjectId userID, ObjectId taskID, String category) {
    taskService.updateCategoryUserTask(userID, taskID, category);
  }

  public void deleteUserTask(ObjectId userID, ObjectId taskID) {
    taskService.deleteUserTask(userID, taskID);
  }

  public List<Task> getAllUserTasks(ObjectId userID) {
    return taskService.getAllUserTasks(userID);
  }

  public List<Task> getCompleteTasks(ObjectId userID) {
    return taskService.getCompleteTasks(userID);
  }
}
