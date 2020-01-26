package model.DAO;

import model.DTO.ContactPerson;

import java.util.List;

public interface ContactPersonDAO {

    List<ContactPerson> contactPersons();
    ContactPerson getContactPersonById(int id);
    boolean insert(ContactPerson contactPerson);
    boolean update(ContactPerson contactPerson);
    boolean delete(ContactPerson contactPerson);
}
