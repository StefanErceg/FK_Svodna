package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DTO.Match;
import model.DTO.Person;
import model.util.FKSvodnaUtilities;

public class MatchController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField result;

    @FXML
    private TableView<Match> tableResult;

    private Stage alertStage;
    private AlertController alertController;
    private List<Match> matchesList;
    private Match selectedMatch;
    private DecisionController decisionController;
    private Stage decisionStage;
    private AddEditMatchesController addEditMatchesController;
    private Stage addEditMatchesStage;

    @FXML
    void addMatch(ActionEvent event) {
        addEditMatchesController.clearFields();
        addEditMatchesController.getAddEditButton().setText("Dodaj meč");
        addEditMatchesController.getResultField().setVisible(false);
        addEditMatchesController.getResultLabel().setVisible(false);
        addEditMatchesStage.showAndWait();
        displayMatches();
        clearFields();
    }

    @FXML
    void addResult(ActionEvent event) {
        selectedMatch = tableResult.getSelectionModel().getSelectedItem();
        if(selectedMatch!=null && !result.getText().isEmpty()){
            selectedMatch.setResult(result.getText());
            FKSvodnaUtilities.getDAOFactory().getMatchDAO().update(selectedMatch);
        }
        else{
            alertController.setText("Nije odabrana utakmica");
            alertStage.show();
        }
        displayMatches();
        clearFields();
    }

    @FXML
    void updateResult(ActionEvent event) {
        selectedMatch = tableResult.getSelectionModel().getSelectedItem();
        if(selectedMatch!=null){
            addEditMatchesController.setSelectedMatch(selectedMatch);
            addEditMatchesController.getOpponentTeamField().setText(selectedMatch.getOpposingTeam());
            addEditMatchesController.getTimeField().setText(selectedMatch.getDate().getHours()+":"+selectedMatch.getDate().getMinutes());
            addEditMatchesController.getDateofMatch().setValue(selectedMatch.getDate().toLocalDateTime().toLocalDate());
            addEditMatchesController.getAddEditButton().setText("Izmjeni meč");
            addEditMatchesController.getResultField().setVisible(true);
            addEditMatchesController.getResultLabel().setVisible(true);
            addEditMatchesStage.showAndWait();
        }
        else{
            alertController.setText("Utakmica nije odabrana");
            alertStage.show();
        }
        displayMatches();
        clearFields();
    }

    @FXML
    void initialize() throws IOException {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_matches.fxml"));
        root = loader.load();
        addEditMatchesController = loader.getController();
        addEditMatchesStage = new Stage();
        addEditMatchesStage.setScene(new Scene(root));

        tableResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        tableResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("opposingTeam"));
        tableResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("result"));

        displayMatches();
    }

    private void clearFields(){
        result.clear();
    }

    private void displayMatches(){
        try {
            tableResult.getItems().clear();
            tableResult.refresh();
            matchesList = new FilteredList<>(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getMatchDAO().matches()));
            tableResult.getItems().addAll(matchesList);
            tableResult.getSelectionModel().clearSelection();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
