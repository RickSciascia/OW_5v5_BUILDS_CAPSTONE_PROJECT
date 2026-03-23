package ricksciascia.ow_5v5_build.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.dto.BuildDTO;
import ricksciascia.ow_5v5_build.dto.BuildResponseDTO;
import ricksciascia.ow_5v5_build.dto.PerkDTO;
import ricksciascia.ow_5v5_build.entities.*;
import ricksciascia.ow_5v5_build.exceptions.BadReqException;
import ricksciascia.ow_5v5_build.exceptions.NotFoundException;
import ricksciascia.ow_5v5_build.exceptions.UnauthorizedException;
import ricksciascia.ow_5v5_build.repositories.BuildRepository;
import ricksciascia.ow_5v5_build.repositories.HeroRepository;
import ricksciascia.ow_5v5_build.repositories.PerkRepository;

import java.util.List;

@Service
public class BuildService {
    @Autowired
    private BuildRepository buildRepo;
    @Autowired
    private HeroService heroService;
    @Autowired
    private PerkRepository perkRepo;


    public Build saveBuild(BuildDTO dto, User userLogged) {
        Hero hero = heroService.findHeroById(dto.heroId());
        Perk minorPerk = perkRepo.findById(dto.minorPerkId()).orElseThrow(()-> new NotFoundException("Minor Perk non trovato"));
        Perk majorPerk = perkRepo.findById(dto.majorPerkId()).orElseThrow(()-> new NotFoundException("Major Perk non trovato"));

        if(minorPerk.getPerkType() != PerkType.MINOR || !minorPerk.getHero().equals(hero)) {
            throw new BadReqException("Il perk selezionato come Minor non è valido per questo eroe");
        }
        if(majorPerk.getPerkType() != PerkType.MAJOR || !majorPerk.getHero().equals(hero)) {
            throw new BadReqException("Il perk selezionato come Major non è valido per questo eroe");
        }

        Build newBuild = new Build();
        newBuild.setName(dto.name());
        newBuild.setUser(userLogged);
        newBuild.setHero(hero);
        newBuild.setMinorPerk(minorPerk);
        newBuild.setMajorPerk(majorPerk);

        return buildRepo.save(newBuild);
    }

    public BuildResponseDTO convertToDTO(Build build) {
        return new BuildResponseDTO(
                build.getId(),
                build.getName(),
                build.getUser().getUsername(),
                build.getHero().getName(),
                new PerkDTO(
                        build.getMinorPerk().getId(),
                        build.getMinorPerk().getName(),
                        build.getMinorPerk().getDescription(),
                        "MINOR",
                        build.getMinorPerk().getPerkImage()
                ),
                new PerkDTO(
                        build.getMajorPerk().getId(),
                        build.getMajorPerk().getName(),
                        build.getMajorPerk().getDescription(),
                        "MAJOR",
                        build.getMajorPerk().getPerkImage()
                ),
                build.getCreatedAt()
        );
    }

    public Page<BuildResponseDTO> getBuildsByHero(Long heroId , int page, int size) {
        if(page<0) page = 0;
        if(size>10 || size<0) size = 10;
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Build> buildPage = buildRepo.findByHeroId(heroId,pageable);
        return buildPage.map(this::convertToDTO);
    }

    public Page<BuildResponseDTO> getBuildsByUser(Long userId , int page, int size) {
        if(page<0) page = 0;
        if(size>10 || size<0) size = 10;
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdAt").descending());
        Page<Build> buildPage =  buildRepo.findByUserId(userId, pageable);
        return buildPage.map(this::convertToDTO);
    }

    public void deleteBuild(Long buildId, User userLogged) {
        Build build = buildRepo.findById(buildId).orElseThrow(()-> new NotFoundException("Build non trovata"));

        if(!build.getUser().getId().equals(userLogged.getId()) && !userLogged.getRole().name().equals("ADMIN")) {
            throw new UnauthorizedException("Non hai i permessi necessari per cancellare questa build");
        }

        buildRepo.delete(build);
    }
}
