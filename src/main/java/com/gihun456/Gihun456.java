package com.gihun456;

import com.gihun456.command.Operation;
import com.gihun456.command.Parser;
import com.gihun456.model.Task;
import com.gihun456.model.TaskList;
import com.gihun456.model.Todo;
import com.gihun456.model.Deadline;
import com.gihun456.model.Event;
import com.gihun456.storage.Storage;
import com.gihun456.ui.Ui;
import java.util.Scanner;

public class Gihun456 {
    private final Ui ui;
    private final TaskList tasks;
    private final Parser parser;
    private final Storage storage;

    public Gihun456(String filePath) {
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.parser = new Parser();
        this.storage = new Storage(filePath);
    }

    public static void main(String[] args) {
        Gihun456 app = new Gihun456("data/Gihun456.txt");
        app.run();
    }

    public void run() {
        ui.showGreeting();
        Scanner sc = new Scanner(System.in);

        try {
            tasks.addAll(storage.load());
        } catch (GihunException ge) {
            ui.showError(ge.getMessage());
            return;
        }

        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            try {
                Parser.ParsedInput parsedInput = parser.parse(input);
                Operation operation = parsedInput.getOperation();
                String arguments = parsedInput.getArguments();

                switch (operation) {
                    case TODO: {
                        if (arguments.trim().isEmpty()) {
                            throw new GihunException("The description of a todo cannot be empty.");
                        }
                        Task newTask = new Todo(arguments);
                        tasks.add(newTask);
                        storage.save(tasks.asList());
                        ui.showTaskAdded(newTask, tasks.size());
                        break;
                    }

                    case DEADLINE: {
                        Task newTask = parser.parseDeadline(arguments);
                        tasks.add(newTask);
                        storage.save(tasks.asList());
                        ui.showTaskAdded(newTask, tasks.size());
                        break;
                    }

                    case EVENT: {
                        Task newTask = parser.parseEvent(arguments);
                        tasks.add(newTask);
                        storage.save(tasks.asList());
                        ui.showTaskAdded(newTask, tasks.size());
                        break;
                    }

                    case LIST:
                        if (tasks.isEmpty()) {
                            ui.showEmptyList();
                            break;
                        }
                        ui.showTaskList(tasks.asList());
                        break;

                    case MARK: {
                        int toMark = tasks.getValidIndex(arguments);
                        Task currentTask = tasks.get(toMark);
                        tasks.markTask(toMark);
                        storage.save(tasks.asList());
                        ui.showTaskMarked(currentTask);
                        break;
                    }

                    case UNMARK: {
                        int toUnmark = tasks.getValidIndex(arguments);
                        Task currentTask = tasks.get(toUnmark);
                        tasks.unmarkTask(toUnmark);
                        storage.save(tasks.asList());
                        ui.showTaskUnmarked(currentTask);
                        break;
                    }

                    case DELETE: {
                        if (tasks.isEmpty()) {
                            ui.showEmptyList();
                            break;
                        }
                        int toDelete = tasks.getValidIndex(arguments);
                        Task currentTask = tasks.get(toDelete);
                        tasks.remove(toDelete);
                        storage.save(tasks.asList());
                        ui.showTaskRemoved(currentTask, tasks.size());
                        break;
                    }

                    case BYE: {
                        ui.showFarewell();
                        return;
                    }
                }
            } catch (GihunException ge) {
                ui.showError(ge.getMessage());
                ui.showLine();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                e.printStackTrace(System.err);
                System.out.println();
            }
        }
    }
}
