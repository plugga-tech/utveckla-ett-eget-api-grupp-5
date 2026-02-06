package org.acme.model;

import org.acme.model.enums.Race;
import org.acme.model.enums.HeroClass;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    // utan den här försöker programmet lagra enumens värde som ett index (0,1,2,3) i databasen
    @Enumerated(EnumType.STRING)
    private Race race;
    @Enumerated(EnumType.STRING)
    private HeroClass heroClass;
    
    private String name;
    private boolean focusedFire;     // Elf specifik
    private boolean steadyFrame;     // Dwarf specifik
    private boolean strongArms;      // Orc specifik
    private boolean jackOfAllTrades; // Human specifik

    public int getId() {
        return id;
    }

    public Hero setId(int id) {
        this.id = id;
        return this;
    }

    public Race getRace() {
        return race;
    }

    public Hero setRace(Race race) {
        this.race = race;
        return this;
    }

    public boolean getFocusedFire() {
        return focusedFire;
    }

    public Hero setFocusedFire(boolean focusedFire) {
        this.focusedFire = focusedFire;
        return this;
    }

    public boolean getSteadyFrame() {
        return steadyFrame;
    }

    public Hero setSteadyFrame(boolean steadyFrame) {
        this.steadyFrame = steadyFrame;
        return this;
    }

    public boolean getStrongArms() {
        return strongArms;
    }

    public Hero setStrongArms(boolean strongArms) {
        this.strongArms = strongArms;
        return this;
    }

    public boolean getJackOfAllTrades() {
        return jackOfAllTrades;
    }

    public Hero setJackOfAllTrades(boolean jackOfAllTrades) {
        this.jackOfAllTrades = jackOfAllTrades;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hero setName(String name) {
        this.name = name;
        return this;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public Hero setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
        return this;
    }

}
