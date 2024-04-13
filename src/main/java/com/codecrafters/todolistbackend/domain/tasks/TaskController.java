package com.codecrafters.todolistbackend.domain.tasks;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
class TaskController {

  private final TaskService taskService;

  TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping("/add/{userID}")
  String addUserTask(@PathVariable ObjectId userID, @RequestBody TaskCreationDTO task) {
    return taskService.addUserTask(userID, task);
  }

  @PutMapping("/complete/{userID}/{taskID}")
  void completeUserTask(@PathVariable ObjectId userID, @PathVariable ObjectId taskID) {
    taskService.completeUserTask(userID, taskID);
  }

  @PutMapping("/update/title/{userID}/{taskID}")
  void updateUserTaskTitle(
      @PathVariable ObjectId userID, @PathVariable ObjectId taskID, @RequestBody String title) {
    taskService.updateTitleUserTask(userID, taskID, title);
  }

  @PutMapping("/update/description/{userID}/{taskID}/{description}")
  void updateUserTaskDescription(
      @PathVariable ObjectId userID,
      @PathVariable ObjectId taskID,
      @PathVariable String description) {
    taskService.updateDescriptionUserTask(userID, taskID, description);
  }

  @PutMapping("/update/duedate/{userID}/{taskID}/{dueDate}")
  void updateUserTaskDueDate(
      @PathVariable ObjectId userID, @PathVariable ObjectId taskID, @PathVariable String dueDate) {
    taskService.updateDescriptionUserTask(userID, taskID, dueDate);
  }

  @PutMapping("/update/category/{userID}/{taskID}/{category}")
  void updateUserTaskCategory(
      @PathVariable ObjectId userID, @PathVariable ObjectId taskID, @PathVariable String category) {
    taskService.updateCategoryUserTask(userID, taskID, category);
  }

  @DeleteMapping("/delete/{userID}/{taskID}")
  void deleteUserTask(@PathVariable ObjectId userID, @PathVariable ObjectId taskID) {
    taskService.deleteUserTask(userID, taskID);
  }

  @GetMapping("/all/{userID}")
  List<Task> getAllUserTasks(@PathVariable ObjectId userID) {
    return taskService.getAllUserTasks(userID);
  }
}
