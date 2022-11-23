package GUI.guiController;

import database.dbController.AController;
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
    protected ComboBox<String> dropDownTicketType;
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
    private TicketType currentTicketType = TicketType.UNIFORM;

    /**
     * Factory types (FactoryType) converted to string names
     *
     * @used to populate the dropdown (Combobox) tables
     */
    private final List<String> factoryType = new ArrayList<>();

    /**
     * Storage of the person database. We create a local copy of it.
     *
     * @used to search through the database locally
     */
    private HashMap<Integer, IPerson> personsData = new HashMap<>();

    /**
     * Storage of all the persons full names.
     *
     * @used to populate the ListView
     */
    private final List<String> personsDataNames = new ArrayList<>();

    /**
     * Storage for the selected from person ID. It is default -1.
     *
     * @used in the creation of the ticket
     */
    private int fromPersonId = -1;

    /**
     * Storage for the selected for persons ID's.
     *
     * @used in the creation of the ticket
     */
    private List<Integer> forPersonIdList = new ArrayList<>();

    /**
     * Storage for the selected ticketType
     *
     * @used in the creation of the ticket
     */
    private String ticketType = "";

    /**
     * Storage for the amount of depth per for person
     *
     * @used in the creation of the ticket
     * @extra The amount of depth depends on Uniform or Variable ticket.
     * It needs to be set differently in the underlying classes.
     */
    private List<Double> forPersonAmount = new ArrayList<>();

    /**
     * Storage for the selected for persons full names.
     *
     * @used to populate and recreate the dropdown (Combobox) tables
     */
    private List<String> forPersonNamesList = new ArrayList<>();

    /**
     * A specific format to show the depth in the ListView
     */
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

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
    public void saveTicket(ActionEvent event) {
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
    public ITicket createTicket(ITicketFactory factory, int fromPersonId, HashMap<Integer, Double> forPersonData) {
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
}
