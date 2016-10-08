package bin.controllers.componentControllers;

import java.io.File;
import java.io.FilenameFilter;

public class FileTypeFilter implements FilenameFilter {
  public boolean accept(File dir, String name) {
    if (name.toLowerCase().endsWith(".mp3"))       return true;
    else if (name.toLowerCase().endsWith(".mp4"))  return true;
    else if (name.toLowerCase().endsWith(".wav"))  return true;
    else if (name.toLowerCase().endsWith(".pcm"))  return true;
    else if (name.toLowerCase().endsWith(".aac"))  return true;
    else if (name.toLowerCase().endsWith(".vp6"))  return true;
    else if (name.toLowerCase().endsWith(".flv"))  return true;
    else if (name.toLowerCase().endsWith(".fxm"))  return true;
    else if (name.toLowerCase().endsWith(".aiff")) return true;
    else if (name.toLowerCase().endsWith(".hls"))  return true;
    else if (name.toLowerCase().endsWith(".m4a"))  return true;
    else return false;
  }
}

