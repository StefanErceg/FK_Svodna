package model.DAO;

import model.DTO.Obligation;

import java.util.List;

public interface ObligationDAO {

    List<Obligation> obligations();
    Obligation getObligationById(int id);
    boolean insert(Obligation obligation);
    boolean update(Obligation obligation);
    boolean delete(Obligation obligation);
}
