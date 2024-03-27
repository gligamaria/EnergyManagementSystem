package ro.tuc.ds2020.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.jwt.JwtUtil;
import ro.tuc.ds2020.request.AuthRequest;
import ro.tuc.ds2020.response.JwtResponse;
import ro.tuc.ds2020.services.PersonService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping( "/person")
@CrossOrigin("http://localhost:4200")
public class AuthControllerJWT {

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthControllerJWT(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    PersonService personService;

    @PostMapping("/authenticate")
    public JwtResponse createToken(@RequestBody AuthRequest authRequest){

        Person person = personService.retrieveByEmail(authRequest.getEmail());
        System.out.println(person.getEmail());
        if(person.getPassword().equals(authRequest.getPassword())){
            List<String> userRoles = new ArrayList<>();
            if(person.getRole() == 1){
                userRoles.add("ROLE_ADMIN");
            }
            else {
                userRoles.add("ROLE_CLIENT");
            }
            String jwt = jwtUtil.generateToken(authRequest.getEmail(), userRoles);
            return new JwtResponse(jwt);
        }
        throw new RuntimeException("credentials are invalid.");
    }

    @PostMapping("/getEmail")
    public ResponseEntity<String> getEmail(@RequestBody String token){
        return new ResponseEntity<>( jwtUtil.extractUsername(token), HttpStatus.OK);
    }

    @PostMapping("/getRole")
    public ResponseEntity<String> getRole(@RequestBody String token){
        String userEmail = jwtUtil.extractUsername(token);
        Person person = personService.retrieveByEmail(userEmail);
        System.out.println("token: " + token);
        System.out.println("user: " + person.getId());
        System.out.println("rol: " + person.getRole().toString());

        return new ResponseEntity<>( person.getRole().toString(), HttpStatus.OK);
    }

    @PostMapping("/getId")
    public ResponseEntity<String> getId(@RequestBody String token){
        String userEmail = jwtUtil.extractUsername(token);
        Person person = personService.retrieveByEmail(userEmail);
        return new ResponseEntity<>( person.getId().toString(), HttpStatus.OK);
    }
}
