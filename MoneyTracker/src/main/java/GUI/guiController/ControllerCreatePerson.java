package GUI.guiController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class ControllerCreatePerson {
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


    /**
     * Add a person
     * @param event
     */
    public void addPerson(ActionEvent event){
        //Check if the field are filled in.

        
    }


}
