package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceRollerTest {
    DiceRoller dice;

    @BeforeEach
    public void start() {
        dice = new DiceRoller();
    }

    @Test
    public void testRolld20() {
        assertTrue(dice.rolld20() >= 1);
        assertTrue(dice.rolld20() <= 20);
    }

    @Test
    public void testRolld12() {
        assertTrue(dice.rolld12() >= 1);
        assertTrue(dice.rolld12() <= 12);
    }

    @Test
    public void testRolld6() {
        assertTrue(dice.rolld6() >= 1);
        assertTrue(dice.rolld6() <= 6);
    }

    @Test
    public void testRolld8() {
        assertTrue(dice.rolld8() >= 1);
        assertTrue(dice.rolld8() <= 8);
    }

    @Test
    public void testRolld4() {
        assertTrue(dice.rolld4() >= 1);
        assertTrue(dice.rolld4() <= 4);
    }

    @Test
    public void testRolld100() {
        assertTrue(dice.rolld100() >= 1);
        assertTrue(dice.rolld100() <= 100);
    }
}