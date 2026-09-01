package com.gihun456.model;

import com.gihun456.GihunException;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the in-memory list of tasks and the operations that act on it.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list initialised from an existing collection.
     *
     * @param tasks Existing tasks to copy into the list.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Adds multiple tasks to the list.
     *
     * @param tasksToAdd Tasks to add.
     */
    public void addAll(List<Task> tasksToAdd) {
        tasks.addAll(tasksToAdd);
    }

    /**
     * Removes the task at the specified zero-based index.
     *
     * @param index Index of task to remove.
     * @return Removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Zero-based task index.
     * @return Task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Converts a user-facing task number into a valid zero-based list index.
     *
     * @param input User input describing the task number.
     * @return Zero-based index for the task.
     * @throws GihunException If the task number is missing or invalid.
     */
    public int getValidIndex(String input) throws GihunException {
        if (input == null || input.trim().isEmpty()) {
            throw new GihunException("The task number is invalid.");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new GihunException("The task number is invalid.", e);
        }

        int zeroBasedIndex = taskNumber - 1;
        if (zeroBasedIndex < 0 || zeroBasedIndex >= tasks.size()) {
            throw new GihunException("The task number is invalid.");
        }

        return zeroBasedIndex;
    }

    /**
     * Retrieves tasks with description matching a given keyword.
     *
     * @param matchingKey User input with a keyword to find matches.
     * @return A list of tasks with matches to the given keyword.
     * @throws GihunException If the keyword is empty.
     */
    public List<Task> getMatchedTasks(String matchingKey) throws GihunException {
        if (matchingKey == null || matchingKey.trim().isEmpty()) {
            throw new GihunException("The keyword cannot be empty.");
        }

        String normalizedKey = matchingKey.trim().toLowerCase();
        List<Task> result = new ArrayList<>();

        for (Task task : tasks) {
            String taskName = task.getTaskName();
            if (taskName.toLowerCase().contains(normalizedKey)) {
                result.add(task);
            }
        }

        return result;
    }

    /**
     * Marks a task as done.
     *
     * @param index Zero-based index of the task to mark.
     */
    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    /**
     * Marks a task as not done.
     *
     * @param index Zero-based index of the task to unmark.
     */
    public void unmarkTask(int index) {
        tasks.get(index).markAsNotDone();
    }

    /**
     * Checks whether the list is empty.
     *
     * @return True if there are no tasks in the list.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Current number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a snapshot of the current tasks in insertion order.
     *
     * @return List of tasks.
     */
    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }
}
