package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WeaponDTO(
        @NotBlank(message = "Il nome della Weapon è un campo obbligatorio")
        @Size(min = 2, message = "Il campo nome della Weapon deve essere minimo 2 caratteri")
        String name,
        @NotBlank(message = "La descrizione della Weapon è un campo obbligatorio")
        @Size(min = 5, message = "Il campo descrizione della Weapon deve essere minimo 5 caratteri")
        String description,
        @NotNull
        @Min(value = 0, message = "il campo maxDmg della Weapon deve essere un valore come minimo 0")
        Double maxDmg,
        @NotNull
        @Min(value = 0, message = "il campo minDmg della Weapon deve essere un valore come minimo 0")
        Double minDmg,
        @NotBlank(message = "Il weaponType è un campo obbligatorio")
        String weaponType,
        @NotBlank(message = "il campo immagine della Weapon è obbligatorio")
        String weaponImage
) {
}
