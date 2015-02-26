package app;

import controller.Mediator;
import data.Data;
import javafx.application.Application;
import javafx.stage.Stage;
import storage.DequeStorage;

public class Main extends Application
{
    public static final String dataFile = "testfile.dat";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        DequeStorage.setInstance(dataFile);
        Data.getInstance().loadData();



        Mediator m = Mediator.getInstance(dataFile);
        primaryStage.setTitle(m.getActive().getTitle());
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
