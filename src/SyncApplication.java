import bin.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import java.io.IOException;


public class SyncApplication extends Application
{
  //define css stylesheets here
  String[] styleSheets = {
    "bin/ui/css/main.css",
    "bin/ui/css/contentPane.css",
    "bin/ui/css/musicBar.css",
  };

  public static void main(String[] args)
  {
    SyncApplication.launch();
  }

  public void start(Stage stage) throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("bin/ui/fxml/root.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root, 790, 480);
    MainController controller = loader.getController();
    controller.setStage(stage);
    stage.setTitle("InSync Music");
    stage.setScene(scene);
    setUserAgentStylesheet(STYLESHEET_CASPIAN);
    scene.getStylesheets().addAll(styleSheets);
    stage.setResizable(false);
    stage.show();
  }
}
