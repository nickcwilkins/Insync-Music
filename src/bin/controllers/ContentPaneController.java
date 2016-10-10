package bin.controllers;

import bin.utils.FileTypeFilter;
import bin.utils.MusicCellFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;

public class ContentPaneController
{
  private MainController main;

  @FXML public TreeView<String> tabListing;
  @FXML public ListView<File> listView;

  public ObservableList<File> libraryList = FXCollections.observableArrayList();
  //private ObservableList<String> playlistListItems = FXCollections.observableArrayList();

  public void Init(MainController mainController)
  {
    main = mainController;

    InitializeTreeView();
    setListView();
  }

  private void InitializeTreeView()
  {
    assert tabListing != null : "TabListing not initialized, check fxml";

    TreeItem<String> rootNode = new TreeItem<>("Root");
    TreeItem<String> libraryTreeItem = new TreeItem<>("Music Library");
    TreeItem<String> playListsTreeItem = new TreeItem<>("Playlists");
    rootNode.getChildren().addAll(libraryTreeItem, playListsTreeItem);

    tabListing.setRoot(rootNode);
    tabListing.setShowRoot(false);
    tabListing.setOnMouseClicked(event -> System.out.println(tabListing.getSelectionModel().getSelectedItem()));
  }

  public void setListView()
  {
    listView.getItems().clear(); //clear the list view each time it is changed
    if(main.libraryDir == null) return;
    //accepted file types
    libraryList.addAll(main.libraryDir.listFiles(new FileTypeFilter()));

    main.currentLibrary = this.libraryList;
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
}
