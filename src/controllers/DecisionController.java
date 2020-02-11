package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DecisionController {

    @FXML
    private Label decisionLabel;

    private boolean result;

    @FXML
    void accept(ActionEvent event) {
        result = true;
        decisionLabel.getScene().getWindow().hide();
    }

    @FXML
    void decline(ActionEvent event) {
        result = false;
        decisionLabel.getScene().getWindow().hide();
    }

    @FXML
    void initialize(){
    }

    public boolean returnResult(){
        return  result;
    }
}
