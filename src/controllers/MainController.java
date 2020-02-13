package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

public class MainController {

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
    void loadHomeUI(ActionEvent event){
        homeUI.setVisible(true);
        matchesUI.setVisible(false);
        teamsUI.setVisible(false);
        playersUI.setVisible(false);
        sponsorsUI.setVisible(false);
        sponsorsSidebarUI.setVisible(false);


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
    }

    @FXML
    void initialize() {
        sponsorsUIController.setSponsorSidebarController(sponsorsSidebarUIController);
    }
}
