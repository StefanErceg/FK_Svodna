package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Sponsor;
import model.util.FKSvodnaUtilities;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSidebarController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label licenceNumberLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label jmbgLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label teamLabel;
    @FXML
    private Label jerseyLabel;

    private DirectoryChooser directoryChooser;

    private Person person;
    private AlertController alertController;
    private Stage alertStage;
    private FinesController finesController;
    private Stage finesStage;
    private MedicalExaminationController medicalExaminationController;
    private Stage medicalExaminationStage;
    private Stage equipmentStage;
    private EquipmentController equipmentController;

    public void initialize() throws IOException {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle("Upozorenje");
        loader = new FXMLLoader(this.getClass().getResource("../view/fines.fxml"));
        root = loader.load();
        finesStage = new Stage();
        finesStage.setScene(new Scene(root));
        finesStage.initModality(Modality.APPLICATION_MODAL);
        finesStage.setTitle("Kazne");
        finesStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
        finesController = loader.getController();
        loader = new FXMLLoader(this.getClass().getResource("../view/medical_examinations.fxml"));
        root = loader.load();
        medicalExaminationStage = new Stage();
        medicalExaminationStage.setScene(new Scene(root));
        medicalExaminationStage.initModality(Modality.APPLICATION_MODAL);
        medicalExaminationStage.setTitle("Ljekarski pregled");
        medicalExaminationStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
        medicalExaminationController = loader.getController();
        loader=new FXMLLoader(this.getClass().getResource("../view/equipment.fxml"));
        root=loader.load();
        equipmentStage=new Stage();
        equipmentStage.setScene(new Scene(root));
        equipmentStage.initModality(Modality.APPLICATION_MODAL);
        equipmentStage.setTitle("Oprema");
        equipmentStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
        equipmentController=loader.getController();

        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Odabir foldera");

    }

    public void setPlayer(Person person) {
        clearPlayer();
        this.person = person;
        nameLabel.setText(person.getName());
        lastNameLabel.setText(person.getSurname());
        licenceNumberLabel.setText(person.getLicenceNumber());
        addressLabel.setText(person.getAddress());
        emailLabel.setText(person.getEmail());
        jmbgLabel.setText(person.getJmb());
        phoneNumberLabel.setText(person.getPhoneNumber());
        positionLabel.setText(FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getPlayerPosition());
        teamLabel.setText(FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getTeam().getName());
        jerseyLabel.setText("" + FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getJerseyNumber());
    }

    public void clearPlayer(){
        this.person=null;
        nameLabel.setText("");
        lastNameLabel.setText("");
        positionLabel.setText("");
        licenceNumberLabel.setText("");
        addressLabel.setText("");
        emailLabel.setText("");
        jmbgLabel.setText("");
        phoneNumberLabel.setText("");
        teamLabel.setText("");
        jerseyLabel.setText("");
    }

    @FXML
    public void checkPunishments(ActionEvent event){
        if(person!=null) {
            finesController.setPlayer(person);
            finesStage.showAndWait();
        }
        else{
            alertController.setText("Igrač nije odabran!");
            alertStage.show();
        }
    }
    @FXML
    void checkEquipment(ActionEvent event){
        if(person!=null){
            equipmentController.setPlayer(person);
            equipmentStage.showAndWait();
        }else {
            alertController.setText("Igrač nije odabran!");
            alertStage.showAndWait();
        }
    }
    @FXML
    public void addMedicalExamination(ActionEvent event){
        if(person!=null) {
            medicalExaminationController.clearFields();
            medicalExaminationController.setPlayer(person);
            medicalExaminationStage.showAndWait();
        }
        else{
            alertController.setText("Igrač nije odabran!");
            alertStage.show();
        }
    }

    @FXML
    public void printPlayer(ActionEvent actionEvent) {
        if(person!=null) {
            try {
                XWPFDocument document = new XWPFDocument();
                XWPFParagraph tmpParagraph = document.createParagraph();
                XWPFRun tmpRun = tmpParagraph.createRun();
                tmpRun.setText("Podaci o igraču:");
                tmpRun.addBreak();tmpRun.addBreak();tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Ime:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.setText(person.getName());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Prezime:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.setText(person.getSurname());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Matični broj:");tmpRun.addTab();tmpRun.addTab();tmpRun.setText(person.getJmb());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Adresa:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.setText(person.getAddress());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Broj telefona:");tmpRun.addTab();tmpRun.addTab();tmpRun.setText(person.getPhoneNumber());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Email:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.setText(person.getEmail());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Tim:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.setText(FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getTeam().getName());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Broj licence:");tmpRun.addTab();tmpRun.addTab();tmpRun.setText(person.getLicenceNumber());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Pozicija u timu:");tmpRun.addTab();tmpRun.setText(FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getPlayerPosition());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Broj dresa:");tmpRun.addTab();tmpRun.addTab();tmpRun.setText("" + FKSvodnaUtilities.getDAOFactory().getPersonTeamDAO().getTeamForPlayer(person).getJerseyNumber());

                tmpRun.setFontSize(18);
                File selectedDirectory = directoryChooser.showDialog(alertStage);
                if(selectedDirectory!=null) {
                    FileOutputStream fos = new FileOutputStream(new File(selectedDirectory.getPath() + File.separator + person.getName() + person.getSurname() + ".docx"));
                    document.write(fos);
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            alertController.setText("Igrač nije odabran!");
            alertStage.show();
        }
    }
}
