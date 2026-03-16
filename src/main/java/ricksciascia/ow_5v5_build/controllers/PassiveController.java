package ricksciascia.ow_5v5_build.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ricksciascia.ow_5v5_build.entities.Passive;
import ricksciascia.ow_5v5_build.services.PassiveService;

import java.util.List;

@RestController
@RequestMapping("/passive")
public class PassiveController {
    private final PassiveService passiveService;

    public PassiveController(PassiveService passiveService) {
        this.passiveService = passiveService;
    }

    @GetMapping
    public List<Passive> getPassive(){
        return passiveService.getAllPassive();
    }
}
