package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        List<SponsorContactPerson> sponsorContactPersonList= DAOFactory.getDAOFactory().getsponsorContractPersonDAO().sponsorContactPersons();
        System.out.println("before filter"+sponsorContactPersonList);
        List<ContactPerson> contactPersonList =sponsorContactPersonList.stream()
                                                            .filter(e->e.getSponsor().equals(sponsor))
                                                            .map(e->e.getContactPerson()).collect(Collectors.toList());
        contactPersonObservableList= FXCollections.observableList(contactPersonList);
        System.out.println("after filter"+contactPersonList);
        contactTable.setItems(contactPersonObservableList);
    }
}
