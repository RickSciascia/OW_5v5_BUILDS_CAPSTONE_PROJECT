package ricksciascia.ow_5v5_build.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ricksciascia.ow_5v5_build.dto.HeroDTO;
import ricksciascia.ow_5v5_build.entities.Hero;
import ricksciascia.ow_5v5_build.services.HeroService;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Component
public class HeroDataImporter implements CommandLineRunner {
    @Autowired
    private HeroService heroService;
    @Autowired
    private ObjectMapper objMapper;

    @Override
    public void run(String... args) throws Exception {
//        try-with-resources: (BP), assicura che anche se qualcosa va storto il file viene chiuso automaticamente evitando IOException Too many open files.
//        inputStream = getClass().getResourceAsStream("file.json") serve per cercare il file nel intero progetto! evita errori che si verificano con FileReader
        try(InputStream inputStream = getClass().getResourceAsStream("/heroes.json")) {
            if(inputStream == null) {
                System.out.println("File heroes.json non trovato nella cartella resources!");
            }
//           L'ObjectMapper Legge il file e crea automaticamente una List<Hero> grazie a TypeReference che gli dice crea proprio una lista di oggetti Hero
            List<HeroDTO> heroList = objMapper.readValue(inputStream, new TypeReference<List<HeroDTO>>() {
            });
            for(HeroDTO heroDTO : heroList) {
                try {
                    heroService.saveHeroFromDTO(heroDTO);
                } catch(Exception e) {
                    System.out.println("L'Eroe: " + heroDTO.name() + " era già presente");
                }
            }
            System.out.println("Caricamento dati completato!");
        } catch (Exception ex) {
            System.out.println("Errore durante il caricamento dati dal JSON " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
