import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import java.io.IOException;


public class SyncApplication extends Application
{
  //define css stylesheets here
  String[] styleSheets = {
    "ui/css/main.css",
    "ui/css/contentPane.css",
    "ui/css/musicBar.css",
  };

  public static void main(String[] args)
  {
    SyncApplication.launch();
  }

  public void start(Stage stage) throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/fxml/root.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    scene.getStylesheets().addAll(styleSheets);
    setUserAgentStylesheet(STYLESHEET_CASPIAN);

    MainController controller = loader.getController();
    controller.setStage(stage);

    stage.setTitle("InSync Music");
    stage.setScene(scene);
    stage.setMinHeight(520);
    stage.setMinWidth(816);

    stage.show();
  }
}
