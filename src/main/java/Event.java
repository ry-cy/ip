public class Event extends Task {
    public String startDate;
    public String endDate;

    public Event(String taskName, String startDate, String endDate) {
        super(taskName);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + 
            " (from: " + startDate + " to: " + endDate + ")";
    }
}
