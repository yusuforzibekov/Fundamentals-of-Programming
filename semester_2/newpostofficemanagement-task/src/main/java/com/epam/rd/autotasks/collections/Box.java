package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

public class Box implements Cloneable {
    private int id;
    private String sender;
    private String recipient;
    private double weight;
    private double volume;
    private BigDecimal cost;
    private String city;
    private int office;

    public Box(String sender, String recipient, double weight, double volume, BigDecimal cost, String city,
            int office) {
        this.id = IntSequence.next();
        this.sender = sender;
        this.recipient = recipient;
        this.weight = weight;
        this.volume = volume;
        this.cost = cost;
        this.city = city;
        this.office = office;
    }

    public Box() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Box box = (Box) o;
        return id == box.id && Double.compare(box.weight, weight) == 0 && Double.compare(box.volume, volume) == 0
                && office == box.office && sender.equals(box.sender) && recipient.equals(box.recipient)
                && cost.equals(box.cost) && city.equals(box.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, recipient, weight, volume, cost, city, office);
    }

    @Override
    public String toString() {
        return "{" + id +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", weight=" + weight +
                ", volume=" + volume +
                ", cost=" + cost +
                ", city='" + city + '\'' +
                ", office=" + office +
                '}';
    }

    @Override
    public Box clone() throws CloneNotSupportedException {
        return (Box) super.clone();
    }

}