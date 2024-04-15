package com.codecrafters.todolistbackend.database.fields;

/**
 * A utility class containing the fields in the {@code task} document that is stored inside the
 * {@code users} collection (The {@code tasks} field inside the {@code users} collection).
 */
public class TaskFields {

  /** The ID field for the task. */
  public static final String ID = "_id";

  /** The title field for the task. */
  public static final String TITLE = "title";

  /** The description field for the task. */
  public static final String DESCRIPTION = "description";

  /** The due date field for the task. */
  public static final String DUE_DATE = "dueDate";

  /** The category field for the task. */
  public static final String CATEGORY = "category";

  /**
   * The fully qualified field name for the category inside the {@code task} document, which is
   * embedded into the {@code users} collection.
   */
  public static final String FULLY_QUALIFIED_CATEGORY = "tasks.category";

  /** The completed field for the task. */
  public static final String COMPLETED = "completed";

  /** The tags field for the task. */
  public static final String TAGS = "tags";

  private TaskFields() {}
}
