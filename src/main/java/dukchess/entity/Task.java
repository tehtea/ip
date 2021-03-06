package dukchess.entity;

/**
 * The base Task class for all other types of tasks in the application.
 * Can also be a vanilla Task on its own.
 */
public abstract class Task {

    private final String taskDescription;
    private boolean isDone;

    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Create a Task from its description and whether it is done
     * @param taskDescription
     * @param isDone
     */
    public Task(String taskDescription, boolean isDone) {
        this.taskDescription = taskDescription;
        this.isDone = isDone;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        String checkMark = isDone ? "X" : " ";
        return String.format("[%s] %s", checkMark, taskDescription);
    }
}
