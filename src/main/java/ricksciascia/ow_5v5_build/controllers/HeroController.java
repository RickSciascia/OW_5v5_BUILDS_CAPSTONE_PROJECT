package ricksciascia.ow_5v5_build.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.ow_5v5_build.dto.HeroDTO;
import ricksciascia.ow_5v5_build.entities.Hero;
import ricksciascia.ow_5v5_build.exceptions.ValException;
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
//    --------------------- P O S T ---------------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hero saveHero(@RequestBody @Validated HeroDTO heroDto, BindingResult validationRes){
        if(validationRes.hasErrors()) {
            List<String> errorList = validationRes.getFieldErrors()
                    .stream().map(fe-> fe.getDefaultMessage())
                    .toList();
            throw new ValException(errorList);
        } else {
            return this.heroService.saveHeroFromDTO(heroDto);
        }
    }
}
