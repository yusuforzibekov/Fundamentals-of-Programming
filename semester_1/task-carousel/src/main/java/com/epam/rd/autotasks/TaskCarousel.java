package com.epam.rd.autotasks;

import java.util.ArrayList;

public class TaskCarousel {
    private final ArrayList<Task> tasks;
    int capacity;
    int executeCounter;

    public TaskCarousel(int capacity) {
        this.tasks = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    public boolean addTask(Task task) {
        isEmpty();
        if (isFull() || task.isFinished()) {
            return false;
        }
        tasks.add(task);
        return true;
    }

    public boolean execute() {
        if (isEmpty()) {
            return false;
        }
        resetCounter();

        Task runningTask = tasks.get(executeCounter++);

        if (!runningTask.isFinished()) {
            runningTask.execute();
            return true;
        }

        if (runningTask.isFinished()) {
            tasks.remove(runningTask);
        }

        return false;
    }

    public void resetCounter() {
        if (executeCounter == tasks.size()) {
            executeCounter = 0;
        }
    }

    public boolean isFull() {
        return tasks.size() == capacity;
    }

    public boolean containsIsFinished() {
        return tasks.stream().anyMatch(Task::isFinished);
    }

    public boolean isEmpty() {
        while (containsIsFinished()) {
            tasks.removeIf(Task::isFinished);
            executeCounter--;
        }
        return tasks.isEmpty();
    }
}