package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DTO.Person;
import model.DTO.Sponsor;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    @FXML
    private TableView<Person> playerTable;

    @FXML
    private TextField findPlayerField;

    private AddEditPlayerController addEditPlayerController;
    private PlayerSidebarController playerSidebarController;
    private Stage addEditPlayerStage;
    private ObservableList<Person> playersList;


    @FXML
    void addPlayer(ActionEvent event) {
        addEditPlayerController.getTeamSelectComboBox().setItems(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams()));
        addEditPlayerController.getAddPlayerButton().setText("Dodaj igrača");
        addEditPlayerController.getNameTextField().clear();
        addEditPlayerController.getLastNameTextField().clear();
        addEditPlayerController.getPositionTextField().clear();
        addEditPlayerController.getLicenceNumberTextField().clear();
        addEditPlayerController.getJmbgTextField().clear();
        addEditPlayerController.getAdressTextField().clear();
        addEditPlayerController.getEmailTextField().clear();
        addEditPlayerController.getPhoneNumberTextField().clear();
        addEditPlayerStage.showAndWait();
        displayPlayers();
    }

    @FXML
    void findPlayer(ActionEvent event) {
        ObservableList<Person> filtered=playersList.filtered( e->e.getName().toLowerCase().matches(".*"+findPlayerField.getText().toLowerCase()+".*"));
        playerTable.setItems(filtered);
        playerSidebarController.setPlayer(filtered.get(0));
    }

    @FXML
    void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_player.fxml"));
        Parent root = loader.load();
        addEditPlayerController = loader.getController();
        addEditPlayerStage = new Stage();
        addEditPlayerStage.initModality(Modality.APPLICATION_MODAL);
        addEditPlayerStage.setScene(new Scene(root));

        playerTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        playerTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        playerTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("licenceNumber"));
        playerTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        displayPlayers();
    }

    private void displayPlayers(){
        try {
            playerTable.getItems().clear();
            playerTable.refresh();
            playersList = new FilteredList<>(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getPersonDAO().persons()));
            playerTable.getItems().addAll(playersList);
            playerTable.getSelectionModel().clearSelection();
            playerSidebarController.clearPlayer();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void selectionOfPlayer(){
        if(playerTable.getSelectionModel().getSelectedItem()!=null){
            playerSidebarController.setPlayer(playerTable.getSelectionModel().getSelectedItem());
        }
    }
    public void setPlayerSidebarController(PlayerSidebarController playerSidebarController){
        this.playerSidebarController = playerSidebarController;
    }
}
