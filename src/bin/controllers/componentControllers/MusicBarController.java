package bin.controllers.componentControllers;

import bin.controllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.io.File;

public class MusicBarController
{
  private MainController main;
  public MediaPlayer mediaPlayer;
  public Media media;

  private boolean repeat = false;
  private boolean shuffle = true;

  @FXML private Label titleLabel;
  @FXML private Label currentTimeLabel;
  @FXML private Label remainingTimeLabel;
  @FXML private Slider scrubber;
  @FXML private Button playBtn;
  @FXML private Button pauseBtn;
  @FXML private Button skipBtn;

  public void Init(MainController mainController)
  {
    this.main = mainController;
  }

  @FXML public void OnScrubbingComplete()
  {
    System.out.println("Scrubbing Complete");
  }

  @FXML public void OnMouseWheelComplete(ActionEvent event)
  {
    System.out.println("Mouse Wheel Scrub Complete");
  }

  @FXML public void OnPlayBtnClicked(ActionEvent event)
  {
    System.out.println("Play Button Clicked");
  }

  public void updateMusicBar()
  {
    if(mediaPlayer != null) mediaPlayer.stop();
    File song = main.selectedSong;
    titleLabel.setText(song.getName().substring(0, song.getName().lastIndexOf(".")));
    media = new Media(song.toURI().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
    System.out.println("Music Bar update called. New Song is " + song.getName());
    mediaPlayer.setOnEndOfMedia(() -> playNextSong());
  }

  public void playNextSong()
  {
    File song = main.selectedSong;

    //Figure out what to play next here
    if(repeat) {
      //doing nothing will allow the song to repeat
    } else if(shuffle) {
      main.selectedSong = main.libraryList.get((int) Math.floor(Math.random() * (main.libraryList.size() + 1)));
    } else {
      //play next song in the list
      try {
        main.selectedSong = main.libraryList.get(main.libraryList.indexOf(song) + 1);
      } catch(IndexOutOfBoundsException e) {
        main.selectedSong = main.libraryList.get(0);
      }
    }

    updateMusicBar();
  }

  public void skip()
  {

  }

  public void back()
  {

  }

}
