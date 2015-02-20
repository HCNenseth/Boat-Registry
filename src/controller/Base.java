package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import model.Member;
import model.Boat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Base implements Initializable
{
    @FXML
    private TableView<Member> tableViewMembers = new TableView<>();
    @FXML
    private TableView<Boat> tableViewBoats = new TableView<>();

    private ObservableList<Boat> tableBoatsList;
    private ObservableList<Member> tableMemberList;

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
        try {
            new view.Member();
        } catch (IOException ioe) {
            System.out.printf(ioe.getMessage());
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

    private void populateData()
    {
        tableBoatsList = tableViewBoats.getItems();
        tableMemberList = tableViewMembers.getItems();

        tableBoatsList.add(new Boat.Builder("123", "abc").build());
        tableViewBoats.setItems(tableBoatsList);

        tableMemberList.add(new Member.Builder("Tjobing").build());
        tableViewMembers.setItems(tableMemberList);
    }
}
