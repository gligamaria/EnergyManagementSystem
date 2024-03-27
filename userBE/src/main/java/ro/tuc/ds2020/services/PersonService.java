package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.dtos.builders.PersonBuilder;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findPersons() {
//        List<Person> personList = personRepository.findAll();
//        return personList.stream()
//                .map(PersonBuilder::toPersonDTO)
//                .collect(Collectors.toList());
        return personRepository.findAll();
    }

    public Person findPersonById(UUID id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
        return personOptional.get();
    }

    public List<String> getUserIds(){
        List<String> ids = new ArrayList<>();
        List<Person> users = this.findPersons();
        for(Person user:users){
            ids.add(user.getId().toString());
        }
        return ids;
    }

    public UUID insert(PersonDetailsDTO personDTO) {
        Person person = PersonBuilder.toEntity(personDTO);
        person = personRepository.save(person);
        LOGGER.debug("Person with id {} was inserted in db", person.getId());
        return person.getId();
    }

//    public UUID insert(Person person) {
//        person = personRepository.save(person);
//        LOGGER.debug("Person with id {} was inserted in db", person.getId());
//        return person.getId();
//    }

    public String deleteById(UUID personID){
        try{
            personRepository.deleteById(personID);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public Person retrieveByEmail(String email) {
        List<Person> users = personRepository.findAll();
        for(Person user:users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }
}
