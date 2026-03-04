package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "perks")
public class Perk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private PerkType perkType;
    @ManyToOne
    @JoinColumn(name = "hero_id")
    @JsonBackReference
    private Hero hero;

    public Perk(){}
    public Perk(String name, String description, PerkType perkType) {
        this.name = name;
        this.description = description;
        this.perkType = perkType;
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

    public PerkType getPerkType() {
        return perkType;
    }

    public void setPerkType(PerkType perkType) {
        this.perkType = perkType;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
