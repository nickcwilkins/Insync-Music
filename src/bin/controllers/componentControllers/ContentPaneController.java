package bin.controllers.componentControllers;

import bin.controllers.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.File;


public class ContentPaneController
{
  private MainController main;

  @FXML private TreeView<String> directoryList;
  @FXML public ListView<File> listView;


  public ObservableList<File> libraryList = FXCollections.observableArrayList();
  //private ObservableList<String> playlistListItems = FXCollections.observableArrayList();

  public void Init(MainController mainController)
  {
    this.main = mainController;
    //check for library
    if(main.library == null)
    {
      main.selectLibrary();
    }

    initializeTreeView();
    setListView();
  }

  private void initializeTreeView()
  {
    //Create a TreeItem to serve as the root. is hidden in application
    TreeItem<String> rootNode = new TreeItem<>("Root");
    TreeItem<String> libraryTreeItem = new TreeItem<>("Music Library");
    TreeItem<String> playListsTreeItem = new TreeItem<>("Playlists");
    rootNode.getChildren().addAll(libraryTreeItem, playListsTreeItem);

    directoryList.setRoot(rootNode);
    directoryList.setShowRoot(false);
    directoryList.setOnMouseClicked(event -> System.out.println(directoryList.getSelectionModel().getSelectedItem()));
  }

  //For creating a new item in the treeview from a string
  private void newTreeItem(String treeItem)
  {

  }

  public void setListView()
  {
    listView.getItems().clear(); //clear the list view each time it is changed
    if(main.library == null) return;
    //accepted file types
    libraryList.addAll(main.library.listFiles((dir, name) -> {
      if(name.toLowerCase().endsWith(".mp3")) return true;
      else if(name.toLowerCase().endsWith(".mp4")) return true;
      else if(name.toLowerCase().endsWith(".wav")) return true;
      else if(name.toLowerCase().endsWith(".pcm")) return true;
      else if(name.toLowerCase().endsWith(".aac")) return true;
      else if(name.toLowerCase().endsWith(".vp6")) return true;
      else if(name.toLowerCase().endsWith(".flv")) return true;
      else if(name.toLowerCase().endsWith(".fxm")) return true;
      else if(name.toLowerCase().endsWith(".aiff")) return true;
      else if(name.toLowerCase().endsWith(".hls")) return true;
      else if(name.toLowerCase().endsWith(".m4a")) return true;
      else return false;
    }));

    main.libraryList = this.libraryList;
    listView.setItems(libraryList);

    listView.setCellFactory(param -> new MusicCellFormat());

    listView.setOnMouseClicked(event -> {
      if(event.getClickCount() == 2)
      {
        main.selectedSong = listView.getSelectionModel().getSelectedItem();
        main.updateSong();
      }
    });
  }

  private void changeListView()
  {

  }
}
