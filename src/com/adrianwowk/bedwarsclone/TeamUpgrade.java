package com.adrianwowk.bedwarsclone;

public enum TeamUpgrade {

    // Melee
    SHARPNESS("Sharpness", 4);


    private final String name;
    private final int cost;

    TeamUpgrade(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }


}
