package com.gihun456.model;

/**
 * Child class of parent Task object. 
 * Simple Todo representation without tracking of any date or time.
 */
public class Todo extends Task {
    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
