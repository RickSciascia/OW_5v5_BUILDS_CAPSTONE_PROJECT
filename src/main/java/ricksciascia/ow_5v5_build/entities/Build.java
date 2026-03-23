package ricksciascia.ow_5v5_build.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "builds")
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @ManyToOne
    @JoinColumn(name = "minor_perk_id")
    private Perk minorPerk;

    @ManyToOne
    @JoinColumn(name = "major_perk_id")
    private Perk majorPerk;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Build(){};

    public Build(String name,
                 User user,
                 Hero hero,
                 Perk minorPerk,
                 Perk majorPerk){
        this.name = name;
        this.user = user;
        this.hero = hero;
        this.minorPerk = minorPerk;
        this.majorPerk = majorPerk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Perk getMinorPerk() {
        return minorPerk;
    }

    public void setMinorPerk(Perk minorPerk) {
        this.minorPerk = minorPerk;
    }

    public Perk getMajorPerk() {
        return majorPerk;
    }

    public void setMajorPerk(Perk majorPerk) {
        this.majorPerk = majorPerk;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
