package ricksciascia.ow_5v5_build.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.dto.LoginDTO;
import ricksciascia.ow_5v5_build.dto.LoginResponseDTO;
import ricksciascia.ow_5v5_build.dto.UserDTO;
import ricksciascia.ow_5v5_build.dto.UserResponseDTO;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.entities.UserRole;
import ricksciascia.ow_5v5_build.exceptions.BadReqException;
import ricksciascia.ow_5v5_build.exceptions.NotFoundException;
import ricksciascia.ow_5v5_build.exceptions.UnauthorizedException;
import ricksciascia.ow_5v5_build.repositories.UserRepository;
import ricksciascia.ow_5v5_build.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTTools jwtTools;


    public User createUser(UserDTO dto) {
        userRepository.findByEmail(dto.email()).ifPresent(user -> {
            throw new BadReqException("L'email " + dto.email() + " è già in uso!");
        });
        userRepository.findByUsername(dto.username()).ifPresent(user -> {
            throw new BadReqException("L'username " + dto.username() + " è già in uso");
        });
        User newUser = new User();
        newUser.setEmail(dto.email());
        newUser.setUsername(dto.username());
        newUser.setImage("https://ui-avatars.com/api?name="+dto.username());
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setRole(UserRole.USER);

        User savedUser = userRepository.save(newUser);

        System.out.println("Utente: " + savedUser.getUsername() + " salvato correttamente!");

        return savedUser;
    }

    public LoginResponseDTO checkCredentials(LoginDTO dto){
        User userFound = this.userRepository.findByEmail(dto.email()).orElseThrow(()-> new NotFoundException("Credenziali errate!"));
        if(passwordEncoder.matches(dto.password(),userFound.getPassword())) {
            String accessToken = jwtTools.generateToken(userFound);
            UserResponseDTO userAuthenticated = new UserResponseDTO(
                    userFound.getId(),
                    userFound.getUsername(),
                    userFound.getEmail(),
                    userFound.getRole().name(),
                    userFound.getImage()
            );

            return new LoginResponseDTO(accessToken,userAuthenticated);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
