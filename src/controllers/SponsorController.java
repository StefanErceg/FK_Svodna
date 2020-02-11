package controllers;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DAO.DAOFactory;
import model.DAO.mysql.MySQLSponsorDAO;
import javafx.beans.Observable;
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
    private TextField sponsorFirstNameField;

    @FXML
    private TextField sponsorLastNameField;

    @FXML
    private TableView<Sponsor> sponsorTable;

    private ObservableList<Sponsor> sponsorList;

    AddEditSponsorController addEditSponsorController;
    Stage addEditSponsorStage;

    @FXML
    void addSponsor(ActionEvent event) throws Exception {
        addEditSponsorStage.show();
    }

    @FXML
    void changeSponsor(ActionEvent event) throws Exception {
       addEditSponsorStage.show();
    }

    @FXML
    void deleteSponsor(ActionEvent event) throws Exception {
        Sponsor selection=sponsorTable.getSelectionModel().getSelectedItem();
        System.out.println(selection);
        if( selection!= null && DAOFactory.getDAOFactory().getSponsorDAO().delete(selection)){
            sponsorList.remove(selection);
        }
      else{
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
            VBox alert=loader.load();
            AlertController alertController=loader.getController();
            alertController.setText("Eureka");
            Stage alertStage=new Stage();
            alertStage.setScene(new Scene(alert));
            alertStage.show();

         }

    }

    @FXML
    void sponsorSearch(ActionEvent event) {

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
