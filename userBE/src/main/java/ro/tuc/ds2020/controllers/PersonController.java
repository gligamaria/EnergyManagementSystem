package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.PersonRepository;
import ro.tuc.ds2020.services.PersonService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @GetMapping( value = "/getAll")
    @ResponseBody
    public List<Person> retrieveQuestions() {
        return personService.findPersons();
    }
    /*public ResponseEntity<List<PersonDTO>> getPersons() {
        List<PersonDTO> dtos = personService.findPersons();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }*/

    @GetMapping( value = "/getUserIds")
    @ResponseBody
    public List<String> getUserIds() {
        return personService.getUserIds();
    }

    @PostMapping(value = "/insertPerson")
    public ResponseEntity<UUID> insertPerson(@Valid @RequestBody PersonDetailsDTO personDTO) {
        UUID id = personService.insert(personDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(value = "getById/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") UUID personId) {
        Person person = personService.findPersonById(personId);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable("id") UUID id){

        String answer = personService.deleteById(id);

        if(answer.equals("Success")){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/updatePerson/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") UUID userID,
                                               @RequestBody PersonDetailsDTO personDetailsDTO) {

        Person newUser = personService.findPersonById(userID);

        if(personDetailsDTO.getName() != null){
            newUser.setName(personDetailsDTO.getName());
        }
        if(personDetailsDTO.getEmail() != null){
            newUser.setEmail(personDetailsDTO.getEmail());
        }
        if(personDetailsDTO.getPassword() != null){
            newUser.setPassword(personDetailsDTO.getPassword());
        }
        if(personDetailsDTO.getRole() != null){
            newUser.setRole(personDetailsDTO.getRole());
        }
        newUser.setId(userID);
        personRepository.save(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
