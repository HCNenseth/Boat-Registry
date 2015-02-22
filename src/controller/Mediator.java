package controller;

import controller.dialog.Boat;
import controller.dialog.Member;
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
 *
 * This apps "God object".
 * This class grows bigger and bigger and have therefore been
 * assigned a colleague to help with the heavy lifting.
 * This pattern should possibly be refactored in the future.
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

        try {
            loadDataFromFile();
        } catch (IOException | ClassNotFoundException e) {
            members = new Deque<>();
            boats = new Deque<>();
        }

        this.colleague = new Colleague(this);
    }

    /**
     * @return
     * @throws IOException
     *
     * This method has got some of its responsibilities moved
     * over to the helper colleague class.
     */
    public Scene activeScene() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(active.getLayout()));
        switch (active) {
            case BOAT_NEW:
            case BOAT_EDIT:
                loader.setController(colleague.getBoatController());
                break;
            case MEMBER_NEW:
            case MEMBER_EDIT:
                loader.setController(colleague.getMemberController());
                break;
            default:
                loader.setController(colleague.getBaseController());
        }
        return new Scene(loader.load(), active.getWidth(), active.getHeight());
    }


    /**
     * @param obj the caller object.
     * @param arg the caller data.
     *
     * This method needs refactoring, and should push some
     * of its responsibilities to the colleague class.
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
                // get payload, push into members, and call basecontroller
                members.addLast(((Member) obj).getPayload().build());
                colleague.getBaseController().updateMembers();
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
                // get payload, push into boats, and call basecontroller
                boats.addLast(((Boat) obj).getPayload().build());
                colleague.getBaseController().updateBoats();
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

    protected Deque<model.Member> getMembers() { return members; }

    protected Deque<model.Boat> getBoats() { return boats; }

    public Windows getActive() { return active; }

    private void switchScene(Windows w) { active = w; }
}
