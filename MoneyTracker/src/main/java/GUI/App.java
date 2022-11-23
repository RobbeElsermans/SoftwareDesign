package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    private StatesGUI statesGUI = StatesGUI.TICKET_CREATION;

    public static void App(){
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = null;
            switch (statesGUI){
                case PERSON_CREATION:
                    break;
                case TICKET_CREATION:
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/UniformTicket.fxml")));
                    break;
                case CALCULATION:
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
