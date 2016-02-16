//The main controller allows component controllers to communicate by calling methods defined in the main controller
package bin.controllers;

import bin.controllers.componentControllers.MusicBarController;
import bin.controllers.componentControllers.ContentPaneController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

public class MainController
{
  private Stage stage;
  public File library;
  public File selectedSong;
  public List<File> libraryList;

  //Component controllers
  @FXML public ContentPaneController contentPaneController;
  @FXML public MusicBarController musicBarController;

  @FXML private MenuItem selectLibraryBtn;
  @FXML private MenuItem syncBtn;

  @FXML public void initialize()
  {
    System.out.println("Main Controller Initialized");
    contentPaneController.Init(this);
    musicBarController.Init(this);
  }

  public void setStage(Stage stage)
  {
    this.stage = stage;
  }

  @FXML public void selectLibrary()
  {
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Select A Library");
    this.library = chooser.showDialog(stage);
    contentPaneController.setListView();
  }

  /* ============================================================================================
                                      MUSIC PLAYER OPERATIONS
   ============================================================================================ */
  public void updateSong()
  {
    musicBarController.updateMusicBar();
  }

  /* ============================================================================================
                                         FILE OPERATIONS
   ============================================================================================ */
  @FXML public void syncOperation() throws IOException
  {
    File source;
    File target;

    if (this.library == null) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Sync Operation");
      alert.setHeaderText("No source location is selected.");
      return;
    } else {
      source = this.library;
    }

    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Select Target Location");
    target = chooser.showDialog(stage);
    if(target == null) return;
    copyDirectory(source, target);
  }

  // ==========================================================================================
  void copyDirectory(File sourceLocation , File targetLocation) throws IOException
  {
    //The first part of this logic is false after the first time around
    if (sourceLocation.isDirectory())
    {
      if (!targetLocation.exists())
      {
        targetLocation.mkdir();
      }

      String[] childrenSource = sourceLocation.list();
      String[] childrenTarget = targetLocation.list();
      ArrayList<String> childrenMissing = findMissing(childrenSource, childrenTarget);

      //If the childrenMissing arrayList is empty then the target folder is up to date
      if(childrenMissing.isEmpty())
      {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sync Operation");
        alert.setHeaderText("Target folder is up to date.");
        alert.showAndWait();
        return;
      }
      System.out.println(childrenMissing);
      for (int i = 0; i < childrenMissing.size(); i++)
      {
        copyDirectory(new File(sourceLocation, childrenMissing.get(i)), new File(targetLocation, childrenMissing.get(i)));
      }

    }
    else
    {
      //Here sourceLocation and targetLocation are actual files instead of directories
      InputStream in = new FileInputStream(sourceLocation);
      OutputStream out = new FileOutputStream(targetLocation);

      //Copy the bits from instream to outstream
      byte[] buf = new byte[4096];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      in.close();
      out.close();
    }
  }

  // ==========================================================================================
  ArrayList<String> findMissing(String[] source, String[] target)
  {
    ArrayList<String> missingList = new ArrayList<String>();
    missingList.ensureCapacity(source.length); //ArrayList is always as big as the amount of files in the source folder

    //check if the source file is contained in the target
    for(int i = 0; i < source.length; i++)
    {
      if (!Arrays.asList(target).contains(source[i]))
      {
        missingList.add(source[i]);
      }
    }
    return missingList;
  }

}
