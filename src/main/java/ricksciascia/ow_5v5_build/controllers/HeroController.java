package ricksciascia.ow_5v5_build.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.ow_5v5_build.dto.HeroDTO;
import ricksciascia.ow_5v5_build.dto.HeroMinDTO;
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

//    --------------------- P U T ---------------------
    @PutMapping("/{heroId}")
    public Hero editHero(@RequestBody @Validated HeroDTO heroDTO, BindingResult validationRes, @PathVariable Long heroId){
        if(validationRes.hasErrors()) {
            List<String> errorsList = validationRes.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
        throw new ValException(errorsList);
        } else {
            return this.heroService.updateHeroById(heroId, heroDTO);
        }
    }

//    --------------------- G E T - G A L L E R Y ---------------------
    @GetMapping("/gallery")
    public List<HeroMinDTO> getHeroesForGallery() {
        return heroService.getAllHeroesMin();
    }
//    --------------------- G E T - S I N G L E - H E R O ---------------------
    @GetMapping("/{heroId}")
    public Hero getHeroById(@PathVariable Long heroId) {
        return heroService.findHeroById(heroId);
    }
}
