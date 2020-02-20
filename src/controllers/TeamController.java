package controllers;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class TeamController {


    @FXML
    private ComboBox<Team> teamsComboBox;
    @FXML
    private VBox playersVBox;
    @FXML
    private TableView<PersonTeam> staffTableView;
    @FXML
    private TableColumn<PersonTeam, String> nameColumn;
    @FXML
    private TableColumn<PersonTeam, String> surnameColumn;

    private List<Team> listOfTeams;
    private List<PersonTeam> listOfStaff;
    private List<PersonTeam> listOfPlayers;
    private Team selectedTeam;
    private PersonTeam selectedPersonTeam;
    private AlertController alertController;
    private DecisionController decisionController;
    private Stage decisionStage;
    private Stage alertStage;
    private Label playerLabel;
    private AddEditPersonController addEditPersonController;
    private Stage addEditPersonStage;

    @FXML
    void initialize() throws Exception {
        teams();

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getSurname()));
        staffTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("role"));

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_person.fxml"));
        Parent root = loader.load();
        addEditPersonController = loader.getController();
        addEditPersonStage = new Stage();
        addEditPersonStage.initModality(Modality.APPLICATION_MODAL);
        addEditPersonStage.setScene(new Scene(root));
        addEditPersonStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));

    }

    public void addPersonTeam() {
        if(selectedTeam != null) {
            addEditPersonController.getAddEditButton().setText("Dodaj osobu");
            addEditPersonController.getNameTextField().clear();
            addEditPersonController.getSurnameTextField().clear();
            addEditPersonController.getJmbTextField().clear();
            addEditPersonController.getAddressTextField().clear();
            addEditPersonController.getEmailTextField().clear();
            addEditPersonController.getPhoneNumberTextField().clear();
            addEditPersonController.getLicenceNumberTextField().clear();
            addEditPersonController.getRoleComboBox().getSelectionModel().clearSelection();
            addEditPersonController.setSelectedTeam(selectedTeam);
            addEditPersonStage.showAndWait();
            displayStaff();
        } else {
            try {
                displayAlert("Nije izabran tim!");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void editPersonTeam() {
        selectedPersonTeam = staffTableView.getSelectionModel().getSelectedItem();
        if(selectedPersonTeam != null) {
            addEditPersonController.getAddEditButton().setText("Izmijeni osobu");
            addEditPersonController.getNameTextField().setText(selectedPersonTeam.getPerson().getName());
            addEditPersonController.getSurnameTextField().setText(selectedPersonTeam.getPerson().getSurname());
            addEditPersonController.getJmbTextField().setText(selectedPersonTeam.getPerson().getJmb());
            addEditPersonController.getAddressTextField().setText(selectedPersonTeam.getPerson().getAddress());
            addEditPersonController.getEmailTextField().setText(selectedPersonTeam.getPerson().getEmail());
            addEditPersonController.getPhoneNumberTextField().setText(selectedPersonTeam.getPerson().getPhoneNumber());
            addEditPersonController.getLicenceNumberTextField().setText(selectedPersonTeam.getPerson().getLicenceNumber());
            addEditPersonController.getRoleComboBox().getSelectionModel().select(selectedPersonTeam.getRole());
            addEditPersonController.setSelectedPersonTeam(selectedPersonTeam);
            addEditPersonStage.showAndWait();
            displayStaff();
        } else {
            try {
                displayAlert("Nije izabrana osoba stručnog štaba za izmjenu!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deletePersonTeam() {
        selectedPersonTeam = staffTableView.getSelectionModel().getSelectedItem();
        if(selectedPersonTeam == null) {
            try {
                displayAlert("Nije izabrana osoba stručnog štaba za brisanje!");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        if(selectedTeam != null && selectedPersonTeam != null) {
            try {
                displayDecision();
                if(decisionController.returnResult()) {
                    selectedPersonTeam.setDateTo(new Timestamp(Calendar.getInstance().getTime().getTime()));
                    selectedPersonTeam.setDateTo(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime().getTime())));
                    FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().update(selectedPersonTeam);
                    displayStaff();
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void selectTeam() {
        selectedTeam = teamsComboBox.getSelectionModel().getSelectedItem();
        if(selectedTeam != null) {
            displayPlayers();
            displayStaff();
        }
    }

    private void teams() {
        listOfTeams = FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams();
        teamsComboBox.getItems().addAll(listOfTeams);
    }

    private void displayPlayers() {
        playersVBox.getChildren().clear();
        listOfPlayers = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getPlayersForTeam(selectedTeam.getId());
        for(PersonTeam personTeam : listOfPlayers) {
            playerLabel = new Label(personTeam.getPerson().getName() + " " + personTeam.getPerson().getSurname());
            playerLabel.setTextFill(Paint.valueOf("WHITE"));
            playerLabel.setFont(Font.font(16));
            playersVBox.getChildren().add(playerLabel);
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

    private void displayStaff() {
        staffTableView.getItems().clear();
        staffTableView.refresh();
        listOfStaff = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getStaffForTeam(selectedTeam.getId());
        staffTableView.getItems().addAll(listOfStaff);
    }

    private void displayDecision() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        Parent root = loader.load();
        decisionController = loader.getController();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da želite da obrišete osobu?");
        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));
        decisionStage.showAndWait();
    }



}
