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
import javafx.scene.layout.VBox;
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

    Sponsor sponsor;

    @FXML
    void addSponsor(ActionEvent event) {
        if(sponsor.getName()==null){
            sponsor=new Sponsor(nameField.getText(),addresField.getText(),
                emailField.getText(),phonenumberField.getText(),typeTextField.getText(),jibField.getText());
        if(!DAOFactory.getDAOFactory().getSponsorDAO().insert(sponsor)){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
            VBox alert = loader.load();
            AlertController alertController = loader.getController();
            alertController.setText("Desila se greska pri dodavanju, dodavanje nije izvrseno.");
            Stage alertStage = new Stage();
            alertStage.setScene(new Scene(alert));
            alertStage.show();
        }catch (IOException e){e.printStackTrace();}
        }
    }else{
            sponsor.setName(nameField.getText());
            sponsor.setAddress(addresField.getText());
            sponsor.setEmail(emailField.getText());
            sponsor.setJmbjib(jibField.getText());
            sponsor.setPhoneNumber(phonenumberField.getText());
            sponsor.setKind(typeTextField.getText());
            if(!DAOFactory.getDAOFactory().getSponsorDAO().update(sponsor)) {
                try {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
                    VBox alert = loader.load();
                    AlertController alertController = loader.getController();
                    alertController.setText("Desila se greska pri promjeni sponzora, promjene nisu izvrsene");
                    Stage alertStage = new Stage();
                    alertStage.setScene(new Scene(alert));
                    alertStage.show();
                }catch (IOException e){e.printStackTrace();}
            }

        }
        ((Stage)jibField.getScene().getWindow()).hide();
        editSponsorButton.setText("Dodaj");
    }

    @FXML
    void close(ActionEvent event) {
        ((Stage)jibField.getScene().getWindow()).hide();
        editSponsorButton.setText("Dodaj");
    }

    public void setSponsor(Sponsor sponsor){
        this.sponsor=sponsor;
        if(sponsor.getName()!=null) {
            nameField.setText(sponsor.getName());
            addresField.setText(sponsor.getAddress());
            emailField.setText(sponsor.getEmail());
            jibField.setText(sponsor.getJmbjib());
            editSponsorButton.setText("Izmjeni");
        }
    }


    @FXML
    void initialize() {

    }

}
