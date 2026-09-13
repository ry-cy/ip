package com.gihun456.ui;

/**
 * Stores user-facing messages displayed by the application.
 */
public final class UiMessages {
    public static final String BANNER = """
                         __                    _   _\s\s
                        /__ o |_      ._ |_|_ |_  |_\s\s
                        \\_| | | | |_| | |  |   _) |_)

                         """;

    public static final String GREETING = "Hello! I'm Gihun456.\nWhat can I do for you?";
    public static final String FAREWELL = "Bye. Hope to see you again soon!";
    public static final String SEP = "____________________________________________________________";

    public static final String LIST_TASKS = "Here are the tasks in your list:";
    public static final String LIST_MATCHING_TASKS = "Here are the matching tasks in your list:";
    public static final String NO_MATCHING_TASKS = "No matching tasks found.";
    public static final String ADD_TASK = "Got it. I've added this task:";
    public static final String REMOVE_TASK = "Noted. I've removed this task:";
    public static final String MARK_TASK = "Nice! I've marked this task as done:";
    public static final String UNMARK_TASK = "OK, I've marked this task as not done yet:";
    public static final String EMPTY_STORAGE = "Storage empty.";
    public static final String UPCOMING_REMINDERS = "Upcoming deadlines/events:";
    public static final String MISSED_REMINDERS = "Missed deadlines/events:";
    public static final String NO_REMINDERS = "No upcoming or missed reminders.";
    public static final String CONFLICT_WARNING = "This task conflicts with existing tasks:";
    public static final String PROPOSED_TASK = "Proposed task:";
    public static final String CONFLICTING_TASKS = "Conflicting tasks:";
    public static final String CONFLICT_CONFIRMATION = "Add it anyway? (yes/no)";
    public static final String TASK_NOT_ADDED = "Okay, I did not add the task.";

    private UiMessages() {
    }
}
