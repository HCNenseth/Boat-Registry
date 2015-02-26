package controller.window;

import common.DataType;
import common.SignalType;
import common.Action;
import controller.Mediator;
import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import model.Member;
import model.Boat;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Base extends Observable implements Initializable
{
    @FXML
    private TableView<Member> tableViewMembers = new TableView<>();
    @FXML
    private TableView<Boat> tableViewBoats = new TableView<>();
    @FXML
    private TabPane tabs;
    @FXML
    private Tab boatsTab, membersTab;
    @FXML
    private ContextMenu membersContextMenu, boatsContextMenu;
    @FXML
    private MenuItem rightBoatEdit, rightBoatDelete,
                     rightMemberEdit, rightMemberDelete;

    private int activeMemberRow, activeBoatRow;

    private ObservableList<Boat> tableBoatsList;
    private ObservableList<Member> tableMemberList;

    private Base(Builder b)
    {
        addObserver(b.mediator);
    }

    public static class Builder implements common.Builder
    {
        private Mediator mediator;

        public Builder()
        {
        }

        public Builder observer(Mediator mediator)
        {
            this.mediator = mediator;
            return this;
        }

        public Base build()
        {
            return new Base(this);
        }
    }


    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle res)
    {
        updateMembers();
        updateBoats();

        rightBoatDelete.setOnAction(e -> boatDelete());
        rightBoatEdit.setOnAction(e -> boatEdit());
        rightMemberDelete.setOnAction(e -> memberDelete());
        rightMemberEdit.setOnAction(e -> memberEdit());
    }

    /**
     *
     * This is not this methods final form.
     * Proof of concept ONLY!
     *
     * @param e
     */
    @FXML
    private void fileChooser(ActionEvent e)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose data file");
        fc.showOpenDialog(new Window()
        {
        });
    }

    @FXML
    private void close(ActionEvent e)
    {
        setChanged();
        notifyObservers(new Action<>(SignalType.QUIT));
    }

    /************************
     * MEMBER LOGIC BE HERE *
     ************************/

    @FXML
    private void newMember(ActionEvent e)
    {
        setChanged();
        //notifyObservers(Mediator.TransmissionSignals.CREATE_MEMBER);
        notifyObservers(new Action<>(SignalType.NEW, DataType.MEMBER));
    }

    @FXML
    private void memberTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            setChanged();
            notifyObservers(new Action<>(getSelectedMember(),
                    SignalType.EDIT, DataType.MEMBER));
            //notifyObservers(Mediator.TransmissionSignals.EDIT_MEMBER);
        }
    }

    @FXML
    private void memberTableKey(KeyEvent k)
    {
    }

    private void memberEdit()
    {
        setChanged();
        notifyObservers(new Action<>(getSelectedMember(),
                SignalType.EDIT, DataType.MEMBER));
    }

    private void memberDelete()
    {
        Member selectedMember = getSelectedMember();

        if (selectedMember.hasBoats()) {
            // nope ...
            return;
        }

        setChanged();
        notifyObservers(new Action<>(selectedMember,
                SignalType.DELETE, DataType.MEMBER));

    }

    private Member getSelectedMember()
    {
        return tableViewMembers.getSelectionModel().getSelectedItem();
    }

    public void updateMembers()
    {
        tableMemberList = FXCollections.observableArrayList();
        for (Member m : Data.getInstance().getMembers()) {
            tableMemberList.add(m);
        }
        tableViewMembers.setItems(tableMemberList);
    }

    public int getActiveMemberRow() { return activeMemberRow; }

    public void focusOnMembers() { tabs.getSelectionModel().select(membersTab); }

    /**********************
     * BOAT LOGIC BE HERE *
     **********************/

    @FXML
    private void newBoat(ActionEvent e)
    {
        setChanged();
        notifyObservers(new Action<>(SignalType.NEW, DataType.BOAT));
    }

    @FXML
    private void boatTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            setChanged();
            notifyObservers(new Action<>(getSelectedBoat(),
                    SignalType.EDIT, DataType.BOAT));
        }
    }

    @FXML
    private void boatTableKey(KeyEvent k)
    {
    }

    private void boatDelete()
    {
        setChanged();
        notifyObservers(new Action<>(getSelectedBoat(),
                SignalType.DELETE, DataType.BOAT));
    }

    private void boatEdit()
    {
        setChanged();
        notifyObservers(new Action<>(getSelectedBoat(),
                SignalType.EDIT, DataType.BOAT));
    }

    private Boat getSelectedBoat()
    {
        return tableViewBoats.getSelectionModel().getSelectedItem();
    }

    public void updateBoats()
    {
        tableBoatsList = FXCollections.observableArrayList();
        for (Boat b : Data.getInstance().getBoats())
            tableBoatsList.add(b);
        tableViewBoats.setItems(tableBoatsList);
    }

    public int getActiveBoatRow() { return activeBoatRow; }

    public void focusOnBoats() { tabs.getSelectionModel().select(boatsTab); }

    /*******************
     * HERE BE DRAGONS *
     *******************/
}
