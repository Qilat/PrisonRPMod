package fr.qilat.prisonrp.server.game;

import fr.qilat.prisonrp.server.utils.Location;

/**
 * Created by Qilat on 21/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class CharacterInfo {
    String name;
    int level;
    String grade;

    Location pos1 = null;
    Location pos2 = null;


    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getGrade() {
        return grade;
    }

}
