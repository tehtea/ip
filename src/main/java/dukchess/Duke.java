package dukchess;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukchess.entity.Deadline;
import dukchess.entity.Event;
import dukchess.entity.Task;
import dukchess.entity.Todo;

/**
 * Main application driver class.
 */
public class Duke {

    private static List<Task> tasks = new ArrayList<Task>();

    private static void printAddedTasks() {
        if (tasks.size() == 0) {
            System.out.println("No previously added tasks to list Sir/Madam/Other :(");
            return;
        }
        System.out.println("Here are the tasks in your list, Sir/Madam/Other:");
        for (int i = 0; i < tasks.size(); i++) {
            Task addedTask = tasks.get(i);
            System.out.printf(">>> %d. %s\n", i + 1, addedTask.toString());
        }
    }

    private static void sayGoodbye() {
        String goodbyeText = "Goodbye, hope to see you soon!";
        System.out.println(goodbyeText);
    }

    private static String setAddedTaskStatus(int taskId, boolean isDone) {
        int actualTaskId = taskId - 1;
        if (actualTaskId >= tasks.size() || actualTaskId < 0) {
            return String.format(
                    "I'm terribly sorry Sir/Madam/Other :(\n"
                            + "%d is not a valid task id for the current list of tasks.",
                    taskId);
        }
        Task selectedTask = tasks.get(actualTaskId);
        String originalTaskStatus = selectedTask.toString();
        selectedTask.setDone(isDone);
        return String.format("Setting to done, original task status: %s\n>>> New task status: %s",
                originalTaskStatus, selectedTask.toString());
    }

    private static String addTodo(String description) {
        Todo newTodo = new Todo(description);
        tasks.add(newTodo);
        return String.format("Gotcha, added this todo: %s", newTodo.toString());
    }

    private static String addDeadline(String description, String by) {
        Deadline newDeadline = new Deadline(description, by);
        tasks.add(newDeadline);
        return String.format("Gotcha, added this deadline: %s", newDeadline.toString());
    }

    private static String addEvent(String description, String at) {
        Event newEvent = new Event(description, at);
        tasks.add(newEvent);
        return String.format("Gotcha, added this event: %s", newEvent.toString());
    }

    /**
     * Source: https://stackoverflow.com/questions/31412294/java-check-not-null-empty-else-assign-default-value
     * Get value of object, and if it is null, set it to the default value.
     * @param defaultValue
     * @return
     */
    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * The main driver function for the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String welcomeText = "Hello, I am a PC assistant\n"
                + "How do you want to set your tasks today?";
        System.out.println(welcomeText);
        Scanner scanner = new Scanner(System.in);
        String commandString = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        String nextCommand = "placeholder";
        boolean firstCommand = true;

        while (!nextCommand.equals("bye")) {
            if (!firstCommand) {
                System.out.println("What other tasks do you want to do?");
                commandString = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
            }
            firstCommand = false;

            Pattern commandPattern = Pattern.compile("(\\w+)( .+)?");
            Matcher commandMatches = commandPattern.matcher(commandString);
            if (!commandMatches.matches()) {
                System.out.println("Oof, invalid command :(");
                continue;
            }
            nextCommand = commandMatches.group(1);
            String commandArgs = getValueOrDefault(commandMatches.group(2), "");
            commandArgs = commandArgs.trim();
            switch (nextCommand) {
            case "list":
                printAddedTasks();
                continue;
            case "bye":
                sayGoodbye();
                return;
            case "done":
                int taskId = Integer.parseInt(commandArgs);
                String taskStatusAdditionOutcome = setAddedTaskStatus(taskId, true);
                System.out.println(taskStatusAdditionOutcome);
                break;
            case "todo":
                if (commandArgs.length() == 0) {
                    System.out.println("Oops, todo description cannot be empty :(");
                    break;
                }
                String todoAdditionOutcome = addTodo(commandArgs);
                System.out.println(todoAdditionOutcome);
                break;
            case "deadline":
                if (commandArgs.length() == 0) {
                    System.out.println("Oops, deadline description cannot be empty :(");
                    break;
                }
                String[] deadlineArgs = commandArgs.split(" /by ");
                if (deadlineArgs.length != 2) {
                    System.out.println("Oops, deadline due date cannot be empty :(");
                    break;
                }
                String deadlineAdditionOutcome = addDeadline(deadlineArgs[0], deadlineArgs[1]);
                System.out.println(deadlineAdditionOutcome);
                break;
            case "event":
                if (commandArgs.length() == 0) {
                    System.out.println("Oops, event description cannot be empty :(");
                    break;
                }
                String[] eventArgs = commandArgs.split(" /at ");
                if (eventArgs.length != 2) {
                    System.out.println("Oops, event time cannot be empty :(");
                    break;
                }
                String eventAdditionOutcome = addEvent(eventArgs[0], eventArgs[1]);
                System.out.println(eventAdditionOutcome);
                break;
            default:
                System.out.println("Invalid command :(");
                break;
            }
        }
    }
}