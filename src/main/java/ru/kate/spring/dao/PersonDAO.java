package ru.kate.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kate.spring.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    // create JdbcTemplate field:
    private final JdbcTemplate jdbcTemplate;

    // inject jdbcTemplate object (bean) initialized in SpringConfig file, that Spring has created
    // to our PersonDAO:
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {

        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    } // END OF INDEX METHOD


    // BeanPropertyRowMapper is provided by Spring and can be used instead the PersonMapper that we created.
    // It does the same thing - it will set every data item from the table to our Person class fields.


     public Person show(int id) {

        // new Object[]{id} - this is where we store the values needed for the preparedStatement (instead ?)
         // this query returns a list, but we need to return a Person object, for this reason we use stream.
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream()
                // we will either find one person or nothing, so we use findAny()
                // orElse() means if we don't find anything, we return null. (We could also return an error
                // message inside orElse)
                .findAny().orElse(null);

     } // END OF SHOW METHOD


     public void save(Person person) {

        // TODO: create id so that it auto increments

         jdbcTemplate.update("INSERT INTO Person(id, name, age, email) VALUES(4, ?, ?, ?)",
                 person.getName(), person.getAge(), person.getEmail());

     } // END OF SAVE METHOD


     public void update(int id, Person updatedPerson) {

        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);

     } // END OF UPDATE METHOD


     public void delete(int id) {

        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);

     } // END OF DELETE METHOD
}
