package controllers;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.util.List;

public class TeamController {


    @FXML
    private ComboBox<Team> teamsComboBox;
    @FXML
    private VBox playersVBox;
    @FXML
    private TableView<PersonTeam> staffTableView;
    @FXML
    private TableColumn<PersonTeam, String> nameColumn;
    @FXML
    private TableColumn<PersonTeam, String> surnameColumn;

    private List<Team> listOfTeams;
    private List<PersonTeam> listOfStaff;
    private List<PersonTeam> listOfPlayers;
    private Team selectedTeam;
    private AlertController alertController;
    private Stage alertStage;
    private Label playerLabel;

    @FXML
    void initialize() {
        teams();

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getSurname()));
        staffTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("role"));

    }

    public void addTeam() {

    }

    public void editTeam() {

    }

    public void deleteTeam() {

    }

    public void selectTeam() {
        selectedTeam = teamsComboBox.getSelectionModel().getSelectedItem();
        if(selectedTeam != null) {
            displayPlayers();
            displayStaff();
        }
    }

    private void teams() {
        listOfTeams = FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams();
        teamsComboBox.getItems().addAll(listOfTeams);
    }

    private void displayPlayers() {
        playersVBox.getChildren().clear();
        listOfPlayers = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getPlayersForTeam(selectedTeam.getId());
        for(PersonTeam personTeam : listOfPlayers) {
            playerLabel = new Label(personTeam.getPerson().getName() + " " + personTeam.getPerson().getSurname());
            playerLabel.setTextFill(Paint.valueOf("WHITE"));
            playerLabel.setFont(Font.font(16));
            playersVBox.getChildren().add(playerLabel);
        }
    }

    private void displayAlert(String content) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root = loader.load();
        alertController = loader.getController();
        alertController.setText(content);
        alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setScene(new Scene(root));
        alertStage.showAndWait();
    }

    private void displayStaff() {
        staffTableView.getItems().clear();
        staffTableView.refresh();
        listOfStaff = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getStaffForTeam(selectedTeam.getId());
        staffTableView.getItems().addAll(listOfStaff);
    }

}
