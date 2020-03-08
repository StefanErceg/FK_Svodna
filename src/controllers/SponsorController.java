package controllers;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import model.DAO.DAOFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Sponsor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;


public class SponsorController {

    @FXML
    private TextField sponsorNameField;
    @FXML
    private TableView<Sponsor> sponsorTable;
    private ObservableList<Sponsor> sponsorList;
    private DirectoryChooser directoryChooser;
    @FXML
    private SponsorSidebarController sponsorSidebarController;
    private AddEditSponsorController addEditSponsorController;
    private Stage addEditSponsorStage;
    private Stage alertStage;
    private AlertController alertController;
    private DecisionController decisionController;
    private Stage decisionStage;

    @FXML
    void addSponsor(ActionEvent event) {    //poziva formu za dodavanje sponzora i refresuje tabelu
        addEditSponsorController.setSponsor(new Sponsor());
        addEditSponsorStage.showAndWait();
        sponsorList=new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
        sponsorTable.setItems(sponsorList);
        sponsorTable.getSelectionModel().clearSelection();
        sponsorSidebarController.clearSponsor();
    }

    @FXML
    void changeSponsor(ActionEvent event)  { //poziva formu za promjenu sponzora i refresuje tabelu
        if(sponsorTable.getSelectionModel().isEmpty()) {
            alertController.setText("Nije odabran sponzor!");
            alertStage.showAndWait();
            return;
        }
        Sponsor selected=sponsorTable.getSelectionModel().getSelectedItem();
        addEditSponsorController.setSponsor(selected);
        addEditSponsorStage.showAndWait();
        sponsorTable.refresh();
        sponsorTable.getSelectionModel().clearSelection();
        sponsorSidebarController.clearSponsor();
    }

    @FXML
    void deleteSponsor(ActionEvent event) { //brise sponzora iz baze refresuje tabelu

        if(sponsorTable.getSelectionModel().isEmpty()) {
            alertController.setText("Nije odabran sponzor!");
            alertStage.showAndWait();
            return;
        }
        Sponsor selection=sponsorTable.getSelectionModel().getSelectedItem();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da želite obrisati sponzora?");
        decisionStage.showAndWait();
        if( selection!= null && decisionController.returnResult()){
            if(!DAOFactory.getDAOFactory().getSponsorDAO().delete(selection)){
                alertController.setText("Desila se greška pri brisanju, brisanje nije izvršeno!");
                alertStage.showAndWait();
            }
            sponsorList=new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
            sponsorTable.setItems(sponsorList);
        }
        sponsorTable.getSelectionModel().clearSelection();
        sponsorSidebarController.clearSponsor();

    }

    @FXML
    void sponsorSearch(Event event) {   //aktivno pretrazuje tabelu i izbacuje samo prihvatljive sponzore, ukoliko ostaje samo jedan sponzor odmah prikazuje njegove podatke na sidebaru
        //pretrazuje po imenima tako da sadrzi podstring ostale izbacuje
        ObservableList<Sponsor> filtered=sponsorList.filtered( e->e.getName().toLowerCase().matches(".*"+sponsorNameField.getText().toLowerCase()+".*"));
        sponsorTable.setItems(filtered);
        if (filtered.size() > 0) {
            sponsorSidebarController.setSponsor(filtered.get(0));
        }

    }

    @FXML
    void selectionChange(MouseEvent event) {    // komunkikacija sa sidebar kontrolerom, postavlja sponzora o kojem treba prikazati detaljne inormacije
        Sponsor sponsor = sponsorTable.getSelectionModel().getSelectedItem();
        if(sponsor!=null){
            sponsorSidebarController.setSponsor(sponsor);
            if (sponsor.getKind().equals("Fizičko lice")){
                sponsorSidebarController.getContactPersonButton().setVisible(false);
                sponsorSidebarController.getContactPersonLabel().setVisible(false);
            }
            else{
                sponsorSidebarController.getContactPersonLabel().setVisible(true);
                sponsorSidebarController.getContactPersonButton().setVisible(true);
            }
        }
    }
    public void setSponsorSidebarController(SponsorSidebarController sponsorSidebarController) {
        this.sponsorSidebarController = sponsorSidebarController;

    }

    @FXML
    void initialize() throws Exception  {
        sponsorList= new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_sponsors.fxml"));
        Parent root = loader.load();
        addEditSponsorController = loader.getController();
        addEditSponsorStage = new Stage();
        addEditSponsorStage.initModality(Modality.APPLICATION_MODAL);
        addEditSponsorStage.setScene(new Scene(root));
        addEditSponsorStage.setTitle("Sponzor");
        addEditSponsorStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
        loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        VBox alert=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(alert));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();
        decisionController = loader.getController();
        decisionController.getDecisionLabel().setText("Da li ste sigurni da želite da obrišete sponzora?");
        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));
        decisionStage.setTitle("Potvrda");
        alertStage.setTitle("Upozorenje");

        sponsorTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        sponsorTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("kind"));
        sponsorTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        sponsorTable.setItems(sponsorList);
        sponsorTable.getSelectionModel().clearSelection();

        directoryChooser = new DirectoryChooser();

    }

    @FXML
    public void printAllSponsors(ActionEvent actionEvent) {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sponzori");
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

        for (int j = 1; j <= sponsorTable.getColumns().size(); j++) {
            row.createCell(j).setCellValue(sponsorTable.getColumns().get(j - 1).getText());
            row.getCell(j).setCellStyle(topStyle);
        }

        for (int i = 0; i < sponsorTable.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            row.createCell(0);
            row.getCell(0).setCellValue(i + 1 + ".");
            row.getCell(0).setCellStyle(style);
            for (int j = 1; j <= sponsorTable.getColumns().size(); j++) {
                if (sponsorTable.getColumns().get(j-1).getCellData(i) != null) {
                    row.createCell(j).setCellValue(sponsorTable.getColumns().get(j - 1).getCellData(i).toString());
                    row.getCell(j).setCellStyle(style);
                } else {
                    row.createCell(j).setCellValue("");
                    row.getCell(j).setCellStyle(style);
                }
            }
        }
        spreadsheet.setColumnWidth(0,1500);
        spreadsheet.setColumnWidth(1,6000);
        spreadsheet.setColumnWidth(2,4500);
        spreadsheet.setColumnWidth(3,6000);
        directoryChooser.setTitle("Odabir foldera");
        File selectedDirectory = directoryChooser.showDialog(alertStage);
        if (selectedDirectory != null)
            try {
                FileOutputStream fileOut = new FileOutputStream(selectedDirectory.getPath() + File.separator + "Sponzori.xls");
                workbook.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}