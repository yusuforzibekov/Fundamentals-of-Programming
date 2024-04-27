package com.epam.rd.autotasks;

public class CompleteByRequestTask implements Task {
    private boolean isComplete;
    private boolean isFinished;

    @Override
    public void execute() {
        isFinished = isComplete;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    public void complete() {
        isComplete = true;
    }
}