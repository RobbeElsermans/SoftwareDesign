package GUI.guiController;

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
    private ComboBox<String> checkBoxDeletePerson;

    @FXML
    private ComboBox<String> checkBoxDepthPerson;

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

            //Refresh ListView
            setListViewPersons(getPersonData());

            //refresh delete Dropdowns
            setDropdownLists(getPersonData());
        }
        else{
            this.labelError.setText("Fill everything in!");
        }
    }

    @FXML
    void deletePerson(ActionEvent event) {
        //Check inputs


        //verwijder persoon in personDB
        //de kosten verdelen in Ticket Controller

    }

    private void setListViewPersons(List<String> temp_personNames) {

        if(!this.listViewPersons.getItems().isEmpty())
            this.listViewPersons.getItems().clear();

        this.listViewPersons.getItems().addAll(temp_personNames);
    }

    private void setDropdownLists(List<String> temp_personNames) {

        if(!this.checkBoxDeletePerson.getItems().isEmpty())
            this.checkBoxDeletePerson.getItems().clear(); //zal al leeg zijn bij init
        this.checkBoxDeletePerson.getItems().addAll(temp_personNames);

        if(!this.checkBoxDepthPerson.getItems().isEmpty())
            this.checkBoxDepthPerson.getItems().clear(); //zal al leeg zijn bij init
        this.checkBoxDepthPerson.getItems().addAll(temp_personNames);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Init and get the person data
        personController = new PersonController(PersonDB.getInstance());

        //Set the ListViews
        setListViewPersons(getPersonData());
        setDropdownLists(getPersonData());

        //Set onAction for deletePerson dropdown
        this.checkBoxDepthPerson.setOnAction(this::setDeletePerson);
    }

    private void setDeletePerson(ActionEvent event) {
        //reset the
        setDropdownLists(getPersonData());
        //Delete the selected person from "fall guy" dropdown
        this.checkBoxDepthPerson.getItems().remove(this.checkBoxDeletePerson.getValue());

    }

    private List<String> getPersonData() {
        return this.personController.getAllFullNames();
    }
}
