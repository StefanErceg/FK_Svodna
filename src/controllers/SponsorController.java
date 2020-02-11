package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SponsorController {

    @FXML
    private TextField sponsorFirstNameField;

    @FXML
    private TextField sponsorLastNameField;

    @FXML
    private TableView<?> sponsorTable;

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
    void deleteSponsor(ActionEvent event) {

    }

    @FXML
    void sponsorSearch(ActionEvent event) {

    }

    @FXML
    void initialize() throws Exception {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/add_edit_sponsors.fxml"));
        Parent root=loader.load();
        addEditSponsorController=loader.getController();
        addEditSponsorStage=new Stage();
        addEditSponsorStage.initModality(Modality.APPLICATION_MODAL);
        addEditSponsorStage.setScene(new Scene(root));


    }
}
