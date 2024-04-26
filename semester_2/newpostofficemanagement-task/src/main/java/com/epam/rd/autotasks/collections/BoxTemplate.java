package com.epam.rd.autotasks.collections;


public class BoxTemplate extends Box {
    private int id = getId();
    private String recipient = getRecipient();

    public BoxTemplate(int id) {
        this.id = id;

    }
    public BoxTemplate(String recipient) {
        this.recipient = recipient;

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

}