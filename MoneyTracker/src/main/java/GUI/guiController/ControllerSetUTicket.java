package GUI.guiController;

import database.PersonDB;
import factory.FactoryType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSetUTicket extends ATicketController implements Initializable, ChangeListener<Double> {

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
        //TODO USE THE PERSON CONTROLLER
        personsData = PersonDB.getInstance().getAll();

        //De soorten Tickets opvragen en toevoegen in locale tabel
        for (FactoryType val : FactoryType.values()) {
            factoryType.add(val.name());
        }
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
        valueFactory.setValue(0.0);
        spinnerAmount.setValueFactory(valueFactory);

        spinnerAmount.valueProperty().addListener(this); //add Observer
    }

    @Override
    public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {

        //Temporarily total amount of money.
        double temp_totAmount = spinnerAmount.getValue();

        for(int i = 0; i < this.forPersonIdList.size(); i++)
        {
            if(this.forPersonAmount.size() < i+1)
                this.forPersonAmount.add(temp_totAmount/this.forPersonIdList.size());
            else
                this.forPersonAmount.set(i, temp_totAmount/this.forPersonIdList.size());
        }

        System.out.println(forPersonAmount);
        super.updateAmountPerPersonInList();
    }
}
