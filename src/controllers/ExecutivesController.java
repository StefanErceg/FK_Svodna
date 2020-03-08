package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Manager;
import model.util.FKSvodnaUtilities;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutivesController {

    @FXML
    private TableView<Manager> executivesTableView;
    @FXML
    private TextField searchTextField;

    private DirectoryChooser directoryChooser;

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
        addEditExecutives.setTitle("Rukovodilac");
        addEditExecutives.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));

        executivesTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        executivesTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        executivesTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        executivesTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        executivesTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("position"));

        directoryChooser = new DirectoryChooser();

        displayExecutives();
    }

    public void search() {
        List<Manager> filteredManagers = listOfExecutives.stream()
                .filter(manager -> manager.getName().toUpperCase().contains(searchTextField.getText().toUpperCase())||manager.getSurname().toUpperCase().contains(searchTextField.getText().toUpperCase()))
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
                executivesTableView.getSelectionModel().clearSelection();
            }
            else {
                displayAlert("Nije odabran rukovodilac!");
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
                executivesTableView.getSelectionModel().clearSelection();
            } else {
                displayAlert("Nije odabran rukovodilac!");
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
        alertStage.setTitle("Upozorenje");
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
        decisionStage.setTitle("Potvrda");
        decisionStage.showAndWait();
    }

    @FXML
    public void printExecutive(ActionEvent actionEvent) {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
//        spreadsheet.setDefaultColumnWidth(15);
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

        for (int j = 1; j < executivesTableView.getColumns().size(); j++) {
            row.createCell(j).setCellValue(executivesTableView.getColumns().get(j - 1).getText());
            row.getCell(j).setCellStyle(topStyle);
        }

        for (int i = 0; i < executivesTableView.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            row.createCell(0);
            row.getCell(0).setCellValue(i + 1 + ".");
            row.getCell(0).setCellStyle(style);
            for (int j = 1; j < executivesTableView.getColumns().size(); j++) {
                if (executivesTableView.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(executivesTableView.getColumns().get(j - 1).getCellData(i).toString());
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
        spreadsheet.setColumnWidth(3,4500);
        spreadsheet.setColumnWidth(4,6500);
        directoryChooser.setTitle("Odabir foldera");
        File selectedDirectory = directoryChooser.showDialog(alertStage);
        if (selectedDirectory != null)
            try {
                FileOutputStream fileOut = new FileOutputStream(selectedDirectory.getPath() + File.separator + "Rukovodstvo.xls");
                workbook.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
