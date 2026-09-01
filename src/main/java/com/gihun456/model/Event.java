package com.gihun456.model;

import com.gihun456.GihunException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a task that spans a start date and end date.
 */
public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Creates an event task from raw user input.
     *
     * @param taskName Description of the event.
     * @param startDateText Start date text.
     * @param endDateText End date text.
     * @throws GihunException If either date is invalid.
     */
    public Event(String taskName, String startDateText, String endDateText) throws GihunException {
        super(taskName);
        this.startDate = parseDateTime(startDateText);
        this.endDate = parseDateTime(endDateText);
    }

    /**
     * Creates an event task from date-time objects.
     *
     * @param taskName Description of the event.
     * @param startDate Start date-time.
     * @param endDate End date-time.
     */
    public Event(String taskName, LocalDateTime startDate, LocalDateTime endDate) {
        super(taskName);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns the event start date-time.
     *
     * @return Event start timestamp.
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Returns the event end date-time.
     *
     * @return Event end timestamp.
     */
    public LocalDateTime getEndDate() {
        return endDate;
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

    /**
     * Parses a string into a LocalDateTime object. Includes support for commonly used date formats.
     * 
     * @param dateText String of date with optional time.
     * @return LocalDateTime representation of the dateTime.
     * @throws GihunException If dateText is empty or the format is not supported.
     */
    private static LocalDateTime parseDateTime(String dateText) throws GihunException {
        String trimmed = dateText.trim();
        if (trimmed.isEmpty()) {
            throw new GihunException("Event date cannot be empty.");
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
                "Invalid event date format. Please use yyyy-MM-dd, dd-MM-yyyy, d/M/yyyy, or d/M/yyyy HHmm.");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DISPLAY_FORMATTER.format(startDate)
                + " to: " + DISPLAY_FORMATTER.format(endDate) + ")";
    }
}
