package controllers;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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

import java.util.List;

public class SponsorController {

    @FXML
    private TextField sponsorNameField;
    @FXML
    private TableView<Sponsor> sponsorTable;
    private ObservableList<Sponsor> sponsorList;
    @FXML
    private SponsorSidebarController sponsorSidebarController;
    private AddEditSponsorController addEditSponsorController;
    private Stage addEditSponsorStage;

    @FXML
    void addSponsor(ActionEvent event) {
        addEditSponsorController.setSponsor(new Sponsor());
        addEditSponsorStage.showAndWait();
        sponsorList=new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
        sponsorTable.setItems(sponsorList);
    }

    @FXML
    void changeSponsor(ActionEvent event)  {
        if(sponsorTable.getSelectionModel().isEmpty())
            return;
        Sponsor selected=sponsorTable.getSelectionModel().getSelectedItem();
        addEditSponsorController.setSponsor(selected);
        addEditSponsorStage.showAndWait();
        sponsorTable.refresh();
    }

    @FXML
    void deleteSponsor(ActionEvent event) {
        if(sponsorTable.getSelectionModel().isEmpty())
            return;
        Sponsor selection=sponsorTable.getSelectionModel().getSelectedItem();
        if( selection!= null && DAOFactory.getDAOFactory().getSponsorDAO().delete(selection)){
            sponsorList=new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
            sponsorTable.setItems(sponsorList);
        }
      else{
            try{
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
            VBox alert=loader.load();
            AlertController alertController=loader.getController();
            alertController.setText("Desila se greska pri brisanju, brisanje nije izvrseno.");
            Stage alertStage=new Stage();
            alertStage.setScene(new Scene(alert));
            alertStage.show();
            }catch (Exception e){e.printStackTrace();}

         }

    }

    @FXML
    void sponsorSearch(Event event) {
       ObservableList<Sponsor> filtered=sponsorList.filtered( e->e.getName().toLowerCase().matches(".*"+sponsorNameField.getText().toLowerCase()+".*"));
       sponsorTable.setItems(filtered);
       sponsorSidebarController.setSponsor(filtered.get(0));

    }

    @FXML
    void selectionChange(MouseEvent event) {
        if(sponsorTable.getSelectionModel().getSelectedItem()!=null){
            sponsorSidebarController.setSponsor(sponsorTable.getSelectionModel().getSelectedItem());
        }
    }
    public void setSponsorSidebarController(SponsorSidebarController sponsorSidebarController) {
        this.sponsorSidebarController = sponsorSidebarController;
        sponsorSidebarController.setSponsor(sponsorList.get(0));
    }

    @FXML
    void initialize() throws Exception {
        sponsorList= new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
        sponsorTable.setItems(sponsorList);
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/add_edit_sponsors.fxml"));
        Parent root=loader.load();
        addEditSponsorController=loader.getController();
        addEditSponsorStage=new Stage();
        addEditSponsorStage.initModality(Modality.APPLICATION_MODAL);
        addEditSponsorStage.setScene(new Scene(root));
        sponsorTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        sponsorTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("kind"));
        sponsorTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

    }
}
