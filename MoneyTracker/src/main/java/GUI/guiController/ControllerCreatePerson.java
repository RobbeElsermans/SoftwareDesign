package GUI.guiController;

import database.ADatabase;
import database.PersonDB;
import database.dbController.PersonController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import person.Person;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCreatePerson implements Initializable {
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
            setListViewPersons(this.personController);
        }
        else{
            this.labelError.setText("Fill everything in!");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Init and get the person data
        personController = new PersonController(PersonDB.getInstance());

        //Set the ListView
        setListViewPersons(personController);
    }

    private void setListViewPersons(PersonController psc) {
        List<String> temp_personNames = psc.getAllFullNames();
        this.listViewPersons.getItems().clear();
        this.listViewPersons.getItems().addAll(temp_personNames);
    }
}
