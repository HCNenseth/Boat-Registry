package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import storage.Deque;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/17/15.
 */
public class Boat extends Observable implements Initializable
{
    private Deque<model.Member> members;

    @FXML
    private Button closeButton, saveButton;
    @FXML
    private Label regNrError, typeError, yearError,
                  lengthError, powerError, colorError;

    private Boat(Builder b)
    {
        this.members = b.members;
        addObserver(b.mediator);
    }

    public static class Builder
    {
        private Mediator mediator;
        private Deque<model.Member> members;

        public Builder(Deque<model.Member> members)
        {
            this.members = members;
        }

        public Builder observer(Mediator mediator)
        {
            this.mediator = mediator;
            return this;
        }

        public Boat build()
        {
            return new Boat(this);
        }
    }

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle res)
    {

    }

    @FXML
    private void closeButtonAction()
    {
        setChanged();
        notifyObservers(Mediator.TransmissionSignals.CLOSE);
    }

    @FXML
    private void saveButtonAction()
    {
        regNrError.setText("Regnr error");
        regNrError.setVisible(true);

        typeError.setText("Type error");
        typeError.setVisible(true);

        yearError.setText("Year error");
        yearError.setVisible(true);

        lengthError.setText("Length error");
        lengthError.setVisible(true);

        powerError.setText("Power error");
        powerError.setVisible(true);

        colorError.setText("Color error");
        colorError.setVisible(true);

        //setChanged();
        //notifyObservers(Mediator.TransmissionSignals.UPDATE_BOAT);
    }
}
