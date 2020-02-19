package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.sql.Timestamp;
import java.util.Calendar;

public class AddEditPersonController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField licenceNumberTextField;
    @FXML
    private TextField jmbTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Button addEditButton;

    private AlertController alertController;
    private Stage alertStage;
    private Person personToAdd;
    private Team selectedTeam;
    private PersonTeam selectedPersonTeam;
    private boolean finished;
    private Person person;

    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll("igrac", "trener", "doktor");
    }

    public void save() {
        if(!checkRole()) {
            finished = false;
            try {
                displayAlert("Nije izabrana uloga!");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        if(!checkJmb()) {
            finished = false;
            try {
                displayAlert("Unos za polje Jmbg nije odgovarajući!");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        if(checkFields()) {
            if("Dodaj osobu".equals(addEditButton.getText())) {
                personToAdd = new Person(0, nameTextField.getText(), surnameTextField.getText(),
                        phoneNumberTextField.getText(), jmbTextField.getText(),emailTextField.getText(), addressTextField.getText(),
                        licenceNumberTextField.getText());
                FKSvodnaUtilities.getDAOFactory().getPersonDAO().insert(personToAdd);
                person = FKSvodnaUtilities.getDAOFactory().getPersonDAO().getLastPerson();
                FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().insert(new PersonTeam(person, selectedTeam, new Timestamp(Calendar.getInstance().getTime().getTime()),
                        null, roleComboBox.getSelectionModel().getSelectedItem(), null));
                finished = true;
            }
            else {
                selectedPersonTeam.getPerson().setName(nameTextField.getText());
                selectedPersonTeam.getPerson().setSurname(surnameTextField.getText());
                selectedPersonTeam.getPerson().setAddress(addressTextField.getText());
                selectedPersonTeam.getPerson().setEmail(emailTextField.getText());
                selectedPersonTeam.getPerson().setLicenceNumber(licenceNumberTextField.getText());
                selectedPersonTeam.getPerson().setPhoneNumber(phoneNumberTextField.getText());
                selectedPersonTeam.setRole(roleComboBox.getSelectionModel().getSelectedItem());
                selectedPersonTeam.getPerson().setJmb(jmbTextField.getText());
                FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().update(selectedPersonTeam);
                FKSvodnaUtilities.getDAOFactory().getPersonDAO().update(selectedPersonTeam.getPerson());
                finished = true;
            }
        }

        if(finished) {
            quit();
        }
    }

    public void quit() {
        ((Stage) addEditButton.getScene().getWindow()).close();
    }

    private boolean checkFields() {
        if(checkName() && checkSurname() && checkAddress() && checkEmail() && checkLicenceNumber() && checkPhoneNumber()) {
            return true;
        }
        return false;
    }

    private boolean checkName() {
        if("".equals(nameTextField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkSurname() {
        if("".equals(surnameTextField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkLicenceNumber() {
        if("".equals(licenceNumberTextField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkJmb() {
        if("".equals(jmbTextField.getText().trim()) || jmbTextField.getText().length() != 13 || !jmbTextField.getText().matches("[0-9]+")) {
            return false;
        }
        return true;
    }

    private boolean checkAddress() {
        if("".equals(addressTextField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkEmail() {
        if("".equals(emailTextField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkPhoneNumber() {
        if("".equals(phoneNumberTextField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkRole() {
        if(roleComboBox.getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
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

    public Button getAddEditButton() {
        return addEditButton;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getSurnameTextField() {
        return surnameTextField;
    }

    public TextField getLicenceNumberTextField() {
        return licenceNumberTextField;
    }

    public TextField getJmbTextField() {
        return jmbTextField;
    }

    public TextField getAddressTextField() {
        return addressTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public TextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }

    public ComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public void setSelectedPersonTeam(PersonTeam selectedPersonTeam) {
        this.selectedPersonTeam = selectedPersonTeam;
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }
}
