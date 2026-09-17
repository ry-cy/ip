package com.gihun456;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import com.gihun456.ui.UiMessages;

public class Gihun456Test {
    private static final LocalDateTime NOW = LocalDateTime.of(2026, 9, 13, 19, 37);
    private static final ZoneId ZONE = ZoneId.of("Asia/Singapore");

    @Test
    public void remindersCommand_validCommand_returnsReminderReport() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-reminders", ".txt");
        try {
            Gihun456 app = new Gihun456(
                    dataFile.toString(),
                    Clock.fixed(NOW.atZone(ZONE).toInstant(), ZONE));
            app.loadTasks();
            app.processCommand("deadline Submit report /by 14/09/2026 2037");

            assertEquals(
                    "Upcoming deadlines/events:\n"
                            + "1. [Deadline] Submit report (by: Sep 14 2026, 8:37 PM)",
                    app.processCommand("reminders"));
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    @Test
    public void remindersCommand_withArguments_returnsValidationError() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-reminders", ".txt");
        try {
            Gihun456 app = new Gihun456(dataFile.toString());

            assertEquals(
                    ErrorMessages.ERROR_PREFIX + ErrorMessages.REMINDERS_ARGUMENTS,
                    app.getResponse("reminders tomorrow"));
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    @Test
    public void conflictingDeadline_requiresConfirmationBeforeSaving() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-conflict", ".txt");
        try {
            Gihun456 app = new Gihun456(dataFile.toString());
            app.loadTasks();
            app.processCommand("event Team meeting /from 15/09/2026 1000 /to 15/09/2026 1100");

            assertEquals(
                    "This task conflicts with existing tasks:\n"
                            + "Proposed task:\n"
                            + "[D][ ] Submit report (by: Sep 15 2026, 10:30 AM)\n"
                            + "Conflicting tasks:\n"
                            + "1. [E][ ] Team meeting (from: Sep 15 2026, 10:00 AM to: Sep 15 2026, 11:00 AM)\n"
                            + "Add it anyway? (yes/no)",
                    app.processCommand("deadline Submit report /by 15/09/2026 1030"));
            assertEquals(1, Files.readAllLines(dataFile).size());

            assertEquals(
                    "Got it. I've added this task:\n"
                            + "[D][ ] Submit report (by: Sep 15 2026, 10:30 AM)\n"
                            + "Player 456, you now have 2 games in your list.",
                    app.processCommand(" YES "));
            assertEquals(2, Files.readAllLines(dataFile).size());
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    @Test
    public void conflictingEvent_noConfirmationResponse_doesNotAddTask() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-conflict-no", ".txt");
        try {
            Gihun456 app = new Gihun456(dataFile.toString());
            app.loadTasks();
            app.processCommand("deadline Submit report /by 15/09/2026 1030");

            app.processCommand("event Team meeting /from 15/09/2026 1000 /to 15/09/2026 1100");

            assertEquals(UiMessages.TASK_NOT_ADDED, app.processCommand("no"));
            assertEquals(1, Files.readAllLines(dataFile).size());
            assertTrue(app.processCommand("list").contains("Submit report"));
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    @Test
    public void invalidConfirmation_keepsPendingAdditionActive() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-conflict-invalid", ".txt");
        try {
            Gihun456 app = new Gihun456(dataFile.toString());
            app.loadTasks();
            app.processCommand("deadline Submit report /by 15/09/2026 1030");
            app.processCommand("event Team meeting /from 15/09/2026 1000 /to 15/09/2026 1100");

            assertEquals(
                    ErrorMessages.ERROR_PREFIX + ErrorMessages.INVALID_CONFIRMATION,
                    app.getResponse("list"));
            assertEquals(UiMessages.TASK_NOT_ADDED, app.processCommand("no"));
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    @Test
    public void byeDuringConfirmation_discardsPendingTask() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-conflict-bye", ".txt");
        try {
            Gihun456 app = new Gihun456(dataFile.toString());
            app.loadTasks();
            app.processCommand("deadline Submit report /by 15/09/2026 1030");
            app.processCommand("event Team meeting /from 15/09/2026 1000 /to 15/09/2026 1100");

            assertEquals(UiMessages.FAREWELL, app.processCommand("bye"));
            assertEquals(1, Files.readAllLines(dataFile).size());
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    @Test
    public void reversedEvent_isRejected() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-invalid-event", ".txt");
        try {
            Gihun456 app = new Gihun456(dataFile.toString());

            assertEquals(
                    ErrorMessages.ERROR_PREFIX + ErrorMessages.EVENT_END_BEFORE_START,
                    app.getResponse("event Invalid /from 15/09/2026 1100 /to 15/09/2026 1000"));
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    @Test
    public void legacyReversedEvent_loadsButDoesNotCauseConflict() throws Exception {
        Path dataFile = Files.createTempFile("gihun456-legacy-event", ".txt");
        try {
            Files.writeString(
                    dataFile,
                    "E |   | Legacy event | 2026-09-15T11:00 | 2026-09-15T10:00"
                            + System.lineSeparator());
            Gihun456 app = new Gihun456(dataFile.toString());
            app.loadTasks();

            assertTrue(app.processCommand("deadline New task /by 15/09/2026 1030")
                    .startsWith(UiMessages.ADD_TASK));
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }
}
