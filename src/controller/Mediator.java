package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import storage.Deque;
import storage.DequeStorage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by alex on 2/21/15.
 */
public class Mediator implements Observer
{
    private String filename;
    private Windows active = Windows.BASE;
    private Deque<model.Boat> boats;
    private Deque<model.Member> members;

    public enum Windows {
        BASE("../layout/base.fxml", 800, 650),
        MEMBER("../layout/member.fxml", 300, 400),
        BOAT("../layout/boat.fxml", 300, 400);

        private int width;
        private int height;
        private String layout;

        private Windows(String layout, int width, int height)
        {
            this.width = width;
            this.height = height;
            this.layout = layout;
        }

        public String getLayout() { return layout; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
    }

    public Mediator(String filename)
    {
        this.filename = filename;
        try {
            loadDataFromFile();
        } catch (IOException | ClassNotFoundException e) {
            // do something useful!
        }
    }

    public Scene activeScene() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(active.getLayout()));
        controller.Base b = new controller.Base(members, boats);
        b.addObserver(this);
        loader.setController(b);

        return new Scene(loader.load(), active.getWidth(), active.getHeight());
    }

    private void switchScene(Windows w)
    {
        active = w;
    }

    private void loadDataFromFile() throws IOException,
            ClassNotFoundException
    {
        DequeStorage ds = new DequeStorage(filename);
        Deque<?> fileList = ds.read();
        members = (Deque<model.Member>) fileList.removeFirst();
        boats = (Deque<model.Boat>) fileList.removeFirst();
    }

    public Windows getActive() { return active; }

    @Override
    public void update(Observable obj, Object arg)
    {
        System.out.println(arg);
    }
}
