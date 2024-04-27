package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

import java.util.Arrays;

public class Sprint {
    private final int capacity;
    private final int ticketsLimit;
    private Ticket[] tickets;
    private int addedTickets = 0;

    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        this.tickets = new Ticket[0];
    }

    public boolean addUserStory(UserStory userStory) {
        if (this.validateTicket(userStory) && this.validateDependencies(userStory.getDependencies())) {
            this.addTicket(userStory);
            return true;
        } else {
            return false;
        }
    }

    public boolean addBug(Bug bugReport) {
        if (this.validateTicket(bugReport)) {
            this.addTicket(bugReport);
            return true;
        } else {
            return false;
        }
    }

    public Ticket[] getTickets() {
        return Arrays.copyOf(this.tickets, this.tickets.length);
    }

    public void addTicket(Ticket ticket) {
        this.tickets = Arrays.copyOf(this.tickets, this.tickets.length + 1);
        this.tickets[addedTickets++] = ticket;
    }

    public int getTotalEstimate() {
        int estimatedSum = 0;
        for (Ticket ticket : this.tickets) {
            if (ticket != null) {
                estimatedSum += ticket.getEstimate();
            }
        }
        return estimatedSum;
    }

    private boolean validateDependencies(Ticket[] dependencies) {
        boolean areDependenciesValid = true;
        for (Ticket dependency : dependencies) {
            if (!this.validateTicket(dependency) || !isTicketFound(dependency)) {
                areDependenciesValid = false;
            }
        }
        return areDependenciesValid;
    }

    private boolean isTicketFound(Ticket dependency) {
        boolean foundTicket = false;
        for (Ticket ticket : this.tickets) {
            if (dependency.getId() == ticket.getId()) {
                foundTicket = true;
            }
        }
        return foundTicket;
    }

    private boolean validateTicket(Ticket ticket) {
        return ticket != null
                && !ticket.isCompleted()
                && ticket.getEstimate() + this.getTotalEstimate() <= this.capacity
                && this.addedTickets < this.ticketsLimit;
    }
}