package ricksciascia.ow_5v5_build.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.dto.HeroDTO;
import ricksciascia.ow_5v5_build.dto.HeroMinDTO;
import ricksciascia.ow_5v5_build.dto.PassiveDTO;
import ricksciascia.ow_5v5_build.entities.*;
import ricksciascia.ow_5v5_build.exceptions.BadReqException;
import ricksciascia.ow_5v5_build.exceptions.NotFoundException;
import ricksciascia.ow_5v5_build.repositories.HeroRepository;
import ricksciascia.ow_5v5_build.repositories.PassiveRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class HeroService {
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private PassiveRepository passiveRepository;

//    FIND BY NAME
    public Hero findHeroByName(String heroName){
        return heroRepository.findByName(heroName).orElseThrow(()-> new NotFoundException("Eroe: " + heroName + " non trovato"));
    }
//    FIND BY ID
    public Hero findHeroById(Long heroId){
        return heroRepository.findById(heroId).orElseThrow(()-> new NotFoundException("Eroe con id: " + heroId + " non trovato"));
    }

//    SAVE IMPORT
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
//                cerca per nome -> se c'è recuperala dal DB e aggiungila alla lista, se non c'è salvala in DB e aggiungila alla lista
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

//    GET HEROES LIST
    public List<Hero> getAllHeroes() {
        return heroRepository.findAll();
    }
//    GET HEROES WITH PAGE
    public Page<Hero> getAllHeroesByPage(int page,int size,String orderBy){
        if(size>10 || size<0) size = 10;
        if(page<0) page = 0;
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy).ascending());
        return this.heroRepository.findAll(pageable);
    }

//    GET HERO MIN LIST FOR GALLERY PAGE
    public List<HeroMinDTO> getAllHeroesMin() {
        List<String> roleOrder = List.of("TANK","DAMAGE","SUPPORT");
        return heroRepository.findAll().stream().map(hero -> new HeroMinDTO(
                hero.getId(),
                hero.getName(),
                hero.getRole().toString(),
                hero.getPortraitImage()
        ))
                .sorted(Comparator
                        .comparing((HeroMinDTO hero) -> roleOrder.indexOf(hero.role()))
                        .thenComparing(HeroMinDTO::name))
                .toList();
    }

