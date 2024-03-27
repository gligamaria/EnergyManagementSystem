package ro.tuc.ds2020.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/admin")
    public String accessAdmin(){
        return "Accessed admin";
    }

    @GetMapping("/client")
    public String accessClient(){
        return "Accessed client";
    }
}
