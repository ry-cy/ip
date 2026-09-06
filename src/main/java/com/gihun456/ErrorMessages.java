package com.gihun456;

/**
 * Stores error messages used throughout the application.
 */
public final class ErrorMessages {
    // Gihun456
    public static final String TODO_DESCRIPTION_EMPTY = "The description of a todo cannot be empty.";
    public static final String UNSUPPORTED_OPERATION = "Unsupported operation.";
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred. Please try again.";

    // Ui
    public static final String ERROR_PREFIX = "ERROR: ";

    // Parser
    public static final String EMPTY_COMMAND = "Empty command.";
    public static final String DEADLINE_DESCRIPTION_EMPTY = "The description of a deadline cannot be empty.";
    public static final String DEADLINE_BY_MISSING = "A deadline must include '/by'.";
    public static final String DEADLINE_DUE_DATE_EMPTY = "The due date of a deadline cannot be empty.";
    public static final String EVENT_DESCRIPTION_EMPTY = "The description of an event cannot be empty.";
    public static final String EVENT_FROM_TO_MISSING = "An event must include '/from' and '/to'.";
    public static final String EVENT_FROM_BEFORE_TO = "An event must specify '/from' before '/to'.";
    public static final String EVENT_START_DATE_EMPTY = "The start date of an event cannot be empty.";
    public static final String EVENT_END_DATE_EMPTY = "The end date of an event cannot be empty.";

    // TaskList
    public static final String INVALID_TASK_NUMBER = "The task number is invalid.";
    public static final String EMPTY_KEYWORD = "The keyword cannot be empty.";

    // Deadline
    public static final String DEADLINE_DATE_EMPTY = "Deadline date cannot be empty.";
    public static final String INVALID_DEADLINE_FORMAT =
            "Invalid deadline format. Please use dd/MM/yyyy or yyyy-MM-dd, optionally followed by a space and HHmm.";

    // Event
    public static final String EVENT_DATE_EMPTY = "Event date cannot be empty.";
    public static final String INVALID_EVENT_DATE_FORMAT =
            "Invalid event date format. Please use dd/MM/yyyy or yyyy-MM-dd, optionally followed by a space and HHmm.";

    // Storage
    public static final String CANNOT_ACCESS_FILE = "Unable to access tasks file.";
    public static final String CANNOT_PARSE_TASK = "Unable to parse task.";

    private ErrorMessages() {
    }

    // Operation
    /**
     * Creates an error message for an unsupported command.
     *
     * @param input Command text that was not recognised.
     * @return Error message containing the invalid command.
     */
    public static String invalidOperation(String input) {
        return "Invalid operation: " + input;
    }
}
