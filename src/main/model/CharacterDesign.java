package model;

import persistence.Writable;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class CharacterDesign implements Writable {
    private final String name;          //Name of the character
    private final String classC;        //Class of the character
    private final String raceC;         //Race of the character
    private int health;                 //Health of character
    private int maxHealth;              //Max health
    private int armourClass;
    private int level;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private ArrayList<String> inventory;
    private static int enemiesSlay;
    private static int comradesLost;


    /*
     * REQUIRES: classC has to be part of the class options, raceC has to be part of the race option, level has to be
     * greater than 0 less than or equal to 10
     * EFFECTS: name is set to name, classC is set ot classC, raceC is set to raceC, level is set to level, strength,
     * dexterity, constitution, intelligence, wisdom, charisma is set ot 0. inventory is initallized as an array list.
     * enimiesSlay and comradesLost and date is set to 0;
     */
    public CharacterDesign(String name, String classC, String raceC, int level) {
        this.name = name;                       //Character's Name
        this.classC = classC;                   //Character's Class
        this.raceC = raceC;                     //Character's Race
        this.level = level;                     //Character's Class
        this.strength = 0;                      //Character's Strength Score
        this.dexterity = 0;                     //Character's Dexterity Score
        this.constitution = 0;                  //Character's Constitution Score
        this.intelligence = 0;                  //Character's intelligence Score
        this.wisdom = 0;                        //Character's Wisdom Score
        this.charisma = 0;                      //Character's Charisma Score
        this.inventory = new ArrayList<>();     //Character's Inventory
        this.enemiesSlay = 0;                   //Number of enemies character has killed
        this.comradesLost = 0;                  //Number of comrades that have died
    }

    //Getter Methods
    public String getName() {
        return name;
    }

    public String getRaceC() {
        return raceC;
    }

    public String getClassC() {
        return classC;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getArmourClass() {
        return armourClass;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getProficiency(int level) {
        if (level <= 4) {
            return 2;
        } else if (level <= 8) {
            return 3;
        } else if (level <= 12) {
            return 4;
        } else if (level <= 16) {
            return 5;
        } else {
            return 6;
        }
    }

    public ArrayList<String> viewInventory() {
        return inventory;
    }

    public int getEnemiesSlay() {
        return enemiesSlay;
    }

    public int getComradesLost() {
        return comradesLost;
    }

    //Setter Methods
    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void enemiesKilled(int num) {
        enemiesSlay += num;
    }

    public void comradesLost(int num) {
        comradesLost += num;
    }

    public void setHealth(int num) {
        health = num;
        maxHealth = num;
    }

    public void setMaxHealth(int num) {
        maxHealth = num;
    }

    public void setComradesLost(int num) {
        comradesLost = num;
    }

    public void setEnemiesSlay(int num) {
        enemiesSlay = num;
    }

    public void setInventory(ArrayList<String> inv) {
        inventory = inv;
    }

    public void levelUp() {
        this.level++;
    }

    public void setAC(int num) {
        armourClass = num;
    }

    /*
     * REQUIRES: heal >= 0
     * heal <= fullHealth - health
     * MODIFIES: this
     * EFFECTS: add heath points to the character
     */
    public void heal(int heal) {
        health += heal;
    }

    /*
     * REQUIRES: damage >= 0
     * MODIFIES: this
     * EFFECTS: reduces heath points of the character
     */
    public void damage(int damage) {
        health -= damage;
    }

    /*
     * REQUIRES: amount >= 0
     * MODIFIES: this
     * EFFECTS: amount is added to balance and updated
     * 			balance is returned
     */
    public void fullRest() {
        health = maxHealth;
    }


    /*
     * REQUIRES: stat >= 1
     * MODIFIES: this
     * EFFECTS: returns a modifier depending on the stat
     */
    public int statModifier(int stat) {
        if (stat == 1) {
            return -5;
        } else if (stat == 2 || stat == 3) {
            return -4;
        } else if (stat == 4 || stat == 5) {
            return -3;
        } else if (stat == 6 || stat == 7) {
            return -2;
        } else if (stat == 8 || stat == 9) {
            return -1;
        } else if (stat == 10 || stat == 11) {
            return 0;
        } else if (stat == 12 || stat == 13) {
            return 1;
        } else if (stat == 14 || stat == 15) {
            return 2;
        } else if (stat == 16 || stat == 17) {
            return 3;
        } else if (stat == 18 || stat == 19) {
            return 4;
        } else {
            return 5;
        }
    }

    /*
     * REQUIRES: object is a string
     * MODIFIES: inventory
     * EFFECTS: adds and object to inventory arraylist
     */
    public void addObject(String object) {
        inventory.add(object);
        EventLog.getInstance().logEvent(new Event(object + " has been added to " + getName() + "'s inventory"));
    }

    /*
     * REQUIRES: inventory has the object
     * MODIFIES: inventory
     * EFFECTS: removes an object from the inventory arraylist
     */
    public void removeObject(String object) {
        inventory.remove(object);
        EventLog.getInstance().logEvent(new Event(object + " has been removed from " + getName() + "'s inventory"));
    }


    /*
     * EFFECTS: returns string representation of saving throws
     */
    public String savingThrows() {
        String characterSaving = "STR ST: " + statModifier(getStrength()) + ", DEX ST: " + statModifier(getDexterity());
        String characterSavin = ", CON ST: " + statModifier(getConstitution());
        String characterSavi = ", INT ST: " + statModifier(getIntelligence());
        String characterSav = ", WIS: " + statModifier(getWisdom()) + ", CHA: " + statModifier(getCharisma());
        return characterSaving + characterSavin + characterSavi + characterSav;
    }

    /*
     * EFFECTS: returns basic string representation of character
     */
    public String characterStats() {
        String characterSt = "STR: " + getStrength() + ", DEX: " + getDexterity() + ", CON: " + getConstitution();
        String characterS = ", INT: " + getIntelligence() + ", WIS: " + getWisdom() + ", CHA: " + getCharisma();
        return characterSt + characterS;
    }

    /*
     * EFFECTS: returns a string representation of your character
     */
    @Override
    public String toString() {
        String characterN = "Name: " + getName() + ", Class: " + getClassC() + ", Race: " + getRaceC();
        String characterH = ", Health: " + getHealth() + ", Armor Class: " + getArmourClass();
        String characterSt = ", STR: " + getStrength() + ", DEX: " + getDexterity() + ", CON: " + getConstitution();
        String characterS = ", INT: " + getIntelligence() + ", WIS: " + getWisdom() + ", CHA: " + getCharisma();
        return characterN + characterH + characterSt + characterS;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("class", classC);
        json.put("race", raceC);
        json.put("health", health);
        json.put("maxHealth", maxHealth);
        json.put("armourClass", armourClass);
        json.put("level", level);
        json.put("strength", strength);
        json.put("dexterity", dexterity);
        json.put("constitution",constitution);
        json.put("intelligence",intelligence);
        json.put("wisdom",wisdom);
        json.put("charisma",charisma);
        json.put("inventory", inventory);
        json.put("enemiesSlay",enemiesSlay);
        json.put("comradesLost",comradesLost);
        return json;
    }
}