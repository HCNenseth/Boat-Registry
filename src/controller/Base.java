package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import model.Member;
import model.Boat;
import storage.Deque;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Base extends Observable implements Initializable
{
    private int activeMemberRow, activeBoatRow;

    @FXML
    private TableView<Member> tableViewMembers = new TableView<>();
    @FXML
    private TableView<Boat> tableViewBoats = new TableView<>();

    Deque<model.Member> members;
    Deque<model.Boat> boats;
    private ObservableList<Boat> tableBoatsList;
    private ObservableList<Member> tableMemberList;

    private Base(Builder b)
    {
        this.members = b.members;
        this.boats = b.boats;
        addObserver(b.mediator);
    }

    public static class Builder
    {
        private Mediator mediator;
        private Deque<model.Member> members;
        private Deque<model.Boat> boats;

        public Builder(Deque<model.Member> members, Deque<model.Boat> boats)
        {
            this.members = members;
            this.boats = boats;
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
        populateData();
    }

    @FXML
    private void fileChooser(ActionEvent e)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose data file");
        fc.showOpenDialog(new Window(){});
    }

    @FXML
    private void close(ActionEvent e)
    {
        setChanged();
        notifyObservers(Mediator.TransmissionSignals.EXIT);
    }

    @FXML
    private void newMember(ActionEvent e)
    {
        setChanged();
        notifyObservers(Mediator.TransmissionSignals.CREATE_MEMBER);
    }

    @FXML
    private void newBoat(ActionEvent e)
    {
        setChanged();
        notifyObservers(Mediator.TransmissionSignals.CREATE_BOAT);
    }

    @FXML
    private void memberTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            TableView tv = (TableView) e.getSource();
            activeMemberRow = tv.getSelectionModel().getSelectedIndex();
            setChanged();
            notifyObservers(Mediator.TransmissionSignals.EDIT_MEMBER);
        }
    }

    @FXML
    private void boatTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            TableView tv = (TableView) e.getSource();
            activeBoatRow = tv.getSelectionModel().getSelectedIndex();
            setChanged();
            notifyObservers(Mediator.TransmissionSignals.EDIT_BOAT);
        }
    }

    private void populateData()
    {
        tableBoatsList = tableViewBoats.getItems();
        tableMemberList = tableViewMembers.getItems();

        for (Member m : members)
            tableMemberList.add(m);
        for (Boat b : boats)
            tableBoatsList.add(b);

        tableViewBoats.setItems(tableBoatsList);
        tableViewMembers.setItems(tableMemberList);
    }

    public int getActiveMemberRow() { return activeMemberRow; }
    public int getActiveBoatRow() { return activeBoatRow; }
}
