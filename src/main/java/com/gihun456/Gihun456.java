package com.gihun456;

import java.util.List;
import java.util.Scanner;

import com.gihun456.command.Operation;
import com.gihun456.command.Parser;
import com.gihun456.model.Task;
import com.gihun456.model.TaskList;
import com.gihun456.model.Todo;
import com.gihun456.storage.Storage;
import com.gihun456.ui.Ui;
import com.gihun456.ui.UiMessages;

/**
 * Entry point for the Gihun456 task application.
 */
public class Gihun456 {
    private static final String DEFAULT_DATA_FILE = "data/Gihun456.txt";

    private final Ui ui;
    private final TaskList tasks;
    private final Parser parser;
    private final Storage storage;

    /**
     * Creates an application instance backed by the given data file.
     *
     * @param filePath Path to the persistent task storage file.
     */
    public Gihun456(String filePath) {
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.parser = new Parser();
        this.storage = new Storage(filePath);
    }

    public Gihun456() {
        this(DEFAULT_DATA_FILE);
    }

    /**
     * Starts the application from the default data file.
     *
     * @param args Command-line arguments ignored by the program.
     */
    public static void main(String[] args) {
        Gihun456 app = new Gihun456(DEFAULT_DATA_FILE);
        app.run();
    }

    /**
     * Runs the application's main command loop.
     */
    public void run() {
        ui.showGreeting();
        final Scanner scanner = new Scanner(System.in);

        try {
            loadTasks();
        } catch (GihunException ge) {
            ui.showError(ge.getMessage());
            return;
        }

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            try {
                String response = processCommand(input);
                System.out.println(response);
                if (parser.parse(input).getOperation() == Operation.BYE) {
                    return;
                }
            } catch (GihunException ge) {
                ui.showError(ge.getMessage());
                ui.showLine();
            } catch (Exception e) {
                System.out.println(ErrorMessages.UNEXPECTED_ERROR);
                e.printStackTrace(System.err);
                System.out.println();
            }
        }
    }

    /**
     * Loads the persisted tasks into memory.
     *
     * @throws GihunException If the task data cannot be read.
     */
    public void loadTasks() throws GihunException {
        if (tasks.isEmpty()) {
            tasks.addAll(storage.load());
        }
    }

    /**
     * Executes a command and returns the text that should be shown to the user.
     * This method is shared by the console and JavaFX interfaces.
     *
     * @param input Raw command entered by the user.
     * @return User-facing command response.
     * @throws GihunException If the command is invalid or cannot be completed.
     */
    public String processCommand(String input) throws GihunException {
        Parser.ParsedInput parsedInput = parser.parse(input);
        Operation operation = parsedInput.getOperation();
        String arguments = parsedInput.getArguments();

        switch (operation) {
            case TODO: {
                if (arguments.trim().isEmpty()) {
                    throw new GihunException(ErrorMessages.TODO_DESCRIPTION_EMPTY);
                }
                Task newTask = new Todo(arguments);
                tasks.add(newTask);
                storage.save(tasks.asList());
                return formatTaskAdded(newTask);
            }
            case DEADLINE: {
                Task newTask = parser.parseDeadline(arguments);
                tasks.add(newTask);
                storage.save(tasks.asList());
                return formatTaskAdded(newTask);
            }
            case EVENT: {
                Task newTask = parser.parseEvent(arguments);
                tasks.add(newTask);
                storage.save(tasks.asList());
                return formatTaskAdded(newTask);
            }
            case LIST:
                return tasks.isEmpty() ? UiMessages.EMPTY_STORAGE : formatTaskList(tasks.asList(), false);
            case FIND:
                if (tasks.isEmpty()) {
                    return UiMessages.EMPTY_STORAGE;
                }
                List<Task> matchingTasks = tasks.getMatchedTasks(arguments);
                return matchingTasks.isEmpty()
                        ? UiMessages.NO_MATCHING_TASKS
                        : formatTaskList(matchingTasks, true);
            case MARK: {
                int index = tasks.getValidIndex(arguments);
                Task task = tasks.get(index);
                tasks.markTask(index);
                storage.save(tasks.asList());
                return UiMessages.MARK_TASK + "\n" + task;
            }
            case UNMARK: {
                int index = tasks.getValidIndex(arguments);
                Task task = tasks.get(index);
                tasks.unmarkTask(index);
                storage.save(tasks.asList());
                return UiMessages.UNMARK_TASK + "\n" + task;
            }
            case DELETE: {
                if (tasks.isEmpty()) {
                    return UiMessages.EMPTY_STORAGE;
                }
                int index = tasks.getValidIndex(arguments);
                Task task = tasks.remove(index);
                storage.save(tasks.asList());
                return UiMessages.REMOVE_TASK + "\n" + task
                        + "\n이제 목록에 " + tasks.size() + " 개의 작업이 있습니다.";
            }
            case BYE:
                return UiMessages.FAREWELL;
            default:
                throw new GihunException(ErrorMessages.UNSUPPORTED_OPERATION);
        }
    }

    /**
     * Generates a response for the JavaFX chat view, including validation errors.
     *
     * @param input Raw command entered by the user.
     * @return User-facing response.
     */
    public String getResponse(String input) {
        try {
            return processCommand(input);
        } catch (GihunException e) {
            return ErrorMessages.ERROR_PREFIX + e.getMessage();
        }
    }

    private String formatTaskAdded(Task task) {
        return UiMessages.ADD_TASK + "\n" + task
                + "\n이제 목록에 " + tasks.size() + " 개의 작업이 있습니다.";
    }

    private String formatTaskList(List<Task> taskList, boolean isMatching) {
        StringBuilder response = new StringBuilder(isMatching
                ? UiMessages.LIST_MATCHING_TASKS
                : UiMessages.LIST_TASKS);
        for (int i = 0; i < taskList.size(); i++) {
            response.append("\n").append(i + 1).append(". ").append(taskList.get(i));
        }
        return response.toString();
    }
}
