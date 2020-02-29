package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    private Stage alertStage;
    private AlertController alertController;


    @FXML
    void showContactPersons(ActionEvent event){
        if(sponsor==null){
            alertController.setText("Nije izabran sponzor za pregled kontakt osoba.");
            alertStage.showAndWait();
            return;
        }
        try{
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/contact_persons.fxml"));
            Parent root=loader.load();
            ContactPersonController contactPersonController=loader.getController();
            contactPersonController.setSponsor(sponsor);
            Stage secondaryStage=new Stage();
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(root));
            secondaryStage.show();
        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void showPayments(ActionEvent event)  {
        if(sponsor==null){
            alertController.setText("Nije izabran sponzor za pregled uplata.");
            alertStage.showAndWait();
            return;
        }
        try{
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/payments.fxml"));
            Parent root=loader.load();
            PaymentController paymentController=loader.getController();
            paymentController.setSponsor(sponsor);
            Stage secondaryStage=new Stage();
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(root));
            secondaryStage.show();
        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void initialize() throws Exception {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();



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
    public void clearSponsor(){
        this.sponsor=null;
        nameLabel.setText("");
        adressLabel.setText("");
        emailLabel.setText("");
        jibLabel.setText("");
        phoneNumberLabel.setText("");
        typeLabel.setText("");
    }

}