//    SAVE FROM POST
    @Transactional
    public Hero saveHeroFromDTO(HeroDTO dto) {
        Optional<Hero> existingHero = heroRepository.findByName(dto.name());
        if (existingHero.isEmpty()) {
            double realHp = dto.health() + dto.shield() + dto.armor();
            if (dto.hp() != realHp) {
                throw new BadReqException("La somma dei campi vita, armor e shield è diversa dagli hp dichiarati, correggi e riprova");
            }
            Hero heroToSave = new Hero();

            heroToSave.setName(dto.name());
            heroToSave.setHp(realHp);
            heroToSave.setHealth(dto.health());
            heroToSave.setShield(dto.shield());
            heroToSave.setArmor(dto.armor());
            heroToSave.setImage(dto.image());
            heroToSave.setPortraitImage(dto.portraitImage());

            try {
                heroToSave.setRole(HeroRole.valueOf(dto.role().toUpperCase()));
            } catch (Exception e) {
                throw new BadReqException("Ruolo: " + dto.role() + " non valido, inserire un valore tra DAMAGE, TANK, SUPPORT");
            }

            if (dto.skills() != null) {
                heroToSave.setSkills(dto.skills().stream().map(skillDTO -> {
                    Skill s = new Skill();
                    s.setName(skillDTO.name());
                    s.setDescription(skillDTO.description());
                    s.setDamage(skillDTO.damage());
                    s.setHealing(skillDTO.healing());
                    s.setDuration(skillDTO.duration());
                    s.setCooldown(skillDTO.cooldown());
                    s.setRange(skillDTO.range());
                    s.setSkillImage(skillDTO.skillImage());
                    s.setHero(heroToSave);
                    return s;
                }).toList());
            }

            if (dto.weapons() != null) {
                heroToSave.setWeapons(dto.weapons().stream().map(weaponDTO -> {
                    Weapon w = new Weapon();
                    w.setName(weaponDTO.name());
                    w.setDescription(weaponDTO.description());
                    w.setMaxDmg(weaponDTO.maxDmg());
                    w.setMinDmg(weaponDTO.minDmg());
                    w.setWeaponImage(weaponDTO.weaponImage());
                    try {
                        w.setWeaponType(WeaponType.valueOf(weaponDTO.weaponType().toUpperCase()));
                    } catch (Exception e) {
                        throw new BadReqException("WeaponType: " + weaponDTO.weaponType() + " non valido, inserisci un valore tra BEAM,HITSCAN,PROJECTILE,MELEE");
                    }
                    w.setHero(heroToSave);
                    return w;
                }).toList());
            }
            if (dto.ultimates() != null) {
                heroToSave.setUltimates(dto.ultimates().stream().map(ultimateDTO -> {
                    Ultimate u = new Ultimate();
                    u.setName(ultimateDTO.name());
                    u.setDescription(ultimateDTO.description());
                    u.setDamage(ultimateDTO.damage());
                    u.setHealing(ultimateDTO.healing());
                    u.setDuration(ultimateDTO.duration());
                    u.setRange(ultimateDTO.range());
                    u.setCost(ultimateDTO.cost());
                    u.setUltimateImage(ultimateDTO.ultimateImage());
                    u.setHero(heroToSave);
                    return u;
                }).toList());
            }

            if (dto.perks() != null) {
                heroToSave.setPerks(dto.perks().stream().map(perkDTO -> {
                    Perk p = new Perk();
                    p.setName(perkDTO.name());
                    p.setDescription(perkDTO.description());
                    p.setPerkImage(perkDTO.perkImage());
                    try {
                        p.setPerkType(PerkType.valueOf(perkDTO.perkType().toUpperCase()));
                    } catch (Exception e) {
                        throw new BadReqException("PerkType: " + perkDTO.perkType() + " non valido, inserisci un valore tra MAJOR e MINOR");
                    }
                    p.setHero(heroToSave);
                    return p;
                }).toList());
            }

            if (dto.passive() != null) {
                List<Passive> passiveList = new ArrayList<>();
                for (PassiveDTO passiveDTO : dto.passive()) {
                    Passive passive = passiveRepository.findByName(passiveDTO.name()).orElseGet(() -> {
                        Passive newPassive = new Passive();
                        newPassive.setName(passiveDTO.name());
                        newPassive.setDescription(passiveDTO.description());
                        newPassive.setPassiveImage(passiveDTO.passiveImage());
                        return passiveRepository.save(newPassive);
                    });
                    passiveList.add(passive);
                }
                heroToSave.setPassive(passiveList);
            }
            System.out.println("Eroe " + heroToSave.getName() + " salvato!");
            return heroRepository.save(heroToSave);
        }
        else {
            throw new BadReqException("L'eroe: " + dto.name() + " è già presente!");
        }
    }
