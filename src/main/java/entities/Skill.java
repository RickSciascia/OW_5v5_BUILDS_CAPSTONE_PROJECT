package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double damage;
    private Double healing;
    private Double duration;
    private Double cooldown;
    private Double range;
    @ManyToOne
    @JoinColumn(name = "hero_id")
    @JsonBackReference
    private Hero hero;

    public Skill(){}
    public Skill(String name, String description, Double damage, Double healing, Double duration, Double cooldown, Double range) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.healing = healing;
        this.duration = duration;
        this.cooldown = cooldown;
        this.range = range;
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

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public Double getHealing() {
        return healing;
    }

    public void setHealing(Double healing) {
        this.healing = healing;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getCooldown() {
        return cooldown;
    }

    public void setCooldown(Double cooldown) {
        this.cooldown = cooldown;
    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
