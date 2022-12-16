package GUI.helperClass;

import javafx.scene.control.Alert;

public class informUser {
    public static void inform(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
