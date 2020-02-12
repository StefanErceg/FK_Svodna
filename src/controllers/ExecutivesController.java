package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Manager;
import model.util.FKSvodnaUtilities;

import java.util.List;
import java.util.stream.Collectors;

public class ExecutivesController {

    @FXML
    private TableView<Manager> executivesTableView;
    @FXML
    private TextField searchTextField;

    private List<Manager> listOfExecutives;
    private AddEditExecutivesController addEditExecutivesController;
    private Stage addEditExecutives;
    private Manager selectedExecutive;
    private AlertController alertController;
    private Stage alertStage;
    private DecisionController decisionController;
    private Stage decisionStage;

    @FXML
    private void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_executives.fxml"));
        Parent root = loader.load();
        addEditExecutivesController = loader.getController();
        addEditExecutives = new Stage();
        addEditExecutives.initModality(Modality.APPLICATION_MODAL);
        addEditExecutives.setScene(new Scene(root));

        executivesTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        executivesTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        executivesTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        executivesTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        executivesTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("position"));

        displayExecutives();
    }

    public void search() {
        List<Manager> filteredManagers = listOfExecutives.stream()
                .filter(manager -> manager.getName().toUpperCase().contains(searchTextField.getText().toUpperCase()))
                .collect(Collectors.toList());
        executivesTableView.getItems().clear();
        executivesTableView.getItems().addAll(filteredManagers);
        executivesTableView.refresh();
    }

    public void addExecutive() {
        try {
            addEditExecutivesController.getAddEditButton().setText("Dodaj rukovodioca");
            addEditExecutivesController.getAddEditButton().setPrefWidth(150);
            addEditExecutivesController.getNameTextField().clear();
            addEditExecutivesController.getSurnameTextField().clear();
            addEditExecutivesController.getPhoneNumberTextField().clear();
            addEditExecutivesController.getEmailTextField().clear();
            addEditExecutivesController.getPositionTextField().clear();
            addEditExecutives.showAndWait();
            displayExecutives();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editExecutive() {
        try {
            selectedExecutive = executivesTableView.getSelectionModel().getSelectedItem();
            if(selectedExecutive != null) {
                addEditExecutivesController.getAddEditButton().setText("Izmijeni rukovodoioca");
                addEditExecutivesController.getAddEditButton().setPrefWidth(165);
                addEditExecutivesController.getNameTextField().setText(selectedExecutive.getName());
                addEditExecutivesController.getSurnameTextField().setText(selectedExecutive.getSurname());
                addEditExecutivesController.getPhoneNumberTextField().setText(selectedExecutive.getPhoneNumber());
                addEditExecutivesController.getEmailTextField().setText(selectedExecutive.getEmail());
                addEditExecutivesController.getPositionTextField().setText(selectedExecutive.getPosition());
                addEditExecutivesController.setSelectedManagerId(selectedExecutive.getId());
                addEditExecutives.showAndWait();
                displayExecutives();
            }
            else {
                displayAlert("Nije izabran rukovodilac za izmjenu!");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteExecutive() {
        try {
            selectedExecutive = executivesTableView.getSelectionModel().getSelectedItem();
            if (selectedExecutive != null) {
                displayDecision();
                if(decisionController.returnResult()) {
                    FKSvodnaUtilities.getDAOFactory().getManagerDAO().delete(selectedExecutive);
                }
                displayExecutives();
            } else {
                displayAlert("Nije izabran rukovodilac za brisanje!");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayExecutives() {
        try {
            executivesTableView.getItems().clear();
            executivesTableView.refresh();
            listOfExecutives = FKSvodnaUtilities.getDAOFactory().getManagerDAO().managers();
            executivesTableView.getItems().addAll(listOfExecutives);
            searchTextField.clear();

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
        decisionController.getDecisionLabel().setText("Da li ste sigurni da želite da obrišete rukovodioca?");
        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));
        decisionStage.showAndWait();
    }

}
