package com.epam.rd.autotask.io.serialization;

import java.io.*;

public class OrderSerializer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // you can add your code here for personal purpose
    }

    /**
     * Serializes Order to a given OutputStream
     */
    public static void serializeOrder(Order order, OutputStream sink) throws IOException {
        // TODO place your code here
        try (ObjectOutputStream oos = new ObjectOutputStream(sink)) {
            oos.writeObject(order);
        }
    }

    /**
     * Deserializes Order from a given InputStream
     */
    public static Order deserializeOrder(InputStream sink) throws IOException, ClassNotFoundException {
        // TODO place your code here
        try (ObjectInputStream ois = new ObjectInputStream(sink)) {
            return (Order) ois.readObject();
        }
    }
}
