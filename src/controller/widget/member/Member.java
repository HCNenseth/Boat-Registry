/**
 *
 * @filename Member.java
 *
 * @date 2015-02-17
 *
 */

package controller.widget.member;

import share.DataType;
import share.WidgetSignal;
import share.SignalType;
import controller.Mediator;
import storage.Data;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import share.validator.StringMatcher;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public final class Member extends Observable implements Initializable
{
    @FXML private TextField firstNameField, lastNameField;
    @FXML private Label firstNameError, lastNameError;
    @FXML private Button closeButton, saveButton;

    private model.member.Member member;
    private Mode mode = Mode.CREATE;

    private enum Mode {CREATE, UPDATE};

    private model.member.Member.Builder payload;

    private Member(Builder b)
    {
        addObserver(b.mediator);
    }

    public static class Builder implements share.Builder
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
        if (mode == Mode.UPDATE && member != null) {
            firstNameField.setText(member.getFirstname());
            lastNameField.setText(member.getLastname());
        }
    }

    @FXML
    private void closeButtonAction()
    {
        setCreateMode();
        resetFields();
        setChanged();
        notifyObservers(new WidgetSignal<>(SignalType.CLOSE));
    }

    /**
     * This method takes care of gathering inputs from the
     * fields in this widget. Then checking them against
     * the shared validator, after which it creates a Member
     * (or not). All this responsibility for one method.
     *
     * This horrible mess needs refactoring.
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

            setChanged();

            switch (mode) {
                case CREATE:
                    Data.getInstance()
                            .addMember(new model.member.Member.Builder(firstname, lastname).build());
                    notifyObservers(new WidgetSignal<>(SignalType.CREATE, DataType.MEMBER));
                    break;
                case UPDATE:
                    member.setFirstname(firstname);
                    member.setLastname(lastname);
                    notifyObservers(new WidgetSignal<>(SignalType.UPDATE, DataType.MEMBER));
                    setCreateMode();
                    break;
            }

            resetFields();
        }
    }

    public model.member.Member.Builder getPayload() { return payload; }

    public void setMember(model.member.Member member)
    {
        this.member = member;
        setUpdateMode();
    }

    private void setCreateMode() { mode = Mode.CREATE; }

    private void setUpdateMode() { mode = Mode.UPDATE; }

    private void resetFields()
    {
        firstNameField.setText("");
        lastNameField.setText("");
    }
}

