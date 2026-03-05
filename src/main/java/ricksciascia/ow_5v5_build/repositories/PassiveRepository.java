package ricksciascia.ow_5v5_build.repositories;

import ricksciascia.ow_5v5_build.entities.Passive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassiveRepository extends JpaRepository<Passive,Long>  {
    Optional<Passive> findByName(String name);
}
