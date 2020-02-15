package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.UserAccount;
import model.util.FKSvodnaUtilities;

public class AddEditAccountsController {

    @FXML
    private Button addEditButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField againPasswordField;

    private int selectedAccountId;
    private AlertController alertController;
    private Stage alertStage;
    private boolean finished = false;

    @FXML
    private void initialize() {

    }

    public void save() {
        if (checkFields()) { // PROBLEM JE POLJE ADMIN, NIJE NEKO OBRATIO PAZNJUUU!!!
            if ("Dodaj nalog".equals(addEditButton.getText())) {
                FKSvodnaUtilities.getDAOFactory().getUserAccountDAO().insert(new UserAccount(0, nameTextField.getText(), surnameTextField.getText(),
                        usernameTextField.getText(), FKSvodnaUtilities.getSHA256(passwordField.getText()), false));
            } else {
                FKSvodnaUtilities.getDAOFactory().getUserAccountDAO().update(new UserAccount(selectedAccountId, nameTextField.getText(), surnameTextField.getText(),
                        usernameTextField.getText(), FKSvodnaUtilities.getSHA256(passwordField.getText()), false));
            }
        } else {
            try {
                displayAlert("Nisu unesena sva polja!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (!checkCorrectPasswordFields()) {
            finished = false;
            try {
                displayAlert("Nije uspješno unesena lozinka!");
                passwordField.clear();
                againPasswordField.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            finished = true;
        }

        if (!checkCorrectUsername()) {
            finished = false;
            try {
                displayAlert("Korisničko ime postoji!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            finished = true;
        }

        if(finished) {
            ((Stage) addEditButton.getScene().getWindow()).close();
        }
    }

    public void quit() {
        ((Stage) addEditButton.getScene().getWindow()).close();
    }

    private boolean checkFields() {
        if(checkName() && checkSurname() && checkUsername() && checkPasswordFields()) {
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

    private boolean checkUsername() {
        if("".equals(usernameTextField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkCorrectUsername() {
        if(FKSvodnaUtilities.getDAOFactory().getUserAccountDAO().checkUsernameExists(usernameTextField.getText())) {
            return false;
        }
        return true;
    }

    private boolean checkPasswordFields() {
        if("".equals(passwordField.getText().trim()) || "".equals(againPasswordField.getText().trim())) {
            return false;
        }
        return true;
    }

    private boolean checkCorrectPasswordFields() {
        if(!(passwordField.getText().trim().equals(againPasswordField.getText().trim()))) {
            return false;
        }
        return true;
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

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public PasswordField getAgainPasswordField() {
        return againPasswordField;
    }

    public void setSelectedAccountId(int selectedAccountId) {
        this.selectedAccountId = selectedAccountId;
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
}
