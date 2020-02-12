package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DTO.ContactPerson;
import model.DTO.Sponsor;
import model.DTO.SponsorContactPerson;
import model.DAO.DAOFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactPersonController {

    @FXML
    private Label sponsorlabel;

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField lastNameLabel;

    @FXML
    private TextField phoneNumberLabel;

    @FXML
    private TableView<ContactPerson> contactTable;
    private Sponsor sponsor;
    private ObservableList<ContactPerson> contactPersonObservableList;


    @FXML
    void addContactPerson(ActionEvent event) {
        if(!nameLabel.getText().isEmpty() && !lastNameLabel.getText().isEmpty() && !phoneNumberLabel.getText().isEmpty()){
            ContactPerson contactPerson=new ContactPerson();
            contactPerson.setName(nameLabel.getText());
            contactPerson.setSurname(lastNameLabel.getText());
            contactPerson.setPhoneNumber(phoneNumberLabel.getText());
            DAOFactory.getDAOFactory().getContactPersonDAO().insert(contactPerson);
            DAOFactory.getDAOFactory().getsponsorContractPersonDAO().insert(new SponsorContactPerson(sponsor,contactPerson));
            reloadTable();
        }
    }
    @FXML
    void deleteContact(ActionEvent event) {
       if(!contactTable.getSelectionModel().isEmpty()){
           ContactPerson contactPerson=contactTable.getSelectionModel().getSelectedItem();
           DAOFactory.getDAOFactory().getContactPersonDAO().delete(contactPerson);
           reloadTable();
       }

    }

    @FXML
    void close(ActionEvent event){
        contactTable.getSelectionModel().clearSelection();
        contactTable.getScene().getWindow().hide();
    }
    @FXML
    void initialize() {
        contactTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        contactTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        contactTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    public void setSponsor(Sponsor sponsor){
        this.sponsor=sponsor;
        System.out.println(sponsor);
        sponsorlabel.setText(sponsor.getName());
        reloadTable();
    }
    private void reloadTable(){
        List<SponsorContactPerson> sponsorContactPersonList= DAOFactory.getDAOFactory().getsponsorContractPersonDAO().sponsorContactPersons();
        List<ContactPerson> contactPersonList =sponsorContactPersonList.stream()
                .filter(e->e.getSponsor().equals(sponsor))
                .map(e->e.getContactPerson()).collect(Collectors.toList());
        contactPersonObservableList= FXCollections.observableList(contactPersonList);
        contactTable.setItems(contactPersonObservableList);
    }
}
