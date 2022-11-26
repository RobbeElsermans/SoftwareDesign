package GUI.guiController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

public class ControllerSetUTicket extends ATicketController implements ChangeListener<Double> {

    //We had to do this
    //source: https://stackoverflow.com/questions/58552417/how-do-i-change-this-superclass-into-an-abstract-class-without-getting-an-instan

    @Override
    public void saveATicket(ActionEvent event) {
        if(spinnerAmount.getValue() > 0)
            super.saveATicket(event);
        else {
            setError("Value is 0!");
        }
    }

    @Override
    public void setForPerson(ActionEvent event) {
        // See if the fromPerson is selected
        // And the Combobox is not empty
        // And the list of Combobox is not empty
        if (this.fromPersonId != -1 &&
                this.dropDownForPerson.getValue() != null &&
                !this.dropDownForPerson.getValue().isEmpty() &&
                this.dropDownForPerson.getItems().size() > 0) {

            //reset labelError if it has been set
            resetError();

            //Get the selected value
            String temp_forPerson = dropDownForPerson.getValue();

            //keep track of person number
            this.numberOfForPersons++;
            System.out.println(this.numberOfForPersons);

            forPersonIdList.add(personController.getIdByName(temp_forPerson));

            //Add the selected person to the ListView and update the listView
            setAmountPerPerson(spinnerAmount.getValue());
            updateForPersonListView(temp_forPerson);

            //Remove person from dropDownForPersons
            this.dropDownForPerson.getItems().remove(temp_forPerson);

            //clear dropdown for person
            this.dropDownForPerson.setValue("");
        }         else if (this.fromPersonId == -1) {
            //Warn the user
            setError("Select a from person first!");
            //System.out.println("Select a from person first!");
        }
        else if (this.dropDownForPerson.getItems().size() == 0) {
            //Warn the user
            setError("The person list is empty!");
            //System.out.println("The person list is empty!");
        }
        else if (this.dropDownForPerson.getValue() == null) {
            //Warn the user
            setError("Select a for person!");
            //System.out.println("Select a for person!");
        }
        else if (this.dropDownForPerson.getValue().isEmpty()) {
            //Warn the user
            setError("Select a for person!");
            //System.out.println("Select a for person!");
        }
        else{
            //Warn the user
            setError("Unknown error");
            //System.out.println("Unknown error");
        }
    }

    public void setFactoryType(ActionEvent event) {
        this.ticketType = this.dropDownFactoryType.getValue();
    }

    @Override
    public void setFromPerson(ActionEvent event) {
        super.setFromPerson(event);
    }

    @Override
    protected void initSpinner() {
        super.initSpinner();
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
        for (int i = 0; i < this.numberOfForPersons; i++) {
            if (this.forPersonAmount.size() < i + 1)
                this.forPersonAmount.add(temp_totAmount / this.forPersonIdList.size());
            else
                this.forPersonAmount.set(i, temp_totAmount / this.forPersonIdList.size());
        }
    }

    public void updateAmountPerPersonInList() {
        listViewForPersons.getItems().clear();
        generateTextForPersonListView();
    }

    protected void updateForPersonListView(String temp_forPerson) {
        super.updateForPersonListView(temp_forPerson);
        updateAmountPerPersonInList();
    }

    @Override
    protected void generateTextForPersonListView() {
        for (int i = 0; i < this.numberOfForPersons; i++) {
            this.listViewForPersons.getItems().add(this.forPersonNamesList.get(i) + " is in depth: " + decimalFormat.format(this.forPersonAmount.get(i)));
        }
    }
}
