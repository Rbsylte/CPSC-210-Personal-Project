package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharacterDesignTest {
    CharacterDesign character;

    @BeforeEach
    public void start() {
        character = new CharacterDesign("HootHoot", "Artificer", "Elf", 3);
        character.setHealth(15);
        character.setAC(18);
        character.setStrength(12);
        character.setDexterity(14);
        character.setConstitution(18);
        character.setWisdom(13);
        character.setIntelligence(17);
        character.setCharisma(10);
        character.enemiesKilled(5);
        character.comradesLost(2);
    }

    @Test
    public void testGetName() {
        assertEquals(character.getName(), "HootHoot");
    }

    @Test
    public void testGetRaceC() {
        assertEquals(character.getRaceC(), "Elf");
    }

    @Test
    public void testGetClassC() {
        assertEquals(character.getClassC(),"Artificer");
    }

    @Test
    public void testGetMaxHealth(){
        assertEquals(character.getMaxHealth(), 15);
    }

    @Test
    public void testGetArmorClass() {
        assertEquals(character.getArmourClass(), 18);
    }

    @Test
    public void testGetLevel(){
        assertEquals(character.getLevel(), 3);
    }

    @Test
    public void testProficency(){
        assertEquals(character.getProficiency(2), 2);
        assertEquals(character.getProficiency(6), 3);
        assertEquals(character.getProficiency(10), 4);
        assertEquals(character.getProficiency(14), 5);
        assertEquals(character.getProficiency(18), 6);
    }

    @Test
    public void testGetEniemesKilled() {
        assertEquals(character.getEnemiesSlay(), 5);
    }

    @Test
    public void testEniemesKilled() {
        character.enemiesKilled(2);
        assertEquals(character.getEnemiesSlay(),7);
    }

    @Test
    public void testGetComradesLost() {
        assertEquals(character.getComradesLost(), 2);
    }

    @Test
    public void testComradesLost() {
        character.comradesLost(2);
        assertEquals(character.getComradesLost(), 4);
    }

    @Test
    public void testLevelUp() {
        character.levelUp();
        assertEquals(character.getLevel(), 4);
    }

    @Test
    public void testHeal() {
        character.heal(5);
        assertTrue(character.getHealth() == 20);
    }

    @Test
    public void testDamage() {
        character.damage(5);
        assertTrue(character.getHealth() == 10);
    }

    @Test
    public void testFullRest() {
        character.damage(5);
        character.fullRest();
        assertTrue(character.getHealth() == 15);
    }

    @Test
    public void testStatModifier() {
        assertTrue(character.statModifier(character.getStrength()) == 1);
        assertTrue(character.statModifier(character.getDexterity()) == 2);
        assertTrue(character.statModifier(character.getConstitution()) == 4);
        assertTrue(character.statModifier(character.getIntelligence()) == 3);
        assertTrue(character.statModifier(character.getWisdom()) == 1);
        assertTrue(character.statModifier(character.getCharisma()) == 0);
    }

    @Test
    public void testModifier() {
        assertEquals(character.statModifier(1), -5);
        assertEquals(character.statModifier(3), -4);
        assertEquals(character.statModifier(5), -3);
        assertEquals(character.statModifier(7), -2);
        assertEquals(character.statModifier(9), -1);
        assertEquals(character.statModifier(11), 0);
        assertEquals(character.statModifier(13), 1);
        assertEquals(character.statModifier(15), 2);
        assertEquals(character.statModifier(17), 3);
        assertEquals(character.statModifier(19), 4);
        assertEquals(character.statModifier(21), 5);
        assertEquals(character.statModifier(2), -4);
        assertEquals(character.statModifier(4), -3);
        assertEquals(character.statModifier(6), -2);
        assertEquals(character.statModifier(8), -1);
        assertEquals(character.statModifier(10), 0);
        assertEquals(character.statModifier(12), 1);
        assertEquals(character.statModifier(14), 2);
        assertEquals(character.statModifier(16), 3);
        assertEquals(character.statModifier(18), 4);
        assertEquals(character.statModifier(20), 5);
        assertEquals(character.statModifier(22), 5);
    }

    @Test
    public void testAddObject() {
        character.addObject("Backpack");
        assertEquals(character.viewInventory().size(), 1);
    }

    @Test
    public void testRemoveObject() {
        character.addObject("Backpack");
        character.removeObject("Backpack");
        assertEquals(character.viewInventory().size(), 0);
    }

    @Test
    public void testSavingThrows() {
        String characterSaving = "STR ST: " + character.statModifier(character.getStrength()) + ", DEX ST: " + character.statModifier(character.getDexterity());
        String characterSavin = ", CON ST: " + character.statModifier(character.getConstitution());
        String characterSavi = ", INT ST: " + character.statModifier(character.getIntelligence());
        String characterSav = ", WIS: " + character.statModifier(character.getWisdom()) + ", CHA: " + character.statModifier(character.getCharisma());
        String all = characterSaving + characterSavin + characterSavi + characterSav;
        assertEquals(character.savingThrows(), all);
    }

    @Test
    public void testCharacterStats() {
        String characterSt = "STR: " + character.getStrength() + ", DEX: " + character.getDexterity() + ", CON: " + character.getConstitution();
        String characterS = ", INT: " + character.getIntelligence() + ", WIS: " + character.getWisdom() + ", CHA: " + character.getCharisma();
        String all = characterSt + characterS;
        assertEquals(character.characterStats(), all);
    }

    @Test
    public void testToString() {
        String characterN = "Name: " + character.getName() + ", Class: " + character.getClassC() + ", Race: " + character.getRaceC();
        String characterH = ", Health: " + character.getHealth() + ", Armor Class: " + character.getArmourClass();
        String characterSt = ", STR: " + character.getStrength() + ", DEX: " + character.getDexterity() + ", CON: " + character.getConstitution();
        String characterS = ", INT: " + character.getIntelligence() + ", WIS: " + character.getWisdom() + ", CHA: " + character.getCharisma();
        String all = characterN + characterH + characterSt + characterS;
        assertEquals(character.toString(), all);
    }

}