//    UPDATE HERO PUT
    @Transactional
    public Hero updateHeroById(Long heroId, HeroDTO heroDTO) {
        Hero heroFound = this.findHeroById(heroId);
//        VALIDAZIONE AGGIUNTIVA CONTROLLO NOME EROE MODIFICATO
        if(heroFound.getName().equals(heroDTO.name())) {
            double realHp = heroDTO.health() + heroDTO.shield() + heroDTO.armor();
            if (heroDTO.hp() != realHp) {
                throw new BadReqException("La somma dei campi vita, armor e shield è diversa dagli hp dichiarati, correggi e riprova");
            }
            heroFound.setHp(realHp);
            heroFound.setHealth(heroDTO.health());
            heroFound.setShield(heroDTO.shield());
            heroFound.setArmor(heroDTO.armor());
            heroFound.setImage(heroDTO.image());
            heroFound.setPortraitImage(heroDTO.portraitImage());
            try {
                heroFound.setRole(HeroRole.valueOf(heroDTO.role().toUpperCase()));
            } catch (Exception e) {
                throw new BadReqException("Ruolo: " + heroDTO.role() + " non valido, inserire un valore tra DAMAGE, TANK, SUPPORT");
            }
            if (heroDTO.skills() != null) {
                heroFound.getSkills().clear();
                List<Skill> newSkills = heroDTO.skills().stream().map(skillDTO -> {
                    Skill s = new Skill();
                    s.setName(skillDTO.name());
                    s.setDescription(skillDTO.description());
                    s.setDamage(skillDTO.damage());
                    s.setHealing(skillDTO.healing());
                    s.setDuration(skillDTO.duration());
                    s.setCooldown(skillDTO.cooldown());
                    s.setRange(skillDTO.range());
                    s.setSkillImage(skillDTO.skillImage());
                    s.setHero(heroFound);
                    return s;
                }).toList();

                heroFound.getSkills().addAll(newSkills);
            }
            if (heroDTO.weapons() != null) {
                heroFound.getWeapons().clear();
                List<Weapon> newWeapons = heroDTO.weapons().stream().map(weaponDTO -> {
                    Weapon w = new Weapon();
                    w.setName(weaponDTO.name());
                    w.setDescription(weaponDTO.description());
                    w.setMaxDmg(weaponDTO.maxDmg());
                    w.setMinDmg(weaponDTO.minDmg());
                    w.setWeaponImage(weaponDTO.weaponImage());
                    try {
                        w.setWeaponType(WeaponType.valueOf(weaponDTO.weaponType().toUpperCase()));
                    } catch (Exception e) {
                        throw new BadReqException("WeaponType: " + weaponDTO.weaponType() + " non valido, inserisci un valore tra BEAM,HITSCAN,PROJECTILE,MELEE");
                    }
                    w.setHero(heroFound);
                    return w;
                }).toList();

                heroFound.getWeapons().addAll(newWeapons);
            }

            if (heroDTO.ultimates() != null) {
                heroFound.getUltimates().clear();
                List<Ultimate> newUltimates = heroDTO.ultimates().stream().map(ultimateDTO -> {
                    Ultimate u = new Ultimate();
                    u.setName(ultimateDTO.name());
                    u.setDescription(ultimateDTO.description());
                    u.setDamage(ultimateDTO.damage());
                    u.setHealing(ultimateDTO.healing());
                    u.setDuration(ultimateDTO.duration());
                    u.setRange(ultimateDTO.range());
                    u.setCost(ultimateDTO.cost());
                    u.setUltimateImage(ultimateDTO.ultimateImage());
                    u.setHero(heroFound);
                    return u;
                }).toList();

                heroFound.getUltimates().addAll(newUltimates);
            }

            if (heroDTO.perks() != null) {
                heroFound.getPerks().clear();
                List<Perk> newPerks = heroDTO.perks().stream().map(perkDTO -> {
                    Perk p = new Perk();
                    p.setName(perkDTO.name());
                    p.setDescription(perkDTO.description());
                    p.setPerkImage(perkDTO.perkImage());
                    try {
                        p.setPerkType(PerkType.valueOf(perkDTO.perkType().toUpperCase()));
                    } catch (Exception e) {
                        throw new BadReqException("PerkType: " + perkDTO.perkType() + " non valido, inserisci un valore tra MAJOR e MINOR");
                    }
                    p.setHero(heroFound);
                    return p;
                }).toList();

                heroFound.getPerks().addAll(newPerks);
            }

            if (heroDTO.passive() != null) {
                List<Passive> passiveList = new ArrayList<>();
                for (PassiveDTO passiveDTO : heroDTO.passive()) {
                    Passive passive = passiveRepository.findByName(passiveDTO.name()).orElseGet(() -> {
                        Passive newPassive = new Passive();
                        newPassive.setName(passiveDTO.name());
                        newPassive.setDescription(passiveDTO.description());
                        newPassive.setPassiveImage(passiveDTO.passiveImage());
                        return passiveRepository.save(newPassive);
                    });
                    passiveList.add(passive);
                }
                heroFound.setPassive(passiveList);
            }
            System.out.println("Eroe: " + heroFound.getName() + " aggiornato correttamente!");
            return heroRepository.save(heroFound);
        }
        else throw new BadReqException("Errore: l'eroe con id: " + heroId + " ha come nome: " + heroFound.getName() + " non puoi rinominarlo in: " + heroDTO.name());
    }
//    DELETE
    public void deleteHeroById(Long heroId) {
        Hero heroFound = this.findHeroById(heroId);
        System.out.println("L'eroe " + heroFound.getName()+ " è stato cancellato insieme a tutti i dati ad esso collegato");
        heroRepository.delete(heroFound);
    }
}
