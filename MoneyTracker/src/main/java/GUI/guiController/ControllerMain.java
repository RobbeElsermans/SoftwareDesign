package GUI.guiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    void setCalculateView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Calculate.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }
    @FXML
    void setPersonView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/CreatePerson.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }

    @FXML
    void setUniformView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/UniformTicket.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }

    @FXML
    void setVariableView(ActionEvent event) {
        Pane view = null;
        try {
            view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/VariableTicket.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane view = null;
        try {
            view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/CreatePerson.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(view);
    }
}