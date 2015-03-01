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
        primaryStage.setTitle(m.getActive().getTitle());
        primaryStage.setScene(m.activeScene());

        /**
         * This is under normal conditions not necessary,
         * but to increase platform support it is added to
         * help window managers on obscure platforms...
         */
        primaryStage.setMaxHeight(m.getActive().getHeight());
        primaryStage.setMaxWidth(m.getActive().getWidth());
        primaryStage.setMinHeight(m.getActive().getHeight());
        primaryStage.setMinWidth(m.getActive().getWidth());

        primaryStage.show();
    }

    public static void main(String[] args) throws Exception
    {
        try {
            String dataFile = args[0];
            Data.getInstance().setFilename(dataFile).loadData();
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.printf("Trying standard 'testfile.dat'");
        }

        launch(args);
    }
}
