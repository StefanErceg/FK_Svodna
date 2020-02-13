package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
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


    @FXML
    void addContactPerson(ActionEvent event) {
        if(!nameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty()){
            ContactPerson contactPerson=new ContactPerson();
            contactPerson.setName(nameTextField.getText());
            contactPerson.setSurname(lastNameTextField.getText());
            contactPerson.setPhoneNumber(phoneNumberTextField.getText());
            DAOFactory.getDAOFactory().getContactPersonDAO().insert(contactPerson);
            DAOFactory.getDAOFactory().getsponsorContractPersonDAO().insert(new SponsorContactPerson(sponsor,contactPerson));
            reloadTable();
        }
    }
    @FXML
    void deleteContact(ActionEvent event) { //brise kontakt osobu iz tabele i baze
       if(!contactTable.getSelectionModel().isEmpty()){
           ContactPerson contactPerson=contactTable.getSelectionModel().getSelectedItem();
           DAOFactory.getDAOFactory().getContactPersonDAO().delete(contactPerson);
           reloadTable();
       }

    }

    @FXML
    void close(ActionEvent event){ //zatvara formu -- sakriva je i cisti polja
        contactTable.getSelectionModel().clearSelection();
        contactTable.getScene().getWindow().hide();
    }
    @FXML
    void initialize() {
        contactTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        contactTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        contactTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

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
}
