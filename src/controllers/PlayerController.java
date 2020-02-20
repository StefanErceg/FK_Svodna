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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DTO.Person;
import model.DTO.PersonTeam;
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
    private AlertController alertController;
    private  DecisionController decisionController;
    private Stage alertStage;
    private Stage decisionStage;



    @FXML
    void addPlayer(ActionEvent event) {
        addEditPlayerController.clearFields();
        addEditPlayerController.getTeamSelectComboBox().setItems(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams()));
        addEditPlayerController.getAddPlayerButton().setText("Dodaj igrača");
        addEditPlayerStage.showAndWait();
        displayPlayers();
    }

    @FXML
    void editPlayer(ActionEvent event){
        addEditPlayerController.clearFields();
        Person person = playerTable.getSelectionModel().getSelectedItem();
        addEditPlayerController.getTeamSelectComboBox().setItems(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams()));
        List<PersonTeam> lista = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().personTeams().stream().filter(e->e.getPerson().getId()==person.getId()).collect(Collectors.toList());
        addEditPlayerController.setSelectedPlayerId(person.getId());
        if(!lista.isEmpty()) {
            addEditPlayerController.getDateFrom().setValue(lista.get(0).getDateFrom().toLocalDateTime().toLocalDate());
            if(lista.get(0).getDateTo()!=null)
                addEditPlayerController.getDateTo().setValue(lista.get(0).getDateTo().toLocalDateTime().toLocalDate());
            addEditPlayerController.getTeamSelectComboBox().getSelectionModel().select(lista.get(0).getTeam());
        }
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
    void deletePlayer(ActionEvent event){
        if(playerTable.getSelectionModel().isEmpty()) {
            alertController.setText("Nije izabaran sponzor za brisanje.");
            alertStage.showAndWait();
            return;
        }
        Person selection=playerTable.getSelectionModel().getSelectedItem();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da zelite obrisati igraca?");
        decisionStage.showAndWait();
        if( selection!= null && decisionController.returnResult()){
            if(!FKSvodnaUtilities.getDAOFactory().getPersonDAO().delete(selection)){
                alertController.setText("Desila se greska pri brisanju, brisanje nije izvrseno.");
                alertStage.showAndWait();
            }
            playersList=new FilteredList<Person>(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getPersonDAO().persons()));
            playerTable.setItems(playersList);
        }
        playerTable.getSelectionModel().clearSelection();
        playerSidebarController.clearPlayer();
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
        loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        VBox alert=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(alert));
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();
        decisionController = loader.getController();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da želite da obrišete igrača?");
        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));


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
