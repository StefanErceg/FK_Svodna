package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Sponsor;

public class SponsorSidebarController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label adressLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label jibLabel;

    Sponsor sponsor;

    @FXML
    void showContactPersons(ActionEvent event) throws Exception {
        Parent root=FXMLLoader.load(this.getClass().getResource("../view/contact_persons.fxml"));
        Stage secondaryStage=new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }

    @FXML
    void showPayments(ActionEvent event) throws Exception {
        Parent root=FXMLLoader.load(this.getClass().getResource("../view/payments.fxml"));
        Stage secondaryStage=new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }

    @FXML
    void initialize() {


    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
        nameLabel.setText(sponsor.getName());
        adressLabel.setText(sponsor.getAddress());
        emailLabel.setText(sponsor.getEmail());
        jibLabel.setText(sponsor.getJmbjib());
        phoneNumberLabel.setText(sponsor.getPhoneNumber());
        typeLabel.setText(sponsor.getKind());
    }
//    public Sponsor getSponsor(){
//
//    }
}
