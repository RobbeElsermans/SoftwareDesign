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

public class ControllerSetUTicket implements Initializable, ChangeListener<Double> {

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


    private final List<String> factoryTypes = new ArrayList<>();               //Ticket Types
    private HashMap<Integer, IPerson> personsData = new HashMap<>();    //Opslag van id van person met IPerson
    private final List<String> personsDataNames = new ArrayList<>();    //Opslag van .toString IPerson
    private int fromPersonId = -1;                                           //Opslag van id van de from person. -1 is geen id
    private List<Integer> forPersonIdList = new ArrayList<>();         //Opslag van id's van de geselecteerde for personen
    private List<String> forPersonNamesList = new ArrayList<>();         //Opslag van names van de geselecteerde for personen
    private double totAmount = 0.0;                                           //totaal bedrag
    //private List<Double> forPersonAmount = new ArrayList<>();               //bedrag per person (enkel voor variabel)
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private String ticketType = "";

    /**
     * Een ticket opslaan
     *
     * @param event
     */
    public void saveTicket(ActionEvent event) {

        //Kijken ofdat de factory selection is ingevuld
        if (!this.ticketType.isEmpty() && forPersonIdList.size() > 0 && totAmount > 0 && fromPersonId != -1) {
            ITicketFactory factory;
            factory = AbstractFactoryProvider.getFactory(FactoryType.valueOf(this.ticketType));
            //Save everything for Uniform Ticket
            HashMap<Integer, Double> forData = new HashMap<>();
            for (int i = 0; i < forPersonIdList.size(); i++) {
                forData.put(forPersonIdList.get(i), (totAmount / forPersonIdList.size()));
            }

            ITicket ticket;
            //Factory Provider aanspreken en soort ticket aanvragen
            //Soort ticket hangt af van menu keuzen
            if (true)//Starten met uniform ticket
            {
                //ticket = factory.getUniformTicket(fromPersonId, new HashMap<>(forPersonIdList));
            } else {
                //ticket = factory.getVariableTicket();
            }

            System.out.println("Ticket Saved");
        } else {
            System.out.println("Fill everything in!");
        }
    }

    /**
     * We voegen een for persoon toe met de drukknop "ADD PERSON"
     */
    public void addForPerson() {
        if (fromPersonId != -1 && dropDownForPerson.getValue() != null && dropDownForPerson.getItems().size() > 0) {
            //get the selected person's id.
            String temp_forPerson = dropDownForPerson.getValue();
            System.out.println("Add Person: " + temp_forPerson);

            //Add id to forPersonIdList
            if (forPersonIdList != null) {
                personsData.forEach((key, value) -> {
                    if (value.toString().equalsIgnoreCase(temp_forPerson)) {
                        forPersonIdList.add(key);
                    }
                });
            }

            //Add person to forPersonListView and update forPersonListView
            updateForPersonListView(temp_forPerson);

            //Remove person from dropDownForPersons
            dropDownForPerson.getItems().remove(temp_forPerson);
        } else {
            System.out.println("Select a from person first! or the list is empty");
        }
    }

    public void setFactoryType(ActionEvent event) {
        this.ticketType = this.dropDownFactoryType.getValue();
    }

    /**
     * Er wordt een from person geselecteerd
     * De lijst van forPerson moet upgedated worden zonder de from person in
     *
     * @param event
     */
    public void addFromPerson(ActionEvent event) {

        //Kijk welke persoon geselecteerd is
        String temp_fromPerson = dropDownFromPerson.getValue();
        //System.out.println(fromPerson);

        //Haal de key uit de database
        personsData.forEach((key, value) -> {
            if (value.toString().equalsIgnoreCase(temp_fromPerson)) {
                fromPersonId = key;
            }
        });
        System.out.println("key: " + fromPersonId);


        dropDownForPerson.getItems().removeAll(personsDataNames);   //Verwijder elke persoon. We weten niet of dat de lijst veranderd is of niet.
        dropDownForPerson.getItems().addAll(personsDataNames);      //Voeg alle personen toe (fresh)
        dropDownForPerson.getItems().remove(temp_fromPerson);       //Verwijder die 1ne persoon die geselecteerd is

        //TODO Verwijder de personen die al eerder geselecteerd zijn. Met een popup bevestigen?
        if (forPersonIdList.size() > 0) {
            forPersonIdList = new ArrayList<>();    //instantieer de lijst, als deze ingevuld is, zodat alles verwijderd is van voorgaande lijst
            forPersonNamesList = new ArrayList<>(); //instantieer de lijst, als deze ingevuld is, zodat alles verwijderd is van voorgaande lijst
            removeAllItemsInListView();
        }
    }

    private void removeAllItemsInListView() {
        List<String> temp_forPersonListView = new ArrayList<>();
        temp_forPersonListView = listViewForPersons.getItems();
        listViewForPersons.getItems().removeAll(temp_forPersonListView);
    }

    private void getData() {
        //De database persons items opvragen en toevoegen in locale tabel
        personsData = PersonDB.getInstance().getAll();

        //De soorten Tickets opvragen en toevoegen in locale tabel
        for (FactoryType val : FactoryType.values()) {
            factoryTypes.add(val.name());
        }
    }

    private void updateForPersonListView(String temp_forPerson) {
        forPersonNamesList.add(temp_forPerson);
        updateAmountPerPersonInList();
    }

    private void updateAmountPerPersonInList() {
        removeAllItemsInListView();
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
        dropDownFactoryType.getItems().addAll(factoryTypes);

        personsData.forEach((key, value) -> {
            personsDataNames.add(value.toString()); //Dit gebruiken we om later bij wijzigingen de names terug origineel te zetten.
            dropDownForPerson.getItems().add(value.toString());
            dropDownFromPerson.getItems().add(value.toString());
        });

        //De acties implementeren
        dropDownFromPerson.setOnAction(this::addFromPerson);
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
