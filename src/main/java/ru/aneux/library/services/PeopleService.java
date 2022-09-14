package ru.aneux.library.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aneux.library.models.Book;
import ru.aneux.library.models.Person;
import ru.aneux.library.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
	private final PeopleRepository peopleRepository;

	@Autowired
	public PeopleService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	public List<Person> findAll() {
		return peopleRepository.findAll();
	}

	public Person findOne(int id, boolean initBooks) {
		Person person = peopleRepository.findById(id).orElse(null);
		if (person != null && initBooks) {
			Hibernate.initialize(person.getBooks());
			// Check if any book has expired for certain holder
			int tenDaysMillis = 10 * 24 * 60 * 60 * 1000;
			for (Book book : person.getBooks())
				book.setExpired(new Date().getTime() - book.getTakenAt().getTime() > tenDaysMillis);
		}
		return person;
	}

	public Person findOne(int id) {
		return findOne(id, false);
	}

	public boolean checkPersonExistenceByFullName(Person person) {
		return peopleRepository.findByFirstNameAndSecondNameAndLastName(
				person.getFirstName(), person.getSecondName(), person.getLastName()).isPresent();
	}

	@Transactional
	public int save(Person person) {
		return peopleRepository.save(person).getId();
	}

	@Transactional
	public void delete(int id) {
		peopleRepository.deleteById(id);
	}
}
