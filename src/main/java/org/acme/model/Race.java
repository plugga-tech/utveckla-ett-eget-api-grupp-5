package org.acme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String race;

    private boolean focusedFire;     // Elf
    private boolean steadyFrame;     // Dwarf
    private boolean strongArms;      // Orc
    private boolean jackOfAllTrades; // Human

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public boolean getFocusedFire() {
        return focusedFire;
    }

    public void setFocusedFire(boolean focusedFire) {
        this.focusedFire = focusedFire;
    }

    public boolean getSteadyFrame() {
        return steadyFrame;
    }

    public void setSteadyFrame(boolean steadyFrame) {
        this.steadyFrame = steadyFrame;
    }

    public boolean getStrongArms() {
        return strongArms;
    }

    public void setStrongArms(boolean strongArms) {
        this.strongArms = strongArms;
    }

    public boolean getJackOfAllTrades() {
        return jackOfAllTrades;
    }

    public void setJackOfAllTrades(boolean jackOfAllTrades) {
        this.jackOfAllTrades = jackOfAllTrades;
    }

}
