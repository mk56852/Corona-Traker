package com.coronatrakerapp.coronavirus.traker.controller;


import com.coronatrakerapp.coronavirus.traker.Service.CoronaDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class controller {
    private CoronaDataService coronaDataService ;


    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("data" , coronaDataService.getAllStats()) ;
        return "index" ;
    }
}
