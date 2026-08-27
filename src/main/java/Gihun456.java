import java.util.ArrayList;
import java.util.Scanner;

public class Gihun456 {
    static final String BANNER = """
                          ____ _ _                 _  _  ____   __   
                         / ___(_) |__  _   _ _ __ | || || ___| / /_  
                        | |  _| | '_ \\| | | | '_ \\| || ||___ \\| '_ \\ 
                        | |_| | | | | | |_| | | | |__   _|__) | (_) |
                         \\____|_|_| |_|\\__,_|_| |_|  |_||____/ \\___/ 
                        """;

    static final String GREETING = "Hello! I'm Gihun456.\nWhat can I do for you?";
    static final String FAREWELL = "Bye. Hope to see you again soon!";
    static final String SEP = "____________________________________________________________";

    static final String LIST_TASKS = "Here are the tasks in your list:";
    static final String ADD_TASK = "Got it. I've added this task:";
    static final String REMOVE_TASK = "Noted. I've removed this task:";
    static final String MARK_TASK = "Nice! I've marked this task as done:";
    static final String UNMARK_TASK = "OK, I've marked this task as not done yet:";
    public static void main(String[] args) {
        System.out.println(BANNER);
        System.out.println(GREETING);
        System.out.println(SEP);
        Scanner sc = new Scanner(System.in);

        ArrayList<Task> storage = new ArrayList<>();

        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            String[] parts = input.split(" ", 2);

            String commandInput = parts[0];
            String arguments = parts.length > 1 ? parts[1] : "";

            try {
                Operation operation = Operation.fromInput(commandInput);
                switch (operation) {
                    case TODO: {
                        if (arguments.trim().isEmpty()) {
                            throw new GihunException("The description of a todo cannot be empty.");
                        }
                        System.out.println(ADD_TASK);
                        Task newTask = new Todo(arguments);
                        storage.add(newTask);

                        System.out.println(newTask.toString());
                        System.out.println(String.format(
                            "Now you have %d tasks in the list.", storage.size()));
                        System.out.println(SEP);
                        break;
                    }

                    case DEADLINE: {
                        int byIndex = arguments.indexOf("/by");

                        String taskName =
                                arguments.substring(0, byIndex).trim();

                        String dueDate =
                                arguments.substring(byIndex + 3).trim();

                        System.out.println(ADD_TASK);
                        Task newTask = new Deadline(taskName, dueDate);
                        storage.add(newTask);

                        System.out.println(newTask.toString());
                        System.out.println(String.format(
                            "Now you have %d tasks in the list.", storage.size()));
                        System.out.println(SEP);
                        break;
                    }

                    case EVENT: {
                        int fromIndex = arguments.indexOf("/from");
                        int toIndex = arguments.indexOf("/to");

                        String taskName =
                                arguments.substring(0, fromIndex).trim();

                        String startDate =
                                arguments.substring(fromIndex + 5, toIndex).trim();

                        String endDate =
                                arguments.substring(toIndex + 3).trim();

                        System.out.println(ADD_TASK);
                        Task newTask = new Event(taskName, startDate, endDate);
                        storage.add(newTask);

                        System.out.println(newTask.toString());
                        System.out.println(String.format(
                            "Now you have %d tasks in the list.", storage.size()));
                        System.out.println(SEP);
                        break;
                    }

                    case LIST:
                        if (storage.isEmpty()) {
                            System.out.println("Storage empty");
                            System.out.println(SEP);
                            break;
                        } else {
                            System.out.println(LIST_TASKS);
                            for (int i = 0; i < storage.size(); i++) {
                                Task currentTask = storage.get(i);
                                System.out.println((i + 1) + ". " + currentTask.toString());
                            }
                            System.out.println(SEP);
                            break;
                        }
                    
                    case MARK: {
                        int toMark = Integer.parseInt(arguments) - 1;
                        Task currentTask = storage.get(toMark);
                        currentTask.markTaskAsDone();

                        System.out.println(MARK_TASK);
                        System.out.println(currentTask.toString());
                        System.out.println(SEP);
                        break;
                    }
                    
                    case UNMARK: {
                        int toUnmark = Integer.parseInt(arguments) - 1;
                        Task currentTask = storage.get(toUnmark);
                        currentTask.markTaskAsNotDone();

                        System.out.println(UNMARK_TASK);
                        System.out.println(currentTask.toString());
                        System.out.println(SEP);
                        break;
                    }

                    case DELETE: {
                        if (storage.isEmpty()) {
                            System.out.println("Storage empty");
                            System.out.println(SEP);
                            break;
                        } else {
                            int toDelete = Integer.parseInt(arguments) - 1;
                            Task currentTask = storage.get(toDelete);
                            storage.remove(toDelete);

                            System.out.println(REMOVE_TASK);
                            System.out.println(currentTask.toString());
                            System.out.println(String.format(
                                "Now you have %d tasks in the list.", storage.size()));
                            System.out.println(SEP);
                            break;
                        }
                    }

                    case BYE: {
                        System.out.println(FAREWELL);
                        return;
                    }

                }
            } catch (GihunException ge) {
                printError(ge.getMessage());
                System.out.println(SEP);
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                e.printStackTrace(System.err);
                System.out.println();
            }
        }
    }

    private static void printError(String msg) {
        System.out.println("ERROR: " + msg);
    }
}