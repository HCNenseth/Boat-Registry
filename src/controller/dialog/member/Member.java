package controller.dialog.member;

import controller.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import validator.StringMatcher;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/17/15.
 */
public final class Member extends Observable implements Initializable
{
    @FXML
    private TextField firstNameField, lastNameField;
    @FXML
    private Label firstNameError, lastNameError;
    @FXML
    private Button closeButton, saveButton;

    private model.Member.Builder payload;

    private Member(Builder b)
    {
        addObserver(b.mediator);
    }

    public static class Builder implements commons.Builder
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

    /**
     * THIS HORRIBLE ABOMINATION NEEDS REFACTORING!
     */
    @FXML
    private void saveButtonAction()
    {
        boolean valid = true;

        // get all the values.
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();

        // reset all error messages.
        firstNameError.setText("");
        lastNameError.setText("");

        if (!StringMatcher.firstname(firstname)) {
            firstNameError.setText("Error value");
            firstNameError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.lastname(lastname)) {
            lastNameError.setText("Error value");
            lastNameError.setVisible(true);
            valid = false;
        }

        if (valid) {
            payload = new model.Member.Builder(firstname, lastname);
            setChanged();
            notifyObservers(Mediator.TransmissionSignals.UPDATE_MEMBER);
        }
    }

    public model.Member.Builder getPayload() { return payload; }
}
