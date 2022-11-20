package GUI.controller;

import factory.FactoryType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;

import person.IPerson;
import person.Person;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerUniform implements Initializable {

    @FXML
    ComboBox<String> dropDownTicketType;
    //Ticket Types
    String[] ticketTypes = {"PLANE", "DINNER"};

    //Opslag van id van person met IPerson
    private HashMap<Integer, IPerson> personNames = new HashMap<>();

    @FXML
    ComboBox<IPerson> dropDownFromPerson;
    //Opslag van id van de from person
    private int idFromPerson;

    @FXML
    ComboBox<IPerson> dropDownForPerson;
    //Opslag van id van de for person
    private int[] idForPerson;

    @FXML
    Button buttonAddForPerson;
    //Opslag van id's van de geselecteerde personen
    List<Integer> forPersonList = new ArrayList<>();

    @FXML
    Spinner<Double> spinnerTotAmount;
    private double totAmount;

    @FXML
    ListView<String> listViewForPersons;
    List<String> forPersonListView = new ArrayList<>();

    public void saveTicket(ActionEvent ev){
        System.out.println("Ticket Saved");
    }

    /**
     * We voegen een persoon toe met de drukknop "ADD PERSON"
     */
    public void addForPerson(){
        //get the selected person's id.
        System.out.println("Add Person");
    }

    //De data in de dropdowns toevoegen
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //De database persons items opvragen


    }
}
