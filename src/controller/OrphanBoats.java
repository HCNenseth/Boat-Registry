package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/18/15.
 */
public class OrphanBoats implements Initializable
{

    @FXML
    private Button closeButton, addButton;

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
    private void addButtonAction()
    {
        closeButtonAction();
    }

}
