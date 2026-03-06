package ricksciascia.ow_5v5_build.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorWithListDTO(String message, LocalDateTime timestamp, List<String> errorsList) {
}
