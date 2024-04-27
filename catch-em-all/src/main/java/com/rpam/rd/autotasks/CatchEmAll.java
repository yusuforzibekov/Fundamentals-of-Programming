package com.rpam.rd.autotasks;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CatchEmAll {
    static Exception exception = new Exception();

    public static void riskyMethod() throws Exception {
        throw exception;
    }

    public static void main(String[] args) throws Exception {
        try {
            riskyMethod();
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Resource is missing", e);
        }
        catch (IOException e2) {
            throw new IllegalArgumentException("Resource error", e2);
        }
        catch (ArithmeticException | NumberFormatException e3) {
            System.err.println(e3.getMessage());
        }
    }
}
