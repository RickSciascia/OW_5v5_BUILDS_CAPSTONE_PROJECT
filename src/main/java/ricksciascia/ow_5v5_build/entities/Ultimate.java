package ricksciascia.ow_5v5_build.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "ultimates")
public class Ultimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double damage;
    private Double healing;
    private Double duration;
    private Double range;
    private Double cost;
    private String ultimateImage;
    @ManyToOne
    @JoinColumn(name = "hero_id")
    @JsonBackReference
    private Hero hero;

    public Ultimate(){}
    public Ultimate(String name, String description, Double damage, Double healing, Double duration, Double range, Double cost, String ultimateImage) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.healing = healing;
        this.duration = duration;
        this.range = range;
        this.cost = cost;
        this.ultimateImage = ultimateImage;
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

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getUltimateImage() {
        return ultimateImage;
    }

    public void setUltimateImage(String ultimateImage) {
        this.ultimateImage = ultimateImage;
    }
}
