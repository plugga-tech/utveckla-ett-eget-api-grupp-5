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
    /* att kontrollera vad som skickas till klienten. */
    /* Format att ta emot HeroDto från klient vid skapande: */
    /*
     * 
     * {
     * 
     * "race": "ELF",
     * "name": "Legolas",
     * "heroClass": "Archer" <--- spelar ingen roll om det är lowercase eller ej.
     * Hanteras i heroservice
     * 
     * }
     * 
     * 
     * 
     */
    /* ============================================================== */
    public HeroResponseDto createHero(HeroDto heroDto) throws IllegalArgumentException {

        // HeroDto objekt tar emot attribut som sträng, så några av dem behöver
        // konverteras till enum
        // Därför 'enumifieras' de här innan den appliceras på övriga hero fält.

        Weapon weapon       = Weapon.fromString(heroDto.getWeapon());
        Race race           = Race.fromString(heroDto.getRace());
        HeroClass heroClass = HeroClass.fromString(heroDto.getHeroClass());

        Hero hero = new Hero();
        hero
                .setName(heroDto.getName())
                .setHeroClass(heroClass)
                .setWeapon(weapon)
                .setRace(race)
                .setFocusedFire(isElf(race)) // Boolean som verifierar raser
                .setSteadyFrame(isDwarf(race)) // Är man alv har man focusedFire true
                .setStrongArms(isOrc(race)) // dwarf steadyFrame true, osv.
                .setJackOfAllTrades(isHuman(race));

        em.persist(hero);

        // Skapa och returnera en HeroResponseDto baserad på den sparade heron

        return createHeroResponseDto(hero);

    }

    // Här sker verifiering av ras för att sätta rätt boolean värde
    // Vi använder enums för att undvika magic strings och underlätta maintenance
    private boolean isElf(Race race) {
        return race == Race.ELF;
    }

    private boolean isDwarf(Race race) {
        return race == Race.DWARF;
    }

    private boolean isOrc(Race race) {
        return race == Race.ORC;
    }

    private boolean isHuman(Race race) {
        return race == Race.HUMAN;
    }

    public List<HeroResponseDto> getAllHeroes() {

        List<Hero> heroes = em.createQuery("SELECT h FROM Hero h", Hero.class).getResultList();
        List<HeroResponseDto> heroResponseDtos = new ArrayList<>();

        for (Hero hero : heroes) {
            HeroResponseDto heroResponseDto = createHeroResponseDto(hero);
            heroResponseDtos.add(heroResponseDto);
        }
        return heroResponseDtos;
    }

    // Uppdaterar en hero baserat på id
    public HeroResponseDto updateHero(HeroDto heroDto) {

        // hämtar hero från databasen med id
        try {

            Hero hero = em.createQuery("SELECT h FROM Hero h WHERE h.name = :name", Hero.class)
                    .setParameter("name", heroDto.getName())
                    .getSingleResult();

            // Konvertera string till enum
            Race enumifiedRace = Race.fromString(heroDto.getRace());
            Weapon weapon = Weapon.fromString(heroDto.getWeapon());
            HeroClass heroClass = HeroClass.fromString(heroDto.getHeroClass());

            //Uppdatera alla fält med nya värden
            hero.setName                (heroDto.getName())
                .setHeroClass           (heroClass)
                .setRace                (enumifiedRace)
                .setWeapon              (weapon)
                .setFocusedFire         (isElf(enumifiedRace))
                .setSteadyFrame         (isDwarf(enumifiedRace))
                .setStrongArms          (isOrc(enumifiedRace))
                .setJackOfAllTrades     (isHuman(enumifiedRace));
    
            // sparar ändringar i databasen
            em.merge(hero);

            // Returnera den uppdaterade hjälten som en DTO
            return createHeroResponseDto(hero);

        } catch(NotFoundException e) {

            throw new NotFoundException("No hero with that ID could be found.");
        }
    }

    // Raderar en hero baserat på id
    public boolean deleteHero(int id) {

        Hero hero = em.find(Hero.class, id);

        // Om hero inte finns, returnera false
        if (hero == null) {
            return false;
        }

        // Om hero finns, ta bort den från databasen
        em.remove(hero);
        return true;
    }

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

    public List<HeroResponseDto> getHeroesByClass(String heroClass) {
        HeroClass heroClassEnum = HeroClass.fromString(heroClass);
        List<Hero> heroes = em.createQuery("SELECT h FROM Hero h WHERE h.heroClass = :heroClass", Hero.class)
                .setParameter("heroClass", heroClassEnum)
                .getResultList();

        List<HeroResponseDto> heroResponseDtos = new ArrayList<>();

        for (Hero hero : heroes) {
            HeroResponseDto heroResponseDto = createHeroResponseDto(hero);
            heroResponseDtos.add(heroResponseDto);

        }
        return heroResponseDtos;
    }


    // Helper klass för att skapa en responseDto utirån ett hero objekt.
    private HeroResponseDto createHeroResponseDto(Hero hero) {

        return new HeroResponseDto()
                .setId(hero.getId())
                .setName(hero.getName())
                .setRace(hero.getRace())
                .setHeroClass(hero.getHeroClass())
                .setWeapon(hero.getWeapon())
                .setFocusedFire(hero.getFocusedFire())
                .setSteadyFrame(hero.getSteadyFrame())
                .setStrongArms(hero.getStrongArms())
                .setJackOfAllTrades(hero.getJackOfAllTrades());

    }


}

        

    
