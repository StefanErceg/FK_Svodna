package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DTO.Manager;
import model.util.FKSvodnaUtilities;

public class AddEditExecutivesController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField positionTextField;
    @FXML
    private Button addEditButton;

    @FXML
    private void initialize() {

    }

    public void save() {
        if(checkFields()) {
            if("Dodaj rukovodoioca".equals(addEditButton.getText())) {
                FKSvodnaUtilities.getDAOFactory().getManagerDAO().insert(new Manager(0, nameTextField.getText(),
                        surnameTextField.getText(), phoneNumberTextField.getText(), emailTextField.getText(), positionTextField.getText()));
            }
            else {
                FKSvodnaUtilities.getDAOFactory().getManagerDAO().update(new Manager(0, nameTextField.getText(),
                        surnameTextField.getText(), phoneNumberTextField.getText(), emailTextField.getText(), positionTextField.getText()));
            }
        }
    }

    public void quit() {
        ((Stage)addEditButton.getScene().getWindow()).close();
    }

    private boolean checkFields() {
        if(checkName() && checkSurname() && checkPhoneNumber() && checkEmail() && checkPosition()) {
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

    private boolean checkPhoneNumber() {
        if("".equals(phoneNumberTextField.getText().trim())) {
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

    private boolean checkPosition() {
        if("".equals(positionTextField.getText().trim())) {
            return false;
        }
        return true;
    }
}
