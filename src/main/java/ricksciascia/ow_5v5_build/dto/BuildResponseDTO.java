package ricksciascia.ow_5v5_build.dto;

import java.time.LocalDateTime;

public record BuildResponseDTO(
        Long id,
        String name,
        String username,
        String heroName,
        PerkDTO minorPerk,
        PerkDTO majorPerk,
        LocalDateTime createdAt
) {
}
