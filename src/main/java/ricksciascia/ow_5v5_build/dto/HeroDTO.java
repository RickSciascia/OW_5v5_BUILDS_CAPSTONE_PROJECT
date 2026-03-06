package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ricksciascia.ow_5v5_build.entities.*;

import java.util.List;

public record HeroDTO(
        @NotBlank(message = "Il nome del eroe è obbligatorio")
        @Size(min = 2, max = 25, message = "il nome dell'eroe deve avere tra 2 e 25 caratteri")
        String name,
        @NotBlank(message = "il ruolo è obbligatorio")
        String role,
        @NotNull
        @Min(value = 1, message = "Gli HP devono essere minimo 1")
        Double hp,
        @NotNull
        @Min(value = 1, message = "i punti vita devono essere minimo 1")
        Double health,
        @NotNull
        @Min(value = 0, message = "i punti shield non possono essere negativi")
        Double shield,
        @NotNull
        @Min(value = 0, message = "i punti armor non possono essere negativi")
        Double armor,
        @NotBlank(message = "L'immagine eroe è un campo obbligatorio")
        String image,
        @NotBlank(message = "L'immagine del ritratto eroe è un campo obbligatorio")
        String portraitImage,
        @NotNull(message = "la lista armi non può essere nulla")
        @Size(min = 1, message = "L'eroe deve avere almeno un'arma")
        @Valid
        List<WeaponDTO> weapons,
        @NotNull(message = "la lista skill non può essere nulla")
        @Size(min = 1, message = "L'eroe deve avere almeno una skill")
        @Valid
        List<SkillDTO> skills,
        @NotNull(message = "la lista perk non può essere nulla")
        @Size(min = 2, message = "L'eroe deve avere almeno due perk")
        @Valid
        List<PerkDTO> perks,
        @NotNull(message = "la lista ultimate non può essere nulla")
        @Size(min = 1, message = "L'eroe deve avere almeno una ultimate")
        @Valid
        List<UltimateDTO> ultimates,
        @NotNull(message = "la lista passive non può essere nulla")
        @Size(min = 1, message = "L'eroe deve avere almeno una passiva")
        @Valid
        List<PassiveDTO> passive
) {
}
