package ricksciascia.ow_5v5_build.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.dto.UpdatedUserDTO;
import ricksciascia.ow_5v5_build.dto.UserDTO;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.exceptions.BadReqException;
import ricksciascia.ow_5v5_build.exceptions.NotFoundException;
import ricksciascia.ow_5v5_build.repositories.UserRepository;

@Service
public class UsersService {
    @Autowired
    UserRepository userRepository;

    public User findById(Long userId){
        return this.userRepository.findById(userId).orElseThrow(()->new NotFoundException("Utente con id: " + userId + " non trovato!"));
    }

    public Page<User> findAllUsers(int page, int size, String orderBy) {
        if(size>50|| size<0) size = 10;
        if(page<0) page = 0;
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return this.userRepository.findAll(pageable);
    }

    public User updateUser(Long userId, UpdatedUserDTO payload){
        User userFound = this.findById(userId);
        if(!userFound.getUsername().equals(payload.username())){
            this.userRepository.findByUsername(payload.username())
                    .ifPresent(user -> {throw new BadReqException("Username: " + payload.username() + " già in uso!");
            });
            userFound.setUsername(payload.username());
        }
        System.out.println("L'Utente è stato aggiornato correttamente");
        return this.userRepository.save(userFound);
    }

}
