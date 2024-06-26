package com.codecrafters.todolistbackend.domain.tasks;

import com.codecrafters.todolistbackend.domain.tasks.exceptions.EmptyTaskTitleException;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.InvalidDateFormatException;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.InvalidTaskCategory;
import com.codecrafters.todolistbackend.domain.validations.TodoValidator;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.util.List;
import org.bson.types.ObjectId;

public class TaskController {

  private final TaskService taskService;

  public TaskController() {
    this.taskService = new TaskService(new TaskRepository(), new TodoValidator());
  }

  public String addUserTask(ObjectId userID, TaskCreationDTO task)
      throws InvalidTaskCategory,
          EmptyTaskTitleException,
          UserDoesNotExistsException,
          InvalidDateFormatException {
    return taskService.addUserTask(userID, task);
  }

  public void setTaskAsComplete(ObjectId userID, ObjectId taskID) {
    taskService.completeUserTask(userID, taskID);
  }

  public void updateUserTaskTitle(ObjectId userID, ObjectId taskID, String title) {
    taskService.updateTitleUserTask(userID, taskID, title);
  }

  public void updateUserTaskDescription(ObjectId userID, ObjectId taskID, String description) {
    taskService.updateDescriptionUserTask(userID, taskID, description);
  }

  public void updateUserTaskDueDate(ObjectId userID, ObjectId taskID, String dueDate) {
    taskService.updateDueDateUserTask(userID, taskID, dueDate);
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

  public List<Task> getTodayTasks(ObjectId userID) {
    return taskService.getTodayTasks(userID);
  }

  public List<Task> getOverdueTasks(ObjectId userID) {
    return taskService.getOverdueTasks(userID);
  }

  public List<Task> getTasksByCategory(ObjectId userID, String category) {
    return taskService.getTasksByCategory(userID, category);
  }
}
