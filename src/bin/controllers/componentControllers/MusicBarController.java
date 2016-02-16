package bin.controllers.componentControllers;

import bin.controllers.MainController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicBarController
{
  private MainController main;
  public MediaPlayer mediaPlayer;
  public Media media;

  private boolean repeat = false;
  private boolean shuffle = false;

  @FXML private Label titleLabel;
  @FXML private Label currentTimeLabel;
  @FXML private Label remainingTimeLabel;
  @FXML private Slider scrubber;
  @FXML private ImageView playBtn;

  ChangeListener<Number> sliderScrubListener;
  ChangeListener<Duration> sliderUpdate;
  InvalidationListener textUpdate;

  public void Init(MainController mainController)
  {
    this.main = mainController;
    this.sliderScrubListener = (observable, oldValue, newValue) -> mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(scrubber.getValue() / 100.0));
    this.sliderUpdate = (observable, oldValue, newValue) -> scrubber.setValue((mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis()) * 100.0);
    this.textUpdate = ((observable) -> {

      String newCurrentTime = "";
      //String newRemainingTime = "";

      newCurrentTime = newCurrentTime.concat(Integer.toString((int) mediaPlayer.getCurrentTime().toSeconds() / 60) + ":");
      if((int) mediaPlayer.getCurrentTime().toSeconds() % 60 < 10) newCurrentTime = newCurrentTime.concat(Integer.toString(0));
      newCurrentTime = newCurrentTime.concat(Integer.toString((int) mediaPlayer.getCurrentTime().toSeconds() % 60));
      currentTimeLabel.setText(newCurrentTime);
      //remainingTimeLabel.setText(newRemainingTime);
    });
  }

  public void updateMusicBar()
  {
    if(mediaPlayer != null) mediaPlayer.stop();
    File song = main.selectedSong;
    titleLabel.setText(song.getName().substring(0, song.getName().lastIndexOf(".")));
    media = new Media(song.toURI().toString());
    mediaPlayer = new MediaPlayer(media);
    main.contentPaneController.listView.getSelectionModel().select(main.contentPaneController.libraryList.indexOf(song));
    mediaPlayer.play();
    mediaPlayer.currentTimeProperty().addListener(sliderUpdate);
    mediaPlayer.currentTimeProperty().addListener(textUpdate);
    mediaPlayer.setOnEndOfMedia(() -> playNextSong());
    System.out.println("Music Bar update called. New Song is " + song.getName());
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

  @FXML public void OnBackClicked()
  {

  }

  @FXML public void OnPlayClicked()
  {

  }

  @FXML public void OnNextClicked()
  {
    playNextSong();
  }

  @FXML public void OnRepeatClicked()
  {
    if(!repeat) repeat = true;
    else repeat = false;
  }

  @FXML public void OnShuffleClicked()
  {
    if(!shuffle) shuffle = true;
    else shuffle = false;
  }

  @FXML public void OnSliderMouseDown()
  {
    mediaPlayer.currentTimeProperty().removeListener(sliderUpdate);
    scrubber.valueProperty().addListener(sliderScrubListener);
  }
  @FXML public void OnSliderMouseUp()
  {
    scrubber.valueProperty().removeListener(sliderScrubListener);
    mediaPlayer.currentTimeProperty().addListener(sliderUpdate);
  }


}
