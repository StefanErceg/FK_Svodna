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
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;

public class AddEditPlayerController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField positionTextField;
    @FXML
    private TextField licenceNumberTextField;
    @FXML
    private TextField jmbgTextField;
    @FXML
    private TextField adressTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField jerseyNumberTextField;
    @FXML
    private ComboBox<Team> teamSelectComboBox;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private Label teamLabel;

    private int selectedPlayerId;
    private Stage alertStage;
    private AlertController alertController;

    @FXML
    private void initialize(){

    }

    public void save(){
        if(checkFields()) {
            if ("Dodaj igrača".equals(addPlayerButton.getText())) {
                Person player = new Person(0,nameTextField.getText(),lastNameTextField.getText(),phoneNumberTextField.getText(),
                        jmbgTextField.getText(),emailTextField.getText(),adressTextField.getText(),licenceNumberTextField.getText());
                FKSvodnaUtilities.getDAOFactory().getPersonDAO().insert(player);
                player = FKSvodnaUtilities.getDAOFactory().getPersonDAO().getLastPerson();
                FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().insert(new PersonTeam(player,teamSelectComboBox.getSelectionModel().getSelectedItem(),Timestamp.valueOf(dateFrom.getValue().atStartOfDay()),null,"igrac",positionTextField.getText(), Integer.parseInt(jerseyNumberTextField.getText())));
            } else {
                Person player = new Person(selectedPlayerId,nameTextField.getText(),lastNameTextField.getText(),phoneNumberTextField.getText(),
                        jmbgTextField.getText(),emailTextField.getText(),adressTextField.getText(),licenceNumberTextField.getText());
                FKSvodnaUtilities.getDAOFactory().getPersonDAO().update(player);
                PersonTeam personTeam = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(player);
                personTeam.setPlayerPosition(positionTextField.getText());
                personTeam.setJerseyNumber(Integer.parseInt(jerseyNumberTextField.getText()));
                FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().update(personTeam);
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

    @FXML
    void saveByEnter(KeyEvent event){
        if (event.getCode().equals(KeyCode.ENTER)) save();
    }

    public void quit(){
        ((Stage) addPlayerButton.getScene().getWindow()).close();
    }

    private boolean checkFields(){
        return !nameTextField.getText().isEmpty()&&!lastNameTextField.getText().isEmpty()&&!jmbgTextField.getText().isEmpty()&&!positionTextField.getText().isEmpty()&&
                !licenceNumberTextField.getText().isEmpty()&&!adressTextField.getText().isEmpty()&&
                !emailTextField.getText().isEmpty()&&!phoneNumberTextField.getText().isEmpty()&&!jerseyNumberTextField.getText().isEmpty()&&isInt(jerseyNumberTextField.getText())
                &&teamSelectComboBox.getSelectionModel().getSelectedItem()!=null&&dateFrom.getValue()!=null;
    }

    public int getSelectedPlayerId() {
        return selectedPlayerId;
    }

    public void setSelectedPlayerId(int selectedPlayerId) {
        this.selectedPlayerId = selectedPlayerId;
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
        nameTextField.clear();
        lastNameTextField.clear();
        phoneNumberTextField.clear();
        jmbgTextField.clear();
        licenceNumberTextField.clear();
        positionTextField.clear();
        adressTextField.clear();
        emailTextField.clear();
        dateFrom.setValue(null);
        teamSelectComboBox.getSelectionModel().clearSelection();
    }

    private boolean isInt(String string){
        if(string.matches("([0-9]*)")) return true;
        return false;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getLastNameTextField() {
        return lastNameTextField;
    }

    public TextField getPositionTextField() {
        return positionTextField;
    }

    public TextField getLicenceNumberTextField() {
        return licenceNumberTextField;
    }

    public TextField getJmbgTextField() {
        return jmbgTextField;
    }

    public TextField getAdressTextField() {
        return adressTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public TextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }

    public ComboBox<Team> getTeamSelectComboBox() {
        return teamSelectComboBox;
    }

    public Button getAddPlayerButton() {
        return addPlayerButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public DatePicker getDateFrom() {
        return dateFrom;
    }

    public Label getTeamLabel() {
        return teamLabel;
    }

    public TextField getJerseyNumberTextField() {
        return jerseyNumberTextField;
    }

    public void setJerseyNumberTextField(TextField jerseyNumberTextField) {
        this.jerseyNumberTextField = jerseyNumberTextField;
    }
}
