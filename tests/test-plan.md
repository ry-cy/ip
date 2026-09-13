# Reminder feature test plan

## Reminder classification

- Incomplete deadlines from now through exactly 72 hours later are upcoming.
- Incomplete events use their start time and follow the same 72-hour rule.
- A timestamp exactly equal to now is upcoming.
- A timestamp exactly 72 hours from now is upcoming.
- Timestamps strictly before now are missed, with no maximum age.
- Tasks more than 72 hours in the future are not reported.
- Completed tasks are excluded from both categories.

## Report formatting

- Upcoming and missed items are shown in separate sections.
- Each section is ordered by relevant timestamp.
- Equal timestamps are ordered by original task number.
- Original one-based task numbers are preserved after sorting.
- Deadline due dates and complete event ranges are displayed.
- An empty report is returned when no reminders apply.

## Command behavior

- `reminders` returns the current report.
- `reminders` with non-blank arguments returns a validation error.
- An explicit `reminders` command with no reminders returns the no-reminders message.
- Startup displays reminders only when at least one reminder applies.

## Compatibility

- Existing task commands continue to work.
- Existing storage records load without migration.
- Reminder evaluation does not modify task status, order, or storage.
