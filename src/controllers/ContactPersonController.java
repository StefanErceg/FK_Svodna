package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FormatStringConverter;
import model.DTO.ContactPerson;
import model.DTO.Sponsor;
import model.DTO.SponsorContactPerson;
import model.DAO.DAOFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ContactPersonController {

    @FXML
    private Label sponsorlabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TableView<ContactPerson> contactTable;
    private Sponsor sponsor;
    private ObservableList<ContactPerson> contactPersonObservableList;
    private DecisionController decisionController;
    private Stage decisionStage;
    private Stage alertStage;
    private AlertController alertController;


    @FXML
    void addContactPerson(ActionEvent event) {
        if(!nameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty()){
            ContactPerson contactPerson=new ContactPerson();
            contactPerson.setName(nameTextField.getText().trim());
            contactPerson.setSurname(lastNameTextField.getText().trim());
            contactPerson.setPhoneNumber(phoneNumberTextField.getText().trim());
            if(!DAOFactory.getDAOFactory().getContactPersonDAO().insert(contactPerson) ||
            !DAOFactory.getDAOFactory().getsponsorContractPersonDAO().insert(new SponsorContactPerson(sponsor,contactPerson))){
                alertController.setText("Desila se greska pri upisu, kontakt nije dodan!");
                alertStage.showAndWait();
                DAOFactory.getDAOFactory().getContactPersonDAO().delete(contactPerson);
            }
            clearFields();
            reloadTable();
        }
        else {
            alertController.setText("Nisu unesena sva polja!");
            alertStage.show();
        }
        contactTable.getSelectionModel().clearSelection();
    }

    @FXML
    void deleteContact(ActionEvent event) { //brise kontakt osobu iz tabele i baze

       if(!contactTable.getSelectionModel().isEmpty() ){
           decisionController.getDecisionLabel().setText("Da li želite da obrišete kontakt osobu?");
           decisionStage.showAndWait();
           if(decisionController.returnResult()){
               ContactPerson contactPerson=contactTable.getSelectionModel().getSelectedItem();
               if(!DAOFactory.getDAOFactory().getContactPersonDAO().delete(contactPerson)) {
                   alertController.setText("Desila se greška pri brisanju, kontakt nije izbrisan!");
                   alertStage.showAndWait();
               }
               reloadTable();
           }
       }else{
           alertController.setText("Nije odabrana kontakt osoba!");
           alertStage.showAndWait();
       }
       contactTable.getSelectionModel().clearSelection();

    }

    @FXML
    void close(ActionEvent event){ //zatvara formu -- sakriva je i cisti polja
        contactTable.getSelectionModel().clearSelection();
        contactTable.getScene().getWindow().hide();
    }
    @FXML
    void initialize() throws Exception {
        contactTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        contactTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        contactTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();
        decisionController = loader.getController();

        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));
        decisionStage.setTitle("Potvrda");
        alertStage.setTitle("Upozorenje");

    }

    public void setSponsor(Sponsor sponsor){    //postavlja podatke o kontakt osobama za specificnog sponzora
        this.sponsor=sponsor;

        sponsorlabel.setText(sponsor.getName());
        reloadTable();
    }
    private void reloadTable(){ //ponovno uvitava podatke iz baze
        List<SponsorContactPerson> sponsorContactPersonList= DAOFactory.getDAOFactory().getsponsorContractPersonDAO().sponsorContactPersons();
        List<ContactPerson> contactPersonList =sponsorContactPersonList.stream()
                .filter(e->e.getSponsor().equals(sponsor))
                .map(e->e.getContactPerson()).collect(Collectors.toList());
        contactPersonObservableList= FXCollections.observableList(contactPersonList);
        contactTable.setItems(contactPersonObservableList);
    }

    public void clearFields(){
        nameTextField.setText("");
        lastNameTextField.setText("");
        phoneNumberTextField.setText("");
    }
}
