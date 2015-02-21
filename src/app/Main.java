package app;

import controller.Mediator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static final String dataFile = "testfile.dat";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Mediator m = new Mediator(dataFile);

        Scene current = m.activeScene();
        primaryStage.setTitle("Boat Registry");
        primaryStage.setScene(current);

        primaryStage.setMaxHeight(m.getActive().getHeight());
        primaryStage.setMaxWidth(m.getActive().getWidth());
        primaryStage.setMinHeight(m.getActive().getHeight());
        primaryStage.setMinWidth(m.getActive().getWidth());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
