/**
 * Task class to track tasks
 * Supports Todo, Deadline and Event
 * 
 * @param taskName Description of task
 * @param isDone Status of task
 */

public class Task {
    protected String taskName;
    protected boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void markTaskAsDone() {
        this.isDone = true;
    }

    public void markTaskAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + " " + this.taskName;
    }
}
