package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private Stage alertStage;
    private AlertController alertController;
    private DecisionController decisionController;
    private Stage decisionStage;

    @FXML
    void addPayment(ActionEvent event) { // dodaje trenutno unesene podatke u novu uplatu
        if(paymentDatePicker.getValue()!=null && expirationDatePicker.getValue()!=null && !paymentAmountField.getText().isEmpty()) {
            Payment payment = new Payment();
            loadPayment(payment);
            if (!DAOFactory.getDAOFactory().geTPaymentDAO().insert(payment)) {
                alertController.setText("Desila se greška pri upisu, upis nije izvršen!");
                alertStage.showAndWait();
            }
            reloadTable();
            clearFields();
        }else{
            alertController.setText("Nisu unesena sva polja!");
            alertStage.show();
        }
    }

    @FXML
     void updatePayment(ActionEvent event) { //sacuvava trenutno unesene podatke u trenutno selektovanu uplatu
        if(paymentTable.getSelectionModel().isEmpty()) {
            alertController.setText("Nije odabrana uplata!");
            alertStage.showAndWait();
            return;
        }
        if(expirationDatePicker != null && paymentDatePicker != null) {
            Payment payment = paymentTable.getSelectionModel().getSelectedItem();
            loadPayment(payment);
            if (!DAOFactory.getDAOFactory().geTPaymentDAO().update(payment)) {
                alertController.setText("Desila se greška pri upisu, nije izvršen upis!");
                alertStage.show();
            }
        }else{
            alertController.setText("Nisu unesena sva polja!");
            alertStage.show();
        }
        reloadTable();
        clearFields();
    }

    @FXML
    void selectionChange(MouseEvent event) { // setuje podatke o uplati na gui, za potrebe promjene
        Payment payment=paymentTable.getSelectionModel().getSelectedItem();
        paymentDatePicker.setValue(payment.getPaymentDate().toLocalDateTime().toLocalDate());
        if(payment.getExpirationDate()!=null)
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
        sponsorNameLabel.setText(sponsor.getName().trim());
        reloadTable();
    }

    private void reloadTable(){ // pomocna metoda za ucitavanje tabele
        List<Payment> paymentList= DAOFactory.getDAOFactory().geTPaymentDAO().payments().stream()
                .filter(e->e.getSponsor().equals(sponsor)).collect(Collectors.toList());
        paymentObservableList = FXCollections.observableList(paymentList);
        paymentTable.getItems().clear();
        paymentTable.refresh();
        paymentTable.setItems(paymentObservableList);
    }

    private void loadPayment(Payment payment) { //ucitava uplatu sa gui formi
        payment.setPaymentDate(Timestamp.valueOf(paymentDatePicker.getValue().atStartOfDay()));
        payment.setExpirationDate(Timestamp.valueOf(expirationDatePicker.getValue().atStartOfDay()));
        payment.setAmount(Double.parseDouble(paymentAmountField.getText()));
        payment.setSponsor(sponsor);
    }

    @FXML
    void initialize() throws Exception {
        paymentTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        paymentTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        paymentTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("amount"));
        paymentAmountField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();
        decisionController = loader.getController();
        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));
        decisionStage.setTitle("Potvrda");
        alertStage.setTitle("Upozorenje");
    }

    private void clearFields(){
        paymentAmountField.setText("");
        expirationDatePicker.setValue(null);
        paymentDatePicker.setValue(null);
    }
}
