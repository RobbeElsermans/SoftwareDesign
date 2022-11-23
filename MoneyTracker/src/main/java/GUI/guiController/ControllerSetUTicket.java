package GUI.guiController;

import database.PersonDB;
import factory.AbstractFactoryProvider;
import factory.FactoryType;
import factory.facts.ITicketFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import person.IPerson;
import ticket.ITicket;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSetUTicket extends ATicketController implements Initializable, ChangeListener<Double> {

//TODO nog wegwerken naar forPersonAmount

    //We had to do this
    //source: https://stackoverflow.com/questions/58552417/how-do-i-change-this-superclass-into-an-abstract-class-without-getting-an-instan
    @Override
    public void saveTicket(ActionEvent event){
        super.saveTicket(event);
    }

    @Override
    public void setForPerson(ActionEvent event) {
        super.setForPerson(event);
    }

    public void setFactoryType(ActionEvent event) {
        this.ticketType = this.dropDownFactoryType.getValue();
    }

    @Override
    public void setFromPerson(ActionEvent event){
        super.setFromPerson(event);
    }

    private void getData() {
        //De database persons items opvragen en toevoegen in locale tabel
        personsData = PersonDB.getInstance().getAll();

        //De soorten Tickets opvragen en toevoegen in locale tabel
        for (FactoryType val : FactoryType.values()) {
            factoryType.add(val.name());
        }
    }

    private void updateForPersonListView(String temp_forPerson) {
        forPersonNamesList.add(temp_forPerson);
        updateAmountPerPersonInList();
    }

    private void updateAmountPerPersonInList() {
        List<String> temp_forPersonListView = new ArrayList<>();
        temp_forPersonListView = listViewForPersons.getItems();
        listViewForPersons.getItems().removeAll(temp_forPersonListView);

        forPersonNamesList.forEach((value) -> {
            listViewForPersons.getItems().add(value + " is in depth: " + decimalFormat.format(totAmount / forPersonIdList.size()));
        });
    }

    /**
     * Een override functie die voor dat de root geladen is, afspeelt.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();          //get data from database
        initDropdowns();    //dropdown initialize
        initSpinner();      //Spinner initialize
    }

    private void initDropdowns() {
        //De lijsten toevoegen aan de respectievelijke dropdowns(combobox)
        dropDownFactoryType.getItems().addAll(factoryType);

        personsData.forEach((key, value) -> {
            personsDataNames.add(value.toString()); //Dit gebruiken we om later bij wijzigingen de names terug origineel te zetten.
            dropDownForPerson.getItems().add(value.toString());
            dropDownFromPerson.getItems().add(value.toString());
        });

        //De acties implementeren
        dropDownFromPerson.setOnAction(this::setFromPerson);
    }

    private void initSpinner() {
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 999);
        valueFactory.setValue(totAmount);
        spinnerAmount.setValueFactory(valueFactory);

        spinnerAmount.valueProperty().addListener(this); //add Observer
    }

    @Override
    public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
        this.totAmount = spinnerAmount.getValue();
        System.out.println(totAmount);
        updateAmountPerPersonInList();
    }
}
