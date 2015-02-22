package app;

import controller.Mediator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    public static final String dataFile = "testfile.dat";
    public static final String appName = "Boat Registry";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Mediator m = new Mediator(dataFile);
        primaryStage.setTitle(appName);
        primaryStage.setScene(m.activeScene());

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
