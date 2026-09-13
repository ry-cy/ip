# Gihun456 User Guide

Gihun456 is a task manager for todos, deadlines, and events.

## Commands

Use the following commands:

```text
todo <description>
deadline <description> /by <date>
event <description> /from <date> /to <date>
list
find <keyword>
mark <task number>
unmark <task number>
delete <task number>
reminders
bye
```

## Reminders

On startup, Gihun456 reports incomplete deadlines and events that are due within
the next 72 hours, inclusive. It also reports all incomplete deadlines and events
whose relevant time has passed.

For deadlines, the due date is used. For events, the start date is used.
Completed tasks are not reported. Events display their complete start-to-end range.

You can request the same report at any time with:

```text
reminders
```

The command does not accept arguments:

```text
reminders tomorrow
```

Date-only deadline input continues to mean midnight on that date.

## Adding deadlines

Example:

```text
deadline Submit project report /by 15/09/2026 1900
```

The supported date formats are `dd/MM/yyyy` and `yyyy-MM-dd`, optionally
followed by a space and `HHmm`.

## Scheduling conflicts

When a new deadline or event overlaps an existing incomplete deadline or event,
Gihun456 displays the proposed task and every conflicting task before adding it:

```text
This task conflicts with existing tasks:
Proposed task:
[D][ ] Submit report (by: Sept 15 2026, 10:30 am)
Conflicting tasks:
1. [E][ ] Team meeting (from: Sept 15 2026, 10:00 am to: Sept 15 2026, 11:00 am)
Add it anyway? (yes/no)
```

Reply with `yes` to add the task or `no` to cancel it. Responses are
case-insensitive and may have surrounding spaces. Any other response is
rejected until `yes`, `no`, or `bye` is entered. Todos and completed dated
tasks do not trigger scheduling warnings. Events must end at or after their
start time.
