package GUI.helperClass;

import javafx.scene.control.Label;

public class errorControl {
    public static void setError(Label label, String text){
        label.setText(text);
    }
    public static void clearError(Label label){
        if(errorPresent(label))
            setError(label, "");
    }
    public static boolean errorPresent(Label label){
        return !label.getText().isEmpty();
    }
}
