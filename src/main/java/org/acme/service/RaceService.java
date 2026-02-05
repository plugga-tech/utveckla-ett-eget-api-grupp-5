package org.acme.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.model.enums.Race;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RaceService {
     
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
        raceList.add(raceMap);
    }

    return raceList;
}

}
