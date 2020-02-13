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
    private Stage alertStage;
    private AlertController alertController;

    @FXML
    void addSponsor(ActionEvent event) {    //poziva formu za dodavanje sponzora i refresuje tabelu
        addEditSponsorController.setSponsor(new Sponsor());
        addEditSponsorStage.showAndWait();
        sponsorList=new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
        sponsorTable.setItems(sponsorList);
    }

    @FXML
    void changeSponsor(ActionEvent event)  { //poziva formu za promjenu sponzora i refresuje tabelu
        if(sponsorTable.getSelectionModel().isEmpty())
            return;
        Sponsor selected=sponsorTable.getSelectionModel().getSelectedItem();
        addEditSponsorController.setSponsor(selected);
        addEditSponsorStage.showAndWait();
        sponsorTable.refresh();
    }

    @FXML
    void deleteSponsor(ActionEvent event) { //brise sponzora iz baze refresuje tabelu
        if(sponsorTable.getSelectionModel().isEmpty())
            return;
        Sponsor selection=sponsorTable.getSelectionModel().getSelectedItem();
        if( selection!= null && DAOFactory.getDAOFactory().getSponsorDAO().delete(selection)){
            sponsorList=new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
            sponsorTable.setItems(sponsorList);
        }
      else{
            alertController.setText("Desila se greska pri brisanju, brisanje nije izvrseno.");
            alertStage.showAndWait();
        }

    }

    @FXML
    void sponsorSearch(Event event) {   //aktivno pretrazuje tabelu i izbacuje samo prihvatljive sponzore, ukoliko ostaje samo jedan sponzor odmah prikazuje njegove podatke na sidebaru
       ObservableList<Sponsor> filtered=sponsorList.filtered( e->e.getName().toLowerCase().matches(".*"+sponsorNameField.getText().toLowerCase()+".*"));
       sponsorTable.setItems(filtered);
       sponsorSidebarController.setSponsor(filtered.get(0));

    }

    @FXML
    void selectionChange(MouseEvent event) {    // komunkikacija sa sidebar kontolerom, postavlja sponzora o kojem treba prikazati detaljne inormacije
        if(sponsorTable.getSelectionModel().getSelectedItem()!=null){
            sponsorSidebarController.setSponsor(sponsorTable.getSelectionModel().getSelectedItem());
        }
    }
    public void setSponsorSidebarController(SponsorSidebarController sponsorSidebarController) {
        this.sponsorSidebarController = sponsorSidebarController;
        sponsorSidebarController.setSponsor(sponsorList.get(0));
    }

    @FXML
    void initialize()  {
        sponsorList= new FilteredList<Sponsor>(FXCollections.observableList(DAOFactory.getDAOFactory().getSponsorDAO().sponsors()));
        sponsorTable.setItems(sponsorList);
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/add_edit_sponsors.fxml"));
            Parent root = loader.load();
            addEditSponsorController = loader.getController();
            addEditSponsorStage = new Stage();
            addEditSponsorStage.initModality(Modality.APPLICATION_MODAL);
            addEditSponsorStage.setScene(new Scene(root));
        }catch (Exception e){e.printStackTrace();};
        try {
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
            VBox alert=loader.load();
            alertController=loader.getController();
            alertStage=new Stage();
            alertStage.setScene(new Scene(alert));

        }catch (Exception e){e.printStackTrace();}
        sponsorTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        sponsorTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("kind"));
        sponsorTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

    }
}
