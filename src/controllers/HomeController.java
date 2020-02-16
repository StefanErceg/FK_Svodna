package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.DTO.MedicalExamination;
import model.DTO.Obligation;
import model.util.FKSvodnaUtilities;

import java.util.List;

public class HomeController {


    @FXML
    private VBox gameObligationsVBox;

    @FXML
    private ListView<MedicalExamination> medicalExaminationsListVIew;

    private CheckBox tempCheckBox;


    @FXML
    void initialize() {
        List<MedicalExamination> examinationsToDo = FKSvodnaUtilities.getDAOFactory().getMedicalExaminationDAO().getAlerts();
        medicalExaminationsListVIew.getItems().addAll(examinationsToDo);
        List<Obligation> obligations = FKSvodnaUtilities.getDAOFactory().getObligationDAO().getObligationsForMatch(FKSvodnaUtilities.getDAOFactory().getMatchDAO().getFirstMatch().getId());
        for (Obligation obligation : obligations) {
            tempCheckBox = new CheckBox();
            tempCheckBox.setText(obligation.getDescription());
            tempCheckBox.setFont(Font.font(16));
            tempCheckBox.setTextFill(Paint.valueOf("WHITE"));
            tempCheckBox.setSelected(obligation.isDone());
            tempCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                obligation.setDone(newValue);
                FKSvodnaUtilities.getDAOFactory().getObligationDAO().update(obligation);
            });
            gameObligationsVBox.getChildren().add(tempCheckBox);
        }
    }
}
