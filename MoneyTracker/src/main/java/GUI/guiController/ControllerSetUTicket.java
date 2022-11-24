package GUI.guiController;

import database.PersonDB;
import factory.FactoryType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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
        // See if the fromPerson is selected
        // And the Combobox is not empty
        // And the list of Combobox is not empty
        if (this.fromPersonId != -1 &&
                this.dropDownForPerson.getValue() != null &&
                this.dropDownForPerson.getItems().size() > 0) {

            //Get the selected value
            String temp_forPerson = dropDownForPerson.getValue();

            forPersonIdList.add(personController.getIdByName(temp_forPerson));

            //Add the selected person to the ListView and update the listView
            setAmountPerPerson(spinnerAmount.getValue());
            updateForPersonListView(temp_forPerson);

            //Remove person from dropDownForPersons
            this.dropDownForPerson.getItems().remove(temp_forPerson);
        } else {
            //Warn the user
            System.out.println("Select a from person first! or the list is empty");
        }
    }

    public void setFactoryType(ActionEvent event) {
        this.ticketType = this.dropDownFactoryType.getValue();
    }

    @Override
    public void setFromPerson(ActionEvent event){
        super.setFromPerson(event);
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
        getDatabaseData();          //get data from database
        initDropdowns();    //dropdown initialize
        initSpinner();      //Spinner initialize
    }

    private void initDropdowns() {
        //De lijsten toevoegen aan de respectievelijke dropdowns(combobox)
        dropDownFactoryType.getItems().addAll(factoryType);

        personsDataNames.forEach(value -> {
            dropDownForPerson.getItems().add(value);
            dropDownFromPerson.getItems().add(value);
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

        setAmountPerPerson(temp_totAmount);

        //System.out.println(forPersonAmount);
        updateAmountPerPersonInList();
    }

    private void setAmountPerPerson(double temp_totAmount) {
        for(int i = 0; i < this.forPersonIdList.size(); i++)
        {
            if(this.forPersonAmount.size() < i+1)
                this.forPersonAmount.add(temp_totAmount /this.forPersonIdList.size());
            else
                this.forPersonAmount.set(i, temp_totAmount /this.forPersonIdList.size());
        }
    }

    public void updateAmountPerPersonInList() {
        clearforPersonListView();
        generateTextForPersonListView();
    }

    protected void updateForPersonListView(String temp_forPerson) {
        forPersonNamesList.add(temp_forPerson);
        updateAmountPerPersonInList();
    }

    protected void generateTextForPersonListView() {
        for (int i = 0; i < this.forPersonNamesList.size(); i++) {
            this.listViewForPersons.getItems().add(this.forPersonNamesList.get(i) + " is in depth: " + decimalFormat.format(this.forPersonAmount.get(i)));
        }
    }

    protected void clearforPersonListView() {
        List<String> temp_forPersonListView;
        temp_forPersonListView = listViewForPersons.getItems();
        listViewForPersons.getItems().removeAll(temp_forPersonListView);
    }

    protected void getDatabaseData() {
        //De database persons items opvragen en toevoegen in locale tabel
        //TODO USE THE PERSON CONTROLLER
        //personsData = PersonDB.getInstance().getAll();

        this.personsDataNames = personController.getAllFullNames();

        // All the FactoryTypes of tickets
        for (FactoryType val : FactoryType.values()) {
            factoryType.add(val.name());
        }
    }
}
