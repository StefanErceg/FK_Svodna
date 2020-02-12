package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DAO.DAOFactory;
import model.DTO.ContactPerson;
import model.DTO.Payment;
import model.DTO.Sponsor;
import model.DTO.SponsorContactPerson;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


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
    private TableView<Payment> paymentTable;

    private Sponsor sponsor;
    private ObservableList<Payment> paymentObservableList;
    @FXML
    void addPayment(ActionEvent event) {
        if(paymentDatePicker.getValue()!=null && expirationDatePicker.getValue()!=null && !paymentAmountField.getText().isEmpty()){
            Payment payment=new Payment();
            payment.setPaymentDate(Timestamp.valueOf(paymentDatePicker.getValue().atStartOfDay()));
            payment.setExpirationDate(Timestamp.valueOf(expirationDatePicker.getValue().atStartOfDay()));
            payment.setAmount(Double.parseDouble(paymentAmountField.getText()));
            payment.setSponsor(sponsor);
            DAOFactory.getDAOFactory().geTPaymentDAO().insert(payment);
            reloadTable();
        }
    }

    @FXML
    void close(ActionEvent event) {
        paymentTable.getSelectionModel().clearSelection();
        paymentTable.getScene().getWindow().hide();
    }


    public void setSponsor(Sponsor sponsor){
        this.sponsor=sponsor;
        sponsorNameLabel.setText(sponsor.getName());
        reloadTable();
    }
    private void reloadTable(){
        List<Payment> paymentList= DAOFactory.getDAOFactory().geTPaymentDAO().payments().stream()
                .filter(e->e.getSponsor().equals(sponsor)).collect(Collectors.toList());
        paymentObservableList= FXCollections.observableList(paymentList);
        paymentTable.setItems(paymentObservableList);
    }

    @FXML
    void initialize() {
        paymentTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        paymentTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        paymentTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("amount"));

    }
}
