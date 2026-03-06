package ricksciascia.ow_5v5_build.exceptions;

import java.util.List;

public class ValException extends RuntimeException {
    private List<String> errorList;
    public ValException(List<String> errorList) {
        super("Errori nel contenuto del payload, controlla la Lista degli errori");
        this.errorList = errorList;
    }
    public List<String> getErrorList() {
        return errorList;
    }
}
