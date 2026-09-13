package com.gihun456;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

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
}
