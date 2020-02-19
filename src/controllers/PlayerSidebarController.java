package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.Sponsor;
import model.util.FKSvodnaUtilities;

import java.io.IOException;

public class PlayerSidebarController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label jerseyNumberLabel;
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

    private Person person;
    private AlertController alertController;
    private Stage alertStage;

    public void initialize() throws IOException {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
    }

    public void setPlayer(Person person) {
        this.person = person;
        nameLabel.setText(person.getName());
        lastNameLabel.setText(person.getSurname());
        positionLabel.setText(FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().personTeams().get(person.getId()).getPlayerPosition());
        jerseyNumberLabel.setText("5");//todo:do this
        licenceNumberLabel.setText(person.getLicenceNumber());
        addressLabel.setText(person.getAddress());
        emailLabel.setText(person.getEmail());
        jmbgLabel.setText(person.getJmb());
        phoneNumberLabel.setText(person.getPhoneNumber());
    }
    public void clearPlayer(){
        this.person=null;
        nameLabel.setText("");
        lastNameLabel.setText("");
        positionLabel.setText("");
        jerseyNumberLabel.setText("");
        licenceNumberLabel.setText("");
        addressLabel.setText("");
        emailLabel.setText("");
        jmbgLabel.setText("");
        phoneNumberLabel.setText("");
    }

}
