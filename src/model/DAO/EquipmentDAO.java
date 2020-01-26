package model.DAO;

import model.DTO.Equipment;

import java.util.List;

public interface EquipmentDAO {

    List<Equipment> equipment();
    Equipment getEquipmentById(int id);
    boolean insert(Equipment equipment);
    boolean update(Equipment equipment);
}
