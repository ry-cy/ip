package com.gihun456.model;

import com.gihun456.GihunException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a task that must be completed by a specific date or time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final LocalDateTime dueDate;

    /**
     * Creates a deadline task from a user-provided date-time text.
     *
     * @param taskName Description of the task.
     * @param dueDateText Date-time string entered by the user.
     * @throws GihunException If the date format is invalid.
     */
    public Deadline(String taskName, String dueDateText) throws GihunException {
        super(taskName);
        this.dueDate = parseDueDate(dueDateText);
    }

    /**
     * Creates a deadline task from a Java date-time object.
     *
     * @param taskName Description of the task.
     * @param dueDate Exact deadline date-time.
     */
    public Deadline(String taskName, LocalDateTime dueDate) {
        super(taskName);
        this.dueDate = dueDate;
    }

    /**
     * Returns the deadline date and time.
     *
     * @return Deadline timestamp.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Formats a date-time for storage.
     *
     * @param dateTime Date-time to format.
     * @return ISO date-time string.
     */
    public static String formatForStorage(LocalDateTime dateTime) {
        return STORAGE_FORMATTER.format(dateTime);
    }

    private static LocalDateTime parseDueDate(String dueDateText) throws GihunException {
        String trimmed = dueDateText.trim();
        if (trimmed.isEmpty()) {
            throw new GihunException("Deadline date cannot be empty.");
        }

        trimmed = trimmed.replaceAll("(?i)(\\d{1,2}:\\d{2})\\s*(am|pm)\\b", "$1 $2");
        trimmed = trimmed.replaceAll("(?i)(\\d{1,2})\\s*(am|pm)\\b", "$1 $2");
        trimmed = trimmed.toUpperCase(Locale.ROOT);

        DateTimeFormatter[] dateTimeFormatters = {
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"),
                DateTimeFormatter.ofPattern("d/M/yyyy h a", Locale.US),
                DateTimeFormatter.ofPattern("d/M/yyyy h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy h a", Locale.US),
                DateTimeFormatter.ofPattern("dd-MM-yyyy h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy h a", Locale.US),
                DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd h a", Locale.US),
                DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("yyyy/M/d HHmm"),
                DateTimeFormatter.ofPattern("yyyy/M/d HH:mm"),
                DateTimeFormatter.ofPattern("yyyy/M/d h a", Locale.US),
                DateTimeFormatter.ofPattern("yyyy/M/d h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("d/M/yy HHmm"),
                DateTimeFormatter.ofPattern("d/M/yy HH:mm"),
                DateTimeFormatter.ofPattern("d/M/yy h a", Locale.US),
                DateTimeFormatter.ofPattern("d/M/yy h:mm a", Locale.US),
                DateTimeFormatter.ofPattern("dd-MM-yy HHmm"),
                DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"),
                DateTimeFormatter.ofPattern("dd-MM-yy h a", Locale.US),
                DateTimeFormatter.ofPattern("dd-MM-yy h:mm a", Locale.US),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
        };

        for (DateTimeFormatter formatter : dateTimeFormatters) {
            try {
                return LocalDateTime.parse(trimmed, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported date-time format.
            }
        }

        DateTimeFormatter[] dateOnlyFormatters = {
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy/M/d"),
                DateTimeFormatter.ofPattern("d/M/yy"),
                DateTimeFormatter.ofPattern("dd/MM/yy"),
                DateTimeFormatter.ofPattern("dd-MM-yy"),
                DateTimeFormatter.ISO_LOCAL_DATE
        };

        for (DateTimeFormatter formatter : dateOnlyFormatters) {
            try {
                return LocalDate.parse(trimmed, formatter).atStartOfDay();
            } catch (DateTimeParseException ignored) {
                // Try the next supported date-only format.
            }
        }

        throw new GihunException(
                "Invalid deadline format. Please use yyyy-MM-dd, dd-MM-yyyy, d/M/yyyy, or d/M/yyyy HHmm.");
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DISPLAY_FORMATTER.format(dueDate) + ")";
    }
}
