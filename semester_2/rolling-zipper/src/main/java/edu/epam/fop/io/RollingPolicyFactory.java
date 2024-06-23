package edu.epam.fop.io;

import java.util.zip.ZipEntry;

public final class RollingPolicyFactory {

  public static RollingPolicy sizePolicy(int sizeThreshold) {
    return (ZipEntry entry, int fileCount, long size) ->
            size + entry.getSize() >= sizeThreshold;
  }

  public static RollingPolicy amountPolicy(int amountThreshold) {
    return (ZipEntry entry, int fileCount, long size) ->
            fileCount >= amountThreshold;

  }
}
