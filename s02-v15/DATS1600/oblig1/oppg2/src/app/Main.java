/**
 *
 * @filename Main.java
 *
 * @date 2015-02-17
 *
 * Boat Registry Main launcher.
 */

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
            System.out.println("=> No file arg!");
        }

        launch(args);
    }
}
