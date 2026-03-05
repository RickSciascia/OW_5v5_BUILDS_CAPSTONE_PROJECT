package ricksciascia.ow_5v5_build.repositories;

import ricksciascia.ow_5v5_build.entities.Ultimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UltimateRepository extends JpaRepository<Ultimate,Long> {
}
