package org.acme.model.enums;

import java.util.List;

// Enum för olika raser.
// Innehåller rasens namn för att kunna användas som display i frontend, samt en beskrivning av rasen.

public enum Race {

    HUMAN   ("Human"    , "Human are potent in just about anything, but they don't excel in any particular trait."),
    ORC     ("Orc"      , "Orcs are massive, intimidating, and strong."),
    ELF     ("Elf"      , "Elves are naturally agile and quick on their feet."),
    DWARF   ("Dwarf"    , "Dwarves are short, but sturdy. They know how to take a punch.");

    // Skapar fält för rasens namn och beskrivning
    private final String race;
    private final String flavour;
    
    // Konstruktor för enum
    Race(String race, String flavour) {
        this.race = race;
        this.flavour = flavour;
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

    public String getFlavour(){
        return flavour;
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
