package cca.transfer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ErrorController implements Initializable{
    
    @FXML private TextArea err;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        err.setText(App.fullError);
    }
}
