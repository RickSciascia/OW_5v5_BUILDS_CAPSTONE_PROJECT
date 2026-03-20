package ricksciascia.ow_5v5_build.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.entities.UserRole;
import ricksciascia.ow_5v5_build.repositories.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;


    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setUsername(adminUsername);
            admin.setImage("https://ui-avatars.com/api?name="+adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);
            System.out.println("ACCOUNT ADMIN CREATO CORRETTAMENTE");
        }
        else {
            System.out.println("ACCOUNT ADMIN GIÀ ESISTENTE");
        }

    }
}
