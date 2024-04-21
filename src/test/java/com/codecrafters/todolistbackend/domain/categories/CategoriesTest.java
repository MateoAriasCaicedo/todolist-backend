package com.codecrafters.todolistbackend.domain.categories;

import static org.junit.jupiter.api.Assertions.*;

import com.codecrafters.todolistbackend.domain.categories.exceptions.CategoryDoesNotExistsException;
import com.codecrafters.todolistbackend.domain.categories.exceptions.DuplicateCategoryException;
import com.codecrafters.todolistbackend.domain.categories.exceptions.EmptyCategoryIdentifierException;
import java.util.List;
import java.util.function.Consumer;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CategoriesTest {

  static ObjectId testUser;

  static CategoryController categoryController;

  static CategoryService categoryService;

  static CategoryRepository categoryRepository;

  @BeforeAll
  static void setup() {
    testUser = new ObjectId("6618560d03f1180ec0cf0e66");
    categoryController = new CategoryController();
    deleteTestUserCategories();
  }

  static void deleteTestUserCategories() {
    List<String> userCategories = categoryController.getAllUserCategories(testUser);
    Consumer<String> tryCategoryDeletion =
        category -> {
          try {
            categoryController.deleteCategory(testUser, category);
          } catch (CategoryDoesNotExistsException exception) {
            // Attempt to delete a default category
          }
        };

    userCategories.forEach(tryCategoryDeletion);
  }

  @AfterEach
  void cleanTestUserCategories() {
    deleteTestUserCategories();
  }

  @Test
  void createEmptyCategory() {
    assertThrows(
        EmptyCategoryIdentifierException.class,
        () -> categoryController.createCategory(testUser, ""));
  }

  @Test
  void createCategory() {
    categoryController.createCategory(testUser, "new category");
    assertTrue(categoryController.getAllUserCategories(testUser).contains("new category"));
  }

  @Test
  void deleteNonExistentCategory() {
    assertThrows(
        CategoryDoesNotExistsException.class,
        () -> categoryController.deleteCategory(testUser, "category"));
  }

  @Test
  void deleteCategory() {
    categoryController.createCategory(testUser, "category");
    categoryController.deleteCategory(testUser, "category");
    assertFalse(categoryController.getAllUserCategories(testUser).contains("category"));
  }

  @Test
  void createDuplicatedCategory() {
    categoryController.createCategory(testUser, "category");
    assertThrows(
        DuplicateCategoryException.class,
        () -> categoryController.createCategory(testUser, "category"));
  }

  @Test
  void getAllUserCategories() {
    List<String> categories = List.of("1", "2", "3");
    categories.forEach(category -> categoryController.createCategory(testUser, category));
    assertTrue(categoryController.getAllUserCategories(testUser).containsAll(categories));
  }
}
