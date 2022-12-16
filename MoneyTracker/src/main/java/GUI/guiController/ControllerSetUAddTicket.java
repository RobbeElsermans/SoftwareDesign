package GUI.guiController;

import GUI.helperClass.dropdownControl;
import GUI.helperClass.errorControl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerSetUAddTicket extends AAddTicketController implements ChangeListener<Double> {

    //We had to do this
    //source: https://stackoverflow.com/questions/58552417/how-do-i-change-this-superclass-into-an-abstract-class-without-getting-an-instan

    @Override
    protected void saveATicket(ActionEvent event) {
        if (spinnerAmount.getValue() > 0)
            super.saveATicket(event);
        else {
            errorControl.setError(this.labelError, "Value is 0!");
        }
    }

    @Override
    protected void setForPerson(ActionEvent event) {
        // See if the fromPerson is selected
        // And the Combobox is not empty
        // And the list of Combobox is not empty
        if (this.fromPersonId != -1 &&
                this.dropDownForPerson.getValue() != null &&
                !this.dropDownForPerson.getValue().isEmpty() &&
                this.dropDownForPerson.getItems().size() > 0) {

            //reset labelError if it has been set
            errorControl.clearError(this.labelError);

            //Get the selected value

            String temp_forPerson = dropdownControl.getDropdownListValue(this.dropDownForPerson);

            //keep track of person number
            this.numberOfForPersons++;
            System.out.println(this.numberOfForPersons);

            forPersonIdList.add(personController.getIdByName(temp_forPerson));

            //Add the selected person to the ListView and update the listView
            setAmountPerPerson(spinnerAmount.getValue());
            updateForPersonListView(temp_forPerson);

            //Remove person from dropDownForPersons
            dropdownControl.deleteAnElementFromDropdownList(this.dropDownForPerson, temp_forPerson);

            //clear dropdown for person
            dropdownControl.clearDropdownListValue(this.dropDownForPerson);
        } else if (this.fromPersonId == -1) {
            //Warn the user
            errorControl.setError(this.labelError, "Select a from person first!");

            //System.out.println("Select a from person first!");
        } else if (this.dropDownForPerson.getItems().size() == 0) {
            //Warn the user
            errorControl.setError(this.labelError, "The person list is empty!");
            //System.out.println("The person list is empty!");
        } else if (dropdownControl.getDropdownListValue(this.dropDownForPerson) == null) {
            //Warn the user
            errorControl.setError(this.labelError, "Select a for person!");
            //System.out.println("Select a for person!");
        } else if (dropdownControl.isDropdownListEmpty(this.dropDownForPerson)) {
            //Warn the user
            errorControl.setError(this.labelError, "Select a for person!");
            //System.out.println("Select a for person!");
        } else {
            //Warn the user
            errorControl.setError(this.labelError, "Unknown error");
            //System.out.println("Unknown error");
        }
    }

    @FXML
    private void setFactoryType(ActionEvent event) {
        this.ticketType = dropdownControl.getDropdownListValue(this.dropDownFactoryType);
    }

    @Override
    protected void setFromPerson(ActionEvent event) {
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

    private void updateAmountPerPersonInList() {
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
