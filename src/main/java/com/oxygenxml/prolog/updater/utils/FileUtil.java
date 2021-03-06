package com.oxygenxml.prolog.updater.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * Utility methods for working with Files.
 * 
 * @author cosmin_duna
 *
 */
public class FileUtil {

  /**
   * Private constructor.
   */
  private FileUtil() {
    // Avoid instantiation.
  }
  
  
  /**
   * Check if the given file is a new one,
   * verifying the creation time and the current time.
   * 
   * @param fileLocation The file location.
   * 
   * @return <code>true</code> if the file is new, <code>false</code> if it's not new and this doesn't exist.
   */
  public static boolean checkCurrentNewDocumentState(URL fileLocation) {
    boolean isNew = false;
    try {
      if("file".equals(fileLocation.getProtocol())) {
        File file = new File(fileLocation.getFile());
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        FileTime creationFileTime = attr.creationTime();
        long currentTimeMillis = System.currentTimeMillis();
        if (creationFileTime != null &&
            currentTimeMillis - creationFileTime.toMillis() < 2000) {
          isNew = true;
        }
      }
    } catch (IOException e) {
      // Ignore this
    }

    return isNew;
  }
}
