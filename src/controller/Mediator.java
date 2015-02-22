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
        BASE("../layout/base.fxml", "Boat Registry", 800, 650),
        MEMBER_NEW("../layout/member_new.fxml", "New Member", 300, 200),
        MEMBER_EDIT("../layout/member_edit.fxml", "Edit Member", 300, 200),
        BOAT_NEW("../layout/boat_new.fxml", "New Boat", 300, 400),
        BOAT_EDIT("../layout/boat_edit.fxml", "Edit Boat", 300, 400);

        private int width;
        private int height;
        private String layout;
        private String title;

        private Windows(String layout, String title, int width, int height)
        {
            this.width = width;
            this.height = height;
            this.layout = layout;
            this.title = title;
        }

        public String getLayout() { return layout; }
        public String getTitle() { return title; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
    }

    public enum TransmissionSignals {
        CREATE_BOAT, EDIT_BOAT, UPDATE_BOAT,
        CREATE_MEMBER, EDIT_MEMBER, UPDATE_MEMBER,
        CLOSE
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
            case BOAT_NEW:
            case BOAT_EDIT:
                loader.setController(new Boat.Builder(members).observer(this).build());
                break;
            case MEMBER_NEW:
            case MEMBER_EDIT:
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
        TransmissionSignals ts = (TransmissionSignals) arg;

        switch (ts) {
            case CLOSE:
                secondaryStage.close();
                break;
            case CREATE_MEMBER:
                switchScene(Windows.MEMBER_NEW);
                setScene();
                break;
            case EDIT_MEMBER:
                // get info
                switchScene(Windows.MEMBER_EDIT);
                setScene();
                break;
            case UPDATE_MEMBER:
                // get payload and do action
                secondaryStage.close();
                break;
            case CREATE_BOAT:
                switchScene(Windows.BOAT_NEW);
                setScene();
                break;
            case EDIT_BOAT:
                // get info and setup edit window
                switchScene(Windows.BOAT_EDIT);
                setScene();
                break;
            case UPDATE_BOAT:
                // get payload and do action
                secondaryStage.close();
                break;
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
    private void setScene()
    {
        try {
            secondaryStage.setTitle(active.getTitle());
            secondaryStage.setScene(activeScene());

            secondaryStage.setMaxHeight(getActive().getHeight());
            secondaryStage.setMaxWidth(getActive().getWidth());
            secondaryStage.setMinHeight(getActive().getHeight());
            secondaryStage.setMinWidth(getActive().getWidth());

            secondaryStage.toFront();
            secondaryStage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
