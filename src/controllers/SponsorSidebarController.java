package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DTO.ContactPerson;
import model.DTO.Payment;
import model.DTO.Sponsor;
import model.DTO.SponsorContactPerson;
import model.util.FKSvodnaUtilities;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.Format;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SponsorSidebarController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label adressLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label jibLabel;
    
    @FXML
    private Label contactPersonLabel;

    @FXML
    private Button contactPersonButton;
    
    private DirectoryChooser directoryChooser;

    Sponsor sponsor;
    private Stage alertStage;
    private AlertController alertController;


    @FXML
    void showContactPersons(ActionEvent event){
        if(sponsor==null){
            alertController.setText("Nije odabran sponzor!");
            alertStage.showAndWait();
            return;
        }
        try{
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/contact_persons.fxml"));
            Parent root=loader.load();
            ContactPersonController contactPersonController=loader.getController();
            contactPersonController.setSponsor(sponsor);
            Stage secondaryStage=new Stage();
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(root));
            secondaryStage.setTitle("Kontakt osobe");
            secondaryStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
            secondaryStage.show();
        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void showPayments(ActionEvent event)  {
        if(sponsor==null){
            alertController.setText("Nije odabran sponzor!");
            alertStage.showAndWait();
            return;
        }
        try{
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/payments.fxml"));
            Parent root=loader.load();
            PaymentController paymentController=loader.getController();
            paymentController.setSponsor(sponsor);
            Stage secondaryStage=new Stage();
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(root));
            secondaryStage.setTitle("Uplate");
            secondaryStage.getIcons().add(new Image("file:" + "src" + File.separator + "view" + File.separator + "icons" + File.separator + "soccer.png"));
            secondaryStage.show();
        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void initialize() throws Exception {
        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/alert.fxml"));
        Parent root=loader.load();
        alertController=loader.getController();
        alertStage=new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle("Upozorenje");
        loader = new FXMLLoader(this.getClass().getResource("../view/decision.fxml"));
        root = loader.load();

        directoryChooser = new DirectoryChooser();

    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
        nameLabel.setText(sponsor.getName());
        adressLabel.setText(sponsor.getAddress());
        emailLabel.setText(sponsor.getEmail());
        jibLabel.setText(sponsor.getJmbjib());
        phoneNumberLabel.setText(sponsor.getPhoneNumber());
        typeLabel.setText(sponsor.getKind());
    }
    public void clearSponsor(){
        this.sponsor=null;
        nameLabel.setText("");
        adressLabel.setText("");
        emailLabel.setText("");
        jibLabel.setText("");
        phoneNumberLabel.setText("");
        typeLabel.setText("");
    }

    public Label getContactPersonLabel() {
        return contactPersonLabel;
    }

    public void setContactPersonLabel(Label contactPersonLabel) {
        this.contactPersonLabel = contactPersonLabel;
    }

    public Button getContactPersonButton() {
        return contactPersonButton;
    }

    public void setContactPersonButton(Button contactPersonButton) {
        this.contactPersonButton = contactPersonButton;
    }

    public void printSponsors(ActionEvent actionEvent) {
        if(sponsor!=null) {
            try {
                XWPFDocument document = new XWPFDocument();
                XWPFParagraph tmpParagraph = document.createParagraph();
                XWPFRun tmpRun = tmpParagraph.createRun();
                tmpRun.setText("Podaci o sponzoru:");
                tmpRun.addBreak();tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Ime:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();
                tmpRun.setText(sponsor.getName());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Adresa:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();
                tmpRun.setText(sponsor.getAddress());
                tmpRun.addBreak();tmpRun.addBreak();
                tmpRun.setText("Broj telefona:");tmpRun.addTab();tmpRun.addTab();
                tmpRun.setText(sponsor.getPhoneNumber());
                tmpRun.addBreak();tmpRun.addBreak();
                if(sponsor.getKind().equals("Fizičko lice")){
                    tmpRun.setText("Matični broj:");tmpRun.addTab();tmpRun.addTab();
                    tmpRun.setText(sponsor.getJmbjib());
                }else {
                    tmpRun.setText("JIB:");tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();tmpRun.addTab();
                    tmpRun.setText(sponsor.getJmbjib());
                    tmpRun.addBreak();tmpRun.addBreak();
                    tmpRun.setText("Kontakt osobe:");
                    List<SponsorContactPerson> sponsorContactPersonList = DAOFactory.getDAOFactory().getsponsorContractPersonDAO().sponsorContactPersons();
                    List<ContactPerson> contactPersonList = sponsorContactPersonList.stream()
                            .filter(e -> e.getSponsor().equals(sponsor))
                            .map(e -> e.getContactPerson()).collect(Collectors.toList());
                    XWPFTable table = document.createTable(contactPersonList.size() + 1, 3);
                    table.setWidth(8000);
                    XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
                    p1.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun r1 = p1.createRun();
                    r1.setBold(true);r1.setFontSize(15);r1.setText("Ime");
                    XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
                    p2.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun r2 = p2.createRun();
                    r2.setBold(true);r2.setFontSize(15);r2.setText("Prezime");
                    XWPFParagraph p3 = table.getRow(0).getCell(2).getParagraphs().get(0);
                    p3.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun r3 = p3.createRun();
                    r3.setBold(true);r3.setFontSize(15);r3.setText("Broj telefona");
                    for (int j = 1; j <= contactPersonList.size(); j++) {
                        XWPFParagraph p = table.getRow(j).getCell(0).getParagraphs().get(0);
                        p.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun r = p.createRun();
                        r.setFontSize(14);r.setText(contactPersonList.get(j - 1).getName());
                        p = table.getRow(j).getCell(1).getParagraphs().get(0);
                        p.setAlignment(ParagraphAlignment.CENTER);
                        r = p.createRun();r.setFontSize(14);r.setText(contactPersonList.get(j - 1).getSurname());
                        p = table.getRow(j).getCell(2).getParagraphs().get(0);
                        p.setAlignment(ParagraphAlignment.CENTER);
                        r = p.createRun();r.setFontSize(14);r.setText(contactPersonList.get(j - 1).getPhoneNumber());
                    }
                }
                XWPFParagraph tmpParagraph2 = document.createParagraph();
                XWPFRun tmpRun2 = tmpParagraph2.createRun();
                tmpRun2.setFontSize(18);
                tmpRun2.addBreak();
                tmpRun2.setText("Uplate:");

                List<Payment> paymentList= DAOFactory.getDAOFactory().geTPaymentDAO().payments().stream()
                        .filter(e->e.getSponsor().equals(sponsor)).collect(Collectors.toList());
                XWPFTable table = document.createTable(paymentList.size() + 1, 3);
                table.setWidth(8000);
                XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
                p1.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r1 = p1.createRun();
                r1.setBold(true);r1.setFontSize(15);r1.setText("Datum uplate");
                XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
                p2.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r2 = p2.createRun();
                r2.setBold(true);r2.setFontSize(15);r2.setText("Datum isteka");
                XWPFParagraph p3 = table.getRow(0).getCell(2).getParagraphs().get(0);
                p3.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r3 = p3.createRun();
                r3.setBold(true);r3.setFontSize(15);r3.setText("Iznos");
                for (int j = 1; j <= paymentList.size(); j++) {
                    XWPFParagraph p = table.getRow(j).getCell(0).getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun r = p.createRun();
                    r.setFontSize(14);r.setText(paymentList.get(j - 1).getPaymentDate().toLocalDateTime().toLocalDate().toString());
                    p = table.getRow(j).getCell(1).getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();r.setFontSize(14);
                    if(paymentList.get(j-1).getExpirationDate()!=null)
                        r.setText(paymentList.get(j - 1).getExpirationDate().toLocalDateTime().toLocalDate().toString());
                    else
                        r.setText("");
                    p = table.getRow(j).getCell(2).getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    r = p.createRun();r.setFontSize(14);r.setText("" + paymentList.get(j - 1).getAmount());
                }

                tmpRun.setFontSize(18);
                File selectedDirectory = directoryChooser.showDialog(alertStage);
                if (selectedDirectory != null) {
                    FileOutputStream fos = new FileOutputStream(new File(selectedDirectory.getPath() + File.separator + sponsor.getName() + ".docx"));
                    document.write(fos);
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            alertController.setText("Nije izabran sponzor");
            alertStage.showAndWait();
        }
    }
}
