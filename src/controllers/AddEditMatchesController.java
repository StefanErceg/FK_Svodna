package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;
import model.DTO.*;
import model.util.FKSvodnaUtilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    @FXML
    private RadioButton homeButton;
    @FXML
    private RadioButton awayButton;
    @FXML
    private ToggleGroup whereIsPlayed;
    @FXML
    private ComboBox<Team> teamsComboBox;
    @FXML
    private Label teamLabel;

    private Match selectedMatch;
    private AlertController alertController;
    private Stage alertStage;
    private Team selectedTeam;

    @FXML
    private void initialize() throws ParseException {
        List<Team> listOfTeams = FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams();
        teamsComboBox.getItems().addAll(listOfTeams);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        timeField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format),format.parse("00:00")));
    }

    public void save(){
        if(checkFields()) {
            if ("Dodaj utakmicu".equals(addEditButton.getText())) {
                Timestamp matchTime = Timestamp.valueOf(dateofMatch.getValue().atStartOfDay());
                String[] time = timeField.getText().split(":");
                if(time.length>1) {
                    matchTime.setHours(Integer.parseInt(time[0]));
                    matchTime.setMinutes(Integer.parseInt(time[1]));
                }
                Match match;
                if(awayButton.isSelected()) match = new Match(0,matchTime,opponentTeamField.getText(),"", IsAway.Jeste);
                else match = new Match(0,matchTime,opponentTeamField.getText(),"", IsAway.Nije);
                FKSvodnaUtilities.getDAOFactory().getMatchDAO().insert(match);
                match = FKSvodnaUtilities.getDAOFactory().getMatchDAO().getLastMatch();
                TeamMatch teamMatch = new TeamMatch(selectedTeam,match);
                System.out.println(FKSvodnaUtilities.getDAOFactory().getPersonTeamMatchDAO().insert(teamMatch));
                try {
                    List<String> obligationsFromFile = Files.readAllLines(Path.of("Zaduženja.txt"));
                    for(String obligation : obligationsFromFile){
                        FKSvodnaUtilities.getDAOFactory().getObligationDAO().insert(new Obligation(0, obligation, false, match));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Timestamp matchTime = Timestamp.valueOf(dateofMatch.getValue().atStartOfDay());
                String[] time = timeField.getText().split(":");
                if(time.length>1) {
                    matchTime.setHours(Integer.parseInt(time[0]));
                    matchTime.setMinutes(Integer.parseInt(time[1]));
                }
                Match match = new Match(selectedMatch.getId(),matchTime,opponentTeamField.getText(),resultField.getText(), null);
                if(awayButton.isSelected()) match.setIsAway(IsAway.Jeste);
                else match.setIsAway(IsAway.Nije);
                FKSvodnaUtilities.getDAOFactory().getMatchDAO().update(match);
            }
        }else{
            try {
                displayAlert("Nisu unesena sva polja!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        quit();
    }

    @FXML
    void saveByEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)) save();
    }

    public void quit(){
        ((Stage) addEditButton.getScene().getWindow()).close();
    }

    private boolean checkFields(){
        return !(timeField.getText().isEmpty()&&dateofMatch.getValue()==null&&opponentTeamField.getText().isEmpty())&&(homeButton.isSelected() || awayButton.isSelected())&&selectedTeam!=null;
    }

    public void selectTeam() {
        selectedTeam = teamsComboBox.getSelectionModel().getSelectedItem();
    }

    private void displayAlert(String content) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root = loader.load();
        alertController = loader.getController();
        alertController.setText(content);
        alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setScene(new Scene(root));
        alertStage.setTitle("Upozorenje");
        alertStage.show();
    }

    public void clearFields(){
        timeField.clear();
        opponentTeamField.clear();
        dateofMatch.setValue(null);
        teamsComboBox.getSelectionModel().clearSelection();
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

    public RadioButton getHomeButton() {
        return homeButton;
    }

    public void setHomeButton(RadioButton homeButton) {
        this.homeButton = homeButton;
    }

    public RadioButton getAwayButton() {
        return awayButton;
    }

    public void setAwayButton(RadioButton awayButton) {
        this.awayButton = awayButton;
    }

    public ComboBox<Team> getTeamsComboBox() {
        return teamsComboBox;
    }

    public void setTeamsComboBox(ComboBox<Team> teamsComboBox) {
        this.teamsComboBox = teamsComboBox;
    }

    public Label getTeamLabel() {
        return teamLabel;
    }

    public void setTeamLabel(Label teamLabel) {
        this.teamLabel = teamLabel;
    }

    public Team getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }
}
