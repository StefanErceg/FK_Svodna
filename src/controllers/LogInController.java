package controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.UserAccount;
import model.util.FKSvodnaUtilities;

public class LogInController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    private AlertController alertController;
    private Stage alertStage;

    private AccountsController accountsController;
    private Stage accountsStage;

    private MainController mainController;
    private Stage mainStage;

    private String username;
    private String password;
    private String passHash;
    private UserAccount userAccount;

    @FXML
    void logIn() {
        if (checkUsername() && checkPassword()) {
            username = usernameTextField.getText();
            password = passwordField.getText();
            passHash = FKSvodnaUtilities.getSHA256(password);
            if (FKSvodnaUtilities.getDAOFactory().getUserAccountDAO().checkUsernameExists(username)) {
                userAccount = FKSvodnaUtilities.getDAOFactory().getUserAccountDAO().getAccount(username, passHash);
                if (userAccount != null) {
                    if(userAccount.isAdmin()) {
                        try {
                            displayAccounts(userAccount.getName());
                            close();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            displayMain(userAccount.getName());
                            close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    try {
                        displayAlert("Pogrešna lozinka!");
                    } catch(Exception e) {

                    }
                }
            }
            else {
                try {
                    displayAlert("Pogrešno korisničko ime!");
                    usernameTextField.clear();
                    passwordField.clear();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else
            try {
                displayAlert("Nisu unesena sva polja!");
                usernameTextField.clear();
                passwordField.clear();
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }

    @FXML
    void logInByEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)) logIn();
    }

    @FXML
    void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/accounts.fxml"));
        Parent root = loader.load();
        accountsController = loader.getController();
        accountsStage = new Stage();
        accountsStage.setScene(new Scene(root));
        accountsStage.setTitle("Korisnički nalozi");
        accountsStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
        loader = new FXMLLoader(this.getClass().getResource("../view/main.fxml"));
        root = loader.load();
        mainController = loader.getController();
        mainStage = new Stage();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("FK Svodna");
        mainStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
    }

    private boolean checkUsername() {
        if ("".equals(usernameTextField.getText().trim()))
            return false;
        return true;
    }

    private boolean checkPassword() {
        if ("".equals(passwordField.getText().trim()))
            return false;
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
        alertStage.setTitle("Upozorenje");
        alertStage.show();
    }

    private void displayAccounts(String user) throws Exception {
        accountsController.setUser(user);
        accountsStage.show();
    }

    private void displayMain(String user) throws Exception {
        mainController.setUser(user);
        mainStage.show();
    }

    private void close() {
        ((Stage) usernameTextField.getScene().getWindow()).close();
    }

}
