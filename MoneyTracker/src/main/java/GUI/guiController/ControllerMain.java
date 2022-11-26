package GUI.guiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ControllerMain {

    @FXML
    private BorderPane mainPane;

    @FXML
    void setCalculateView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(getClass().getResource("/fxml/Calculate.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }

    @FXML
    void setPersonView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(getClass().getResource("/fxml/CreatePerson.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }

    @FXML
    void setUniformView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(getClass().getResource("/fxml/UniformTicket.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }

    @FXML
    void setVariableView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(getClass().getResource("/fxml/VariableTicket.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }
}