package com.gihun456.command;

import com.gihun456.GihunException;
import com.gihun456.model.Deadline;
import com.gihun456.model.Event;

/**
 * Parses raw user input into an operation and its arguments.
 */
public class Parser {
    /**
     * Represents one parsed user command.
     */
    public static class ParsedInput {
        private final Operation operation;
        private final String arguments;

        public ParsedInput(Operation operation, String arguments) {
            this.operation = operation;
            this.arguments = arguments;
        }

        public Operation getOperation() {
            return operation;
        }

        public String getArguments() {
            return arguments;
        }
    }

    /**
     * Converts a raw command line into a command and its argument text.
     *
     * @param input Raw input from the user.
     * @return Parsed input containing the operation and remaining text.
     * @throws GihunException If the command is empty or invalid.
     */
    public ParsedInput parse(String input) throws GihunException {
        if (input == null || input.trim().isEmpty()) {
            throw new GihunException("Empty command.");
        }

        String trimmed = input.trim();
        String[] parts = trimmed.split("\\s+", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        Operation operation = Operation.fromInput(command);
        return new ParsedInput(operation, arguments);
    }

    /**
     * Parses a deadline command argument block.
     */
    public Deadline parseDeadline(String arguments) throws GihunException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new GihunException("The description of a deadline cannot be empty.");
        }

        int byIndex = arguments.indexOf("/by");
        if (byIndex < 0) {
            throw new GihunException("A deadline must include '/by'.");
        }

        String taskName = arguments.substring(0, byIndex).trim();
        if (taskName.isEmpty()) {
            throw new GihunException("The description of a deadline cannot be empty.");
        }

        String dueDate = arguments.substring(byIndex + 3).trim();
        if (dueDate.isEmpty()) {
            throw new GihunException("The due date of a deadline cannot be empty.");
        }

        return new Deadline(taskName, dueDate);
    }

    /**
     * Parses an event command argument block.
     */
    public Event parseEvent(String arguments) throws GihunException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new GihunException("The description of an event cannot be empty.");
        }

        int fromIndex = arguments.indexOf("/from");
        int toIndex = arguments.indexOf("/to");

        if (fromIndex < 0 || toIndex < 0) {
            throw new GihunException("An event must include '/from' and '/to'.");
        }

        if (toIndex < fromIndex) {
            throw new GihunException("An event must specify '/from' before '/to'.");
        }

        String taskName = arguments.substring(0, fromIndex).trim();
        if (taskName.isEmpty()) {
            throw new GihunException("The description of an event cannot be empty.");
        }

        String startDate = arguments.substring(fromIndex + 5, toIndex).trim();
        String endDate = arguments.substring(toIndex + 3).trim();

        if (startDate.isEmpty()) {
            throw new GihunException("The start date of an event cannot be empty.");
        }
        if (endDate.isEmpty()) {
            throw new GihunException("The end date of an event cannot be empty.");
        }

        return new Event(taskName, startDate, endDate);
    }
}
