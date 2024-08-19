package model;

import java.util.Random;

public class DiceRoller {
    private int roll;

    public DiceRoller() {
        this.roll = 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-20
     */
    public static int rolld20() {
        Random r = new Random();
        return r.nextInt(20) + 1;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-12
     */
    public static int rolld12() {
        Random r = new Random();
        return r.nextInt(12) + 1;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-6
     */
    public static int rolld6() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-8
     */
    public static int rolld8() {
        Random r = new Random();
        return r.nextInt(8) + 1;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-4
     */
    public static int rolld4() {
        Random r = new Random();
        return r.nextInt(4) + 1;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-100
     */
    public static int rolld100() {
        Random r = new Random();
        return r.nextInt(100) + 1;
    }
}
