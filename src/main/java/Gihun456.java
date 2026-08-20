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

        System.out.println(GREETING);
        System.out.println();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String userInput = sc.nextLine();
            String cmd = userInput.trim();

            if (cmd.equalsIgnoreCase("bye")) {
                System.out.println(FAREWELL);
                break;
            } else if (cmd.isEmpty()) {
                continue;
            } else {
                System.out.println(cmd);
                System.out.println();
            }

        }
    }
}