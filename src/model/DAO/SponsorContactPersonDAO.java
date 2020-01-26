package model.DAO;

import model.DTO.SponsorContactPerson;

import java.util.List;

public interface SponsorContactPersonDAO {

    List<SponsorContactPerson> sponsorContactPersons();
    SponsorContactPerson getSponsorContactPersonByIds(int sponsorId, int contactPersonId);
    boolean insert(SponsorContactPerson sponsorContactPerson);
}
