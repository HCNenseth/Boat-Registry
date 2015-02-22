package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    private Stage secondaryStage;
    private String filename;
    private Windows active = Windows.BASE;
    private Deque<model.Boat> boats;
    private Deque<model.Member> members;

    public enum Windows {
        BASE("../layout/base.fxml", 800, 650),
        MEMBER("../layout/member.fxml", 300, 200),
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
        this.secondaryStage = new Stage();

        try {
            loadDataFromFile();
        } catch (IOException | ClassNotFoundException e) {
            // do something useful!
        }
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public Scene activeScene() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(active.getLayout()));
        switch (active) {
            case BOAT:
                loader.setController(new Boat.Builder(members).observer(this).build());
                break;
            case MEMBER:
                loader.setController(new Member.Builder().observer(this).build());
                break;
            default:
                loader.setController(new Base.Builder(members, boats).observer(this).build());
        }
        return new Scene(loader.load(),active.getWidth(), active.getHeight());
    }

    public Windows getActive() { return active; }
    private void switchScene(Windows w) { active = w; }

    /**
     *
     * @param obj the caller object
     * @param arg the caller data
     */
    @Override
    public void update(Observable obj, Object arg)
    {
        System.out.println(obj);
        System.out.println(arg);

        String keyWord = (String) arg;

        if (keyWord.equals("newBoat")) {
            switchScene(Windows.BOAT);
        }
        if (keyWord.equals("newMember")) {
            switchScene(Windows.MEMBER);
        }
        try {
            setScene();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadDataFromFile() throws IOException,
            ClassNotFoundException
    {
        DequeStorage ds = new DequeStorage(filename);
        Deque<?> fileList = ds.read();
        members = (Deque<model.Member>) fileList.removeFirst();
        boats = (Deque<model.Boat>) fileList.removeFirst();
    }

    /**
     *
     * @throws IOException
     */
    private void setScene() throws IOException
    {
        secondaryStage.setTitle("Iam secondary");
        secondaryStage.setScene(activeScene());

        secondaryStage.setMaxHeight(getActive().getHeight());
        secondaryStage.setMaxWidth(getActive().getWidth());
        secondaryStage.setMinHeight(getActive().getHeight());
        secondaryStage.setMinWidth(getActive().getWidth());

        secondaryStage.toFront();
        secondaryStage.show();
    }
}
