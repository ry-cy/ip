import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Persists the current task list to the application's data file.
 */
public class Storage {
    private static final Path TASK_FILE = Path.of("data", "Gihun456.txt");

    /**
     * Writes all tasks to disk, replacing the previous snapshot.
     *
     * @param tasks The current task list.
     * @throws GihunException If the data directory or file cannot be written.
     */
    public static void saveTasks(List<Task> tasks) throws GihunException {
        try {
            Files.createDirectories(TASK_FILE.getParent());

            StringBuilder contents = new StringBuilder();
            for (Task task : tasks) {
                contents.append(formatTask(task)).append(System.lineSeparator());
            }
            Files.writeString(
                    TASK_FILE,
                    contents.toString(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new GihunException("Unable to access tasks file.", e);
        }
    }

    /**
     * Loads tasks from the previous snapshot.
     *
     * @return List of tasks, empty list if previous snapshot does not exist.
     * @throws GihunException If the task could not be loaded.
     */
    public static List<Task> loadTasks() throws GihunException {
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(TASK_FILE)) {
            return tasks;
        }

        try (BufferedReader reader = Files.newBufferedReader(TASK_FILE, StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 3) {
                    continue;
                }

                String type = parts[0].trim();
                boolean isDone = parts[1].trim().equals("X");
                String description = parts[2].trim();

                Task task;

                try {
                    switch (type) {
                        case "T":
                            task = new Todo(description);
                            break;

                        case "D":
                            if (parts.length < 4) {
                                continue;
                            }
                            String deadline = parts[3].trim();
                            task = new Deadline(description, deadline);
                            break;

                        case "E":
                            if (parts.length < 5) {
                                continue;
                            }
                            String eventStart = parts[3].trim();
                            String eventEnd = parts[4].trim();

                            task = new Event(description, eventStart, eventEnd);
                            break;

                        default:
                            continue;
                    }
                } catch (GihunException e) {
                    continue;
                }

                if (isDone) {
                    task.markAsDone();
                }

                tasks.add(task);
            }

            return tasks;
        } catch (IOException e) {
            throw new GihunException("Unable to load tasks from file.", e);
        }
    }

    private static String formatTask(Task task) {
        String type;
        String details = "";
        switch (task) {
            case Deadline deadline -> {
                type = "D";
                details = " | " + Deadline.formatForStorage(deadline.getDueDate());
            }
            case Event event -> {
                type = "E";
                details = " | " + Event.formatForStorage(event.getStartDate())
                        + " | " + Event.formatForStorage(event.getEndDate());
            }
            default -> {
                type = "T";
            }
        }

        return type + " | " + task.getStatusIcon() + " | " + task.taskName + details;
    }
}
