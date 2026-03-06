package ricksciascia.ow_5v5_build.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.dto.HeroDTO;
import ricksciascia.ow_5v5_build.dto.PassiveDTO;
import ricksciascia.ow_5v5_build.entities.*;
import ricksciascia.ow_5v5_build.exceptions.BadReqException;
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

//    GET
    public List<Hero> getAllHeroes() {
        return heroRepository.findAll();
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
}
