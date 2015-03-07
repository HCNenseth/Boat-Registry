/**
 *
 * @filename Boat.java
 *
 * @date 2015-02-17
 *
 */

package controller.widget.boat;

import model.boat.BoatSkeleton;
import model.boat.BoatType;
import share.DataType;
import share.WidgetSignal;
import share.SignalType;
import controller.Mediator;
import storage.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.member.Member;
import share.validator.StringMatcher;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public final class Boat extends Observable implements Initializable
{
    @FXML private Button closeButton, saveButton;
    @FXML private Label typeError, regNrError, yearError,
            lengthError, powerError, colorError;
    @FXML private TextField regnrField, yearField,
                      lengthField, powerField, colorField;

    @FXML private ChoiceBox<BoatType> boatTypeChoiceBox;
    @FXML private ChoiceBox<Member> ownerChoiceBox = new ChoiceBox<>();

    private ObservableList<Member> choiceBoxMembers;
    private BoatSkeleton boat;
    private Mode mode = Mode.CREATE;

    private enum Mode {CREATE, UPDATE};

    private Boat(Builder b)
    {
        addObserver(b.mediator);
        choiceBoxMembers = FXCollections.observableArrayList();
    }

    public static class Builder implements share.Builder
    {
        private Mediator mediator;

        public Builder() {}

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
        insertMembers(); insertBoatTypes();
        if (mode == Mode.UPDATE) { populateFields(); }
    }

    private void insertMembers()
    {
        choiceBoxMembers.removeAll(choiceBoxMembers);

        for (Member m : Data.getInstance().getMembers())
            choiceBoxMembers.add(m);

        ownerChoiceBox.setItems(choiceBoxMembers);
    }

    private void insertBoatTypes()
    {
        boatTypeChoiceBox.getItems().setAll(BoatType.values());
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
     * This method takes care of gathering input values
     * and validating them against the shared validator.
     * This process is all to manual and should be made
     * slimmer and more effective. After all that it
     * creates (or not) a Boat object and passes signals
     * to the Mediator.
     *
     * This horrible abomination needs refactoring.
     */
    @FXML
    private void saveButtonAction()
    {
        boolean valid = true;

        BoatType type = boatTypeChoiceBox.getValue();

        // fetch all the values.
        String regNr = regnrField.getText();
        String year = yearField.getText();
        String length = lengthField.getText();
        String power = powerField.getText();
        String color = colorField.getText();

        Member member = ownerChoiceBox.getValue();

        // reset all the error messages.
        typeError.setText("");
        regNrError.setText("");
        yearError.setText("");
        lengthError.setText("");
        powerError.setText("");
        colorError.setText("");

        if (type == null) {
            typeError.setText("Type must be set");
            typeError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.regnr(regNr)) {
            regNrError.setText("Error in regnr value");
            regNrError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.year(year)) {
            yearError.setText("Year must be four digits");
            yearError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.length(length)) {
            lengthError.setText("Can only contain digits");
            lengthError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.power(power)) {
            powerError.setText("Can only contain digits");
            powerError.setVisible(true);
            valid = false;
        }

        if (!StringMatcher.color(color)) {
            colorError.setText("Color must be set");
            colorError.setVisible(true);
            valid = false;
        }

        if (valid) {
            setChanged();

            switch (mode) {
                case CREATE:
                    BoatSkeleton.Builder payload = new
                            BoatSkeleton.Builder(type, regNr)
                            .year(Integer.parseInt(year))
                            .length(Double.parseDouble(length))
                            .power(Double.parseDouble(power))
                            .color(color);
                    BoatSkeleton b = payload.build();

                    if (member != null)
                        Data.getInstance().connectBoatAndMember(b, member);

                    Data.getInstance().addBoat(b);

                    notifyObservers(new WidgetSignal<>(SignalType.CREATE, DataType.BOAT));
                    break;
                case UPDATE:
                    updateData();
                    notifyObservers(new WidgetSignal<>(SignalType.UPDATE, DataType.BOAT));
                    break;
            }
            resetFields();
        }
    }

    public void setBoat(BoatSkeleton boat) {
        this.boat = boat;
        setUpdateMode();
    }

    private void setCreateMode() { mode = Mode.CREATE; }

    private void setUpdateMode() { mode = Mode.UPDATE; }

    private void populateFields()
    {
        if (boat != null) {
            boatTypeChoiceBox.setValue(boat.getType());
            regnrField.setText(boat.getRegnr());
            yearField.setText(Integer.toString(boat.getYear()));
            lengthField.setText(Double.toString(boat.getLength()));
            powerField.setText(Double.toString(boat.getPower()));
            colorField.setText(boat.getColor());

            if (boat.getOwner() != null) {
                ownerChoiceBox.setValue(boat.getOwner());
            }

        }
    }

    private void updateData()
    {
        if (boat != null) {
            boat.setRegnr(regnrField.getText());
            boat.setType(boatTypeChoiceBox.getValue());
            boat.setYear(Integer.parseInt(yearField.getText()));
            boat.setLength(Double.parseDouble(lengthField.getText()));
            boat.setPower(Double.parseDouble(powerField.getText()));
            boat.setColor(colorField.getText());

            if (! ownerChoiceBox.getValue().equals(boat.getOwner())) {
                if (boat.getOwner() != null)
                    Data.getInstance().disconnectBoatAndMember(boat, boat.getOwner());
                Data.getInstance().connectBoatAndMember(boat, ownerChoiceBox.getValue());
            }
        }
    }

    private void resetFields()
    {
        regnrField.setText("");
        yearField.setText("");
        lengthField.setText("");
        powerField.setText("");
        colorField.setText("");
    }
}
