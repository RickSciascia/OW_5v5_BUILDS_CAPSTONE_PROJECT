package ricksciascia.ow_5v5_build.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.entities.Hero;
import ricksciascia.ow_5v5_build.entities.Passive;
import ricksciascia.ow_5v5_build.repositories.HeroRepository;
import ricksciascia.ow_5v5_build.repositories.PassiveRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HeroService {
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private PassiveRepository passiveRepository;

//    SAVE
    @Transactional
    public Hero saveHero(Hero hero) {
//        controllo se l eroe esiste già in DB
        Optional<Hero> existingHero = heroRepository.findByName(hero.getName());
        if (existingHero.isEmpty()) {
//            Se dentro l eroe che voglio salvare ci sono le passive
        if (hero.getPassive() != null) {
            List<Passive> passive = new ArrayList<>();
//            per ogni passiva del eroe
            for(Passive p : hero.getPassive()) {
//                cerca per nome -> se c'è recuperala dal DB ed aggiungila alla lista, se non c'è salvala in DB e aggiungila alla lista
                Passive existingPassive = passiveRepository.findByName(p.getName()).orElseGet(()-> passiveRepository.save(p));
                passive.add(existingPassive);
            }
            hero.setPassive(passive);
        }
        if(hero.getSkills() != null) hero.getSkills().forEach(skill -> skill.setHero(hero));
        if(hero.getWeapons() != null) hero.getWeapons().forEach((weapon -> weapon.setHero(hero)));
        if(hero.getPerks() != null) hero.getPerks().forEach(perk -> perk.setHero(hero));
        if(hero.getUltimates() != null) hero.getUltimates().forEach(ultimate -> ultimate.setHero(hero));

        System.out.println("Salvataggio Eroe: " + hero.getName() + " avvenuto con successo!");

        return heroRepository.save(hero);
    }
        else return existingHero.get();
    }
}
