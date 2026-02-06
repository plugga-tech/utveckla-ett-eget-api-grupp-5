package org.acme.service;

import java.util.ArrayList;
import java.util.List;

import org.acme.model.Hero;
import org.acme.model.HeroDto;
import org.acme.model.HeroResponseDto;
import org.acme.model.enums.Race;
import org.acme.model.enums.HeroClass;
import org.acme.model.enums.Weapon;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.NotFoundException;


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
        Race race = toRaceEnumFromString(heroDto.getRace());
        Weapon weapon = toWeaponEnumFromString(heroDto.getWeapon());

        HeroClass heroClass = HeroClass.fromString(heroDto.getHeroClass());
        Hero hero = new Hero();
            hero
                .setName            (heroDto.getName())                    
                .setHeroClass       (heroDto.getHeroClass())
                .setWeapon          (weapon)
                .setRace            (race)                
                .setFocusedFire     (isElf   (race)) // Boolean som verifierar raser
                .setSteadyFrame     (isDwarf (race)) // Är man alv har man focusedFire true
                .setStrongArms      (isOrc   (race)) // dwarf steadyFrame true, osv.
                .setJackOfAllTrades (isHuman (race));

        em.persist(hero);

        // Skapa och returnera en HeroResponseDto baserad på den sparade heron
        HeroResponseDto heroResponseDto = createHeroResponseDto(hero);

        return heroResponseDto;
    }


    // Metod för att konvertera sträng till enum. HeroDto tar emot ras
    // i form av sträng, men vi vill ha den som enum i vår Hero entity.
    private Race toRaceEnumFromString(String race) throws IllegalArgumentException {

        // Den här metoden använder den statiska fromString metoden i Race enum
        // Den hämtar rätt enum baserat på den inkommande strängen, och verifierar
        // att det är en giltig ras. Om inte, kastas ett illegalargumentexception.
        Race raceEnum = Race.fromString(race);
        
        switch (raceEnum) {
            case HUMAN : return Race.HUMAN;
            case ORC   : return Race.ORC;
            case ELF   : return Race.ELF;
            case DWARF : return Race.DWARF;
            default    : throw new IllegalArgumentException("Unknown race: " + race);
        }
    }

    private Weapon toWeaponEnumFromString (String weapon){
        // Samma princip som ovanför med ras-enum
        Weapon weaponEnum = Weapon.fromString(weapon);
        
        switch(weaponEnum) {
            case SWORD  : return Weapon.SWORD;
            case MACE   : return Weapon.MACE;
            case DAGGER : return Weapon.DAGGER;
            case STAFF  : return Weapon.STAFF;
            default     : throw new IllegalArgumentException("Unknown weapon: " + weapon);
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
            HeroResponseDto heroResponseDto = createHeroResponseDto(hero);
            heroResponseDtos.add(heroResponseDto);
        }
        return heroResponseDtos;
    }
    //Uppdaterar en hero baserat på id
    public HeroResponseDto updateHero(int id, HeroDto heroDto) {

        //hämtar hero från databasen med id
        Hero hero = em.find(Hero.class, id);

        //Om Hero inte finns, returnerar null
        if (hero == null){
            return null; 
        }

        // Konvertera string till enum
        Race enumifiedRace = toRaceEnumFromString(heroDto.getRace());
    

        //Uppdatera alla fält med nya värden
        hero.setName                (heroDto.getName())
            .setHeroClass           (HeroClass.fromString(heroDto.getHeroClass()))
            .setRace                (enumifiedRace)
            .setFocusedFire         (isElf(enumifiedRace))
            .setSteadyFrame         (isDwarf(enumifiedRace))
            .setStrongArms          (isOrc(enumifiedRace))
            .setJackOfAllTrades     (isHuman(enumifiedRace));

        //sparar ändringar i databasen
        em.merge(hero);

        // Returnera den uppdaterade hjälten som en DTO
        return new HeroResponseDto()
            .setId              (hero.getId())
            .setName            (hero.getName())
            .setHeroClass       (hero.getHeroClass())
            .setRace            (hero.getRace())
            .setFocusedFire     (hero.getFocusedFire())
            .setSteadyFrame     (hero.getSteadyFrame())
            .setStrongArms      (hero.getStrongArms())
            .setJackOfAllTrades (hero.getJackOfAllTrades());
    }

    // Raderar en hero baserat på id
    public boolean deleteHero(int id){

        Hero hero = em.find(Hero.class, id);

        // Om hero inte finns, returnera false
        if (hero == null){
            return false; 
        }

        // Om hero finns, ta bort den från databasen
        em.remove(hero);
        return true;
    public HeroResponseDto getHeroByName(String name) throws NoResultException {
        
        try {
        Hero hero = em.createQuery("SELECT h FROM Hero h WHERE h.name = :name", Hero.class)
                .setParameter("name", name)
                .getSingleResult();
        return createHeroResponseDto(hero);
        } catch (NoResultException e) {
            throw new IllegalArgumentException("No hero by that name could be found.");
        }

    }
    
    private HeroResponseDto createHeroResponseDto(Hero hero){

        return new HeroResponseDto()
            .setId              (hero.getId())
            .setName            (hero.getName())
            .setRace            (hero.getRace())
            .setHeroClass       (hero.getHeroClass())
            .setWeapon          (hero.getWeapon())
            .setFocusedFire     (hero.getFocusedFire())
            .setSteadyFrame     (hero.getSteadyFrame())
            .setStrongArms      (hero.getStrongArms())
            .setJackOfAllTrades (hero.getJackOfAllTrades());

    }

     public List<HeroResponseDto> getHeroesByClass(String heroClass) {
        HeroClass heroClassEnum = HeroClass.fromString(heroClass);
        List<Hero> heroes = em.createQuery("SELECT h FROM Hero h WHERE h.heroClass = :heroClass", Hero.class)
                                .setParameter("heroClass", heroClassEnum)
                                .getResultList();

        List<HeroResponseDto> heroResponseDtos = new ArrayList<>();

        for (Hero hero : heroes){
            HeroResponseDto heroResponseDto = new HeroResponseDto()
                .setId              (hero.getId())
                .setName            (hero.getName())
                .setHeroClass       (hero.getHeroClass())
                .setRace(hero.getRace())
                .setFocusedFire(hero.getFocusedFire())
                .setSteadyFrame(hero.getSteadyFrame())
                .setStrongArms(hero.getStrongArms())
                .setJackOfAllTrades(hero.getJackOfAllTrades());
            heroResponseDtos.add(heroResponseDto);


}
        return heroResponseDtos;
    }
}

        

    
