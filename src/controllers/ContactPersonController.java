package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ContactPersonController {

    @FXML
    private Label sponsorlabel;

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField lastNameLabel;

    @FXML
    private TextField phoneNumberLabel;

    @FXML
    private TableView<?> contactTable;

    @FXML
    private Button deleteContact;

    @FXML
    private Button close;

    @FXML
    void addContactPerson(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
