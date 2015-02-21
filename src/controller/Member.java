package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/17/15.
 */
public class Member implements Initializable
{
    @FXML
    private Button orphanBoatsButton, closeButton, saveButton;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle res)
    {

    }

    @FXML
    private void closeButtonAction()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonAction()
    {
        closeButtonAction();
    }

    @FXML
    private void orphanBoatsButtonAction()
    {
        try {
            new view.OrphanBoats();
        } catch (IOException ioe) {

        }
    }
}
