package controller;

import share.Command;
import storage.Data;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by alex on 2/21/15.
 *
 * This apps central object.
 * This class grows bigger and bigger and have therefore been
 * assigned a colleague to help with the heavy lifting.
 * This pattern should possibly be refactored in the future.
 */
public class Mediator implements Observer
{
    // Singleton
    private static Mediator INSTANCE = new Mediator();

    protected Stage secondaryStage;
    private Configuration active = Configuration.BASE;

    private Colleague colleague;

    private Mediator()
    {
        this.secondaryStage = new Stage();
        this.colleague = Colleague.getInstance(this);
    }

    public static Mediator getInstance() { return INSTANCE; }

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
            case DIALOG:
                loader.setController(colleague.getDialogController());
                break;
            default:
                loader.setController(colleague.getBaseController());
        }
        return new Scene(loader.load(), active.getWidth(), active.getHeight());
    }

    /**
     * Receive signals from the controllers, and delegate
     * them in the right direction.
     *
     * @param obj the caller object.
     * @param arg the caller data.
     */
    @Override
    public void update(Observable obj, Object arg)
    {
        if (! (arg instanceof Command))
            throw new IllegalArgumentException("Wrong arg type!");

        Command c = (Command) arg;

        switch (c.getSignalOrigin()) {
            case WIDGET: colleague.processWidgetSignal(c); return;
            case WINDOW: colleague.processWindowSignal(c); return;
        }
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
     * for displaying widget boxes etc.
     */
    private void setScene()
    {
        try {
            secondaryStage.setTitle(active.getTitle());
            secondaryStage.setScene(activeScene());

            /**
             * This is under normal conditions not necessary,
             * but to increase platform support it is added to
             * help window managers on obscure platforms...
             */
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

    public Configuration getActive() { return active; }

    protected void switchScene(Configuration w) {
        active = w;
        setScene();
    }
}
