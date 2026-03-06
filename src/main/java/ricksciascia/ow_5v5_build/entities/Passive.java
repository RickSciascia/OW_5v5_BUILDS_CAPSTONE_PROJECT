package ricksciascia.ow_5v5_build.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passive")
public class Passive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String passiveImage;

    @ManyToMany(mappedBy = "passive")
    @JsonIgnoreProperties("passive")
    private List<Hero> heroList = new ArrayList<>();

    public Passive(){}
    public Passive(String name, String description, String image){
        this.name = name;
        this.description = description;
        this.passiveImage = image;
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

    public List<Hero> getHeroList() {
        return heroList;
    }

    public void setHeroList(List<Hero> heroList) {
        this.heroList = heroList;
    }

    public String getPassiveImage() {
        return passiveImage;
    }

    public void setPassiveImage(String passiveImage) {
        this.passiveImage = passiveImage;
    }
}
