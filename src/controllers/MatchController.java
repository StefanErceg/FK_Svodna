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
    private DatePicker dateofMatch;

    @FXML
    private TextField opponentName;

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

    @FXML
    void addMatch(ActionEvent event) {
        if(checkFields()){
            Match match = new Match(0, Timestamp.valueOf(dateofMatch.getValue().atStartOfDay()),opponentName.getText(),"");
            FKSvodnaUtilities.getDAOFactory().getMatchDAO().insert(match);
        }
        else{
            alertController.setText("Nisu unesena polja!");
            alertStage.show();
        }
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
        displayMatches();
        clearFields();
    }

    @FXML
    void deleteResult(ActionEvent event) {
//        if(tableResult.getSelectionModel().isEmpty()) {
//            alertController.setText("Nije izabaran sponzor za brisanje.");
//            alertStage.showAndWait();
//            return;
//        }
//        Match selection=tableResult.getSelectionModel().getSelectedItem();
//        decisionController.getDecisionLabel().setText("Da li ste sigurni da zelite obrisati igraca?");
//        decisionStage.showAndWait();
//        if( selection!= null && decisionController.returnResult()){
//            if(!FKSvodnaUtilities.getDAOFactory().getMatchDAO().delete(selection)){
//                alertController.setText("Desila se greska pri brisanju, brisanje nije izvrseno.");
//                alertStage.showAndWait();
//            }
//        }
//        tableResult.getSelectionModel().clearSelection();
//        displayMatches();
//        clearFields();
    }

    @FXML
    void updateResult(ActionEvent event) {
        selectedMatch = tableResult.getSelectionModel().getSelectedItem();
        if(checkFields() && selectedMatch!=null && !result.getText().isEmpty()){
            selectedMatch.setResult(result.getText());
            selectedMatch.setDate(Timestamp.valueOf(dateofMatch.getValue().atStartOfDay()));
            selectedMatch.setOpposingTeam(opponentName.getText());
            FKSvodnaUtilities.getDAOFactory().getMatchDAO().update(selectedMatch);
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

        tableResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        tableResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("opposingTeam"));
        tableResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("result"));

        displayMatches();
    }

    private boolean checkFields(){
        return dateofMatch.getValue()!=null && !opponentName.getText().isEmpty();
    }

    private void clearFields(){
        opponentName.clear();
        result.clear();
        dateofMatch.setValue(null);
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
