package ru.aneux.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.aneux.library.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Person> personRowMapper = new BeanPropertyRowMapper<>(Person.class);

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("select * from person", personRowMapper);
    }

    public Person getPerson(int id) {
        return jdbcTemplate.query("select * from person where id = ?", personRowMapper, id).stream().findAny().orElse(null);
    }

    public boolean checkPersonExistenceByFullName(Person person) {
        return !jdbcTemplate.query("select * from person where first_name = ? and second_name = ? and last_name = ?",
                personRowMapper, person.getFirstName(), person.getSecondName(), person.getLastName()).isEmpty();
    }

    public Integer addPerson(Person person) {
        return jdbcTemplate.queryForObject("insert into person(first_name, second_name, last_name, birth_year) values (?, ?, ?, ?) returning id",
                Integer.class, person.getFirstName(), person.getSecondName(), person.getLastName(), person.getBirthYear());
    }

    public void updatePerson(Person person) {
        jdbcTemplate.update("update person set first_name = ?, second_name = ?, last_name = ?, birth_year = ? where id = ?",
                person.getFirstName(), person.getSecondName(), person.getLastName(), person.getBirthYear(), person.getId());
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("delete from person where id = ?", id);
    }
}
