package ricksciascia.ow_5v5_build.repositories;

import ricksciascia.ow_5v5_build.entities.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeroRepository extends JpaRepository<Hero,Long> {
    Optional<Hero> findByName(String name);
}
