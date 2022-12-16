package GUI.guiController;

import GUI.helperClass.dropdownControl;
import GUI.helperClass.errorControl;
import GUI.helperClass.informUser;
import database.PersonDB;
import database.dbController.AController;
import database.dbController.PersonController;
import database.dbController.TicketController;
import database.TicketDB;
import database.observer.IObserver;
import factory.AbstractFactoryProvider;
import factory.FactoryType;
import factory.facts.ITicketFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ticket.ITicket;
import ticket.TicketType;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AAddTicketController implements Initializable, IObserver {
    @FXML
    protected Label labelError;
    @FXML
    protected ComboBox<String> dropDownFactoryType;
    @FXML
    protected ComboBox<String> dropDownFromPerson;
    @FXML
    protected ComboBox<String> dropDownForPerson;
    @FXML
    protected Spinner<Double> spinnerAmount;
    @FXML
    protected ListView<String> listViewForPersons;


    /**
     * The current selected ticketType
     *
     * @used to populate the dropdown (Combobox) tables
     */
    private TicketType currentTicketType = TicketType.UNIFORM;

    /**
     * Factory types (FactoryType) converted to string names
     *
     * @used to populate the dropdown (Combobox) tables
     */
    private final List<String> factoryType = new ArrayList<>();

    /**
     * Storage of all the persons full names.
     *
     * @used to populate the ListView
     */
    private List<String> personsDataNames = new ArrayList<>();

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
    protected static final DecimalFormat decimalFormat = new DecimalFormat("0.##");


    /**
     * Storage for the amount of for persons added
     *
     * @used to iterate
     */
    protected int numberOfForPersons = 0;

    /**
     * The controller for the personDB
     */
     protected PersonController personController;

    /**
     * The controller for the ticket
     */
    protected TicketController ticketController;

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
    @FXML
    protected void saveATicket(ActionEvent event) {
        //Check if the input fields are filled in
        if (!this.ticketType.isEmpty() &&
                this.forPersonIdList.size() > 0 &&
                this.forPersonAmount.size() > 0 &&
                this.fromPersonId != -1) {

            //reset labelError if it has been set
            errorControl.clearError(this.labelError);

            //Get the factory for the ticket type
            ITicketFactory factory = AbstractFactoryProvider.getFactory(FactoryType.valueOf(this.ticketType));

            //Format data in the right style
            HashMap<Integer, Double> forPersonData = formatForPersonData(this.forPersonIdList, this.forPersonAmount);

            //Create the ticket based on the TicketType
            ITicket createdTicket = createTicket(factory, this.fromPersonId, forPersonData);

            //Save the ticket with use of the ticket controller
            ticketController.addValue(createdTicket);

            //Delete everything
            resetData();

            //initialize
            initData();

            //set by observer
            //alertUser();
        }
        else if(this.ticketType.isEmpty()) {
            //Warn the user
            errorControl.setError(this.labelError, "Select a ticket type!");
        }
        else if (this.fromPersonId == -1)
        {
            errorControl.setError(this.labelError, "No from person selected!");
        }
        else if(this.forPersonIdList.size() == 0 &&
                this.forPersonAmount.size() == 0) {
            //Warn the user
            errorControl.setError(this.labelError, "No for person(s) are selected!");
        }
    }

    /**
     * reset the form to te initial state
     *
     * @description We use this method to clear the form, so we can fill in a clean empty form.
     */
    private void resetData() {
        this.listViewForPersons.getItems().clear();
        dropdownControl.clearDropdownList(this.dropDownFromPerson);
        dropdownControl.clearDropdownList(this.dropDownForPerson);
        dropdownControl.clearDropdownList(this.dropDownFactoryType);

        this.factoryType.clear();
        this.personsDataNames.clear();

        this.fromPersonId = -1;
        this.forPersonAmount.clear();
        this.forPersonIdList.clear();
        this.forPersonNamesList.clear();
        this.ticketType = "";
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
    protected void setFromPerson(ActionEvent event) {
        try {
            // Get the selected persons fill name and store it temporary
            String temp_fromPerson = dropdownControl.getDropdownListValue(this.dropDownFromPerson);

            fromPersonId = personController.getIdByName(temp_fromPerson);

            dropdownControl.resetDropdownList(this.dropDownForPerson, this.personsDataNames);
            dropdownControl.deleteAnElementFromDropdownList(this.dropDownForPerson, temp_fromPerson);

            //if there were for persons selected for a specific from person, we need to clear the lists.
            //this.forPersonIdList.size() > 0
            if (this.numberOfForPersons > 0) {
                // Delete the lists
                this.forPersonIdList.clear();
                this.forPersonNamesList.clear();
                this.listViewForPersons.getItems().clear();
                this.numberOfForPersons = 0;
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
    @FXML
    protected abstract void setForPerson(ActionEvent event);
    @FXML
    protected void updateForPersonListView(String temp_forPerson) {

        errorControl.clearError(this.labelError);

        this.forPersonNamesList.add(temp_forPerson);
    }

    protected abstract void generateTextForPersonListView();

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
     * @param factory
     * @param fromPersonId
     * @param forPersonData
     * @return
     */
    @FXML
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
        //add the person controller
        personController = new PersonController(PersonDB.getInstance());
        ticketController = new TicketController(TicketDB.getInstance());
        ticketController.addObserver(this);  //only add observer to a ticketController
        //initialize the data
        initData();
    }

    private void initData() {
        getDatabaseData();          //get data from database
        initDropdowns();    //dropdown initialize
        initSpinner();      //Spinner initialize
    }

    private void getDatabaseData() {
        //De database persons items opvragen en toevoegen in locale tabel

        this.personsDataNames = personController.getAllFullNames();

        // All the FactoryTypes of tickets
        for (FactoryType val : FactoryType.values()) {
            factoryType.add(val.name());
        }
    }

    private void initDropdowns() {
        //De lijsten toevoegen aan de respectievelijke dropdowns(combobox)

        dropdownControl.setDropdownList(this.dropDownFactoryType, this.factoryType);
        dropdownControl.setDropdownList(this.dropDownForPerson, this.personsDataNames);
        dropdownControl.setDropdownList(this.dropDownFromPerson, this.personsDataNames);
        dropdownControl.setDropdownListAction(this.dropDownFromPerson, this::setFromPerson);
    }

    protected void initSpinner() {
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 999,0,0.5);
        spinnerAmount.setValueFactory(valueFactory);
    }

    public void update(String text){
        informUser.inform("Database changed", text);
        System.out.println(text);
    }
}