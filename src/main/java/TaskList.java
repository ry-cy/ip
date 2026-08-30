import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the in-memory list of tasks and the operations that act on it.
 */
public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void addAll(List<Task> tasksToAdd) {
        tasks.addAll(tasksToAdd);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int getValidIndex(String input) throws GihunException {
        if (input == null || input.trim().isEmpty()) {
            throw new GihunException("The task number is invalid.");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new GihunException("The task number is invalid.", e);
        }

        int zeroBasedIndex = taskNumber - 1;
        if (zeroBasedIndex < 0 || zeroBasedIndex >= tasks.size()) {
            throw new GihunException("The task number is invalid.");
        }

        return zeroBasedIndex;
    }

    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        tasks.get(index).markAsNotDone();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }
}
