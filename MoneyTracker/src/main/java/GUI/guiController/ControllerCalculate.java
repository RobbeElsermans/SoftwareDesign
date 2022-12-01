package GUI.guiController;

import calculator.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.javatuples.Triplet;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalculate implements Initializable {

    @FXML
    private Button buttonCalculate;

    @FXML
    private ListView<String> listViewForPersons;

    @FXML
    void calculateTicket(ActionEvent event) {
        //Calculate everything
        if(this.listViewForPersons.getItems().isEmpty()) {
            List<Triplet<Integer, Integer, Double>> tallies = Calculator.CalculateFinalTallies(Calculator.CalculateTallyPairs()); //Calculate the depths

            //get a list of the tallies
            List<String> output = Calculator.TalliesToString(tallies);


            //set the list
            output.forEach(value -> {
                this.listViewForPersons.getItems().add(value);
            });
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Get the users wo have a ticket stored

    }
}