package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.services.PasswordService;
import ro.tuc.ds2020.services.PersonService;

@RestController
@RequestMapping( "/person")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<Person> login(@RequestBody LoginRequest loginRequest) {
        Person person = personService.retrieveByEmail(loginRequest.email);
        if(person!=null){
            if(person.getPassword().equals(loginRequest.password)){
                return ResponseEntity.ok(person);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}