package controller.dialog;

import controller.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import share.SignalType;
import share.WidgetSignal;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/28/15.
 */
public final class Dialog extends Observable implements Initializable
{
    @FXML
    private Label messageLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView mainIcon;

    public enum Icons
    {
        ERROR("errorIcon.png"),
        SUCCESS("infoIcon.png");

        private String path;

        private Icons(String path)
        {
            this.path = path;
        }

        public String getPath()
        {
            return path;
        }
    }

    public Dialog(Mediator mediator)
    {
        addObserver(mediator);
    }

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle res)
    {
        confirmButton.setOnAction(e -> onConfirmButton());
    }

    private void onConfirmButton()
    {
        setChanged();
        notifyObservers(new WidgetSignal<>(SignalType.CLOSE));
    }

    public void setMessageLabel(String message)
    {
        messageLabel.setText(message);
    }

    public void setIcon(Icons i)
    {
        mainIcon.setImage(new Image(getClass().getResourceAsStream(i.getPath())));
    }
}
