package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DoubleStringConverter;
import model.DAO.DAOFactory;
import model.DTO.Payment;
import model.DTO.Sponsor;
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
    void addPayment(ActionEvent event) { // dodaje trenutno unesene podatke u novu uplatu
        if(paymentDatePicker.getValue()!=null && expirationDatePicker.getValue()!=null && !paymentAmountField.getText().isEmpty()){
            Payment payment=new Payment();

                loadPayment(payment);
                DAOFactory.getDAOFactory().geTPaymentDAO().insert(payment);

            reloadTable();
        }
    }
    @FXML
     void updatePayment(ActionEvent event) { //sacuvava trenutno unesene podatke u trenutno selektovanu uplatu
        if(!paymentTable.getSelectionModel().isEmpty()){
            Payment payment=paymentTable.getSelectionModel().getSelectedItem();
            loadPayment(payment);
            DAOFactory.getDAOFactory().geTPaymentDAO().update(payment);
            reloadTable();
        }
    }
    @FXML
    void selectionChange(MouseEvent event) { // setuje podatke o uplati na gui, za potrebe promjene
        Payment payment=paymentTable.getSelectionModel().getSelectedItem();
        paymentDatePicker.setValue(payment.getPaymentDate().toLocalDateTime().toLocalDate());
        expirationDatePicker.setValue(payment.getExpirationDate().toLocalDateTime().toLocalDate());
        paymentAmountField.setText(""+payment.getAmount());

    }

    @FXML
    void close(ActionEvent event) {
        paymentTable.getSelectionModel().clearSelection();
        paymentTable.getScene().getWindow().hide();
    }


    public void setSponsor(Sponsor sponsor){    //sluzi za prihvatanje informacija od drugih kontorlera
        this.sponsor=sponsor;
        sponsorNameLabel.setText(sponsor.getName());
        reloadTable();
    }
    private void reloadTable(){ // pomocna metoda za ucitavanje tabele
        List<Payment> paymentList= DAOFactory.getDAOFactory().geTPaymentDAO().payments().stream()
                .filter(e->e.getSponsor().equals(sponsor)).collect(Collectors.toList());
        paymentObservableList= FXCollections.observableList(paymentList);
        paymentTable.setItems(paymentObservableList);
    }
    private void loadPayment(Payment payment) { //ucitava uplatu sa gui formi
        payment.setPaymentDate(Timestamp.valueOf(paymentDatePicker.getValue().atStartOfDay()));
        payment.setExpirationDate(Timestamp.valueOf(expirationDatePicker.getValue().atStartOfDay()));
        payment.setAmount(Double.parseDouble(paymentAmountField.getText()));
        payment.setSponsor(sponsor);
    }
    @FXML
    void initialize() {
        paymentTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        paymentTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        paymentTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("amount"));
        paymentAmountField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

    }
}
