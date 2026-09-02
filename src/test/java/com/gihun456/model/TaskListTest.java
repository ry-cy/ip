package com.gihun456.model;

import com.gihun456.GihunException;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {
    @Test
    public void add_singleTask_taskIsStored() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Read book");

        taskList.add(todo);

        assertEquals(1, taskList.size());
        assertSame(todo, taskList.get(0));
    }

    @Test
    public void addAll_multipleTasks_tasksAreAppended() {
        TaskList taskList = new TaskList();
        Todo first = new Todo("Read book");
        Todo second = new Todo("Write code");

        taskList.addAll(List.of(first, second));

        assertEquals(2, taskList.size());
        assertSame(first, taskList.get(0));
        assertSame(second, taskList.get(1));
    }

    @Test
    public void remove_validIndex_removesAndReturnsTask() {
        TaskList taskList = new TaskList();
        Todo first = new Todo("Read book");
        Todo second = new Todo("Write code");
        taskList.addAll(List.of(first, second));

        Todo removed = (Todo) taskList.remove(0);

        assertSame(first, removed);
        assertEquals(1, taskList.size());
        assertSame(second, taskList.get(0));
    }

    @Test
    public void getValidIndex_validPositiveInput_returnsZeroBasedIndex() {
        TaskList taskList = new TaskList();
        taskList.addAll(List.of(new Todo("Read book"), new Todo("Write code"), new Todo("Sleep")));

        assertEquals(1, assertDoesNotThrow(() -> taskList.getValidIndex("2")));
    }

    @Test
    public void getValidIndex_nullInput_throwsGihunException() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));

        assertThrows(GihunException.class, () -> taskList.getValidIndex(null));
    }

    @Test
    public void getValidIndex_blankInput_throwsGihunException() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));

        assertThrows(GihunException.class, () -> taskList.getValidIndex("   "));
    }

    @Test
    public void getValidIndex_nonNumericInput_throwsGihunException() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));

        assertThrows(GihunException.class, () -> taskList.getValidIndex("abc"));
    }

    @Test
    public void getValidIndex_outOfRangeInput_throwsGihunException() {
        TaskList taskList = new TaskList();
        taskList.addAll(List.of(new Todo("Read book"), new Todo("Write code")));

        assertThrows(GihunException.class, () -> taskList.getValidIndex("3"));
        assertThrows(GihunException.class, () -> taskList.getValidIndex("0"));
    }

    @Test
    public void markTask_validIndex_marksSelectedTaskDone() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Read book");
        taskList.add(todo);

        taskList.markTask(0);

        assertEquals("X", taskList.get(0).getStatusIcon());
    }

    @Test
    public void unmarkTask_doneTask_marksSelectedTaskNotDone() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Read book");
        taskList.add(todo);
        taskList.markTask(0);

        taskList.unmarkTask(0);

        assertEquals(" ", taskList.get(0).getStatusIcon());
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        TaskList taskList = new TaskList();

        assertTrue(taskList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));

        assertFalse(taskList.isEmpty());
    }

    @Test
    public void getMatchedTasks_blankKeyword_throwsGihunException() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));

        assertThrows(GihunException.class, () -> taskList.getMatchedTasks("   "));
    }

    @Test
    public void getMatchedTasks_caseInsensitiveKeyword_returnsMatchingTasks() {
        TaskList taskList = new TaskList();
        Todo readBook = new Todo("Read book");
        Todo writeCode = new Todo("Write code");
        Todo reviewNotes = new Todo("Review notes");
        taskList.addAll(List.of(readBook, writeCode, reviewNotes));

        List<Task> matchedTasks = assertDoesNotThrow(() -> taskList.getMatchedTasks("BOOK"));

        assertEquals(List.of(readBook), matchedTasks);
    }

    @Test
    public void getMatchedTasks_noMatches_returnsEmptyList() {
        TaskList taskList = new TaskList();
        taskList.addAll(List.of(new Todo("Read book"), new Todo("Write code")));

        List<Task> matchedTasks = assertDoesNotThrow(() -> taskList.getMatchedTasks("travel"));

        assertTrue(matchedTasks.isEmpty());
    }

    @Test
    public void asList_returnsCopyOfTasks() {
        TaskList taskList = new TaskList();
        Todo first = new Todo("Read book");
        Todo second = new Todo("Write code");
        taskList.addAll(List.of(first, second));

        List<Task> tasks = taskList.asList();
        tasks.add(new Todo("Extra task"));

        assertEquals(2, taskList.size());
        assertEquals(List.of(first, second), taskList.asList());
    }
}
