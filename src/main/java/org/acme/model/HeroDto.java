package org.acme.model;

// Data Transfer Object som skickas från frontend. Innehåller endast nödvändiga fält.

public class HeroDto {

    private String race;
    private String name;
    private String heroClass;
    private String weapon;

    

    public String getRace() {
        return race;
    }

    public HeroDto setRace(String race) {
        this.race = race;
        return this;
    }

    public String getName() {
        return name;
    }

    public HeroDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public HeroDto setHeroClass(String heroClass) {
        this.heroClass = heroClass;
        return this;
    }

    public String getWeapon() {
        return weapon;
    }

    public HeroDto setWeapon(String weapon) {
        this.weapon = weapon;
        return this;
    }

    

}
