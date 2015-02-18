package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import view.Member;
import view.Boat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Base implements Initializable
{
    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle res)
    {

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
        System.exit(0);
    }

    @FXML
    private void newMember(ActionEvent e)
    {
        try {
            new Member();
        } catch (IOException ioe) {

        }
    }

    @FXML
    private void newBoat(ActionEvent e)
    {
        try {
            new Boat();
        } catch (IOException ioe) {

        }
    }
}
