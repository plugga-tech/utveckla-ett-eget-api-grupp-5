package org.acme.model.enums;

import java.util.List;

public enum Weapon {

    SWORD   ("Sword",   "Shines in the sun, sings in battle, and solves most problems with a swing."),
    MACE    ("Mace",    "Because sometimes subtlety is overrated."),
    DAGGER  ("Dagger",  "Fits in your boot, your belt, and your enemy's back"),
    STAFF   ("Staff",   "Not just for wizards. Also good for pointing at things you'd rather not touch.");

    private final String type;
    private final String flavour;

    Weapon(String type, String flavour) {
        this.type = type;
        this.flavour = flavour;
    }

    public String getType(){
        return type;
    }

    public String getFlavour() {
        return flavour;
    }

    public static List<Weapon> weaponList(){
        return List.of(Weapon.values());
    }

    public static Weapon fromString(String value) {
        for (Weapon w : Weapon.values()) {
            if (w.type.equalsIgnoreCase(value)) {
                return w;
            }
        }
        throw new IllegalArgumentException("Unknown weapontype: " + value);
    }

}
