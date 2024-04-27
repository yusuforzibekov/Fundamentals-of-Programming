package com.epam.rd.autotasks;

public class CountDownTask implements Task{
    private int value;

    public CountDownTask(int i) {
        setValue(i);
    }

    @Override
    public void execute() {
        if (value > 0)
            value--;
    }

    @Override
    public boolean isFinished() {
        return getValue() == 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = Math.max(value, 0);
    }
}