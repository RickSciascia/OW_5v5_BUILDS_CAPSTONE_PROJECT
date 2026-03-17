package ricksciascia.ow_5v5_build.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Il campo username è obbligatorio")
        @Size(min = 2, max = 30, message = "Il campo username deve essere compreso tra 2 e 30 caratteri")
        String username,
        @NotBlank(message = "Il campo email è obbligatorio")
        @Email(message = "L'indirizzo email non è nel formato corretto")
        String email,
        @NotBlank(message = "Il campo password è obbligatorio")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$" , message = "la password deve avere almeno 8 caratteri, almeno un numero, una lettera maiuscola,una minuscola, un carattere speciale e non può contenere spazi")
        String password
) {
}
