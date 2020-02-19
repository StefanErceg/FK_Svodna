package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
import java.util.stream.Collectors;

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
    void editPlayer(ActionEvent event){

        var person = playerTable.getSelectionModel().getSelectedItem();
        addEditPlayerController.getTeamSelectComboBox().setItems(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams()));
        var lista = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().personTeams().stream().filter(e->e.getPerson().getId()==person.getId()).collect(Collectors.toList());

        if(!lista.isEmpty())
            addEditPlayerController.getTeamSelectComboBox().getSelectionModel().select(lista.get(0).getTeam());
        addEditPlayerController.getAddPlayerButton().setText("Izmjeni igrača");
        addEditPlayerController.getNameTextField().setText(person.getName());
        addEditPlayerController.getLastNameTextField().setText(person.getSurname());
        addEditPlayerController.getPositionTextField().setText("");//TODO: UPDATE !
        addEditPlayerController.getLicenceNumberTextField().setText(person.getLicenceNumber());
        addEditPlayerController.getJmbgTextField().setText(person.getJmb());
        addEditPlayerController.getAdressTextField().setText(person.getAddress());
        addEditPlayerController.getEmailTextField().setText(person.getEmail());
        addEditPlayerController.getPhoneNumberTextField().setText(person.getPhoneNumber());

        addEditPlayerStage.showAndWait();
        displayPlayers();
    }

    @FXML
    void findPlayer(KeyEvent event) {
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
        playerTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


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
    private void selectionOfPlayer(MouseEvent event){
        if(playerTable.getSelectionModel().getSelectedItem()!=null){
            playerSidebarController.setPlayer(playerTable.getSelectionModel().getSelectedItem());
        }
    }
    public void setPlayerSidebarController(PlayerSidebarController playerSidebarController){
        this.playerSidebarController = playerSidebarController;
        displayPlayers();
    }
}
