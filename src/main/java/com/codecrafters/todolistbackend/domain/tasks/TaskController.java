package com.codecrafters.todolistbackend.domain.tasks;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
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
}
