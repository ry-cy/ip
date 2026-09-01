package com.gihun456.model;

/**
 * Represents a task in the user's task list.
 * Supports todo, deadline, and event tasks.
 */
public class Task {
    private String taskName;
    private boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param taskName Description of the task.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return Description of the task.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns the status icon for display.
     *
     * @return "X" when done and a blank space otherwise.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + " " + this.taskName;
    }
}
