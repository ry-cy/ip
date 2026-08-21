import java.util.ArrayList;
import java.util.Scanner;

public class Gihun456 {
    public static void main(String[] args) {
        String banner = """
                          ____ _ _                 _  _  ____   __   
                         / ___(_) |__  _   _ _ __ | || || ___| / /_  
                        | |  _| | '_ \\| | | | '_ \\| || ||___ \\| '_ \\ 
                        | |_| | | | | | |_| | | | |__   _|__) | (_) |
                         \\____|_|_| |_|\\__,_|_| |_|  |_||____/ \\___/ 
                        """;
        System.out.println(banner);

        final String GREETING = "Hello! I'm Gihun456.\nWhat can I do for you?";
        final String FAREWELL = "Bye. Hope to see you again soon!";

        final String LIST_TASKS = "Here are the tasks in your list:";
        final String ADD_TASK = "Got it. I've added this task:";
        final String REMOVE_TASK = "Noted. I've removed this task:";
        final String MARK_TASK = "Nice! I've marked this task as done:";
        final String UNMARK_TASK = "OK, I've marked this task as not done yet:";

        System.out.println(GREETING);
        System.out.println();
        Scanner sc = new Scanner(System.in);

        ArrayList<Task> storage = new ArrayList<>();

        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            String[] parts = input.split(" ", 2);

            String command = parts[0];
            String arguments = parts.length > 1 ? parts[1] : "";

            try {
                switch (command) {
                    case "todo": {
                        if (arguments.trim().isEmpty()) {
                            throw new GihunException("The description of a todo cannot be empty.");
                        }
                        System.out.println(ADD_TASK);
                        Task newTask = new Todo(arguments);
                        storage.add(newTask);

                        System.out.println(newTask.toString());
                        System.out.println(String.format(
                            "Now you have %d tasks in the list.", storage.size()));
                        System.out.println();
                        break;
                    }

                    case "deadline": {
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
                        System.out.println();
                        break;
                    }

                    case "event": {
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
                        System.out.println();
                        break;
                    }

                    case "list" :
                        if (storage.isEmpty()) {
                            System.out.println("Storage empty");
                            System.out.println();
                            break;
                        } else {
                            System.out.println(LIST_TASKS);
                            for (int i = 0; i < storage.size(); i++) {
                                Task currentTask = storage.get(i);
                                System.out.println((i + 1) + ". " + currentTask.toString());
                            }
                            System.out.println();
                            break;
                        }
                    
                    case "mark" : {
                        int toMark = Integer.parseInt(arguments) - 1;
                        Task currentTask = storage.get(toMark);
                        currentTask.markTaskAsDone();

                        System.out.println(MARK_TASK);
                        System.out.println(currentTask.toString());
                        System.out.println();
                        break;
                    }
                    
                    case "unmark" : {
                        int toUnmark = Integer.parseInt(arguments) - 1;
                        Task currentTask = storage.get(toUnmark);
                        currentTask.markTaskAsNotDone();

                        System.out.println(UNMARK_TASK);
                        System.out.println(currentTask.toString());
                        System.out.println();
                        break;
                    }

                    case "delete" : {
                        if (storage.isEmpty()) {
                            System.out.println("Storage empty");
                            System.out.println();
                            break;
                        } else {
                            int toDelete = Integer.parseInt(arguments) - 1;
                            Task currentTask = storage.get(toDelete);
                            storage.remove(toDelete);

                            System.out.println(REMOVE_TASK);
                            System.out.println(currentTask.toString());
                            System.out.println(String.format(
                                "Now you have %d tasks in the list.", storage.size()));
                            System.out.println();
                            break;
                        }
                    }

                    case "bye": {
                        System.out.println(FAREWELL);
                        return;
                    }

                    default:
                        throw new GihunException("Command not supported.");
                }
            } catch (GihunException ge) {
                printError(ge.getMessage());
                System.out.println();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                e.printStackTrace(System.err);
                System.out.println();
            }
        }
    }

    private static void printError(String msg) {
        System.out.println("____________________________________________________________");
        System.out.println("ERROR: " + msg);
        System.out.println("____________________________________________________________");
    }
}
