package org.acme.service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.acme.config.ApiKeyHolder;
import org.acme.model.Hero;
import org.acme.model.HeroDto;
import org.acme.model.HeroResponseDto;
import org.acme.model.User;
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

@SuppressWarnings("null")
@ApplicationScoped
@Named
public class HeroService {
    
    
    @Inject
    EntityManager em;
    
    @Inject
    UserService userService;
    
    @Inject
    ApiKeyHolder apiKeyHolder;
    

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
     * "weapon": "dagger"
     * Hanteras i heroservice
     * 
     * }
     * 
     * 
     * 
     */
    /* ============================================================== */
    public HeroResponseDto createHero(HeroDto heroDto) throws IllegalArgumentException, AccessDeniedException {


        // HeroDto objekt tar emot attribut som sträng, så några av dem behöver
        // konverteras till enum
        // Därför 'enumifieras' de här innan den appliceras på övriga hero fält.

        HeroClass heroClass = HeroClass .fromString(heroDto.getHeroClass());
        Weapon    weapon    = Weapon    .fromString(heroDto.getWeapon());
        Race      race      = Race      .fromString(heroDto.getRace());


        User    owner       = userService.getUserByApiKey(apiKeyHolder.getApiKey());
        String  ownerName   = owner.getUsername();
       
        
        if (heroExists(heroDto)) {
            throw new AccessDeniedException("A hero by this name is already created. Pick another name.");
        }
        
        Hero hero = new Hero();
        hero
        .setName(heroDto.getName())      
        .setHeroClass(heroClass)
        .setOwnerApiKey(apiKeyHolder.getApiKey())
        .setOwnerName(ownerName)
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

    // Helper för att kolla om en hero finns eller ej.
    private boolean heroExists(HeroDto heroDto) {
        List<Hero> heroes = getAllHeroes();
        for (Hero h : heroes) {
            if (h.getName().equals(heroDto.getName())) {
                return true;
            }
        }
        return false;
    }

    // Här sker verifiering av ras för att sätta rätt boolean värde
    // Vi använder enums för att undvika magic strings och underlätta maintenance
    private boolean isElf   (Race race) {return race == Race.ELF;}
    private boolean isDwarf (Race race) {return race == Race.DWARF;}
    private boolean isOrc   (Race race) {return race == Race.ORC;}
    private boolean isHuman (Race race) {return race == Race.HUMAN;}

    // returnerar en lista på alla heroes
    public List<Hero> getAllHeroes(){
        return em.createQuery("SELECT h FROM Hero h", Hero.class).getResultList();
    }

    // returnerar en lista på alla heroes, konverterade till ResponseDto's
    public List<HeroResponseDto> getAllHeroesResponse() throws NoResultException {
        List<HeroResponseDto> allHeroesAsResponse = new ArrayList<>();
        for (Hero h : getAllHeroes()) {
            allHeroesAsResponse.add(createHeroResponseDto(h));
        }
        if (allHeroesAsResponse.isEmpty()) {
            throw new NoResultException("No heroes to list. :(");
        }
        return allHeroesAsResponse;
    }

    // Hämtar en specifik användares heroes
    public List<HeroResponseDto> getHeroes() throws NoResultException {

        List<Hero> heroes = getAllHeroes();
        List<HeroResponseDto> heroResponseDtos = new ArrayList<>();
        for (Hero h : heroes) {
            if (h.getOwnerApiKey().equals(apiKeyHolder.getApiKey())) {
                HeroResponseDto heroResponseDto = createHeroResponseDto(h);
                heroResponseDtos.add(heroResponseDto);
            }
        }
        if (heroResponseDtos.isEmpty()) {
            throw new NoResultException("No heroes to list. :(");
        }

        return heroResponseDtos;
    }

    // Uppdaterar en hero baserat på id
    public HeroResponseDto updateHero(HeroDto heroDto) throws AccessDeniedException, NoResultException {

        // hämtar hero från databasen med id
        try {

            Hero hero = em.createQuery("SELECT h FROM Hero h WHERE h.name = :name", Hero.class)
                    .setParameter("name", heroDto.getName())
                    .getSingleResult();

            
            if (!hero.getOwnerApiKey().equals(apiKeyHolder.getApiKey())) {
                throw new AccessDeniedException("The hero you are trying to edit belongs another user.");
            }
            // Konvertera string till enum
            Race        enumifiedRace   = Race.fromString(heroDto.getRace());
            Weapon      weapon          = Weapon.fromString(heroDto.getWeapon());
            HeroClass   heroClass       = HeroClass.fromString(heroDto.getHeroClass());

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

        } catch(NoResultException e) {
            throw new NoResultException("No hero with that name could be found.");
        }
    }

    // Hämtar hero baserat på id
    public Hero getHeroById(int id) throws NotFoundException {
        return em.find(Hero.class, id);
    }

    // Hämtar heroResponseDto baserat på id
    public HeroResponseDto getHeroResponseById(int id) throws NotFoundException{
        Hero hero = getHeroById(id);
        if (hero == null) {
            throw new NotFoundException("No hero with that ID could be found.");
        }
        return createHeroResponseDto(hero);
    }

    // hämtar en lista av heroResponseDto's baserat på vapen
    public List<HeroResponseDto> getHeroesByWeapon(String weapon) throws NotFoundException {
       
        Weapon weaponToEnum = Weapon.fromString(weapon);

        List<Hero> heroes = em.createQuery("SELECT h FROM Hero h WHERE h.weapon = :weapon", Hero.class)
        .setParameter("weapon", weaponToEnum)
        .getResultList();

        List<HeroResponseDto> responseHeroes = new ArrayList<>();

        for (Hero h : heroes) {
            if (h.getWeapon().equals(weaponToEnum)) {
                responseHeroes.add(createHeroResponseDto(h));
            }
        }

        if (responseHeroes.isEmpty()) {
            throw new NotFoundException("There are no heroes using " + weapon);
        }

        return responseHeroes;
    }

    // Raderar en hero baserat på id
    public boolean deleteHero(int id) throws AccessDeniedException, NotFoundException {

        Hero hero = getHeroById(id);

        // Om hero inte finns, returnera false
        if (hero == null) {
            throw new NotFoundException("Hero not found.");
        }

        if (!hero.getOwnerApiKey().equals(apiKeyHolder.getApiKey())) {
            throw new AccessDeniedException("This hero belongs to another user.");
        }

        // Om hero finns, ta bort den från databasen
        em.remove(hero);
        return true;
    }

    // Hämtar hero baserat på namn
    public HeroResponseDto getHeroByName(String name) throws NoResultException, AccessDeniedException {

        try {
            Hero hero = em.createQuery("SELECT h FROM Hero h WHERE LOWER(h.name) = LOWER(:name)", Hero.class)
                    .setParameter("name", name)
                    .getSingleResult();

            if (!hero.getOwnerApiKey().equals(apiKeyHolder.getApiKey())) {
                throw new AccessDeniedException("This hero belongs to another user.");
            }

            return createHeroResponseDto(hero);
        } catch (NoResultException e) {
            throw new NoResultException("No hero by that name could be found.");
        }

    }

    // Hämtar alla heroes av specifik ras
    public List<HeroResponseDto> getHeroesByRace(String race) throws NotFoundException {
    
        Race raceEnum;

        try {
            raceEnum = Race.fromString(race);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No such race exists");
        }

        List<Hero> heroes = em.createQuery("SELECT h FROM Hero h WHERE h.race = :raceEnum", Hero.class)
                .setParameter("raceEnum", raceEnum)
                .getResultList();
        List<HeroResponseDto> responseHeroes = new ArrayList<>();
        for (Hero h : heroes) {
            if (h.getRace().equals(raceEnum)) {
                responseHeroes.add(createHeroResponseDto(h));
            }
        }

        if (responseHeroes.isEmpty()) {
            throw new NotFoundException("No heroes with that race exist.");
        }

        return responseHeroes;
    }

    // Hämtar alla heroes av specifik klass
    public List<HeroResponseDto> getHeroesByClass(String heroClass) throws NotFoundException {
        HeroClass heroClassEnum = HeroClass.fromString(heroClass);
        
        List<Hero> heroes = em.createQuery("SELECT h FROM Hero h WHERE h.heroClass = :heroClass", Hero.class)
                .setParameter("heroClass", heroClassEnum)
                .getResultList();

        List<HeroResponseDto> heroResponseDtos = new ArrayList<>();

        for (Hero hero : heroes) {
            if (hero.getOwnerApiKey().equals(apiKeyHolder.getApiKey())) {
                HeroResponseDto heroResponseDto = createHeroResponseDto(hero);
                heroResponseDtos.add(heroResponseDto);
            }
        }
        if (heroResponseDtos.isEmpty()) {
            throw new NotFoundException("There are no heroes with that class.");
        }
        return heroResponseDtos;
    }

    // Helper klass för att skapa en responseDto utirån ett hero objekt.
    private HeroResponseDto createHeroResponseDto(Hero hero) {

        return new HeroResponseDto()
                .setId             (hero.getId())
                .setName           (hero.getName())
                .setOwnerName      (hero.getOwnerName())
                .setRace           (hero.getRace())
                .setHeroClass      (hero.getHeroClass())
                .setWeapon         (hero.getWeapon())
                .setFocusedFire    (hero.getFocusedFire())
                .setSteadyFrame    (hero.getSteadyFrame())
                .setStrongArms     (hero.getStrongArms())
                .setJackOfAllTrades(hero.getJackOfAllTrades())
                .setRaceImageUrl   (hero.getRace().getImageUrl());
    }
}

        

    
