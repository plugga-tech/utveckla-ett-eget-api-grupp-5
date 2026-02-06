package org.acme.model.enums;

import java.util.List;

// Enum för olika raser.
// Innehåller rasens namn för att kunna användas som display i frontend, samt en beskrivning av rasen.

public enum HeroClass {

    PALADIN     ("Paladin" ,"A shining hero devoted to justice, healing friends and smiting evil while delivering speeches nobody asked for but everyone endures."),
    WARRIOR     ("Warrior","A fearless frontline brawler who believes every problem improves after repeated hitting, preferably with something large and expensive."),
    ROGUE       ("Rogue","A stealthy opportunist specializing in surprise attacks and “borrowing” valuables, then generously leaving before awkward explanations begin."),
    MAGE       ("Mage", "A scholar of dangerous magic who bends reality at will, insisting the explosion was intentional and absolutely part of the plan.");

    // Skapar fält för rasens namn och beskrivning
    private final String heroClass;
    private final String flavour;
   
    
    // Konstruktor för enum
    HeroClass(String heroClass, String flavour) {
        this.heroClass = heroClass;
        this.flavour = flavour;
       
    }

    // Getter för rasens namn och beskrivning
    // Hämtas statiskt genom att skriva HeroClass.PALADIN.getHeroClass(), HeroClass.WARRIOR.getFlavour(), etc.
    // Eller om man vill hämta flavour text från en specifik klass blir det hero.getHeroClass().getFlavour()
    // Vill man printa namnet på klassen blir det hero.getHeroClass().getHeroClass() 
    // TODO: byt namn?
    public String getHeroClass() {
        return heroClass;
    }
     public String getFlavour(){
        return flavour;
    }

  
    // Statisk metod för att hämta en lista med alla Klasser
    // Kan användas av klient för att t.ex visa val och flavourtext vid skapande av hero
    public static List<HeroClass> HeroClassList() {
    return List.of(HeroClass.values());
    }

    // Den här metoden konverterar en sträng till motsvarande enum värde
    // Används för att validera inkommande ras från klient vid skapande av hero
    public static HeroClass fromString(String value) {   // in värde i form av sträng
        for (HeroClass h : HeroClass.values()) {              // loopa igenom alla enum värden
            if (h.heroClass.equalsIgnoreCase(value)) {   // om strängen matchar enum värdets namn
                return h;                           // returneras motsvarande enum värde
            }
        }
        throw new IllegalArgumentException("Unknown attribute: " + value);
    } 

}
