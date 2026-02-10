package org.acme.model.enums;

import java.util.List;

/**
 * 
 * 
 * Enum för olika raser.
 * Innehåller rasens namn för att kunna användas som display i frontend, samt en beskrivning av rasen.
 * 
 */


@SuppressWarnings("null")
public enum Race {

    HUMAN   ("Human"    , "Human are potent in just about anything, but they don't excel in any particular trait.",
             "https://res.cloudinary.com/dz2lmzpd7/image/upload/q_auto,f_auto,w_500/v1770643202/Human_ksxozo.png", 50, 30, 70),

    ORC     ("Orc"      , "Orcs are massive, intimidating, and strong.",
            "https://res.cloudinary.com/dz2lmzpd7/image/upload/q_auto,f_auto,w_500/v1770643219/Orc_mvazmm.png", 100, 60, 10),

    ELF     ("Elf"      , "Elves are naturally agile and quick on their feet.",
            "https://res.cloudinary.com/dz2lmzpd7/image/upload/q_auto,f_auto,w_500/v1770643214/Elf_mbq3xl.png", 30, 50, 80),

    DWARF   ("Dwarf"    , "Dwarves are short, but sturdy. They know how to take a punch.",
            "https://res.cloudinary.com/dz2lmzpd7/image/upload/q_auto,f_auto,w_500/v1770643102/Dwarf_oypwgf.png", 80, 20, 50);

    // Skapar fält för rasens namn och beskrivning
    private final String race;
    private final String flavour;
    private final String imageUrl;
    private final int strength;
    private final int stamina;
    private final int intelligence;

    
    // Konstruktor för enum
    Race(String race, String flavour, String imageUrl, int strength, int stamina, int intelligence) {
        this.race = race;
        this.flavour = flavour;
        this.imageUrl = imageUrl;
        this.strength = strength;
        this.stamina = stamina;
        this.intelligence = intelligence;
    }

    // Getter för rasens namn och beskrivning
    // Hämtas statiskt genom att skriva Race.HUMAN.getRace(), Race.ELF.getFlavour(), etc.
    // Eller om man vill hämta flavour text från en specifik hero blir det hero.getRace().getFlavour()
    // Vill man printa namnet på rasen blir det hero.getRace().getRace() 
    // (lite konstigt namnval kanske, men enkelt att komma ihåg)
    // TODO: byt namn?

    
    public String getRace() {
        return race;
    }

    public int getStrength() {
        return strength;
    }

    public int getStamina() {
        return stamina;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public String getFlavour(){
        return flavour;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    // Statisk metod för att hämta en lista med alla raser
    // Kan användas av klient för att t.ex visa val och flavourtext vid skapande av hero
    public static List<Race> raceList() {
    return List.of(Race.values());
    }

    // Den här metoden konverterar en sträng till motsvarande enum värde
    // Används för att validera inkommande ras från klient vid skapande av hero
    public static Race fromString(String value) {   // in värde i form av sträng
        for (Race r : Race.values()) {              // loopa igenom alla enum värden
            if (r.race.equalsIgnoreCase(value)) {   // om strängen matchar enum värdets namn
                return r;                           // returneras motsvarande enum värde
            }
        }
        throw new IllegalArgumentException("Unknown attribute: " + value);
    } 

}
