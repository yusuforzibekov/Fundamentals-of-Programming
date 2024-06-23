package edu.epam.fop.io;

import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RollingZipper {

  public void zip(Iterable<File> inputFiles, RollingPolicy policy, Iterator<OutputStream> outputs) throws IOException {
    // TODO write your code here
    ZipOutputStream zipOutputStream = null;
    OutputStream currentOutput = null;
    int currentFileCount = 0;
    long currentSize = 0;

    try {
      for (File inputFile : inputFiles) {
        ZipEntry entry = new ZipEntry(inputFile.getName());
        if (zipOutputStream == null || policy.shouldRoll(entry, currentFileCount, currentSize)) {
          if (zipOutputStream != null) {
            zipOutputStream.close();
            currentOutput.close();
          }
          currentOutput = outputs.next();
          zipOutputStream = new ZipOutputStream(currentOutput);

          currentFileCount = 0;
          currentSize = 0;
        }

        zipOutputStream.putNextEntry(entry);
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        byte[] buffer = new byte[1032];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
          zipOutputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();

        zipOutputStream.closeEntry();
        currentFileCount++;
        currentSize += entry.getCompressedSize();
      }
    } catch (IOException e) {
      zipOutputStream.close();
      currentOutput.close();
      throw e;
    }
  }
}
