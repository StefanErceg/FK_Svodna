package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Sponsor;
import model.DTO.Team;
import model.util.FKSvodnaUtilities;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerController {

    @FXML
    private TableView<Person> playerTable;

    @FXML
    private TextField findPlayerField;

    private DirectoryChooser directoryChooser;

    private AddEditPlayerController addEditPlayerController;
    private PlayerSidebarController playerSidebarController;
    private Stage addEditPlayerStage;
    private ObservableList<Person> playersList;
    private AlertController alertController;
    private  DecisionController decisionController;
    private Stage alertStage;
    private Stage decisionStage;



    @FXML
    void addPlayer(ActionEvent event) {
        addEditPlayerController.getTeamSelectComboBox().setVisible(true);
        addEditPlayerController.getTeamLabel().setVisible(true);
        addEditPlayerController.clearFields();
        addEditPlayerController.getTeamSelectComboBox().setItems(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams()));
        addEditPlayerController.getAddPlayerButton().setText("Dodaj igrača");
        addEditPlayerStage.showAndWait();
        displayPlayers();
    }

    @FXML
    void editPlayer(ActionEvent event) {
        addEditPlayerController.clearFields();
        Person person = playerTable.getSelectionModel().getSelectedItem();
        if (person == null) {
            alertController.setText("Nije izabran igrač za izmjenu.");
            alertStage.show();
            return;
        }
        addEditPlayerController.getTeamSelectComboBox().setVisible(false);
        addEditPlayerController.getTeamLabel().setVisible(false);
        addEditPlayerController.getTeamSelectComboBox().setItems(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams()));
        addEditPlayerController.setSelectedPlayerId(person.getId());
        PersonTeam personTeam = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person);
        addEditPlayerController.getDateFrom().setValue(personTeam.getDateFrom().toLocalDateTime().toLocalDate());
        addEditPlayerController.getPositionTextField().setText(personTeam.getPlayerPosition());
        addEditPlayerController.getTeamSelectComboBox().getSelectionModel().select(personTeam.getTeam());
        addEditPlayerController.getJerseyNumberTextField().setText("" + personTeam.getJerseyNumber());
        addEditPlayerController.getAddPlayerButton().setText("Izmijeni igrača");
        addEditPlayerController.getNameTextField().setText(person.getName());
        addEditPlayerController.getLastNameTextField().setText(person.getSurname());
        addEditPlayerController.getLicenceNumberTextField().setText(person.getLicenceNumber());
        addEditPlayerController.getJmbgTextField().setText(person.getJmb());
        addEditPlayerController.getAdressTextField().setText(person.getAddress());
        addEditPlayerController.getEmailTextField().setText(person.getEmail());
        addEditPlayerController.getPhoneNumberTextField().setText(person.getPhoneNumber());

        addEditPlayerStage.showAndWait();
        displayPlayers();
    }

    @FXML
    void deletePlayer(ActionEvent event){
        if(playerTable.getSelectionModel().isEmpty()) {
            alertController.setText("Nije izabran igrač za brisanje.");
            alertStage.showAndWait();
            return;
        }
        Person selection=playerTable.getSelectionModel().getSelectedItem();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da zelite obrisati igraca?");
        decisionStage.showAndWait();
        if( selection!= null && decisionController.returnResult()){
            PersonTeam personTeam = FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(selection);
            personTeam.setDateTo(new Timestamp(Calendar.getInstance().getTime().getTime()));
            if(!FKSvodnaUtilities.getDAOFactory().getPersonDAO().delete(selection)){
                alertController.setText("Desila se greska pri brisanju, brisanje nije izvrseno.");
                alertStage.showAndWait();
            }
            playersList=new FilteredList<Person>(FXCollections.observableList(FKSvodnaUtilities.getDAOFactory().getPersonDAO().persons()));
            playerTable.setItems(playersList);
        }
        playerTable.getSelectionModel().clearSelection();
        playerSidebarController.clearPlayer();
    }

    @FXML
    void findPlayer(KeyEvent event) {
        ObservableList<Person> filtered=playersList.filtered( e->e.getName().toLowerCase().matches(".*"+findPlayerField.getText().toLowerCase()+".*")||e.getSurname().toLowerCase().matches(".*"+findPlayerField.getText().toLowerCase()+".*"));
        playerTable.setItems(filtered);
        if(filtered.size() > 0) {
            playerSidebarController.setPlayer(filtered.get(0));
        }
    }

    @FXML
    void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_player.fxml"));
        Parent root = loader.load();
        addEditPlayerController = loader.getController();
        addEditPlayerStage = new Stage();
        addEditPlayerStage.initModality(Modality.APPLICATION_MODAL);
        addEditPlayerStage.setScene(new Scene(root));
        loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        VBox alert=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(alert));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();
        decisionController = loader.getController();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da želite da obrišete igrača?");
        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));

        directoryChooser = new DirectoryChooser();

        playerTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        playerTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        playerTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


    }

    private void displayPlayers(){
        try {
            List<Team> teams = FKSvodnaUtilities.getDAOFactory().getTeamDAO().teams();
            playersList = FXCollections.observableArrayList();
            for(Team team:teams) {
                for(PersonTeam personTeam : FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getPlayersForTeam(team.getId())) {
                    playersList.add(personTeam.getPerson());
                }
            }
            playerTable.setItems(playersList);
            playerTable.getSelectionModel().clearSelection();
            playerSidebarController.clearPlayer();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void selectionOfPlayer(MouseEvent event){
        if(playerTable.getSelectionModel().getSelectedItem()!=null){
            playerSidebarController.setPlayer(playerTable.getSelectionModel().getSelectedItem());
        }
    }
    public void setPlayerSidebarController(PlayerSidebarController playerSidebarController){
        this.playerSidebarController = playerSidebarController;
        displayPlayers();
    }

    @FXML
    private void printPlayers() {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
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

        for (int j = 1; j < playerTable.getColumns().size(); j++) {
            row.createCell(j).setCellValue(playerTable.getColumns().get(j - 1).getText());
            row.getCell(j).setCellStyle(topStyle);
        }

        for (int i = 0; i < playerTable.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            row.createCell(0);
            row.getCell(0).setCellValue(i + 1 + ".");
            row.getCell(0).setCellStyle(style);
            for (int j = 1; j < playerTable.getColumns().size(); j++) {
                if (playerTable.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(playerTable.getColumns().get(j - 1).getCellData(i).toString());
                    row.getCell(j).setCellStyle(style);
                } else {
                    row.createCell(j).setCellValue("");
                    row.getCell(j).setCellStyle(style);
                }
            }
        }
        spreadsheet.setColumnWidth(0,1500);
        spreadsheet.setColumnWidth(1,4000);
        spreadsheet.setColumnWidth(2,4500);
        directoryChooser.setTitle("Odabir foldera");
        File selectedDirectory = directoryChooser.showDialog(addEditPlayerStage);
        if (selectedDirectory != null)
            try {
                FileOutputStream fileOut = new FileOutputStream(selectedDirectory.getPath() + File.separator + "Igrači.xls");
                workbook.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
