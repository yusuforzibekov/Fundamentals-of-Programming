package edu.epam.fop.io;

import java.util.zip.ZipEntry;

public interface RollingPolicy {

  boolean shouldRoll(ZipEntry entry,int fileCount, long size);
}
