package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/17/15.
 */
public class Member extends Observable implements Initializable
{
    @FXML
    private Button closeButton, saveButton;

    private Member(Builder b)
    {
        addObserver(b.mediator);
    }

    public static class Builder
    {
        private Mediator mediator;
        public Builder observer(Mediator mediator)
        {
            this.mediator = mediator;
            return this;
        }

        public Member build()
        {
            return new Member(this);
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
        setChanged();
        notifyObservers(Mediator.TransmissionSignals.UPDATE_MEMBER);
    }

}
