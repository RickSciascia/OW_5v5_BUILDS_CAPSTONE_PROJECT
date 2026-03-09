package ricksciascia.ow_5v5_build.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double damage;
    @Column(nullable = false)
    private Double healing;
    @Column(nullable = false)
    private Double duration;
    @Column(nullable = false)
    private Double cooldown;
    @Column(nullable = false)
    private Double range;
    @Column(nullable = false)
    private String skillImage;
    @ManyToOne
    @JoinColumn(name = "hero_id", nullable = false)
    @JsonBackReference
    private Hero hero;

    public Skill(){}
    public Skill(String name, String description, Double damage, Double healing, Double duration, Double cooldown, Double range, String skillImage) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.healing = healing;
        this.duration = duration;
        this.cooldown = cooldown;
        this.range = range;
        this.skillImage = skillImage;
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

    public String getSkillImage() {
        return skillImage;
    }

    public void setSkillImage(String skillImage) {
        this.skillImage = skillImage;
    }
}
