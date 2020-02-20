package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;
import model.DTO.Match;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.util.FKSvodnaUtilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class AddEditMatchesController {
    @FXML
    private Button addEditButton;
    @FXML
    private TextField timeField;
    @FXML
    private TextField opponentTeamField;
    @FXML
    private DatePicker dateofMatch;
    @FXML
    private TextField resultField;
    @FXML
    private Label resultLabel;

    private Match selectedMatch;
    private AlertController alertController;
    private Stage alertStage;

    @FXML
    private void initialize() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        timeField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format),format.parse("00:00")));
    }

    public void save(){
        if(checkFields()) {
            if ("Dodaj meč".equals(addEditButton.getText())) {
                Timestamp matchTime = Timestamp.valueOf(dateofMatch.getValue().atStartOfDay());
                String[] time = timeField.getText().split(":");
                if(time.length>1) {
                    matchTime.setHours(Integer.parseInt(time[0]));
                    matchTime.setMinutes(Integer.parseInt(time[1]));
                }
                Match match = new Match(0,matchTime,opponentTeamField.getText(),"");
                
                FKSvodnaUtilities.getDAOFactory().getMatchDAO().insert(match);
            } else {
                Timestamp matchTime = Timestamp.valueOf(dateofMatch.getValue().atStartOfDay());
                String[] time = timeField.getText().split(":");
                if(time.length>1) {
                    matchTime.setHours(Integer.parseInt(time[0]));
                    matchTime.setMinutes(Integer.parseInt(time[1]));
                }
                Match match = new Match(selectedMatch.getId(),matchTime,opponentTeamField.getText(),resultField.getText());
                FKSvodnaUtilities.getDAOFactory().getMatchDAO().update(match);
            }
        }else{
            try {
                displayAlert("Nisu unesena sva polja");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        quit();
    }

    public void quit(){
        ((Stage) addEditButton.getScene().getWindow()).close();
    }

    private boolean checkFields(){
        return !(timeField.getText().isEmpty()&&dateofMatch.getValue()==null&&opponentTeamField.getText().isEmpty());
    }

    private void displayAlert(String content) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root = loader.load();
        alertController = loader.getController();
        alertController.setText(content);
        alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setScene(new Scene(root));
        alertStage.show();
    }

    public void clearFields(){
        timeField.clear();
        opponentTeamField.clear();
        dateofMatch.setValue(null);
    }

    public Match getSelectedMatch() {
        return selectedMatch;
    }

    public void setSelectedMatch(Match selectedMatch) {
        this.selectedMatch = selectedMatch;
    }

    public void setTimeField(TextField timeField) {
        this.timeField = timeField;
    }

    public void setOpponentTeamField(TextField opponentTeamField) {
        this.opponentTeamField = opponentTeamField;
    }

    public void setDateofMatch(DatePicker dateofMatch) {
        this.dateofMatch = dateofMatch;
    }

    public TextField getTimeField() {
        return timeField;
    }

    public TextField getOpponentTeamField() {
        return opponentTeamField;
    }

    public DatePicker getDateofMatch() {
        return dateofMatch;
    }

    public Button getAddEditButton() {
        return addEditButton;
    }

    public void setAddEditButton(Button addEditButton) {
        this.addEditButton = addEditButton;
    }

    public TextField getResultField() {
        return resultField;
    }

    public Label getResultLabel() {
        return resultLabel;
    }
}
