//The main controller allows component controllers to communicate by calling methods defined in the main controller
package bin.controllers;

import bin.utils.FileTypeFilter;
import bin.utils.JSONConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.*;
import java.util.*;

public class MainController
{
  private Stage stage;
  public File libraryDir;//location of music files
  public File selectedSong;
  public ObservableList<File> currentLibrary;//List of files that can be played currently, most times the main music libraryDir

  //Component controllers
  @FXML public ContentPaneController contentPaneController;
  @FXML public MusicBarController musicBarController;

  @FXML private MenuItem selectLibraryBtn;
  @FXML private MenuItem syncBtn;

  @FXML public void initialize() {
    currentLibrary = FXCollections.observableArrayList();

    JSONConfig.Init();
    contentPaneController.Init(this);
    musicBarController.Init(this);

    LoadMainLibrary();//always do this last
  }

  public void setStage(Stage stage)
  {
    this.stage = stage;
  }

  private void LoadMainLibrary()
  {
    String directoryStr = JSONConfig.GetLibraryDirectory();
    if(!directoryStr.equals("")) {
      libraryDir = new File(JSONConfig.GetLibraryDirectory());
      currentLibrary.addAll(libraryDir.listFiles(new FileTypeFilter()));
      contentPaneController.setListView();
    } else {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("No Library Found");
      alert.setHeaderText("Would you like to select a new library?");
      alert.showAndWait();
      SelectNewMainLibrary();
    }
  }

  @FXML public void SelectNewMainLibrary()
  {
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Select A Library");
    libraryDir = chooser.showDialog(stage);
    JSONConfig.SetLibraryDirectory(libraryDir.getAbsolutePath());
    LoadMainLibrary();
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
  @FXML public void AddFilesToLibrary()
  {
    File source;

    if(libraryDir == null) {
      //todo: add an alert here
      SelectNewMainLibrary();
    }






  }



  @FXML public void SyncIntoLibrary() throws IOException
  {
    File source;
    File target;

    if (libraryDir == null) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Sync Operation");
      alert.setHeaderText("No source location is selected.");
      return;
    } else {
      source = libraryDir;
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
      for (String missingFile : childrenMissing) {

        InputStream in = new FileInputStream(new File(sourceLocation, missingFile));
        OutputStream out = new FileOutputStream(new File(targetLocation, missingFile));

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
    else
    {
      //probably perform these operations on a separate thread?


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
