package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Il campo email è obbligatorio")
        @Email(message = "L'indirizzo email non è nel formato corretto")
        String email,
        @NotBlank(message = "Il campo password è obbligatorio")
        String password
) {
}
