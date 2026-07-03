package org.nityam.sarathi.Controller;

import org.nityam.sarathi.Service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NavigationController {

    @Autowired
    private NavigationService navigationService;



    @GetMapping ("/Map/{station}/{platformNumber}/{from}/{to}")
    public String goTo(@PathVariable String from, @PathVariable String to,@PathVariable int platformNumber,@PathVariable String station) {
        return navigationService.navigate(from,to,platformNumber,station);
    }

}
