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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DTO.ContactPerson;
import model.DTO.Equipment;
import model.DTO.Person;
import model.DTO.SponsorContactPerson;

import java.util.List;
import java.util.stream.Collectors;

public class EquipmentController {

    @FXML
    private TextField typeTxtField;

    @FXML
    private Label playerLabel;

    @FXML
    private TableView<Equipment> equipmentTable;

    @FXML
    private TextField numberTxtField;

    @FXML
    private TextField codeTxtField;

    private Person player;
    private ObservableList<Equipment> equipmentObservableList;
    private Stage alertStage;
    private AlertController alertController;
    private DecisionController decisionController;
    private Stage decisionStage;

    @FXML
    void addEquipment(ActionEvent event) { //dodaje opremu u tabelu
        if(!typeTxtField.getText().isEmpty() && !numberTxtField.getText().isEmpty() && !codeTxtField.getText().isEmpty()){
            Equipment equipment = new Equipment();
            equipment.setType(typeTxtField.getText().trim());
            equipment.setJerseyNumber(Integer.parseInt(numberTxtField.getText().trim()));
            equipment.setCode(codeTxtField.getText().trim());
            if(!DAOFactory.getDAOFactory().getEquipmentDAO().insert(equipment)){
                alertController.setText("Desila se greska pri upisu, oprema nije dodana.");
                alertStage.showAndWait();
                DAOFactory.getDAOFactory().getEquipmentDAO().update(equipment);
            }
            reloadTable();
        }
        equipmentTable.getSelectionModel().clearSelection();
    }

    @FXML
    void deleteEquipment(ActionEvent event) { //ukljanja opremu iz tabele
        if(!equipmentTable.getSelectionModel().isEmpty() ){
            decisionController.getDecisionLabel().setText("Jeste li sigurni da zelite da izbrisete izabranu opremu?");
            decisionStage.showAndWait();
            if(decisionController.returnResult()){
                Equipment equipment= (Equipment) equipmentTable.getSelectionModel().getSelectedItem();
                if(!DAOFactory.getDAOFactory().getEquipmentDAO().update(equipment)) {
                    alertController.setText("Desila se greska pri brisanju, oprema nije izbrisana.");
                    alertStage.showAndWait();
                }
                reloadTable();
            }
        }else{
            alertController.setText("Nije izabrana oprema za razduzivanje.");
            alertStage.showAndWait();
        }
        equipmentTable.getSelectionModel().clearSelection();
    }

    @FXML
    void close(ActionEvent event) {
        equipmentTable.getSelectionModel().clearSelection();
        equipmentTable.getScene().getWindow().hide();
    }

    @FXML
    void initialize() throws  Exception{
        equipmentTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("type"));
        equipmentTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("jerseyNumber"));
        equipmentTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("code"));

        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();
        decisionController = loader.getController();

        decisionStage = new Stage();
        decisionStage.initModality(Modality.APPLICATION_MODAL);
        decisionStage.setScene(new Scene(root));

    }

    private void reloadTable(){
        List<Equipment> equipmentList= DAOFactory.getDAOFactory().getEquipmentDAO().equipment();
        List<Equipment>  playerEquipmentList = equipmentList.stream()
                .filter(e->e.getPerson().equals(player)).collect(Collectors.toList());
        equipmentObservableList= FXCollections.observableList(playerEquipmentList);
        equipmentTable.setItems(equipmentObservableList);
    }

    public void setPlayer(Person player){
        this.player = player;
        playerLabel.setText(player.getName());
        reloadTable();
    }
}
