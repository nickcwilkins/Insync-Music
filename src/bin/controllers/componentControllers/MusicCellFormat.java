package bin.controllers.componentControllers;

import javafx.scene.control.ListCell;
import java.io.File;

public class MusicCellFormat extends ListCell<File>
{
    String musicName;

    public MusicCellFormat() { }

    @Override
    protected void updateItem(File item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            //set the text of the cell to the name of the the file minus the extension
            musicName = item.getName();
            musicName = musicName.substring(0, musicName.lastIndexOf("."));

            setText(musicName);
        }
    }
}
