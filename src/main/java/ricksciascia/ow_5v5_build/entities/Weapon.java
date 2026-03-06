package ricksciascia.ow_5v5_build.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "weapons")
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double maxDmg;
    private Double minDmg;
    private String weaponImage;
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    @ManyToOne
    @JoinColumn(name = "hero_id")
    @JsonBackReference
    private Hero hero;

    public Weapon(){}

    public Weapon(String name, String description, Double maxDmg, Double minDmg, WeaponType weaponType, String weaponImage) {
        this.name = name;
        this.description = description;
        this.maxDmg = maxDmg;
        this.minDmg = minDmg;
        this.weaponType = weaponType;
        this.weaponImage = weaponImage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaxDmg() {
        return maxDmg;
    }

    public void setMaxDmg(Double maxDmg) {
        this.maxDmg = maxDmg;
    }

    public Double getMinDmg() {
        return minDmg;
    }

    public void setMinDmg(Double minDmg) {
        this.minDmg = minDmg;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getWeaponImage() {
        return weaponImage;
    }

    public void setWeaponImage(String weaponImage) {
        this.weaponImage = weaponImage;
    }
}
