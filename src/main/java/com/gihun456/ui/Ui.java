package com.gihun456.ui;

import com.gihun456.model.Task;
import java.util.List;

/**
 * Handles all user-visible output for the application.
 */
public class Ui {
    private static final String BANNER = """
                          ____ _ _                 _  _  ____   __   
                         / ___(_) |__  _   _ _ __ | || || ___| / /_  
                        | |  _| | '_ \\| | | | '_ \\| || ||___ \\| '_ \\ 
                        | |_| | | | | | |_| | | | |__   _|__) | (_) |
                         \\____|_|_| |_|\\__,_|_| |_|  |_||____/ \\___/ 
                         """;

    private static final String GREETING = "Hello! I'm Gihun456.\nWhat can I do for you?";
    private static final String FAREWELL = "Bye. Hope to see you again soon!";
    private static final String SEP = "____________________________________________________________";

    private static final String LIST_TASKS = "Here are the tasks in your list:";
    private static final String LIST_MATCHING_TASKS = "Here are the matching tasks in your list:";
    private static final String NO_MATCHING_TASKS = "No matching tasks found.";
    private static final String ADD_TASK = "Got it. I've added this task:";
    private static final String REMOVE_TASK = "Noted. I've removed this task:";
    private static final String MARK_TASK = "Nice! I've marked this task as done:";
    private static final String UNMARK_TASK = "OK, I've marked this task as not done yet:";

    public void showGreeting() {
        System.out.println(BANNER);
        System.out.println(GREETING);
        showLine();
    }

    public void showLine() {
        System.out.println(SEP);
    }

    public void showFarewell() {
        System.out.println(FAREWELL);
    }

    public void showError(String message) {
        System.out.println("ERROR: " + message);
    }

    public void showEmptyList() {
        System.out.println("Storage empty.");
        showLine();
    }

    public void showNoMatchingTasks() {
        System.out.println(NO_MATCHING_TASKS);
        showLine();
    }

    public void showTaskList(List<Task> tasks, boolean isMatching) {
        if (isMatching) {
            System.out.println(LIST_MATCHING_TASKS);
        } else {
            System.out.println(LIST_TASKS);
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task currentTask = tasks.get(i);
            System.out.println((i + 1) + ". " + currentTask.toString());
        }
        showLine();
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(ADD_TASK);
        System.out.println(task.toString());
        System.out.println(String.format("Now you have %d tasks in the list.", taskCount));
        showLine();
    }

    public void showTaskRemoved(Task task, int taskCount) {
        System.out.println(REMOVE_TASK);
        System.out.println(task.toString());
        System.out.println(String.format("Now you have %d tasks in the list.", taskCount));
        showLine();
    }

    public void showTaskMarked(Task task) {
        System.out.println(MARK_TASK);
        System.out.println(task.toString());
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(UNMARK_TASK);
        System.out.println(task.toString());
        showLine();
    }
}
