package GUI.guiController;

import GUI.helperClass.dropdownControl;
import GUI.helperClass.errorControl;
import database.PersonDB;
import database.dbController.PersonController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import person.Person;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerPersonPage implements Initializable {
    @FXML
    ListView listViewPersons;
    @FXML
    Button buttonAddPerson;
    @FXML
    TextField textFieldName;
    @FXML
    TextField textFieldLastName;
    @FXML
    Label labelError;

    @FXML
    private Label labelError2;

    @FXML
    private ComboBox<String> comboBoxDeletePerson;

    @FXML
    private ComboBox<String> comboBoxDepthPerson;

    private PersonController personController;

    /**
     * Add a person
     */
    public void addPerson(){
        //Check if the field are filled in.

        if(!this.textFieldName.getText().isEmpty() && !this.textFieldLastName.getText().isEmpty()){
            if(!this.labelError.getText().isEmpty())
                this.labelError.setText("");

            String temp_name = this.textFieldName.getText();
            String temp_lastName = this.textFieldLastName.getText();
            //System.out.println("Add: " + temp_name + " " + temp_lastName);

            //clear the TextFields
            this.textFieldName.clear();
            this.textFieldLastName.clear();

            //Add to database
            this.personController.addValue(new Person(temp_name, temp_lastName));

            refreshPage();
        }
        else{
            errorControl.setError(this.labelError,"Fill everything in!");
        }
    }


    @FXML
    void deletePerson(ActionEvent event) {
        //Check inputs

        //local used variables
        int deleteID = -1;
        int depthID = -1;


        if(dropdownControl.getDropdownListValue(this.comboBoxDeletePerson) != null && dropdownControl.getDropdownListValue(this.comboBoxDepthPerson) != null){

            errorControl.clearError(labelError2);

            //get the id's from database
            deleteID = personController.getIdByName(dropdownControl.getDropdownListValue(this.comboBoxDeletePerson));
            depthID = personController.getIdByName(dropdownControl.getDropdownListValue(this.comboBoxDepthPerson));

            //verwijder persoon in personDB
            //TODO verwijder persoon
            //de kosten verdelen in Ticket Controller
            //TODO verdeel de kosten van het ticket

            //System.out.println("Verwijderen persoon");

            //refresh page
            refreshPage();
        }
        else if(dropdownControl.getDropdownListValue(this.comboBoxDeletePerson) == null)
        {
            //warn the user
            errorControl.setError(labelError2,"Fill delete person in!");
        }

        else if(dropdownControl.getDropdownListValue(this.comboBoxDepthPerson) == null)
        {
            errorControl.setError(labelError2,"Fill depth person in!");
        }
    }

    private void setListViewPersons(List<String> temp_personNames) {

        if(!this.listViewPersons.getItems().isEmpty())
            this.listViewPersons.getItems().clear();

        this.listViewPersons.getItems().addAll(temp_personNames);
    }

    private void refreshDepthsPersonList(ActionEvent event) {
        // Reset the dropdown
        dropdownControl.setDropdownList(this.comboBoxDepthPerson, getPersonData());

        // Delete the selected person from "fall guy" dropdown
        this.comboBoxDepthPerson.getItems().remove(this.comboBoxDeletePerson.getValue());
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

        //Set the ListViews
        refreshPage();

        //Set onAction for deletePerson dropdown
        dropdownControl.setDropdownListAction(this.comboBoxDeletePerson,this::refreshDepthsPersonList);
    }
}