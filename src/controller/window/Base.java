package controller.window;

import share.DataType;
import share.SignalType;
import share.WidgetSignal;
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

import model.Member;
import model.Boat;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
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
                     rightMemberEdit, rightMemberDelete,
                     newRegistry;

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

        newRegistry.setOnAction(e -> newRegistry());

        String helper = "Empty registry!\nStart creating objects or open datafile from menu.";
        tableViewBoats.setPlaceholder(new Label(helper));
        tableViewMembers.setPlaceholder(new Label(helper));
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
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Data files (*.dat)", "*.dat")
        );
        File file = fc.showOpenDialog(null);

        setChanged();

        if (file != null) {
            try {
                Data.getInstance().setFilename(file.getPath()).loadData();
                notifyObservers(new WindowSignal.Builder<>(SignalType.UPDATE)
                        .build());
            } catch (IOException | ClassNotFoundException ioe) {
                notifyObservers(new WindowSignal.Builder<>(SignalType.ERROR)
                        // the default exception does not give much description...
                        .payload(new IOException("Unable to open file!"))
                        .build());
            }
        }
    }

    @FXML
    private void saveFile(ActionEvent e)
    {
        try {
            Data.getInstance().writeData();
        } catch (IOException ioe) {
            setChanged();
            notifyObservers(new WindowSignal.Builder<>(SignalType.ERROR)
                    .payload(ioe)
                    .build());
        }
    }

    @FXML
    private void saveAsFile(ActionEvent e)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save to data file");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Data file (*.dat)", "*.dat")
        );
        File file = fc.showSaveDialog(null);

        if (file != null) {

            // if extension is omitted from filename
            if (! file.getName().contains(".")) {
                file = new File(file.getAbsolutePath() + ".dat");
            }

            setChanged();
            // try to save to file
            try {
                Data.getInstance().setFilename(file.getPath()).writeData();
                notifyObservers(new WindowSignal.Builder<>(SignalType.UPDATE)
                        .build());
            } catch (IOException ioe) {
                notifyObservers(new WindowSignal.Builder<>(SignalType.ERROR)
                        .payload(ioe)
                        .build());
            }
        }

    }

    @FXML
    private void newRegistry()
    {
        Data.getInstance().emptyLists();
        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.UPDATE).build());
    }

    @FXML
    private void close(ActionEvent e)
    {
        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.QUIT)
                .build());
    }

    @FXML
    private void about(ActionEvent e)
    {
        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.NEW)
                .dataType(DataType.ABOUT)
                .build());
    }

    /************************
     * MEMBER LOGIC BE HERE *
     ************************/

    @FXML
    private void newMember(ActionEvent e)
    {
        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.NEW)
                .dataType(DataType.MEMBER)
                .build());
    }

    @FXML
    private void memberTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            setChanged();
            notifyObservers(new WindowSignal.Builder<>(SignalType.EDIT)
                    .payload(getSelectedMember())
                    .dataType(DataType.MEMBER)
                    .build());
        }
    }

    private void memberEdit()
    {
        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.EDIT)
                .payload(getSelectedMember())
                .dataType(DataType.MEMBER)
                .build());
    }

    private void memberDelete()
    {
        Member selectedMember = getSelectedMember();

        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.DELETE)
                .payload(selectedMember)
                .dataType(DataType.MEMBER)
                .build());
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
        notifyObservers(new WindowSignal.Builder<>(SignalType.NEW)
                .dataType(DataType.BOAT)
                .build());
    }

    @FXML
    private void boatTableClick(MouseEvent e)
    {
        // double click only
        if (e.getClickCount() == 2) {
            setChanged();
            notifyObservers(new WindowSignal.Builder<>(SignalType.EDIT)
                    .payload(getSelectedBoat())
                    .dataType(DataType.BOAT)
                    .build());
        }
    }

    private void boatDelete()
    {
        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.DELETE)
                .payload(getSelectedBoat())
                .dataType(DataType.BOAT)
                .build());
    }

    private void boatEdit()
    {
        setChanged();
        notifyObservers(new WindowSignal.Builder<>(SignalType.EDIT)
                .payload(getSelectedBoat())
                .dataType(DataType.BOAT)
                .build());
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
