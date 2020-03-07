package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DTO.Sponsor;

import java.io.IOException;

public class AddEditSponsorController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField addresField;
    @FXML
    private TextField phonenumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField jibField;
    @FXML
    private TextField typeTextField;
    @FXML
    private Button editSponsorButton;
    AlertController alertController;
    Stage alertStage;
    Sponsor sponsor;

    @FXML
    void addSponsor() {
        if(nameField.getText().equals("") || (emailField.getText().equals("") && phonenumberField.getText().equals("") ) ){
            alertController.setText("Nisu uneseni svi potrebni podaci, sponzor mora imati ime i email ili broj telefona.");
            alertStage.showAndWait();
            return;
        }

        if(sponsor.getName()==null){
            sponsor=new Sponsor(nameField.getText().trim(),addresField.getText().trim(),
                emailField.getText().trim(),phonenumberField.getText().trim(),typeTextField.getText().trim(),jibField.getText().trim());
        if(!DAOFactory.getDAOFactory().getSponsorDAO().insert(sponsor)){
            alertController.setText("Desila se greska pri upisu, promjena nije upisana.");
            alertStage.showAndWait();

        }
    }else{
            sponsor.setName(nameField.getText().trim());
            sponsor.setAddress(addresField.getText().trim());
            sponsor.setEmail(emailField.getText().trim());
            sponsor.setJmbjib(jibField.getText().trim());
            sponsor.setPhoneNumber(phonenumberField.getText().trim());
            sponsor.setKind(typeTextField.getText().trim());
            if(!DAOFactory.getDAOFactory().getSponsorDAO().update(sponsor)) {
                alertController.setText("Desila se greska pri uspisu, promjena nije upisana.");
                alertStage.showAndWait();
            }
        }
        ((Stage)jibField.getScene().getWindow()).hide();
        editSponsorButton.setText("Dodaj");
    }

    @FXML
    void saveByEnter(KeyEvent event){
        if (event.getCode().equals(KeyCode.ENTER)) addSponsor();
    }

    @FXML
    void close(ActionEvent event) {
        ((Stage)jibField.getScene().getWindow()).hide();
        editSponsorButton.setText("Dodaj");
    }

    public void setSponsor(Sponsor sponsor){
        this.sponsor=sponsor;
        nameField.setText(sponsor.getName()!=null?sponsor.getName():"");
        addresField.setText(sponsor.getAddress()!=null?sponsor.getAddress():"");
        typeTextField.setText(sponsor.getKind()!=null?sponsor.getKind():"");
        phonenumberField.setText(sponsor.getPhoneNumber()!=null?sponsor.getPhoneNumber():"");
        emailField.setText(sponsor.getEmail()!=null?sponsor.getEmail():"");
        jibField.setText(sponsor.getJmbjib()!=null?sponsor.getJmbjib():"");
        if(sponsor.getName()!=null)
            editSponsorButton.setText("Izmijeni");
        else
            editSponsorButton.setText("Dodaj");
    }

    @FXML
    void initialize() throws Exception {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
            VBox alert = loader.load();
            alertController = loader.getController();
            alertStage = new Stage();
            alertStage.setScene(new Scene(alert));
            alertStage.initModality(Modality.APPLICATION_MODAL);
    }

}
