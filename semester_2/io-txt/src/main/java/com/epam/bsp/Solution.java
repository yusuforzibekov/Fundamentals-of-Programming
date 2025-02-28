package com.epam.bsp;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Solution {

    public static void lengthFrequency(File in, File out) throws FileNotFoundException {
        Map<Integer, Integer> frequencyMap = new TreeMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    // Parse the line to extract coordinates
                    double[] coords = parseCoordinates(line);
                    double x1 = coords[0];
                    double y1 = coords[1];
                    double x2 = coords[2];
                    double y2 = coords[3];
                    
                    // Calculate the length of the segment
                    double length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                    
                    // Round the length to the nearest integer
                    int roundedLength = (int) Math.round(length);
                    
                    // Update the frequency map
                    frequencyMap.put(roundedLength, frequencyMap.getOrDefault(roundedLength, 0) + 1);
                } catch (Exception e) {
                    // Continue processing other lines
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Error reading the input file: " + e.getMessage());
        }
        
        // Write the results to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Error writing to the output file: " + e.getMessage());
        }
    }
    
    private static double[] parseCoordinates(String line) {
        // Remove all spaces
        line = line.replaceAll("\\s+", "");
        
        // Find the positions of the parentheses
        int firstOpenParenIndex = line.indexOf('(');
        int firstCloseParenIndex = line.indexOf(')', firstOpenParenIndex);
        int secondOpenParenIndex = line.indexOf('(', firstCloseParenIndex);
        int secondCloseParenIndex = line.indexOf(')', secondOpenParenIndex);
        
        String firstPair = line.substring(firstOpenParenIndex + 1, firstCloseParenIndex);
        String secondPair = line.substring(secondOpenParenIndex + 1, secondCloseParenIndex);
        
        String[] firstCoords = firstPair.split(";");
        String[] secondCoords = secondPair.split(";");
        
        double x1 = Double.parseDouble(firstCoords[0].replace("f", ""));
        double y1 = Double.parseDouble(firstCoords[1].replace("f", ""));
        double x2 = Double.parseDouble(secondCoords[0].replace("f", ""));
        double y2 = Double.parseDouble(secondCoords[1].replace("f", ""));
        
        return new double[]{x1, y1, x2, y2};
    }

    public static void main(String[] args) {
        File in = new File("src/in.txt");
        File out = new File("src/out.txt");
        try {
            lengthFrequency(in, out);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
