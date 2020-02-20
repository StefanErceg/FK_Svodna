package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DTO.Card;
import model.DTO.Person;
import model.DTO.Punishment;
import model.util.FKSvodnaUtilities;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FinesController {

    @FXML
    private TableView<Punishment> finesTable;

    @FXML
    private Label playerLabel;

    @FXML
    private TextField suspensionTxtField;

    @FXML
    private RadioButton redCardButton;

    @FXML
    private RadioButton yellowCardButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionTxtField;

    @FXML
    private TextField priceTxtField;

    @FXML
    private ToggleGroup card;

    private Person player;
    private ObservableList<Punishment> punishmentObservableList;
    private Stage alertStage;
    private AlertController alertController;
    private DecisionController decisionController;
    private Stage decisionStage;

    @FXML
    void add(ActionEvent event) {
        Punishment punishment = new Punishment();
        LocalDate localDate = datePicker.getValue();
        Date date = Date.valueOf(localDate);
        punishment.setDate(new Timestamp(date.getTime()));
        punishment.setMonetaryAmount(Double.parseDouble(priceTxtField.getText()));
        if(yellowCardButton.isSelected()) {
            punishment.setCard(Card.Zuti);
        } else if(redCardButton.isSelected()) {
            punishment.setCard(Card.Crveni);
        }
        punishment.setSuspensionMatchesNumber(Integer.parseInt(suspensionTxtField.getText()));
        punishment.setDescription(descriptionTxtField.getText());
        punishment.setPerson(player);
        if(!DAOFactory.getDAOFactory().getPunishmentDAO().insert(punishment)){
            alertController.setText("Desila se greska pri upisu, kazna nije dodana.");
            alertStage.showAndWait();
          //  DAOFactory.getDAOFactory().getPunishmentDAO().returnEquipment(equipment);
        }
        reloadTable();
        finesTable.getSelectionModel().clearSelection();
    }

    @FXML
    void close(ActionEvent event) {
        finesTable.getSelectionModel().clearSelection();
        finesTable.getScene().getWindow().hide();
    }

    @FXML
    void initialize() throws Exception {
        finesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        finesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("monetaryAmount"));
        finesTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("card"));
        finesTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("suspensionMatchesNumber"));
        finesTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("description"));

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
        List<Punishment> punishmentsList= DAOFactory.getDAOFactory().getPunishmentDAO().panishments();
        List<Punishment>  playerPunishmentsList = punishmentsList.stream()
                .filter(e->e.getPerson().equals(player)).collect(Collectors.toList());
        punishmentObservableList= FXCollections.observableList(playerPunishmentsList);
        finesTable.setItems(punishmentObservableList);
    }

    public void setPlayer(Person player){
        this.player = player;
        playerLabel.setText(player.getName());
        reloadTable();
    }
}
