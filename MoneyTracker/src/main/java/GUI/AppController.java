package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class AppController extends Application {

    private StatesGUI statesGUI = StatesGUI.PERSON_CREATION;
    

    public static void App(){
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = null;
            switch (statesGUI){
                case PERSON_CREATION:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/CreatePerson.fxml")));
                    break;
                case UNIFORM_TICKET_CREATION:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/UniformTicket.fxml")));
                    break;
                case VALIABLE_TICKET_CREATION:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/VariableTicket.fxml")));
                    break;
                case CALCULATION:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/CalculateTicket.fxml")));
                    break;
            }
            //TODO de FXML abstract maken
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
