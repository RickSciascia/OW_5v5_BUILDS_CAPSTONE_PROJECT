package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BuildDTO(
        @NotBlank(message = "Il nome della build è un campo obbligatorio")
        @Size(min = 3, max = 50, message = "il nome della build deve essere minimo 3 caratteri massimo 50")
        String name,
        @NotNull(message = "Il campo id eroe è obbligatorio")
        @Min(value = 1, message = "L'id dell eroe non può essere 0 o negativo")
        Long heroId,
        @NotNull(message = "Il campo id perk minore è obbligatorio")
        @Min(value = 1, message = "L'id del perk minore non può essere 0 o negativo")
        Long minorPerkId,
        @NotNull(message = "Il campo id perk maggiore è obbligatorio")
        @Min(value = 1, message = "L'id del perk maggiore non può essere 0 o negativo")
        Long majorPerkId
) {
}
