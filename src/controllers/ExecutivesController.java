package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DTO.Manager;
import model.util.FKSvodnaUtilities;

import java.util.List;

public class ExecutivesController {

    @FXML
    private TableView<Manager> executivesTableView;
    @FXML
    private TextField searchTextField;

    private List<Manager> listOfExecutives;

    @FXML
    private void initialize() {
        executivesTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        executivesTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("surname"));
        executivesTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        executivesTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        executivesTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("position"));

        displayExecutives();
    }

    public void addExecutive() {

    }

    public void editExecutive() {

    }

    public void deleteExecutive() {

    }

    private void displayExecutives() {
        try {
            executivesTableView.getItems().clear();
            executivesTableView.refresh();
            listOfExecutives = FKSvodnaUtilities.getDAOFactory().getManagerDAO().managers();

            for(Manager manager : listOfExecutives) {
                executivesTableView.getItems().add(manager);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
