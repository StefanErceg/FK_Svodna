package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Sponsor;
import model.util.FKSvodnaUtilities;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSidebarController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label licenceNumberLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label jmbgLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label teamLabel;

    private Person person;
    private AlertController alertController;
    private Stage alertStage;
    private FinesController finesController;
    private Stage finesStage;
    private MedicalExaminationController medicalExaminationController;
    private Stage medicalExaminationStage;
    private Stage equipmentStage;
    private EquipmentController equipmentController;

    public void initialize() throws IOException {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        loader = new FXMLLoader(this.getClass().getResource("../view/fines.fxml"));
        root = loader.load();
        finesStage = new Stage();
        finesStage.setScene(new Scene(root));
        finesController = loader.getController();
        loader = new FXMLLoader(this.getClass().getResource("../view/medical_examinations.fxml"));
        root = loader.load();
        medicalExaminationStage = new Stage();
        medicalExaminationStage.setScene(new Scene(root));
        medicalExaminationController = loader.getController();
        loader=new FXMLLoader(this.getClass().getResource("../view/equipment.fxml"));
        root=loader.load();
        equipmentStage=new Stage();
        equipmentStage.setScene(new Scene(root));
        equipmentController=loader.getController();
    }

    public void setPlayer(Person person) {
        clearPlayer();
        this.person = person;
        nameLabel.setText(person.getName());
        lastNameLabel.setText(person.getSurname());
        licenceNumberLabel.setText(person.getLicenceNumber());
        addressLabel.setText(person.getAddress());
        emailLabel.setText(person.getEmail());
        jmbgLabel.setText(person.getJmb());
        phoneNumberLabel.setText(person.getPhoneNumber());
        positionLabel.setText(FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getPlayerPosition());
        teamLabel.setText(FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getTeam().getName());
    }

    public void clearPlayer(){
        this.person=null;
        nameLabel.setText("");
        lastNameLabel.setText("");
        positionLabel.setText("");
        licenceNumberLabel.setText("");
        addressLabel.setText("");
        emailLabel.setText("");
        jmbgLabel.setText("");
        phoneNumberLabel.setText("");
        teamLabel.setText("");
    }

    @FXML
    public void checkPunishments(ActionEvent event){
        if(person!=null) {
            finesController.setPlayer(person);
            finesStage.showAndWait();
        }
        else{
            alertController.setText("Igrač nije izabran.");
            alertStage.show();
        }
    }
    @FXML
    void checkEquipment(ActionEvent event){
        if(person!=null){
            equipmentController.setPlayer(person);
            equipmentStage.showAndWait();
        }else {
            alertController.setText("Igrač nije izabran.");
            alertStage.showAndWait();
        }
    }
    @FXML
    public void addMedicalExamination(ActionEvent event){
        if(person!=null) {
            medicalExaminationController.clearFields();
            medicalExaminationController.setPlayer(person);
            medicalExaminationStage.showAndWait();
        }
        else{
            alertController.setText("Igrač nije izabran.");
            alertStage.show();
        }
    }
}
