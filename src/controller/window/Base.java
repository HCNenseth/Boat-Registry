package controller.window;

import share.DataType;
import share.SignalType;
import share.WindowSignal;
import controller.Mediator;
import storage.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML private TableView<Member> tableViewMembers = new TableView<>();
    @FXML private TableView<Boat> tableViewBoats = new TableView<>();
    @FXML private TabPane tabs;
    @FXML private Tab boatsTab, membersTab;
    @FXML private ContextMenu membersContextMenu, boatsContextMenu;
    @FXML private MenuItem rightBoatEdit, rightBoatDelete,
                     rightMemberEdit, rightMemberDelete;

    private ObservableList<Boat> tableBoatsList;
    private ObservableList<Member> tableMemberList;

    private Base(Builder b)
    {
        addObserver(b.mediator);
        tableBoatsList = FXCollections.observableArrayList();
        tableMemberList = FXCollections.observableArrayList();
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
        notifyObservers(new WindowSignal<>(SignalType.QUIT));
    }

    /************************
     * MEMBER LOGIC BE HERE *
     ************************/

    @FXML
    private void newMember(ActionEvent e)
    {
        setChanged();
        notifyObservers(new WindowSignal<>(SignalType.NEW, DataType.MEMBER));
    }

    @FXML
    private void memberTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            setChanged();
            notifyObservers(new WindowSignal<>(getSelectedMember(),
                    SignalType.EDIT, DataType.MEMBER));
        }
    }

    private void memberEdit()
    {
        setChanged();
        notifyObservers(new WindowSignal<>(getSelectedMember(),
                SignalType.EDIT, DataType.MEMBER));
    }

    private void memberDelete()
    {
        Member selectedMember = getSelectedMember();

        setChanged();
        notifyObservers(new WindowSignal<>(selectedMember,
                SignalType.DELETE, DataType.MEMBER));

    }

    private Member getSelectedMember()
    {
        return tableViewMembers.getSelectionModel().getSelectedItem();
    }

    public void updateMembers()
    {
        tableMemberList.removeAll(tableMemberList);

        for (Member m : Data.getInstance().getMembers())
            tableMemberList.add(m);

        tableViewMembers.setItems(tableMemberList);
    }

    public void focusOnMembers() { tabs.getSelectionModel().select(membersTab); }

    /**********************
     * BOAT LOGIC BE HERE *
     **********************/

    @FXML
    private void newBoat(ActionEvent e)
    {
        setChanged();
        notifyObservers(new WindowSignal<>(SignalType.NEW, DataType.BOAT));
    }

    @FXML
    private void boatTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            setChanged();
            notifyObservers(new WindowSignal<>(getSelectedBoat(),
                    SignalType.EDIT, DataType.BOAT));
        }
    }

    private void boatDelete()
    {
        setChanged();
        notifyObservers(new WindowSignal<>(getSelectedBoat(),
                SignalType.DELETE, DataType.BOAT));
    }

    private void boatEdit()
    {
        setChanged();
        notifyObservers(new WindowSignal<>(getSelectedBoat(),
                SignalType.EDIT, DataType.BOAT));
    }

    private Boat getSelectedBoat()
    {
        return tableViewBoats.getSelectionModel().getSelectedItem();
    }

    public void updateBoats()
    {
        tableBoatsList.removeAll(tableBoatsList);

        for (Boat b : Data.getInstance().getBoats())
            tableBoatsList.add(b);

        tableViewBoats.setItems(tableBoatsList);
    }

    public void focusOnBoats() { tabs.getSelectionModel().select(boatsTab); }

    /*******************
     * HERE BE DRAGONS *
     *******************/
}
