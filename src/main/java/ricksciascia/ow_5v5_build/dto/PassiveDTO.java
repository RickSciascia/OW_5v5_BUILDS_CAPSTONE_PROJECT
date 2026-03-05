package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PassiveDTO(
        @NotBlank(message = "Il nome della passiva è obbligatorio")
        @Size(min = 2, message = "Il nome della passiva deve essere minimo 2 caratteri")
        String name,
        @NotBlank(message = "La descrizione della passiva è obbligatorio")
        @Size(min = 5, message = "La descrizione della passiva deve essere minimo 5 caratteri")
        String description
) {
}
