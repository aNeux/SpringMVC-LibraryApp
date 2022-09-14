package ru.aneux.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aneux.library.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findByFirstNameAndSecondNameAndLastName(String firstName, String secondName, String lastName);
}
