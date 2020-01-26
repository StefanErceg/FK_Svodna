package model.DAO;

import model.DTO.Person;

import java.util.List;

public interface PersonDAO {

    List<Person> persons();
    Person getPersonById(int id);
    boolean insert(Person person);
    boolean update(Person person);
    boolean delete(Person person);
}
