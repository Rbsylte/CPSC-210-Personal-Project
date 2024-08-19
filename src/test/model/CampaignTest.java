package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampaignTest {
    Campaign camp;
    CharacterDesign character;

    @BeforeEach
    public void start() {
        camp = new Campaign("Winter is Coming");
        character = new CharacterDesign("HootHoot", "Artificer", "Elf", 3);
    }

    @Test
    public void testCheckDate() {
        camp.setCalendar(1);
        assertEquals(camp.checkDate(4), 4);
        camp.setCalendar(4);
        assertEquals(camp.checkDate(1), 3);
    }

    @Test
    public void testToString() {
        assertEquals(camp.toString(), camp.getName());
    }

    @Test
    public void testAddCharacter(){
        camp.addCharacter(character);
        assertEquals(camp.getCampaign().size(), 1);
    }
}
