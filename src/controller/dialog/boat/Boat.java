package controller.dialog.boat;

import common.DataType;
import common.DialogSignal;
import common.SignalType;
import controller.Mediator;
import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Member;
import storage.Deque;
import validator.StringMatcher;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/17/15.
 */
public final class Boat extends Observable implements Initializable
{
    private Deque<model.Member> members;

    @FXML
    private Button closeButton, saveButton;
    @FXML
    private Label regNrError, typeError, yearError,
                  lengthError, powerError, colorError;
    @FXML
    private TextField regnrField, typeField, yearField,
                      lengthField, powerField, colorField;
    @FXML
    private ChoiceBox<model.Member> ownerSelector;

    private ObservableList<model.Member> choiceBoxMembers;

    private Boat(Builder b)
    {
        this.members = b.members;
        addObserver(b.mediator);
    }

    public static class Builder implements common.Builder
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
        insertMembers();
    }

    public void insertMembers()
    {
        choiceBoxMembers = FXCollections.observableArrayList();
        for (model.Member m : members)
            choiceBoxMembers.add(m);
        ownerSelector.setItems(choiceBoxMembers);
    }

    @FXML
    private void closeButtonAction()
    {
        setChanged();
        notifyObservers(new DialogSignal<>(SignalType.CLOSE));
    }

    /**
     * THIS HORRIBLE ABOMINATION NEEDS REFACTORING!
     */
    @FXML
    private void saveButtonAction()
    {
        boolean valid = true;

        // fetch all the values.
        String regNr = regnrField.getText();
        String type = typeField.getText();
        String year = yearField.getText();
        String length = lengthField.getText();
        String power = powerField.getText();
        String color = colorField.getText();

        Member member = ownerSelector.getValue();

        // reset all the error messages.
        regNrError.setText("");
        typeError.setText("");
        yearError.setText("");
        lengthError.setText("");
        powerError.setText("");
        colorError.setText("");

        if (!StringMatcher.regnr(regNr)) {
            regNrError.setText("Error value");
            regNrError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.type(type)) {
            typeError.setText("Error value");
            typeError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.year(year)) {
            yearError.setText("Error value");
            yearError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.length(length)) {
            lengthError.setText("Error value");
            lengthError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.power(power)) {
            powerError.setText("Error value");
            powerError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.color(color)) {
            colorError.setText("Error value");
            colorError.setVisible(true);
            valid = false;
        }

        if (valid) {
            model.Boat.Builder payload = new model.Boat.Builder(regNr, type)
                                    .year(Integer.parseInt(year))
                                    .length(Double.parseDouble(length))
                                    .power(Double.parseDouble(power))
                                    .color(color);

            model.Boat b = payload.build();

            if (member != null) {
                b.setOwner(member);
                member.push(b);
            }

            Data.getInstance().getBoats().addLast(b);

            setChanged();
            notifyObservers(new DialogSignal<>(SignalType.CREATE, DataType.BOAT));
        }
    }
}
