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
            this.labelError.setText("Fill everything in!");
        }
    }

    private void refreshPage() {
        //Refresh ListView
        setListViewPersons(getPersonData());

        //refresh delete Dropdowns
        setDropdownListsDeletePerson(getPersonData());
        setDropdownListsDepthPerson(getPersonData());
    }

    @FXML
    void deletePerson(ActionEvent event) {
        //Check inputs

        //local used variables
        int deleteID = -1;
        int depthID = -1;


        if(this.comboBoxDeletePerson.getValue() != null && this.comboBoxDepthPerson.getValue() != null){
            if(errorPresent(labelError2)){
                resetError(labelError2);
            }

            //get the id's from database
            deleteID = personController.getIdByName(this.comboBoxDeletePerson.getValue());
            depthID = personController.getIdByName(this.comboBoxDepthPerson.getValue());

            //verwijder persoon in personDB

            //de kosten verdelen in Ticket Controller


            //System.out.println("Verwijderen persoon");

            //refresh page
            refreshPage();
        }
        else if(this.comboBoxDeletePerson.getValue() == null)
        {
            //warn the user
            setError(labelError2,"Fill delete person in!");
        }

        else if(this.comboBoxDepthPerson.getValue() == null)
        {
            //alert the user
            /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete user without fall guy");
            alert.setHeaderText("No fall guy selected!");
            alert.setContentText("Are you sure to discard your depths?");*/
            setError(labelError2,"Fill depth person in!");
        }

    }

    private void setListViewPersons(List<String> temp_personNames) {

        if(!this.listViewPersons.getItems().isEmpty())
            this.listViewPersons.getItems().clear();

        this.listViewPersons.getItems().addAll(temp_personNames);
    }

    private void setDropdownListsDeletePerson(List<String> temp_personNames) {

        if(!this.comboBoxDeletePerson.getItems().isEmpty())
            this.comboBoxDeletePerson.getItems().clear(); //zal al leeg zijn bij init
        this.comboBoxDeletePerson.getItems().addAll(temp_personNames);
    }

    private void setDropdownListsDepthPerson(List<String> temp_personNames) {
        if(!this.comboBoxDepthPerson.getItems().isEmpty())
            this.comboBoxDepthPerson.getItems().clear(); //zal al leeg zijn bij init
        this.comboBoxDepthPerson.getItems().addAll(temp_personNames);
    }

    private void refreshDepthsPersonList(ActionEvent event) {
        //reset the dropdown
        setDropdownListsDepthPerson(getPersonData());

        //Delete the selected person from "fall guy" dropdown
        this.comboBoxDepthPerson.getItems().remove(this.comboBoxDeletePerson.getValue());
    }

    private List<String> getPersonData() {
        return this.personController.getAllFullNames();
    }

    private void setError(Label label ,String value) {
        label.setText(value);
    }

    private void resetError(Label label) {
        if(errorPresent(label))
            label.setText("");
    }

    private boolean errorPresent(Label label) {
        return this.labelError.getText().isEmpty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Init and get the person data
        personController = new PersonController(PersonDB.getInstance());

        //Set the ListViews
        refreshPage();

        //Set onAction for deletePerson dropdown
        this.comboBoxDeletePerson.setOnAction(this::refreshDepthsPersonList);
    }
}
