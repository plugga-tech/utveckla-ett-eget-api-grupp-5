package org.acme.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.model.enums.HeroClass;
import org.acme.model.enums.Race;
import org.acme.model.enums.Weapon;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Denna klassen hanterar enum services
 * Dessa kan användas för att lista de olika typerna av enums vi har
 */

@ApplicationScoped
public class EnumService {
     
// Exporterar ras-enums som en lista av maps, för att kunna användas i klienten
// för display av rasval och beskrivningar
// Användning för att visa data i klienten: (0 motsvarar id:t på den specifika heron.)
// raceList[0].name    (Skulle visa enum namnet HUMAN)
// raceList[0].displayName  (Skulle visa display namnet "Human")
// raceList[0].flavour (Skulle visa flavor text för rasen Human)
// etc.

public List<Map<String, String>> getAllRaces() {

    List<Map<String, String>> raceList = new ArrayList<>();

    for (Race r : Race.values()) {
        Map<String, String> raceMap = new HashMap<>();
        raceMap.put("name", r.name());
        raceMap.put("displayName", r.getRace());
        raceMap.put("flavour", r.getFlavour());
        raceMap.put("imageUrl", r.getImageUrl());
        raceList.add(raceMap);
    }

    return raceList;
}

// samma för vapen
public List<Map<String, String>> getAllWeapons() {

    List<Map<String, String>> weaponList = new ArrayList<>();

    for (Weapon w : Weapon.values()) {
        Map<String, String> weaponMap = new HashMap<>();
        weaponMap.put("name", w.name());
        weaponMap.put("displayType", w.getType());
        weaponMap.put("flavour", w.getFlavour());
        weaponList.add(weaponMap);
    }

    return weaponList;
}


// samma för klasser
public List<Map<String, String>> getAllClasses() {

    List<Map<String, String>> heroClassList = new ArrayList<>();

    for (HeroClass c : HeroClass.values()) {
        Map<String, String> heroClassMap = new HashMap<>();
        heroClassMap.put("name", c.name());
        heroClassMap.put("displayName", c.getHeroClass());
        heroClassMap.put("flavour", c.getFlavour());
        heroClassList.add(heroClassMap);
    }

    return heroClassList;
}





}
