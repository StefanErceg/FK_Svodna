package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Manager;
import model.util.FKSvodnaUtilities;

import java.io.File;

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

    private int selectedManagerId;
    private AlertController alertController;
    private Stage alertStage;

    @FXML
    private void initialize() {

    }

    public void save() {
        if (checkFields()) {
            if ("Dodaj rukovodioca".equals(addEditButton.getText())) {
                FKSvodnaUtilities.getDAOFactory().getManagerDAO().insert(new Manager(0, nameTextField.getText(),
                        surnameTextField.getText(), phoneNumberTextField.getText(), emailTextField.getText(), positionTextField.getText()));
            } else {
                FKSvodnaUtilities.getDAOFactory().getManagerDAO().update(new Manager(selectedManagerId, nameTextField.getText(),
                        surnameTextField.getText(), phoneNumberTextField.getText(), emailTextField.getText(), positionTextField.getText()));
            }
            ((Stage) addEditButton.getScene().getWindow()).close();
        }
        else {
            try {
                displayAlert("Nisu unesena sva polja!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void saveByEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)) save();
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

    public Button getAddEditButton() {
        return addEditButton;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getSurnameTextField() {
        return surnameTextField;
    }

    public TextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public TextField getPositionTextField() {
        return positionTextField;
    }

    public void setSelectedManagerId(int selectedManagerId) {
        this.selectedManagerId = selectedManagerId;
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
}
