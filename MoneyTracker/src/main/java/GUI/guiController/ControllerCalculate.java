package GUI.guiController;

import GUI.helperClass.informUser;
import calculator.Calculator;
import database.TicketDB;
import database.dbController.TicketController;
import database.observer.IObserver;
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

public class ControllerCalculate implements Initializable, IObserver {

    @FXML
    private Button buttonCalculate;

    @FXML
    private ListView<String> listViewForPersons;

    private TicketController ticketController;

    @FXML
    private void calculateTicket(ActionEvent event) {
        //Calculate everything
        if (this.listViewForPersons.getItems().isEmpty()) {
            List<Triplet<Integer, Integer, Double>> tallies = Calculator.CalculateFinalTallies(Calculator.CalculateTallyPairs()); //Calculate the depths

            //get a list of the tallies
            List<String> output = Calculator.TalliesToString(tallies);

            //set the list
            output.forEach(value -> {
                this.listViewForPersons.getItems().add(value);
            });
        }

    }

    @FXML
    private void clearAllTickets(ActionEvent event) {
        this.listViewForPersons.getItems().clear();
        this.ticketController.delAllValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Get the ticketController initialized
        this.ticketController = new TicketController(TicketDB.getInstance());
        this.ticketController.addObserver(this);
    }

    @Override
    public void update(String text) {
        informUser.inform("Database changed", text);
    }
}