package controller;

import common.Command;
import data.Data;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storage.Deque;

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

    protected Stage secondaryStage;
    private String filename;
    private Windows active = Windows.BASE;

    private Colleague colleague;

    private Mediator(String filename)
    {
        this.filename = filename;
        this.secondaryStage = new Stage();
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
        Command c = (Command) arg;

        switch (c.getSignalOrigin()) {
            case DIALOG: colleague.processDialogSignal(c); return;
            case WINDOW: colleague.processWindowSignal(c); return;
        }

        /**
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
        */
    }

    /**
     * End routines to be executes before app goes bye bye.
     */
    protected void endRoutine()
    {
        try {
            Data.getInstance().writeData();
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

    public Windows getActive() { return active; }

    protected void switchScene(Windows w) {
        active = w;
        setScene();
    }
}
