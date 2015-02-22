package controller;

import com.sun.javafx.css.BorderPaint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/17/15.
 */
public class Member extends Observable implements Initializable
{
    @FXML
    private TextField firstName, lastName;
    @FXML
    private Label firstNameError, lastNameError;
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
        firstNameError.setText("Error value");
        firstNameError.setVisible(true);
        lastNameError.setText("Error value");
        lastNameError.setVisible(true);
        return;
        //setChanged();
        //notifyObservers(Mediator.TransmissionSignals.UPDATE_MEMBER);
    }

}
