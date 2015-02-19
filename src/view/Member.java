package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by alex on 2/17/15.
 */
public class Member extends Stage
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;

    public Member() throws IOException
    {
        setTitle("New Member");
        Parent root = FXMLLoader.load(getClass().getResource("../layout/member.fxml"));
        setScene(new Scene(root, WIDTH, HEIGHT));

        setMaxHeight(HEIGHT);
        setMaxWidth(WIDTH);
        setMinHeight(HEIGHT);
        setMinWidth(WIDTH);

        show();
    }
}
