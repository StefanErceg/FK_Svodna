package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.UserAccount;
import model.util.FKSvodnaUtilities;

import java.io.File;
import java.util.List;

public class AccountsController {

    @FXML
    private TableView<UserAccount> accountsTableView;
    @FXML
    private ImageView adminImageView;
    @FXML
    private Label userLabel;

    private LogInController logInController;
    private Stage logInStage;

    private AddEditAccountsController addEditAccountsController;
    private Stage addEditAccountsStage;
    private List<UserAccount> listOfAccounts;
    private AlertController alertController;
    private Stage alertStage;
    private DecisionController decisionController;
    private Stage decisionStage;
    private UserAccount selectedAccount;

    @FXML
    private void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_accounts.fxml"));
        Parent root = loader.load();
        addEditAccountsController = loader.getController();
        addEditAccountsStage = new Stage();
        addEditAccountsStage.initModality(Modality.APPLICATION_MODAL);
        addEditAccountsStage.setScene(new Scene(root));

        accountsTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        accountsTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        accountsTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("username"));

        accountsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isAdmin()) {
                adminImageView.setImage(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "check.png"));
            } else {
                adminImageView.setImage(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "cancel.png"));
            }
        });

        displayAccounts();
    }


    public void addAccount() {
        try {
            addEditAccountsController.getAddEditButton().setText("Dodaj nalog");
            addEditAccountsController.getNameTextField().clear();
            addEditAccountsController.getSurnameTextField().clear();
            addEditAccountsController.getUsernameTextField().clear();
            addEditAccountsController.getPasswordField().clear();
            addEditAccountsController.getAgainPasswordField().clear();
            addEditAccountsStage.showAndWait();
            displayAccounts();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editAccount() {
        try {
            selectedAccount = accountsTableView.getSelectionModel().getSelectedItem();
            if(selectedAccount != null) {
                addEditAccountsController.getAddEditButton().setText("Izmijeni nalog");
                addEditAccountsController.getNameTextField().setText(selectedAccount.getName());
                addEditAccountsController.getSurnameTextField().setText(selectedAccount.getSurname());
                addEditAccountsController.getUsernameTextField().setText(selectedAccount.getUsername());
                addEditAccountsController.getAdminCheckBox().setSelected(selectedAccount.isAdmin());
                addEditAccountsController.getPasswordField().clear();
                addEditAccountsController.getAgainPasswordField().clear();
                addEditAccountsController.setSelectedAccountId(selectedAccount.getId());
                addEditAccountsStage.showAndWait();
                displayAccounts();
            }
            else {
                displayAlert("Nije izabran korisnički nalog za izmjenu!");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAccount() {
        try {
            selectedAccount = accountsTableView.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                displayDecision();
                if(decisionController.returnResult()) {
                    FKSvodnaUtilities.getDAOFactory().getUserAccountDAO().delete(selectedAccount);
                }
                displayAccounts();
            } else {
                displayAlert("Nije izabran korisnički nalog za brisanje!");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayAccounts() {
        try {
            accountsTableView.getItems().clear();
            accountsTableView.refresh();
            listOfAccounts = FKSvodnaUtilities.getDAOFactory().getUserAccountDAO().accounts();
            accountsTableView.getItems().addAll(listOfAccounts);
            accountsTableView.getSelectionModel().select(1);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayAlert(String content) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root = loader.load();
        alertController = loader.getController();
        alertController.setText(content);
        alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setScene(new Scene(root));
        alertStage.showAndWait();
    }

    private void displayDecision() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        Parent root = loader.load();
        decisionController = loader.getController();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da želite da obrišete korisnički nalog?");
        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));
        decisionStage.showAndWait();
    }

    public void setUser(String user) {
        userLabel.setText(user);
    }

    private void displayLogIn() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/logIn.fxml"));
        Parent root = loader.load();
        logInController = loader.getController();
        logInStage = new Stage();
        logInStage.initModality(Modality.APPLICATION_MODAL);
        logInStage.setScene(new Scene(root));
        logInStage.show();
    }

    public void logOut() {
        try {
            displayLogIn();
            ((Stage) userLabel.getScene().getWindow()).close();
        }
            catch (Exception e) {
            e.printStackTrace();
        }

    }
}
