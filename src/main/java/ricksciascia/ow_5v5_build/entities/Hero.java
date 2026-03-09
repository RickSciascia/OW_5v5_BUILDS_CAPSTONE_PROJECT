package ricksciascia.ow_5v5_build.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "heroes")
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HeroRole role;
    @Column(nullable = false)
    private Double hp;
    private Double health;
    private Double shield;
    private Double armor;
    private String image;
    private String portraitImage;

    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Weapon> weapons = new ArrayList<>();
    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Skill> skills = new ArrayList<>() ;
    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Perk> perks = new ArrayList<>();
    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ultimate> ultimates = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "heroes_passive",
    joinColumns = @JoinColumn(name = "hero_id", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "passive_id", nullable = false))
    @JsonIgnoreProperties("heroList")
    private List<Passive> passive = new ArrayList<>();

    public Hero(){}

    public Hero(String name,
                HeroRole role,
                Double hp,
                Double health,
                Double shield,
                Double armor,
                String image,
                String portraitImage,
                List<Weapon> weaponList,
                List<Skill> skillsList,
                List<Perk> perksList,
                List<Ultimate> ultimatesList,
                List<Passive> passiveList) {
        this.name = name;
        this.role = role;
        this.hp = hp;
        this.health = health;
        this.shield = shield;
        this.armor = armor;
        this.image = image;
        this.portraitImage = portraitImage;
        this.weapons = weaponList;
        this.skills = skillsList;
        this.perks = perksList;
        this.ultimates = ultimatesList;
        this.passive = passiveList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HeroRole getRole() {
        return role;
    }

    public Double getHp() {
        return hp;
    }

    public Double getHealth() {
        return health;
    }

    public Double getShield() {
        return shield;
    }

    public Double getArmor() {
        return armor;
    }

    public String getImage() {
        return image;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Perk> getPerks() {
        return perks;
    }

    public List<Ultimate> getUltimates() {
        return ultimates;
    }

    public List<Passive> getPassive() {
        return passive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(HeroRole role) {
        this.role = role;
    }

    public void setHp(Double hp) {
        this.hp = hp;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public void setShield(Double shield) {
        this.shield = shield;
    }

    public void setArmor(Double armor) {
        this.armor = armor;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setPerks(List<Perk> perks) {
        this.perks = perks;
    }

    public void setUltimates(List<Ultimate> ultimates) {
        this.ultimates = ultimates;
    }

    public void setPassive(List<Passive> passive) {
        this.passive = passive;
    }

    public String getPortraitImage() {
        return portraitImage;
    }

    public void setPortraitImage(String portraitImage) {
        this.portraitImage = portraitImage;
    }
}
