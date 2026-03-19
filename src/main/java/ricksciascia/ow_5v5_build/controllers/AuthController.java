package ricksciascia.ow_5v5_build.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.ow_5v5_build.dto.LoginDTO;
import ricksciascia.ow_5v5_build.dto.LoginResponseDTO;
import ricksciascia.ow_5v5_build.dto.UserDTO;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.exceptions.ValException;
import ricksciascia.ow_5v5_build.services.AuthService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Validated UserDTO dto, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream().map(fe->fe.getDefaultMessage())
                    .toList();
        throw new ValException(errorsList);
        } else {
            return this.authService.createUser(dto);
        }
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated LoginDTO dto, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream().map(fe -> fe.getDefaultMessage())
                    .toList();
            throw new ValException(errorsList);
        } else {
            return this.authService.checkCredentials(dto);
        }
    }

}
