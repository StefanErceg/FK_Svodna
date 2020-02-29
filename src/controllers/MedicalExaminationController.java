package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.MedicalExamination;
import model.DTO.Person;
import model.util.FKSvodnaUtilities;

import java.io.IOException;
import java.sql.Timestamp;

public class MedicalExaminationController {
    @FXML
    private Button addEditButton;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;

    private MedicalExamination medicalExamination;
    private Person player;
    private Stage alertStage;
    private AlertController alertController;

    public void initialize() throws IOException {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
    }
    @FXML
    public void save(){
        if(checkFields()){
            medicalExamination = new MedicalExamination(0, Timestamp.valueOf(dateFrom.getValue().atStartOfDay()),Timestamp.valueOf(dateTo.getValue().atStartOfDay()),player);
            FKSvodnaUtilities.getDAOFactory().getMedicalExaminationDAO().insert(medicalExamination);
        }
        else{
            try {
                alertController.setText("Nisu unesena sva polja");
                alertStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        quit();
    }

    @FXML
    public void quit() {
        ((Stage)addEditButton.getScene().getWindow()).close();
    }

    public MedicalExamination getMedicalExamination() {
        return medicalExamination;
    }

    public void setMedicalExamination(MedicalExamination medicalExamination) {
        this.medicalExamination = medicalExamination;
    }

    public Person getPlayer() {
        return player;
    }

    public void setPlayer(Person player) {
        this.player = player;
    }

    private boolean checkFields(){
        return dateFrom.getValue()!=null && dateTo.getValue()!=null;
    }

    public void clearFields(){
        dateFrom.setValue(null);
        dateTo.setValue(null);
    }
}
