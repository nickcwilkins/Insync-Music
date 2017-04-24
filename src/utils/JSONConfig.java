package utils;

//for reading and writing data from the config.json file

import com.google.gson.stream.*;

import java.io.*;

public class JSONConfig {
  private static String configDirectoryStr;
  private static String configFileStr;
  private static File config;

  public static void Init()
  {
    configDirectoryStr = System.getProperty("user.home") + "\\AppData\\Roaming\\Insync";
    configFileStr = configDirectoryStr + "\\config.json";

    LoadConfig();
  }

  private static void LoadConfig()
  {
    File configDir = new File(configDirectoryStr);
    config = new File(configFileStr);

    try {
      if(configDir.exists()) {
        if(!config.exists()) {
          config.createNewFile();
        }
      } else {
        configDir.mkdir();
        config.createNewFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void SetLibraryDirectory(String newLibraryDir)
  {
    try {
      JsonWriter writer = new JsonWriter(new OutputStreamWriter(new FileOutputStream(config), "UTF-8"));
      writer.beginObject();
      writer.name("libraryDir").value(newLibraryDir);
      writer.endObject();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String GetLibraryDirectory() {
    JsonReader reader = null;

    try {
      FileInputStream inputStream = new FileInputStream(config);

      //check if there's any data in the file
      if(inputStream.available() == 0) {
        return "";
      }

      reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

      reader.beginObject();

      while(reader.hasNext()) {
        if(reader.nextName().equals("libraryDir")) {
          return reader.nextString();
        }
      }

      reader.endObject();
      reader.close();

    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }
}
