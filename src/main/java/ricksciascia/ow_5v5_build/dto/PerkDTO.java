package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PerkDTO(
        @NotBlank(message = "Il nome del Perk è un campo obbligatorio")
        @Size(min = 2, message = "Il campo nome del Perk deve essere minimo 2 caratteri")
        String name,
        @NotBlank(message = "La descrizione del Perk è un campo obbligatorio")
        @Size(min = 5, message = "La descrizione del Perk deve essere minimo 5 caratteri")
        String description,
        @NotBlank(message = "Il tipo di perk è obbligatorio")
        String perkType
) {
}
