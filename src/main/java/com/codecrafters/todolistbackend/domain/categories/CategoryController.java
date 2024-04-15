package com.codecrafters.todolistbackend.domain.categories;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
class CategoryController {

  private final CategoryService categoryService;

  @PostMapping("/create/{userID}/{category}")
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
