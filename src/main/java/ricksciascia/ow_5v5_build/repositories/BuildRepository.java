package ricksciascia.ow_5v5_build.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ricksciascia.ow_5v5_build.entities.Build;



@Repository
public interface BuildRepository extends JpaRepository<Build,Long> {
    Page<Build> findByHeroId(Long heroId, Pageable pageable);
    Page<Build> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    void deleteByHeroId(Long heroId);
}
