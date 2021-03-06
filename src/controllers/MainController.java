package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class MainController {

    @FXML
    private Label usernameLabel;
    @FXML
    BorderPane borderPane;
    @FXML
    private AnchorPane matchesUI;
    @FXML
    private MatchController matchesUIController;
    @FXML
    private AnchorPane homeUI;
    @FXML
    private HomeController homeUIController;
    @FXML
    private AnchorPane teamsUI;
    @FXML
    private TeamController teamsUIController;
    @FXML
    private AnchorPane playersUI;
    @FXML
    private PlayerController playersUIController;
    @FXML
    private AnchorPane sponsorsUI;
    @FXML
    private SponsorController sponsorsUIController;
    @FXML
    private AnchorPane executivesUI;
    @FXML
    private ExecutivesController executivesController;
    @FXML
    private AnchorPane sponsorsSidebarUI;
    @FXML
    private SponsorSidebarController sponsorsSidebarUIController;
    @FXML
    private AnchorPane playersSidebarUI;
    @FXML
    private PlayerSidebarController playersSidebarUIController;

    private LogInController logInController;
    private Stage logInStage;

    public void logOut() {
        try {
            displayLogIn();
        } catch (Exception e) {
            ((Stage) usernameLabel.getScene().getWindow()).close();
            e.printStackTrace();
        }

    }

    @FXML
    void loadHomeUI(ActionEvent event){
        homeUI.setVisible(true);
        matchesUI.setVisible(false);
        teamsUI.setVisible(false);
        playersUI.setVisible(false);
        sponsorsUI.setVisible(false);
        sponsorsSidebarUI.setVisible(false);
        playersSidebarUI.setVisible(false);
        executivesUI.setVisible(false);
        borderPane.setRight(null);

    }
    @FXML
    void loadAdministrationUI(ActionEvent event) {
        executivesUI.setVisible(true);
        homeUI.setVisible(false);
        matchesUI.setVisible(false);
        teamsUI.setVisible(false);
        playersUI.setVisible(false);
        sponsorsUI.setVisible(false);
        sponsorsSidebarUI.setVisible(false);
        playersSidebarUI.setVisible(false);
        borderPane.setRight(null);
    }

    @FXML
    void loadMatchesUI(ActionEvent event) {
        matchesUI.setVisible(true);
        teamsUI.setVisible(false);
        homeUI.setVisible(false);
        playersUI.setVisible(false);
        sponsorsUI.setVisible(false);
        sponsorsSidebarUI.setVisible(false);
        executivesUI.setVisible(false);
        playersSidebarUI.setVisible(false);
        borderPane.setRight(null);
    }

    @FXML
    void loadPlayersUI(ActionEvent event) {
        playersUI.setVisible(true);
        teamsUI.setVisible(false);
        homeUI.setVisible(false);
        matchesUI.setVisible(false);
        sponsorsUI.setVisible(false);
        sponsorsSidebarUI.setVisible(false);
        executivesUI.setVisible(false);
        borderPane.setRight(playersSidebarUI);
        playersSidebarUI.setVisible(true);
    }

    @FXML
    void loadSponsorsUI(ActionEvent event) {
        sponsorsUI.setVisible(true);
        sponsorsSidebarUI.setVisible(true);
        matchesUI.setVisible(false);
        teamsUI.setVisible(false);
        homeUI.setVisible(false);
        playersUI.setVisible(false);
        executivesUI.setVisible(false);
        borderPane.setRight(sponsorsSidebarUI);
        playersSidebarUI.setVisible(false);

    }

    @FXML
    void loadTeamsUi(ActionEvent event) {
        teamsUI.setVisible(true);
        homeUI.setVisible(false);
        matchesUI.setVisible(false);
        playersUI.setVisible(false);
        sponsorsUI.setVisible(false);
        sponsorsSidebarUI.setVisible(false);
        executivesUI.setVisible(false);
        playersSidebarUI.setVisible(false);
        borderPane.setRight(null);
    }

    @FXML
    void initialize() {
        sponsorsUIController.setSponsorSidebarController(sponsorsSidebarUIController);
        playersUIController.setPlayerSidebarController(playersSidebarUIController);


    }

    public void setUser(String user) {
        usernameLabel.setText(user);
        borderPane.setRight(null);
    }

    private void displayLogIn() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/logIn.fxml"));
        Parent root = loader.load();
        logInController = loader.getController();
        logInStage = (Stage) this.borderPane.getScene().getWindow();
        logInStage.setScene(new Scene(root));
        logInStage.setTitle("Prijava");
        logInStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
    }
}
