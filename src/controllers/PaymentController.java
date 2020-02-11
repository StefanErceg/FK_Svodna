package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;



public class PaymentController {

    @FXML
    private Label sponsorNameLabel;

    @FXML
    private DatePicker paymentDatePicker;

    @FXML
    private DatePicker expirationDatePicker;

    @FXML
    private TextField paymentAmountField;

    @FXML
    private TableView<?> paymentTable;

    @FXML
    void addPayment(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void deletePayment(ActionEvent event) {

    }

    @FXML
    void initialize() {


    }
}
