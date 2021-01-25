package ru.kate.spring.dao;

import org.springframework.stereotype.Component;
import ru.kate.spring.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Mary"));
        people.add(new Person(++PEOPLE_COUNT, "Jane"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
    }

    public List<Person> index() {
        return people;
    }

     public Person show(int id) {

        for(Person person : this.people) {
            if(person.getId() == id) {
                return person;
            }
        }

        return null;
     }

     public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        this.people.add(person);
     }

     public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = this.show(id);

        personToBeUpdated.setName(updatedPerson.getName());
     }

     public void delete(int id) {
        this.people.removeIf(p -> p.getId() == id);
     }
}
