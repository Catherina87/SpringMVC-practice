package ru.kate.spring.dao;

import org.springframework.stereotype.Component;
import ru.kate.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    // Create a connection to our database using jdbc:
    private static Connection connection;


    static {

        // we make sure the class Driver is loaded in RAM,
        // and we can use it
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Now that we have a driver to work with the database,
        // we can use it and connect to the database:
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("CONNECTION IS CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // END OF STATIC BLOCK



    public List<Person> index() {
        List<Person> people = new ArrayList<>();


        // Statement is an object that contains SQL query to DB:
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";

            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                 Person person = new Person();

                 person.setId(resultSet.getInt("id"));
                 person.setName(resultSet.getString("name"));
                 person.setAge(resultSet.getInt("age"));
                 person.setEmail(resultSet.getString("email"));

                 people.add(person);
                 System.out.println("INSIDE WHILE LOOP");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("CANNOT BELIEVE I AM HERE");
        }

        return people;
    } // END OF INDEX METHOD


     public Person show(int id) {
         Person person = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            // initially the cursor is positioned before the first row,
            // so to get the first row, we need to move the cursor using next()
            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }


        return person;
     } // END OF SHOW METHOD


     public void save(Person person) {

        // TODO: create id so that it auto increments

         try {
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO Person(id, name, age, email) VALUES(4, ?, ?, ?)");

             preparedStatement.setString(1, person.getName());
             preparedStatement.setInt(2, person.getAge());
             preparedStatement.setString(3, person.getEmail());

             preparedStatement.executeUpdate();

         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }

     } // END OF SAVE METHOD


     public void update(int id, Person updatedPerson) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();


        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

     } // END OF UPDATE METHOD


     public void delete(int id) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();


        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

     } // END OF DELETE METHOD
}
