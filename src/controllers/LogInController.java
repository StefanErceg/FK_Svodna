package controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
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
    private ResourceBundle resources;

    @FXML
    private URL location;

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
                        displayAlert("Nije unesena ispravna lozinka!");
                    } catch(Exception e) {

                    }
                }
            }
            else {
                try {
                    displayAlert("Korisničko ime nije odgovarajuće!");
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
    void initialize() {


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
        alertStage.show();
    }

    private void displayAccounts(String user) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/accounts.fxml"));
        Parent root = loader.load();
        accountsController = loader.getController();
        accountsController.setUser(user);
        accountsStage = new Stage();
        accountsStage.initModality(Modality.APPLICATION_MODAL);
        accountsStage.setScene(new Scene(root));
        accountsStage.show();
    }

    private void displayMain(String user) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/main.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        mainController.setUser(user);
        mainStage = new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.setScene(new Scene(root));
        mainStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
        mainStage.show();
    }

    private void close() {
        ((Stage) usernameTextField.getScene().getWindow()).close();
    }

}
