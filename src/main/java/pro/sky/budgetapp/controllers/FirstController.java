package pro.sky.budgetapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String helloWorld(){
        return "Hello, new bright Web!";
    }

    @GetMapping("path/to/page")
    public String page(@RequestParam int page){
        return "Hello, new bright PAGE! " + page;
    }

}
