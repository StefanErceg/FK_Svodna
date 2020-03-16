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
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
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

    private DirectoryChooser directoryChooser;

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
        addEditPersonStage.setTitle("Stručni štab");
        addEditPersonStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));

        directoryChooser = new DirectoryChooser();

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
                displayAlert("Nije odabran tim!");
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
                displayAlert("Nije odabrana osoba stručnog štaba!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deletePersonTeam() {
        selectedPersonTeam = staffTableView.getSelectionModel().getSelectedItem();
        if(selectedPersonTeam == null) {
            try {
                displayAlert("Nije odabrana osoba stručnog štaba!");
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
        alertStage.setTitle("Upozorenje");
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
        decisionStage.setTitle("Potvrda");
        decisionStage.showAndWait();
    }

    @FXML
    private void printStaff() {
        if (selectedTeam != null) {
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("strucni stab");
            spreadsheet.setDefaultColumnWidth(15);
            CellStyle topStyle = workbook.createCellStyle();
            topStyle.setBorderBottom(BorderStyle.MEDIUM);
            topStyle.setBorderLeft(BorderStyle.MEDIUM);
            topStyle.setBorderRight(BorderStyle.MEDIUM);
            topStyle.setBorderTop(BorderStyle.MEDIUM);
            topStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle style = workbook.createCellStyle();
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setAlignment(HorizontalAlignment.CENTER);

            Row row = spreadsheet.createRow(0);

            row.createCell(0).setCellStyle(topStyle);
            row.getCell(0).setCellValue("RB");

            for (int j = 0; j < staffTableView.getColumns().size(); j++) {
                row.createCell(j+1).setCellValue(staffTableView.getColumns().get(j).getText());
                row.getCell(j+1).setCellStyle(topStyle);
            }

            for (int i = 0; i < staffTableView.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                row.createCell(0);
                row.getCell(0).setCellValue(i + 1 + ".");
                row.getCell(0).setCellStyle(style);
                for (int j = 0; j < staffTableView.getColumns().size(); j++) {
                    if (staffTableView.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j+1).setCellValue(staffTableView.getColumns().get(j).getCellData(i).toString());
                        row.getCell(j+1).setCellStyle(style);
                    } else {
                        row.createCell(j+1).setCellValue("");
                        row.getCell(j+1).setCellStyle(style);
                    }
                }
            }
            spreadsheet.setColumnWidth(0,1500);
            spreadsheet.setColumnWidth(1,4000);
            spreadsheet.setColumnWidth(2,4500);
            spreadsheet.setColumnWidth(3,6000);
            Sheet spreadsheet2 = workbook.createSheet("igraci");
            Row row2 = spreadsheet2.createRow(0);

            row2.createCell(0).setCellStyle(topStyle);
            row2.getCell(0).setCellValue("RB");
            row2.createCell(1).setCellStyle(topStyle);
            row2.getCell(1).setCellValue("Ime");
            row2.createCell(2).setCellStyle(topStyle);
            row2.getCell(2).setCellValue("Prezime");
            row2.createCell(3).setCellStyle(topStyle);
            row2.getCell(3).setCellValue("Broj dresa");
            row2.createCell(4).setCellStyle(topStyle);
            row2.getCell(4).setCellValue("Pozicija");

            for (int i = 0; i < listOfPlayers.size(); i++) {
                row2 = spreadsheet2.createRow(i + 1);
                row2.createCell(0);
                row2.getCell(0).setCellValue(i + 1 + ".");
                row2.getCell(0).setCellStyle(style);
                row2.createCell(1);
                row2.getCell(1).setCellValue(listOfPlayers.get(i).getPerson().getName());
                row2.getCell(1).setCellStyle(style);
                row2.createCell(2);
                row2.getCell(2).setCellValue(listOfPlayers.get(i).getPerson().getSurname());
                row2.getCell(2).setCellStyle(style);
                row2.createCell(3);
                row2.getCell(3).setCellValue(listOfPlayers.get(i).getJerseyNumber());
                row2.getCell(3).setCellStyle(style);
                row2.createCell(4);
                row2.getCell(4).setCellValue(listOfPlayers.get(i).getPlayerPosition());
                row2.getCell(4).setCellStyle(style);
            }
            spreadsheet2.setColumnWidth(0,1500);
            spreadsheet2.setColumnWidth(1,3500);
            spreadsheet2.setColumnWidth(2,4000);
            spreadsheet2.setColumnWidth(3,3500);
            spreadsheet2.setColumnWidth(4,3500);

            directoryChooser.setTitle("Odabir foldera");
            File selectedDirectory = directoryChooser.showDialog(addEditPersonStage);
            if (selectedDirectory != null)
                try {
                    FileOutputStream fileOut = new FileOutputStream(selectedDirectory.getPath() + File.separator + selectedTeam.getName() + ".xls");
                    workbook.write(fileOut);
                    fileOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }else{
            try {
                displayAlert("Nije odabran tim!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
