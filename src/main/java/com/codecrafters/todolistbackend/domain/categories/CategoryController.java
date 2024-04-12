package com.codecrafters.todolistbackend.domain.categories;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PutMapping("/create/{userID}/{category}")
  void createCategory(@PathVariable ObjectId userID, @PathVariable String category) {
    categoryService.createUserCategory(userID, category);
  }

  @DeleteMapping("/delete/{userID}/{category}")
  void deleteCategory(@PathVariable ObjectId userID, @PathVariable String category) {
    categoryService.deleteUserCategory(userID, category);
  }

  @GetMapping("/all/{userID}")
  List<String> getAllUserCategories(@PathVariable ObjectId userID) {
    return categoryService.getAllUserCategories(userID);
  }
}
