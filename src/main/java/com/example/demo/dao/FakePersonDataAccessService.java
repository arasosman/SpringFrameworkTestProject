package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 0;
    }

    @Override
    public List<Person> selectAllPeople() {
        return null;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePerson(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if(person.isEmpty()){
            return 0;
        }
        DB.remove(person.get());
        return 1;
    }

    @Override
    public int updatePerson(UUID id, Person person) {
        return selectPersonById(id)
                .map(person1 -> {
                    int index = DB.indexOf(person1);
                    if(index >= 0){
                        DB.set(index, new Person(id, person.getName()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }
}
