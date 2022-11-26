package GUI.guiController;

import javafx.event.ActionEvent;

public class ControllerSetVTicket extends ATicketController{
    @Override
    public void setFromPerson(ActionEvent event) {
        super.setFromPerson(event);
    }

    @Override
    public void setForPerson(ActionEvent event) {
        // See if the fromPerson is selected
        // And the Combobox is not empty
        // And the list of Combobox is not empty
        if (this.fromPersonId != -1 &&
                this.dropDownForPerson.getValue() != null &&
                !this.dropDownForPerson.getValue().isEmpty() &&
                this.dropDownForPerson.getItems().size() > 0 &&
                this.spinnerAmount.getValue() != 0.0) {

            //reset labelError if it has been set
            resetError();

            //Get the selected value
            String temp_forPerson = dropDownForPerson.getValue();

            //Get the selected amount
            double temp_amountForPerson = this.spinnerAmount.getValue();


            //keep track of person number
            this.numberOfForPersons++;
            //System.out.println(this.numberOfForPersons);

            //Add the ID and amount to the local storage
            forPersonIdList.add(personController.getIdByName(temp_forPerson));
            forPersonAmount.add(temp_amountForPerson);

            //Add the selected person to the ListView and update the listView
            //setAmountPerPerson(spinnerAmount.getValue());
            updateForPersonListView(temp_forPerson);
            generateTextForPersonListView();

            //Remove person from dropDownForPersons
            this.dropDownForPerson.getItems().remove(temp_forPerson);

            //clear amount and dropdown for person
            this.spinnerAmount.getValueFactory().setValue(0.0);
            this.dropDownForPerson.setValue("");

        }
        else if (this.fromPersonId == -1) {
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
        else if (this.spinnerAmount.getValue() == 0.0){
            //Warn the user
            setError("Select an amount greater than 0!");
            //System.out.println("Select an amount greater than 0!");
        }
        else{
            //Warn the user
            setError("Unknown error");
            //System.out.println("Unknown error");
        }
    }

    @Override
    protected void generateTextForPersonListView() {
        this.listViewForPersons.getItems().add(this.forPersonNamesList.get(this.numberOfForPersons-1) + " is in depth: " + decimalFormat.format(this.forPersonAmount.get(this.numberOfForPersons-1)));
    }

    public void setFactoryType(ActionEvent event) {
        this.ticketType = this.dropDownFactoryType.getValue();
    }

    @Override
    public void saveATicket(ActionEvent event) {
        super.saveATicket(event);
    }
}