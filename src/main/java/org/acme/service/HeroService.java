package org.acme.service;

import java.util.ArrayList;
import java.util.List;

import org.acme.model.Hero;
import org.acme.model.HeroDto;
import org.acme.model.HeroResponseDto;
import org.acme.model.Race;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;


/* ========================================================= */
/* Den här klassen hanterar alla hero-relaterade operationer */
/* ========================================================= */

@ApplicationScoped
@Named
public class HeroService {

    @Inject
    EntityManager em;

    /* ============================================================== */
    /* Skapar ny hero baserad på data från inkommande HeroDto objekt. */
    /* Spara hero i databasen, och returnerar sedan en responsDto för */
    /* att kontrollera vad som skickas till klienten.                 */
    /* Format att ta emot HeroDto från klient vid skapande:           */
    /*
    
        {

            "race": "ELF",
            "name": "Legolas",
            "heroClass": "Archer" <--- spelar ingen roll om det är lowercase eller ej. Hanteras i heroservice
      
        }
    
    
    
    */
    /* ============================================================== */
    public HeroResponseDto createHero(HeroDto heroDto) throws IllegalArgumentException {

        // HeroDto objekt tar emot rasen som sträng, så de behöver konverteras till enum
        // Därför 'enumifieras' rasen här innan den appliceras på övriga hero fält.
        Race enumifiedRace = normalizeRace(heroDto.getRace());

        Hero hero = new Hero();
            hero
                .setName            (heroDto.getName())                    
                .setHeroClass       (heroDto.getHeroClass())
                .setRace            (enumifiedRace)                
                .setFocusedFire     (isElf   (enumifiedRace)) // Boolean som verifierar raser
                .setSteadyFrame     (isDwarf (enumifiedRace)) // Är man alv har man focusedFire true
                .setStrongArms      (isOrc   (enumifiedRace)) // dwarf steadyFrame true, osv.
                .setJackOfAllTrades (isHuman (enumifiedRace));

        em.persist(hero);

        // Skapa och returnera en HeroResponseDto baserad på den sparade heron
        // TODO: Justera vilka fält som ska vara med i responsen
        HeroResponseDto heroResponseDto = new HeroResponseDto();
            heroResponseDto
                .setId              (hero.getId())
                .setName            (hero.getName())
                .setHeroClass       (hero.getHeroClass())
                .setRace            (hero.getRace())                
                .setFocusedFire     (hero.getFocusedFire())
                .setSteadyFrame     (hero.getSteadyFrame())
                .setStrongArms      (hero.getStrongArms())
                .setJackOfAllTrades (hero.getJackOfAllTrades());

        return heroResponseDto;
    }


    // Metod för att konvertera sträng till enum. HeroDto tar emot ras
    // i form av sträng, men vi vill ha den som enum i vår Hero entity.
    private Race normalizeRace(String race) throws IllegalArgumentException {

        // Den här metoden använder den statiska fromString metoden i Race enum
        // Den hämtar rätt enum baserat på den inkommande strängen, och verifierar
        // att det är en giltig ras. Om inte, kastas ett illegalargumentexception.
        Race raceEnum = Race.fromString(race);
        
        switch (raceEnum) {
            case HUMAN : return Race.HUMAN;
            case ORC   : return Race.ORC;
            case ELF   : return Race.ELF;
            case DWARF : return Race.DWARF;
            default      : throw new IllegalArgumentException("Unknown race: " + race);
        }
    }

    // Här sker verifiering av ras för att sätta rätt boolean värde
    // Vi använder enums för att undvika magic strings och underlätta maintenance
    private boolean isElf   (Race race) { return race == Race.ELF;   }
    private boolean isDwarf (Race race) { return race == Race.DWARF; }
    private boolean isOrc   (Race race) { return race == Race.ORC;   }
    private boolean isHuman (Race race) { return race == Race.HUMAN; }



    public List<HeroResponseDto> getAllHeroes() {

        List<Hero> heroes = em.createQuery("SELECT h FROM Hero h", Hero.class).getResultList();
        List<HeroResponseDto> heroResponseDtos = new ArrayList<>();

        for (Hero hero : heroes){
            HeroResponseDto heroResponseDto = new HeroResponseDto()
                .setId              (hero.getId())
                .setName            (hero.getName())
                .setHeroClass       (hero.getHeroClass())
                .setRace            (hero.getRace())                
                .setFocusedFire     (hero.getFocusedFire())
                .setSteadyFrame     (hero.getSteadyFrame())
                .setStrongArms      (hero.getStrongArms())
                .setJackOfAllTrades (hero.getJackOfAllTrades());
            heroResponseDtos.add(heroResponseDto);
        }
        return heroResponseDtos;
    }




}
