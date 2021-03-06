package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.*;
import model.util.FKSvodnaUtilities;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;

public class MatchController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private ComboBox<Team> teamsComboBox;

    @FXML
    private URL location;

    @FXML
    private TextField result;

    @FXML
    private TableView<Match> tableResult;

    private DirectoryChooser directoryChooser;

    private Stage alertStage;
    private AlertController alertController;
    private List<Match> matchesList;
    private Match selectedMatch;
    private AddEditMatchesController addEditMatchesController;
    private Stage addEditMatchesStage;
    private Team selectedTeam;

    @FXML
    void addMatch(ActionEvent event) {
        addEditMatchesController.clearFields();
        addEditMatchesController.getAddEditButton().setText("Dodaj utakmicu");
        addEditMatchesController.getResultField().setVisible(false);
        addEditMatchesController.getResultLabel().setVisible(false);
        addEditMatchesController.getTeamLabel().setVisible(true);
        addEditMatchesController.getTeamsComboBox().setVisible(true);
        addEditMatchesStage.showAndWait();
        displayMatches();
        clearFields();
    }

    @FXML
    void addResult(ActionEvent event) {
        selectedMatch = tableResult.getSelectionModel().getSelectedItem();
        if(selectedMatch!=null && !result.getText().isEmpty()){
            selectedMatch.setResult(result.getText());
            FKSvodnaUtilities.getDAOFactory().getMatchDAO().update(selectedMatch);
        }
        else{
            alertController.setText("Nije odabrana utakmica!");
            alertStage.show();
        }
        displayMatches();
        clearFields();
    }

    @FXML
    void updateResult(ActionEvent event) {
        selectedMatch = tableResult.getSelectionModel().getSelectedItem();
        if(selectedMatch!=null){
            addEditMatchesController.setSelectedMatch(selectedMatch);
            addEditMatchesController.getOpponentTeamField().setText(selectedMatch.getOpposingTeam());
            addEditMatchesController.getTimeField().setText(selectedMatch.getDate().getHours()+":"+selectedMatch.getDate().getMinutes());
            addEditMatchesController.getDateofMatch().setValue(selectedMatch.getDate().toLocalDateTime().toLocalDate());
            addEditMatchesController.getAddEditButton().setText("Izmijeni utakmicu");
            addEditMatchesController.getResultField().setVisible(true);
            addEditMatchesController.getResultLabel().setVisible(true);
            addEditMatchesController.getTeamLabel().setVisible(false);
            addEditMatchesController.getTeamsComboBox().setVisible(false);
            addEditMatchesController.setSelectedTeam(selectedTeam);
            addEditMatchesController.getResultField().setText(selectedMatch.getResult());
            if(selectedMatch.getIsAway().equals(IsAway.Jeste)){
                addEditMatchesController.getHomeButton().setSelected(false);
                addEditMatchesController.getAwayButton().setSelected(true);
            }else{
                addEditMatchesController.getHomeButton().setSelected(true);
                addEditMatchesController.getAwayButton().setSelected(false);
            }
            addEditMatchesStage.showAndWait();
        }
        else{
            alertController.setText("Nije odabrana utakmica!");
            alertStage.show();
        }
        displayMatches();
        clearFields();
    }

    public void selectTeam() {
        selectedTeam = teamsComboBox.getSelectionModel().getSelectedItem();
        displayMatches();
    }

    @FXML
    void saveMatches(ActionEvent event){
        if(selectedTeam!=null) {
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("utakmice");
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

            for (int j = 0; j < tableResult.getColumns().size(); j++) {
                row.createCell(j + 1).setCellValue(tableResult.getColumns().get(j).getText());
                row.getCell(j + 1).setCellStyle(topStyle);
            }

            for (int i = 0; i < tableResult.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                row.createCell(0);
                row.getCell(0).setCellValue(i + 1 + ".");
                row.getCell(0).setCellStyle(style);
                for (int j = 0; j < tableResult.getColumns().size(); j++) {
                    if (tableResult.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j + 1).setCellValue(tableResult.getColumns().get(j).getCellData(i).toString());
                        row.getCell(j + 1).setCellStyle(style);
                    } else {
                        row.createCell(j + 1).setCellValue("");
                        row.getCell(j + 1).setCellStyle(style);
                    }
                }
            }
            spreadsheet.setColumnWidth(0, 1500);
            spreadsheet.setColumnWidth(1, 6000);
            spreadsheet.setColumnWidth(2, 6000);
            spreadsheet.setColumnWidth(3, 2500);
            directoryChooser.setTitle("Odabir foldera");
            File selectedDirectory = directoryChooser.showDialog(addEditMatchesStage);
            if (selectedDirectory != null)
                try {
                    FileOutputStream fileOut = new FileOutputStream(selectedDirectory.getPath() + File.separator + "Utakmice-" + selectedTeam.getName() + ".xls");
                    workbook.write(fileOut);
                    fileOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }else{
            alertController.setText("Nije odabran tim!");
            alertStage.showAndWait();
        }
    }

    @FXML
    void initialize() throws IOException {
        List<Team> listOfTeams = FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams();
        teamsComboBox.getItems().addAll(listOfTeams);
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle("Upozorenje");
        loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_matches.fxml"));
        root = loader.load();
        addEditMatchesController = loader.getController();
        addEditMatchesStage = new Stage();
        addEditMatchesStage.setScene(new Scene(root));
        addEditMatchesStage.initModality(Modality.APPLICATION_MODAL);
        addEditMatchesStage.setTitle("Utakmica");
        addEditMatchesStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));

        tableResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tableResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("opposingTeam"));
        tableResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("result"));

        directoryChooser = new DirectoryChooser();

    }

    private void clearFields(){
        result.clear();
    }

    private void displayMatches(){
        try {
            if(selectedTeam != null) {
                tableResult.getItems().clear();
                tableResult.refresh();
                List<TeamMatch> teamMatches = FKSvodnaUtilities.getDAOFactory().getPersonTeamMatchDAO().getTeamMatchByTeamID(selectedTeam.getId());
                List<Match> matches = new ArrayList<>();
                for (TeamMatch teamMatch : teamMatches) {
                    matches.add(teamMatch.getMatch());
                }
                tableResult.getItems().addAll(matches);
                tableResult.getSelectionModel().clearSelection();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
