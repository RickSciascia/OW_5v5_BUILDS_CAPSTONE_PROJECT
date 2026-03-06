package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SkillDTO(
        @NotBlank(message = "Il nome della Skill è un campo obbligatorio")
        @Size(min = 2, message = "Il campo nome della Skill deve essere minimo 2 caratteri")
        String name,
        @NotBlank(message = "La descrizione della Skill è un campo obbligatorio")
        @Size(min = 5, message = "Il campo descrizione della Skill deve essere minimo 5 caratteri")
        String description,
        @NotNull
        @Min(value = 0, message = "il campo damage della Skill deve essere un valore come minimo 0")
        Double damage,
        @NotNull
        @Min(value = 0, message = "il campo healing della Skill deve essere un valore come minimo 0")
        Double healing,
        @NotNull
        @Min(value = 0, message = "il campo duration della Skill deve essere un valore come minimo 0")
        Double duration,
        @NotNull
        @Min(value = 0, message = "il campo cooldown della Skill deve essere un valore come minimo 0")
        Double cooldown,
        @NotNull
        @Min(value = 0, message = "il campo range della Skill deve essere un valore come minimo 0")
        Double range
) {
}
