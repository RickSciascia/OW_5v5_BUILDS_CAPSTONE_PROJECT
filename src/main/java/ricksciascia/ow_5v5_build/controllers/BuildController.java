package ricksciascia.ow_5v5_build.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.ow_5v5_build.dto.BuildDTO;
import ricksciascia.ow_5v5_build.dto.BuildResponseDTO;
import ricksciascia.ow_5v5_build.entities.Build;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.services.BuildService;

import java.util.List;

@RestController
@RequestMapping("/builds")
public class BuildController {
    @Autowired
    private BuildService buildService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Build createBuild(@RequestBody @Validated BuildDTO dto, @AuthenticationPrincipal User userLogged) {
        return buildService.saveBuild(dto,userLogged);
    }


    @GetMapping("/hero/{heroId}")
    public Page<BuildResponseDTO> getHeroBuilds(@PathVariable Long heroId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return buildService.getBuildsByHero(heroId,page,size);
    }

    @GetMapping("/me")
    public Page<BuildResponseDTO> getMyBuilds(@AuthenticationPrincipal User userLogged,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return buildService.getBuildsByUser(userLogged.getId(),page,size);
    }

    @DeleteMapping("/{buildId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void delete(@PathVariable Long buildId, @AuthenticationPrincipal User userLogged) {
        buildService.deleteBuild(buildId,userLogged);
    }
}
