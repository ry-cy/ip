package com.gihun456.command;

import com.gihun456.GihunException;

/**
 * Represents the valid operations supported by the application.
 */
public enum Operation {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    FIND("find"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    BYE("bye");

    private final String operationText;

    Operation(String operationText) {
        this.operationText = operationText;
    }

    /**
     * Converts the raw user command into an application operation.
     *
     * @param input Raw command text entered by the user.
     * @return Corresponding operation enum.
     * @throws GihunException If the command is not a supported action.
     */
    public static Operation fromInput(String input) throws GihunException {
        for (Operation operation : Operation.values()) {
            if (operation.operationText.equalsIgnoreCase(input)) {
                return operation;
            }
        }

        throw new GihunException("Invalid operation: " + input);
    }
}
