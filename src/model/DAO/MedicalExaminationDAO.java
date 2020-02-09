package model.DAO;

import model.DTO.MedicalExamination;

import java.util.List;

public interface MedicalExaminationDAO {

    List<MedicalExamination> medicalExaminations();
    MedicalExamination getMedicalExaminationById(int id);
    List<MedicalExamination> getAlerts();
    boolean insert(MedicalExamination medicalExamination);
    boolean update(MedicalExamination medicalExamination);
}
