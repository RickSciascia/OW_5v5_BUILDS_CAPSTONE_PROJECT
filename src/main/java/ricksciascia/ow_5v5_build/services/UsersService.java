package ricksciascia.ow_5v5_build.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.exceptions.NotFoundException;
import ricksciascia.ow_5v5_build.repositories.UserRepository;

@Service
public class UsersService {
    @Autowired
    UserRepository userRepository;

    public User findById(Long userId){
        return this.userRepository.findById(userId).orElseThrow(()->new NotFoundException("Utente con id: " + userId + " non trovato!"));
    }
}
