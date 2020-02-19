package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.sql.Timestamp;
import java.time.Instant;

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
    private ComboBox<Team> teamSelectComboBox;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private DatePicker dateFrom;

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
                FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().insert(new PersonTeam(player,teamSelectComboBox.getSelectionModel().getSelectedItem(),Timestamp.valueOf(dateFrom.getValue().atStartOfDay()),Timestamp.from(Instant.now()),"",positionTextField.getText()));//todo:check date picker to timestamp
            } else {
                Person player = new Person(selectedPlayerId,nameTextField.getText(),lastNameTextField.getText(),phoneNumberTextField.getText(),
                        jmbgTextField.getText(),emailTextField.getText(),adressTextField.getText(),licenceNumberTextField.getText());
                FKSvodnaUtilities.getDAOFactory().getPersonDAO().update(player);
                FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().update(new PersonTeam(player,teamSelectComboBox.getSelectionModel().getSelectedItem(),Timestamp.valueOf(dateFrom.getValue().toString()),Timestamp.from(Instant.now()),"",positionTextField.getText()));
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
        ((Stage) addPlayerButton.getScene().getWindow()).close();
    }

    private boolean checkFields(){
        return !(nameTextField.getText().isEmpty()&&lastNameTextField.getText().isEmpty()&&jmbgTextField.getText().isEmpty()&&positionTextField.getText().isEmpty()&&
                licenceNumberTextField.getText().isEmpty()&&adressTextField.getText().isEmpty()&&
                emailTextField.getText().isEmpty()&&phoneNumberTextField.getText().isEmpty()&&teamSelectComboBox.getSelectionModel().getSelectedItem()!=null&&dateFrom.getValue()!=null);
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
}
