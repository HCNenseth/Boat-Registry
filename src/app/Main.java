package app;

import model.Boat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Member;
import storage.Deque;

public class Main extends Application
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 650;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/base.fxml"));
        primaryStage.setTitle("Boat Registry");
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        primaryStage.setMaxHeight(HEIGHT);
        primaryStage.setMaxWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMinWidth(WIDTH);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Deque<Boat> boats = new Deque<Boat>();
        Deque<Member> members = new Deque<Member>();

        launch(args);
    }
}
