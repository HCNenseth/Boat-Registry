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
    // Singleton
    private static Mediator INSTANCE;

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

    private Mediator(String filename)
    {
        this.filename = filename;
        this.secondaryStage = new Stage();

        try {
            loadDataFromFile();
        } catch (IOException | ClassNotFoundException e) {
            members = new Deque<>();
            boats = new Deque<>();
        }

        this.colleague = Colleague.getInstance(this);
    }

    public static Mediator getInstance(String filename)
    {
        if (INSTANCE == null) { INSTANCE = new Mediator(filename); }
        return INSTANCE;
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
                // get payload, push into members.
                members.addLast(((Member) obj).getPayload().build());
                colleague.reloadMembers();
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
                // get payload, push into boats.
                model.Boat boat = ((Boat) obj).getPayload().build();
                boats.addLast(boat);

                if (boat.getOwner() != null)  {
                    model.Member member = boat.getOwner();
                    member.push(boat);
                    colleague.reloadMembers();
                }

                colleague.reloadBoats();
                secondaryStage.close();
                break;
            case CLOSE:
                secondaryStage.close();
                break;
            case EXIT:
                endRoutine();
                break;
        }
    }

    /**
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * Read data from file and load into the private members
     * "members" and "boats"
     */
    private void loadDataFromFile() throws IOException, ClassNotFoundException
    {
        DequeStorage ds = DequeStorage.getInstance(filename);
        Deque<?> fileList = ds.read();
        members = (Deque<model.Member>) fileList.removeFirst();
        boats = (Deque<model.Boat>) fileList.removeFirst();

        // Tippy toe around the static member problem...
        model.Member.setMemberCount(members.size() + 1);
    }

    /**
     * @throws IOException
     *
     * Write data to tile. Create a new god list and
     * push inn "members" and "boats". Write this new god
     * list to file.
     */
    private void writeDataToFile() throws IOException
    {
        DequeStorage ds = DequeStorage.getInstance(filename);
        Deque<Deque> godList = new Deque<Deque>(); // yo dawg!
        godList.addLast(members);
        godList.addLast(boats);
        ds.write(godList);
    }

    /**
     * End routines to be executes before app goes bye bye.
     */
    private void endRoutine()
    {
        try {
            writeDataToFile();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        Platform.exit();
    }

    /**
     * @throws IOException
     *
     * Set a scene on the secondary stage. This is
     * for displaying dialog boxes etc.
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
            System.out.println("=> Mediator.setScene");
            System.out.println(ioe);
        }
    }

    protected Deque<model.Member> getMembers() { return members; }

    protected Deque<model.Boat> getBoats() { return boats; }

    public Windows getActive() { return active; }

    private void switchScene(Windows w) { active = w; }
}
