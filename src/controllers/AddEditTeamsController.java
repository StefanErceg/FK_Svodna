package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.io.IOException;
import java.util.List;

public class AddEditTeamsController {
    @FXML
    public TableView<Team> teamsTableView;
    @FXML
    public TextField teamNameField;

    private ObservableList<Team> teamsList;
    private AlertController alertController;
    private Stage alertStage;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root = loader.load();
        alertController = loader.getController();
        alertStage = new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle("Upozorenje");

        teamsTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));

        displayTeams();
    }

    @FXML
    public void deleteTeam(ActionEvent actionEvent) {
        Team team = teamsTableView.getSelectionModel().getSelectedItem();
        if(team!=null){
            FKSvodnaUtilities.getDAOFactory().getTeamDAO().delete(team);
            displayTeams();
        }else{
            alertController.setText("Nije odabran tim!");
            alertStage.showAndWait();
        }
    }

    @FXML
    public void addTeam(ActionEvent actionEvent){
        if(!teamNameField.getText().isEmpty()){
            List<Team> teams = FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams();
            for(Team team:teams){
                if(teamNameField.getText().equals(team.getName())){
                    alertController.setText("Tim već postoji!");
                    alertStage.showAndWait();
                    teamNameField.setText("");
                    break;
                }
            }
            Team team = new Team(0,teamNameField.getText());
            FKSvodnaUtilities.getDAOFactory().getTeamDAO().insert(team);
            displayTeams();
            teamNameField.setText("");
        }
        else{
            alertController.setText("Nije unešen naziv tima!");
            alertStage.showAndWait();
        }
    }

    private void displayTeams(){
        teamsList = FXCollections.observableArrayList();
        teamsList.addAll(FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams());
        teamsTableView.setItems(teamsList);
        teamsTableView.refresh();
        teamsTableView.getSelectionModel().clearSelection();
    }
}
