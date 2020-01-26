package model.DAO;

import model.DTO.Sponsor;

import java.util.List;

public interface SponsorDAO {

    List<Sponsor> sponsors();
    Sponsor getSponsorById(int id);
    boolean insert(Sponsor sponsor);
    boolean update(Sponsor sponsor);
    boolean delete(Sponsor sponsor);

}
