package com.epam.rd.autocode.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class CashBox {

    private final int number;

    private final Deque<Buyer> byers = new ArrayDeque<>();

    private State state = State.DISABLED;

    public CashBox(int number) {
        this.number = number;
    }

    public Deque<Buyer> getQueue() {
        return new ArrayDeque<>(byers);
    }

    public Buyer serveBuyer() {
        if (! byers.isEmpty()) {
            Buyer servedBuyer = byers.pollFirst();
            if (inState(State.IS_CLOSING) && byers.isEmpty()) {
                setState(State.DISABLED);
            }
            return servedBuyer;
        }
        return null;
    }

    public boolean inState(State state) {
        return this.state == state;
    }

    public boolean notInState(State state) {
        return this.state != state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addLast(Buyer buyer) {
        byers.addLast(buyer);
    }

    public Buyer removeLast() {
        return byers.pollLast();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        State s = getState();
        sb.append("#").append(number).append("[");
        sb.append(s == State.ENABLED ? '+' : (s == State.DISABLED ? '-' : '|')).append("]");
        for (Buyer buyer : byers) {
            sb.append(buyer.toString());
        }
        return sb.toString();
    }

    public enum State {
        ENABLED, DISABLED, IS_CLOSING
    }
}