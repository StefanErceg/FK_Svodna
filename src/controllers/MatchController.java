package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MatchController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateofMatch;

    @FXML
    private TextField oponentName;

    @FXML
    private TextField result;

    @FXML
    private TableView<?> tableResult;

    @FXML
    void addMatch(ActionEvent event) {
        System.out.println("dodaj utakmicu");
    }

    @FXML
    void addResult(ActionEvent event) {

    }

    @FXML
    void deleteResult(ActionEvent event) {

    }

    @FXML
    void updateResult(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
