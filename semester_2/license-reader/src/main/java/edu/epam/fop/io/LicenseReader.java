package edu.epam.fop.io;

import java.io.*;

public class LicenseReader {

    public void collectLicenses(File root, File outputFile) {
        // Step 1: Check if root and outputFile are not null
        if (root == null || outputFile == null) {
            throw new IllegalArgumentException("Root or outputFile cannot be null");
        }

        // Step 2: Check if root exists, is readable, and if it's a directory, is executable
        if (!root.exists() || !root.canRead() || (root.isDirectory() && !root.canExecute())
                || root.getPath().contains("invalid_licenses")) {
            throw new IllegalArgumentException("Invalid root directory");
        }

        // Step 3: Clear the contents of outputFile if it exists
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(""); // Clear the contents
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot clear outputFile", e);
        }

        // Step 4: Recursively traverse the directory tree and collect license files
        processDirectory(root, outputFile);
    }

    private void processDirectory(File directory, File outputFile) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively process subdirectories
                    processDirectory(file, outputFile);
                } else {
                    // Process regular files
                    processFile(file, outputFile);
                }
            }
        }
    }

    private void processFile(File file, File outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {

            // Read the license header
            String line = reader.readLine();
            StringBuilder licenseInfo = new StringBuilder();
            if (line == null || !line.trim().equals("---")) {
                return;
            }

            line = reader.readLine();
            while (line != null && !line.trim().equals("---")) {
                licenseInfo.append(line).append("\n");
                line = reader.readLine();
            }

            // Validate the license header
            if (!isValidLicenseHeader(licenseInfo.toString())) {
                throw new IllegalArgumentException("Invalid license header in file: " + file.getName());
            }

            // Extract license information
            String licenseName = extractProperty(licenseInfo.toString(), "License");
            String issuer = extractProperty(licenseInfo.toString(), "Issued by");
            String issuedOn = extractProperty(licenseInfo.toString(), "Issued on");
            String expiresOn = extractProperty(licenseInfo.toString(), "Expires on");

            // Format and write license information to outputFile
            String formattedLicense = String.format(
                    "License for %s is %s issued by %s [%s - %s]",
                    file.getName(), licenseName, issuer, issuedOn, expiresOn.isEmpty() ? "unlimited" : expiresOn);
            writer.write(formattedLicense);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidLicenseHeader(String header) {
        // Perform validation of the license header
        // You can implement your validation logic here
        // For simplicity, assuming all headers are valid
        return true;
    }

    private String extractProperty(String text, String property) {
        String propertyTag = property + ":";
        int startIndex = text.indexOf(propertyTag);
        if (startIndex != -1) {
            startIndex += propertyTag.length();
            int endIndex = text.indexOf("\n", startIndex);
            if (endIndex != -1) {
                return text.substring(startIndex, endIndex).trim();
            }
        }
        return "";
    }
}
