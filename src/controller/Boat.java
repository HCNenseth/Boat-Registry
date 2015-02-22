package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
        notifyObservers("close");
    }

    @FXML
    private void saveButtonAction()
    {
        closeButtonAction();
    }
}
