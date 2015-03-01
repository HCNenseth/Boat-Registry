package app;

import controller.Mediator;
import storage.Data;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Mediator m = Mediator.getInstance();

        m.setPrimaryStage(primaryStage);
        m.loadPrimaryStage();

    }

    public static void main(String[] args) throws Exception
    {
        try {
            String dataFile = args[0];
            Data.getInstance().setFilename(dataFile).loadData();
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.printf("No file is selected!");
        }

        launch(args);
    }
}
