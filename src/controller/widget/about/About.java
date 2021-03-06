/**
 *
 * @filename About.java
 *
 * @date 2015-03-01
 *
 */

package controller.widget.about;

import controller.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import share.SignalType;
import share.WidgetSignal;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public final class About extends Observable implements Initializable
{
    @FXML private Button confirmButton;

    public About(Mediator mediator)
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
}
