package GUI.helperClass;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.util.List;

public class dropdownControl {

    public static void resetDropdownList(ComboBox<String> dropdownList, List<String> data) {
        setDropdownList(dropdownList, data);
    }
    public static void clearDropdownList(ComboBox<String> dropdownList) {
        dropdownList.getItems().clear();
    }
    public static void setDropdownList(ComboBox<String> dropdownList, List<String> data) {
        // Remove all the persons out of the Combobox. We do not know if it was complete.
        if(!isDropdownListEmpty(dropdownList))
            dropdownList.getItems().clear();
        // Add a fresh new list
        dropdownList.getItems().addAll(data);
    }
    public static void deleteAnElementFromDropdownList(ComboBox<String> dropdownList, String deleteItem ) {
        dropdownList.getItems().remove(deleteItem);
    }
    public static String getDropdownListValue(ComboBox<String> dropdownList) {
        return dropdownList.getValue();
    }
    public static void clearDropdownListValue(ComboBox<String> dropdownList) {
        dropdownList.setValue(null);
    }
    public static boolean isDropdownListEmpty(ComboBox<String> dropdownList){
        return dropdownList.getItems().isEmpty();
    }
    public static void setDropdownListAction(ComboBox<String> dropdownList, EventHandler<ActionEvent> e) {
        dropdownList.setOnAction(e);
    }
}
