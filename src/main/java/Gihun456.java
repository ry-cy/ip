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
        final String ADD_TASK = "Added this task to your list: ";
        final String MARK_TASK = "Nice! I've marked this task as done:";
        final String UNMARK_TASK = "OK, I've marked this task as not done yet:";

        System.out.println(GREETING);
        System.out.println();
        Scanner sc = new Scanner(System.in);

        ArrayList<Task> storage = new ArrayList<>();

        while (sc.hasNextLine()) {
            String cmd = sc.nextLine();

            if (!cmd.isEmpty()) {
                if (cmd.equalsIgnoreCase("bye")) {
                    System.out.println(FAREWELL);
                    break;
                } else if (cmd.equalsIgnoreCase("list")) {
                    if (storage.isEmpty()) {
                        System.out.println("Storage empty");
                        System.out.println();

                        continue;
                    }
                    System.out.println(LIST_TASKS);
                    for (int i = 0; i < storage.size(); i++) {
                        Task current = storage.get(i);
                        System.out.println((i + 1) + ". " + current.toString());
                    }
                    System.out.println();
                } else {
                    String[] cmdArray = cmd.split(" ");
                    

                    if (cmdArray[0].equalsIgnoreCase("mark")) {
                        Task current = storage.get(Integer.parseInt(cmdArray[1]) - 1);
                        current.markTaskAsDone();

                        System.out.println(MARK_TASK);
                        System.out.println(current.toString());
                        System.out.println();
                    } else if (cmdArray[0].equalsIgnoreCase("unmark")) {
                        Task current = storage.get(Integer.parseInt(cmdArray[1]) - 1);
                        current.markTaskAsNotDone();

                        System.out.println(UNMARK_TASK);
                        System.out.println(current.toString());
                        System.out.println();
                    } else {
                        Task newTask = new Task(cmd);

                        storage.add(newTask);
                        System.out.println(ADD_TASK + newTask.toString());
                        System.out.println();
                    }
                }
            }
        }
    }
}