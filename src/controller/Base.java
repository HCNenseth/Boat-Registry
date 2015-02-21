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

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Base extends Observable implements Initializable
{
    @FXML
    private TableView<Member> tableViewMembers = new TableView<>();
    @FXML
    private TableView<Boat> tableViewBoats = new TableView<>();

    Deque<model.Member> members;
    Deque<model.Boat> boats;
    private ObservableList<Boat> tableBoatsList;
    private ObservableList<Member> tableMemberList;

    public Base(Deque<model.Member> members, Deque<model.Boat> boats)
    {
        this.members = members;
        this.boats = boats;
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
        Platform.exit();
    }

    @FXML
    private void newMember(ActionEvent e)
    {
        setChanged();
        notifyObservers("newMember");
        /*
        try {
            new view.Member();
        } catch (IOException ioe) {
            System.out.printf(ioe.getMessage());
        }
        */
    }

    /**
     * Edit member
     * Should take Member as argument.
     */
    private void editMember()
    {
        try {
            new view.Member(); // <- Insert member
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @FXML
    private void newBoat(ActionEvent e)
    {
        try {
            new view.Boat();
        } catch (IOException ioe) {
            System.out.printf(ioe.getMessage());
        }
    }

    /**
     * Edit boat
     * Should take Boat as argument.
     */
    private void editBoat()
    {
        try {
            new view.Boat(); // <- Insert boat
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @FXML
    private void memberTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            TableView tv = (TableView) e.getSource();
            int row = tv.getSelectionModel().getSelectedIndex();
            editMember();
        }
    }

    @FXML
    private void boatTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            TableView tv = (TableView) e.getSource();
            int row = tv.getSelectionModel().getSelectedIndex();
            editBoat();
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

}
