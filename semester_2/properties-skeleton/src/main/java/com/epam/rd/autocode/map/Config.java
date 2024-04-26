package com.epam.rd.autocode.map;

import java.io.*;
import java.util.Properties;

public class Config {
    Properties config;


    public Config() throws IOException {
        this.config = new Properties();
        reset();
    }


    public void reset() throws IOException {
        config.clear();
        try (InputStream input = new FileInputStream("config.properties")) {
            config.load(input);
            String defaultFilenames = config.getProperty("default.filenames");
            if (defaultFilenames != null) {
                String[] filenames = defaultFilenames.split(",");
                for (int i = filenames.length - 1; i >= 0; i--) {
                    String filename = filenames[i].trim() + ".properties";
                    try (InputStream reader = new FileInputStream(filename)) {
                        Properties defaultProp = new Properties();
                        defaultProp.load(reader);
                    }
                }
            }
        }
    }

    public String get(String key) throws IOException {
        try (InputStream input = new FileInputStream("config.properties")) {
            if (config.isEmpty()) {
                config.load(input);
            } else {
                if (config.getProperty(key) == null) {

                    String defaultFilenames = config.getProperty("default.filenames");
                    if (defaultFilenames != null) {
                        String[] filenames = defaultFilenames.split(",");
                        for (String s : filenames) {
                            String filename = s.trim() + ".properties";
                            try (InputStream reader = new FileInputStream(filename)) {
                                Properties defaultProp = new Properties();
                                defaultProp.load(reader);
                                if (defaultProp.getProperty(key) != null) {
                                    return defaultProp.getProperty(key);
                                }
                            }
                        }
                    }
                }
            }
            return config.getProperty(key);
        }
    }

    public void remove(String key) {
        String existingValue = config.getProperty(key);
        if (existingValue != null) {
            config.remove(key);
        }
    }

    public void save() {
        try (OutputStream output = new FileOutputStream("config.properties")) {
            config.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String key, String value) {
        config.setProperty(key, value);
    }

}
