package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by alex on 2/17/15.
 */
public class Boat extends Stage
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 650;

    public Boat() throws IOException
    {
        setTitle("New Boat");
        Parent root = FXMLLoader.load(getClass().getResource("../layout/boat.fxml"));
        setScene(new Scene(root, WIDTH, HEIGHT));
        show();
    }
}
