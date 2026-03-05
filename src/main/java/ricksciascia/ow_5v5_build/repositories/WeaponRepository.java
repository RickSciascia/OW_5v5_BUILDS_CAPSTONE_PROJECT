package ricksciascia.ow_5v5_build.repositories;

import ricksciascia.ow_5v5_build.entities.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon,Long> {
}
