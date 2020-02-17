package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertController {

    @FXML
    private Label alertLabel;

    @FXML
    void confirm(ActionEvent event) {
        alertLabel.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
    }

    public void setText(String str){
        alertLabel.setText(str);
    }

    public void showAlert(){
        ((Stage)alertLabel.getScene().getWindow()).show();
    }
}
