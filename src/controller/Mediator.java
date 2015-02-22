package controller;

import javafx.application.Platform;
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
    private Colleague colleague;
    private Deque<model.Boat> boats;
    private Deque<model.Member> members;

    public enum TransmissionSignals {
        CREATE_BOAT, EDIT_BOAT, UPDATE_BOAT,
        CREATE_MEMBER, EDIT_MEMBER, UPDATE_MEMBER,
        CLOSE, EXIT
    }

    public Mediator(String filename)
    {
        this.filename = filename;
        this.secondaryStage = new Stage();
        this.colleague = new Colleague(this);

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
        switch ((TransmissionSignals) arg) {
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
            case CLOSE:
                secondaryStage.close();
                break;
            case EXIT:
                Platform.exit();
                break;
        }
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadDataFromFile() throws IOException, ClassNotFoundException
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
