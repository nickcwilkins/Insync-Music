import bin.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

import java.io.IOException;


public class SyncApplication extends Application
{

  public static void main(String[] args)
  {
    SyncApplication.launch();
  }

  public void start(Stage stage) throws IOException
  {
    String[] styleSheets = {
      "bin/css/main.css",
      "bin/css/contentPane.css",
      "bin/css/musicBar.css",
    };
    FXMLLoader loader = new FXMLLoader(getClass().getResource("bin/fxml/root.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root, 790, 480);
    MainController controller = loader.getController();
    controller.setStage(stage);
    stage.setTitle("InSync Music");
    stage.setScene(scene);
    scene.getStylesheets().addAll(styleSheets);
    stage.setResizable(false);
    stage.show();
  }
}
