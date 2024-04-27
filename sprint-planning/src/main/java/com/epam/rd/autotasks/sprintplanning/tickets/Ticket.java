package com.epam.rd.autotasks.sprintplanning.tickets;

public class Ticket {
    private int id;
    private String name;
    private int estimateHours;
    private boolean isComplete;

    public Ticket(int id, String name, int estimateHours) {
        this.id = id;
        this.name = name;
        this.estimateHours = estimateHours;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isCompleted() {
        return this.isComplete;
    }

    public void complete() {
        this.isComplete = true;
    }

    public int getEstimate() {
        return this.estimateHours;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }
}