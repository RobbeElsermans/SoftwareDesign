package GUI.guiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalculate implements Initializable {

    @FXML
    private Button buttonCalculate;

    @FXML
    private ComboBox<?> dropDownFromPerson;

    @FXML
    private Label labelPriceType;

    @FXML
    private ListView<?> listViewForPersons;

    @FXML
    void calculateTicket(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Get the users wo have a ticket stored

    }
}