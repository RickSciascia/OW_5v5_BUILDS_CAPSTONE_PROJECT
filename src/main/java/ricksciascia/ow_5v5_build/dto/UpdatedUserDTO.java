package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatedUserDTO(
        @NotBlank(message = "Il campo username è obbligatorio")
        @Size(min = 2, max = 30, message = "Il campo username deve essere compreso tra 2 e 30 caratteri")
        String username) {
}
