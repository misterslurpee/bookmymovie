package com.example.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String sayHello(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @RequestMapping("/alfura")
    public List<Person> alfuras() {
        return Arrays.asList(
                new Person("Alfian", 18),
                new Person("Fuadi", 19),
                new Person("Rafli", 21)
        );
    }
}
