package GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class ControllerUniform {

    @FXML
    ListView listViewTicket;
    @FXML
    ListView listViewFromPerson;
    @FXML
    ListView listViewForPerson;
    @FXML
    TextArea textAreaTotAmount;

    public void saveTicket(ActionEvent ev){
        System.out.println("Ticket Saved");
    }

}
