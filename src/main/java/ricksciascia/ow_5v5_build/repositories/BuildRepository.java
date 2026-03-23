package ricksciascia.ow_5v5_build.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ricksciascia.ow_5v5_build.entities.Build;



@Repository
public interface BuildRepository extends JpaRepository<Build,Long> {
    Page<Build> findByHeroId(Long heroId, Pageable pageable);
    Page<Build> findByUserId(Long userId, Pageable pageable);
}
