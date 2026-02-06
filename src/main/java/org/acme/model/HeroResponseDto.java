package org.acme.model;

import org.acme.model.enums.HeroClass;
import org.acme.model.enums.Race;

// Response Data Transfer Object som skickas till frontend. 
// Innehåller de fält som vi vill visa till klient. (TODO: fixa vilka fält som ska vara med)

public class HeroResponseDto {

    private int     id;
    private String  name;
    private HeroClass  heroClass;
    private Race    race;
    private boolean focusedFire;     // Elf
    private boolean steadyFrame;     // Dwarf
    private boolean strongArms;      // Orc
    private boolean jackOfAllTrades; // Human

    public int getId() {
        return id;
    }

    public HeroResponseDto setId(int id) {
        this.id = id;
        return this;
    }

    public Race getRace() {

        return race;
    }

    public HeroResponseDto setRace(Race race) {
        this.race = race;
        return this;
    }

    public boolean getFocusedFire() {
        return focusedFire;
    }

    public HeroResponseDto setFocusedFire(boolean focusedFire) {
        this.focusedFire = focusedFire;
        return this;
    }

    public boolean getSteadyFrame() {
        return steadyFrame;
    }

    public HeroResponseDto setSteadyFrame(boolean steadyFrame) {
        this.steadyFrame = steadyFrame;
        return this;
    }

    public boolean getStrongArms() {
        return strongArms;
    }

    public HeroResponseDto setStrongArms(boolean strongArms) {
        this.strongArms = strongArms;
        return this;
    }

    public boolean getJackOfAllTrades() {
        return jackOfAllTrades;
    }

    public HeroResponseDto setJackOfAllTrades(boolean jackOfAllTrades) {
        this.jackOfAllTrades = jackOfAllTrades;
        return this;
    }

    public String getName() {
        return name;
    }

    public HeroResponseDto setName(String name) {
        this.name = name;
        return this;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public HeroResponseDto setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
        return this;
    }

}
