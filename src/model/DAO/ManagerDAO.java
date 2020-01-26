package model.DAO;

import model.DTO.Manager;

import java.util.List;

public interface ManagerDAO {

    List<Manager> managers();
    Manager getManagerById(int id);
    boolean insert(Manager manager);
    boolean update(Manager manager);
    boolean delete(Manager manager);
}
