package controller.widget.boat;

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
import model.Member;
import storage.Deque;
import share.validator.StringMatcher;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/17/15.
 */
public final class Boat extends Observable implements Initializable
{
    private Deque<model.Member> members;

    @FXML private Button closeButton, saveButton;
    @FXML private Label regNrError, typeError, yearError,
                  lengthError, powerError, colorError;
    @FXML private TextField regnrField, typeField, yearField,
                      lengthField, powerField, colorField;
    @FXML private ChoiceBox<model.Member> ownerSelector;

    private ObservableList<model.Member> choiceBoxMembers;
    private model.Boat boat;
    private Mode mode = Mode.CREATE;

    private enum Mode {CREATE, UPDATE};

    private Boat(Builder b)
    {
        this.members = b.members;
        addObserver(b.mediator);
    }

    public static class Builder implements share.Builder
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
        if (mode == Mode.UPDATE) { populateFields(); }
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
        setCreateMode();
        resetFields();
        setChanged();
        notifyObservers(new WidgetSignal<>(SignalType.CLOSE));
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
            setChanged();

            switch (mode) {
                case CREATE:
                    model.Boat.Builder payload = new model.Boat.Builder(regNr, type)
                            .year(Integer.parseInt(year))
                            .length(Double.parseDouble(length))
                            .power(Double.parseDouble(power))
                            .color(color);
                    model.Boat b = payload.build();

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

    public void setBoat(model.Boat boat) {
        this.boat = boat;
        setUpdateMode();
    }

    private void setCreateMode() { mode = Mode.CREATE; }

    private void setUpdateMode() { mode = Mode.UPDATE; }

    private void populateFields()
    {
        if (boat != null) {
            regnrField.setText(boat.getRegnr());
            typeField.setText(boat.getType());
            yearField.setText(Integer.toString(boat.getYear()));
            lengthField.setText(Double.toString(boat.getLength()));
            powerField.setText(Double.toString(boat.getPower()));
            colorField.setText(boat.getColor());

            if (boat.getOwner() != null) {
                ownerSelector.setValue(boat.getOwner());
            }

        }
    }

    private void updateData()
    {
        if (boat != null) {
            boat.setRegnr(regnrField.getText());
            boat.setType(typeField.getText());
            boat.setYear(Integer.parseInt(yearField.getText()));
            boat.setLength(Double.parseDouble(lengthField.getText()));
            boat.setPower(Double.parseDouble(powerField.getText()));
            boat.setColor(colorField.getText());

            if (! ownerSelector.getValue().equals(boat.getOwner())) {
                if (boat.getOwner() != null)
                    Data.getInstance().disconnectBoatAndMember(boat, boat.getOwner());
                Data.getInstance().connectBoatAndMember(boat, ownerSelector.getValue());
            }
        }
    }

    private void resetFields()
    {
        regnrField.setText("");
        typeField.setText("");
        yearField.setText("");
        lengthField.setText("");
        powerField.setText("");
        colorField.setText("");
    }
}
