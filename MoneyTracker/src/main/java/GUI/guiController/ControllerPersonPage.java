package GUI.guiController;

import GUI.helperClass.dropdownControl;
import GUI.helperClass.errorControl;
import database.PersonDB;
import database.TicketDB;
import database.dbController.AController;
import database.dbController.PersonController;
import database.dbController.TicketController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import person.Person;
import ticket.ITicket;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerPersonPage implements Initializable {
    @FXML
    private ListView listViewPersons;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private Label labelError;

    @FXML
    private Label labelError2;

    @FXML
    private ComboBox<String> comboBoxDeletePerson;

    @FXML
    private ComboBox<String> comboBoxDepthPerson;

    private PersonController personController;

    private TicketController ticketController;

    /**
     * Add a person
     */
    @FXML
    private void addPerson() {
        //Check if the field are filled in.

        if (!this.textFieldName.getText().isEmpty() && !this.textFieldLastName.getText().isEmpty()) {
            errorControl.clearError(this.labelError);

            String temp_name = this.textFieldName.getText();
            String temp_lastName = this.textFieldLastName.getText();
            //System.out.println("Add: " + temp_name + " " + temp_lastName);

            //clear the TextFields
            this.textFieldName.clear();
            this.textFieldLastName.clear();

            //Add to database
            this.personController.addValue(new Person(temp_name, temp_lastName));

            refreshPage();
        } else {
            errorControl.setError(this.labelError, "Fill everything in!");
        }
    }


    @FXML
    private void deletePerson(ActionEvent event) {
        //Check inputs

        //local used variables
        int deleteID = -1;
        int depthID = -1;

        if (dropdownControl.getDropdownListValue(this.comboBoxDeletePerson) != null && dropdownControl.getDropdownListValue(this.comboBoxDepthPerson) != null) {

            errorControl.clearError(labelError2);

            //get the id's from database
            deleteID = personController.getIdByName(dropdownControl.getDropdownListValue(this.comboBoxDeletePerson));
            depthID = personController.getIdByName(dropdownControl.getDropdownListValue(this.comboBoxDepthPerson));

            //verwijder persoon in personDB
            this.personController.delValue(deleteID);

            //de kosten verdelen in Ticket Controller
            this.ticketController.distributeDebts(deleteID, depthID);

            //refresh page
            refreshPage();
        } else if (dropdownControl.getDropdownListValue(this.comboBoxDeletePerson) == null) {
            //warn the user
            errorControl.setError(labelError2, "Fill delete person in!");
        } else if (dropdownControl.getDropdownListValue(this.comboBoxDepthPerson) == null) {
            errorControl.setError(labelError2, "Fill depth person in!");
        }
    }

    private void setListViewPersons(List<String> temp_personNames) {

        if (!this.listViewPersons.getItems().isEmpty())
            this.listViewPersons.getItems().clear();

        this.listViewPersons.getItems().addAll(temp_personNames);
    }

    private void refreshDepthsPersonList(ActionEvent event) {
        // Reset the dropdown
        dropdownControl.resetDropdownList(this.comboBoxDepthPerson, getPersonData());

        // Delete the selected person from "fall guy" dropdown
        dropdownControl.deleteAnElementFromDropdownList(this.comboBoxDepthPerson, dropdownControl.getDropdownListValue(this.comboBoxDeletePerson));
    }

    private List<String> getPersonData() {
        return personController.getAllFullNames();
    }

    private void refreshPage() {
        //Refresh ListView
        setListViewPersons(getPersonData());

        //refresh delete Dropdowns
        dropdownControl.setDropdownList(this.comboBoxDeletePerson, getPersonData());
        dropdownControl.setDropdownList(this.comboBoxDepthPerson, getPersonData());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Init and get the person data
        personController = new PersonController(PersonDB.getInstance());
        ticketController = new TicketController(TicketDB.getInstance());

        //Set the ListViews
        refreshPage();

        //Set onAction for deletePerson dropdown
        dropdownControl.setDropdownListAction(this.comboBoxDeletePerson, this::refreshDepthsPersonList);
    }
}