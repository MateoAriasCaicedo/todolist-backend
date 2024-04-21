package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.domain.categories.exceptions.CategoryDoesNotExistsException;
import com.codecrafters.todolistbackend.domain.categories.exceptions.EmptyCategoryIdentifierException;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/** Controller that allows operations with user categories. */
@Slf4j
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController() {
    this.categoryService = new CategoryService(new CategoryRepository());
  }

  /**
   * Creates a new category for the specified user.
   *
   * @param userID The ID of the user who will own the category.
   * @param category The category to be created.
   * @throws IllegalArgumentException If the user ID or category is null.
   * @throws UserDoesNotExistsException If the user does not exist in the database.
   * @throws EmptyCategoryIdentifierException If the given category identified is empty.
   */
  public void createCategory(ObjectId userID, String category)
      throws IllegalArgumentException,
          UserDoesNotExistsException,
          EmptyCategoryIdentifierException {

    log.debug("Creating category {} for user with ID {}", category, userID);

    if (userID == null) {
      log.warn("An attempt to create a category with null user ID was done");
      throw new IllegalArgumentException("The user ID cannot be null");
    }

    if (category == null) {
      log.warn("An attempt to create a category with null category was done");
      throw new IllegalArgumentException("The category cannot be null");
    }

    categoryService.createUserCategory(userID, category);
  }

  /**
   * Deletes a category associated to the specified user.
   *
   * @param userID The ID of the user which category will be deleted from its categories.
   * @param category The category to be deleted from the user.
   * @throws IllegalArgumentException If the userID or category is null.
   * @throws UserDoesNotExistsException If the user does not exist in the database.
   * @throws CategoryDoesNotExistsException If the category is not part of the registered categories
   *     for the user.
   */
  public void deleteCategory(ObjectId userID, String category)
      throws IllegalArgumentException, UserDoesNotExistsException, CategoryDoesNotExistsException {

    log.debug("Deleting a category {} of the user with ID {}", category, userID);

    if (userID == null) {
      log.warn("Attempt to delete a category with null user ID");
      throw new IllegalArgumentException("The user ID cannot be null");
    }

    if (category == null) {
      log.warn("Attempt to delete a category with null category identified");
      throw new IllegalArgumentException("The category cannot be null");
    }

    categoryService.deleteUserCategory(userID, category);
  }

  /**
   * Retrieves all the categories associated with a user, including the default categories that all
   * users have.
   *
   * @param userID The ID of the user which categories will be retrieved.
   * @return The list of categories associated with the user, both default and custom ones.
   * @throws UserDoesNotExistsException If the specified user does not exist in the database.
   */
  public List<String> getAllUserCategories(ObjectId userID) throws UserDoesNotExistsException {
    log.debug("Retrieving all user categories for user with ID {}", userID);

    if (userID == null) {
      log.warn("Attempt to retrieve categories associated with a null user ID");
      throw new IllegalArgumentException("The user ID cannot be null");
    }

    return categoryService.getAllUserCategories(userID);
  }
}
