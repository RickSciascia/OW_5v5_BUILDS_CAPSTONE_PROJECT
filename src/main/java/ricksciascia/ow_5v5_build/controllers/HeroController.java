package ricksciascia.ow_5v5_build.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ricksciascia.ow_5v5_build.entities.Hero;
import ricksciascia.ow_5v5_build.services.HeroService;

import java.util.List;

@RestController
@RequestMapping("/heroes")
public class HeroController {
    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }
//    --------------------- G E T ---------------------
    @GetMapping
    public List<Hero> getAllHeroes() {
        return heroService.getAllHeroes();
    }
}
