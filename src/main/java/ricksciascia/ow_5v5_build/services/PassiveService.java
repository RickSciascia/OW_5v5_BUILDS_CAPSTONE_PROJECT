package ricksciascia.ow_5v5_build.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ricksciascia.ow_5v5_build.entities.Passive;
import ricksciascia.ow_5v5_build.exceptions.NotFoundException;
import ricksciascia.ow_5v5_build.repositories.PassiveRepository;

import java.util.List;

@Service
public class PassiveService {
    @Autowired
    private PassiveRepository passiveRepository;

//    GET PASSIVE LIST
    public List<Passive> getAllPassive() {
        return passiveRepository.findAll();
    }
}
