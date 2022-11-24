package GUI.guiController;

import database.PersonDB;
import database.dbController.AController;
import database.dbController.PersonController;
import database.dbController.TicketController;
import database.TicketDB;
import factory.AbstractFactoryProvider;
import factory.FactoryType;
import factory.facts.ITicketFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import person.IPerson;
import ticket.ITicket;
import ticket.TicketType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ATicketController {
    @FXML
    protected Label labelPriceType;
    @FXML
    protected Button buttonSaveTicket;
    @FXML
    protected ComboBox<String> dropDownFactoryType;
    @FXML
    protected ComboBox<String> dropDownFromPerson;
    @FXML
    protected ComboBox<String> dropDownForPerson;
    @FXML
    protected Button buttonAddForPerson;
    @FXML
    protected Spinner<Double> spinnerAmount;
    @FXML
    protected ListView<String> listViewForPersons;


    /**
     * The current selected ticketType
     *
     * @used to populate the dropdown (Combobox) tables
     */
    protected TicketType currentTicketType = TicketType.UNIFORM;

    /**
     * Factory types (FactoryType) converted to string names
     *
     * @used to populate the dropdown (Combobox) tables
     */
    protected final List<String> factoryType = new ArrayList<>();

    /**
     * Storage of the person database. We create a local copy of it.
     *
     * @used to search through the database locally
     */
    //TODO wijzigen naar personNames
    //En alles omzetten naar personsDataNames
    //protected HashMap<Integer, IPerson> personsData = new HashMap<>();

    /**
     * Storage of all the persons full names.
     *
     * @used to populate the ListView
     */
    //TODO wijzig personsData naar personsDataNames
    protected List<String> personsDataNames = new ArrayList<>();

    /**
     * Storage for the selected from person ID. It is default -1.
     *
     * @used in the creation of the ticket
     */
    protected int fromPersonId = -1;

    /**
     * Storage for the selected for persons ID's.
     *
     * @used in the creation of the ticket
     */
    protected List<Integer> forPersonIdList = new ArrayList<>();

    /**
     * Storage for the selected ticketType
     *
     * @used in the creation of the ticket
     */
    protected String ticketType = "";

    /**
     * Storage for the amount of depth per for person
     *
     * @used in the creation of the ticket
     * @extra The amount of depth depends on Uniform or Variable ticket.
     * It needs to be set differently in the underlying classes.
     */
    protected List<Double> forPersonAmount = new ArrayList<>();

    /**
     * Storage for the selected for persons full names.
     *
     * @used to populate and recreate the dropdown (Combobox) tables
     */
    protected List<String> forPersonNamesList = new ArrayList<>();

    /**
     * A specific format to show the depth in the ListView
     */
    protected static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /**
     * The controller for the personDB
     */
     PersonController personController;

    /**
     * The constructor of an abstract ticket
     */
    public ATicketController() {
        //Init person Controller
        personController = new PersonController(PersonDB.getInstance());

        //Get the data
        //getDatabaseData();
    }


    /**
     * Method to save a ticket.
     *
     * @param event ActionEvent
     * @description We check in this method that the user has
     * filled in all the needed items to create a ticket.
     * Afterwards we create a Factory of the selected Ticket Type.
     * Then we use the propper Uniform or Variable ticket receiving from the factory.
     * Final we save the created ticket, and we reset the GUI.
     */
    protected void saveTicket(ActionEvent event) {
        //Check if the input fields are filled in
        if (!this.ticketType.isEmpty() && this.forPersonIdList.size() > 0 && this.forPersonAmount.size() > 0) {

            //Get the factory for the ticket type
            ITicketFactory factory = AbstractFactoryProvider.getFactory(FactoryType.valueOf(this.ticketType));

            //Format data in the right style
            HashMap<Integer, Double> forPersonData = formatForPersonData(this.forPersonIdList, this.forPersonAmount);

            //Create the ticket based on the TicketType
            ITicket createdTicket = createTicket(factory, this.fromPersonId, forPersonData);

            //Save the ticket with use of the ticket controller
            AController<ITicket> tController = new TicketController(TicketDB.getInstance());
            tController.addValue(createdTicket);

        } else {
            //Warn the user
            System.out.println("Fill everything in!");
        }
    }

    /**
     * Method to set a from person
     *
     * @param event ActionEvent
     * @description When we select a person in the Combobox, we want to store the person's ID.
     * The selected person should not be in the for person list.
     * If we had selected a previous from person,
     * this previously person may now be part of the for persons
     */
    public void setFromPerson(ActionEvent event) {
        try {
            // Get the selected persons fill name and store it temporary
            String temp_fromPerson = dropDownFromPerson.getValue();

            fromPersonId = personController.getIdByName(temp_fromPerson);

            // Remove From person out of the For persons list
            // Remove all the persons out of the Combobox. We do not know if it was complete.
            dropDownForPerson.getItems().removeAll(personsDataNames);
            // Add a fresh new list
            dropDownForPerson.getItems().addAll(personsDataNames);
            // Remove the selected from person
            dropDownForPerson.getItems().remove(temp_fromPerson);

            //if there were for persons selected for a specific from person, we need to clear the list.
            if (this.forPersonIdList.size() > 0) {
                // Delete the list by instantiate it
                this.forPersonIdList = new ArrayList<>();
                this.forPersonNamesList = new ArrayList<>();

                List<String> temp_forPersonListView = this.listViewForPersons.getItems();
                this.listViewForPersons.getItems().removeAll(temp_forPersonListView);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    /**
     * Method to set a for person.
     *
     * @param event ActionEvent
     * @description This method only can continue if the from person,
     * the combobox and the for persons list is not empty.
     * Afterwards we set the ListView with new users, and we calculate the correct price per user
     */

    public abstract void setForPerson(ActionEvent event);


    private HashMap<Integer, Double> formatForPersonData(List<Integer> forPersonIdList, List<Double> forPersonAmount) {

        if (forPersonIdList.size() == forPersonAmount.size()) {

            //Create temporary HashMap
            HashMap<Integer, Double> tempHash = new HashMap<>();
            for (int i = 0; i < forPersonIdList.size(); i++) {
                tempHash.put(forPersonIdList.get(i), forPersonAmount.get(i));
            }
            return tempHash;
        }

        return null;
    }

    /**
     * Method to create a ticket (Variable, Uniform, ...)
     *
     * @param factory       ITicketFactory
     * @param fromPersonId
     * @param forPersonData
     * @return A created object of ITicket
     */
    protected ITicket createTicket(ITicketFactory factory, int fromPersonId, HashMap<Integer, Double> forPersonData) {
        ITicket tempTicket = null;
        switch (currentTicketType) {
            case UNIFORM:
                tempTicket = factory.getUniformTicket(fromPersonId, forPersonData);
                break;
            case VARIABLE:
                tempTicket = factory.getVariableTicket(fromPersonId, forPersonData);
                break;
        }
        return tempTicket;
    }

//    protected void updateForPersonListView(String temp_forPerson) {
//        forPersonNamesList.add(temp_forPerson);
//        updateAmountPerPersonInList();
//    }
//    public abstract void updateAmountPerPersonInList();
//
//    protected void generateTextForPersonListView() {
//        for (int i = 0; i < this.forPersonNamesList.size(); i++) {
//            this.listViewForPersons.getItems().add(this.forPersonNamesList.get(i) + " is in depth: " + decimalFormat.format(this.forPersonAmount.get(i)));
//        }
//    }
//
//    protected void clearforPersonListView() {
//        List<String> temp_forPersonListView;
//        temp_forPersonListView = listViewForPersons.getItems();
//        listViewForPersons.getItems().removeAll(temp_forPersonListView);
//    }
//
//    protected void getDatabaseData() {
//        //De database persons items opvragen en toevoegen in locale tabel
//        //TODO USE THE PERSON CONTROLLER
//        //personsData = PersonDB.getInstance().getAll();
//
//        this.personsDataNames = personController.getAllFullNames();
//
//        // All the FactoryTypes of tickets
//        for (FactoryType val : FactoryType.values()) {
//            factoryType.add(val.name());
//        }
//    }
}

