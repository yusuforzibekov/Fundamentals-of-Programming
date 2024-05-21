package edu.epam.fop.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;

public class SymbolsDistributorFactory {

  public SymbolsDistributor getInstance() {
    return new SymbolsDistributorImpl();
  }

  private static class SymbolsDistributorImpl implements SymbolsDistributor {

    @Override
    public void distribute(File inputFile, Map<Predicate<Integer>, File> outputMapping) {
      // Check if input file exists and is readable
      if (!inputFile.exists() || !inputFile.canRead() || !inputFile.isFile()) {
        throw new IllegalArgumentException("Input file does not exist or is not readable");
      }

      // Create output files if they don't exist
      for (File outputFile : outputMapping.values()) {
        try {
          outputFile.createNewFile();
        } catch (IOException e) {
          throw new IllegalArgumentException("Error creating output file: " + outputFile.getName());
        }
      }

      try (FileInputStream inputStream = new FileInputStream(inputFile)) {
        int symbol;
        while ((symbol = inputStream.read()) != -1) {
          // Iterate over predicates and write symbol to corresponding output files
          for (Map.Entry<Predicate<Integer>, File> entry : outputMapping.entrySet()) {
            Predicate<Integer> predicate = entry.getKey();
            File outputFile = entry.getValue();
            if (predicate.test(symbol)) {
              try (FileOutputStream outputStream = new FileOutputStream(outputFile, true)) {
                outputStream.write(symbol);
              } catch (IOException e) {
                throw new IllegalArgumentException("Error writing to output file: " + outputFile.getName());
              }
            }
          }
        }
      } catch (IOException e) {
        throw new IllegalArgumentException("Error reading input file");
      }
    }
  }
}
