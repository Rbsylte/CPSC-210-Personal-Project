package persistence;

import model.Campaign;
import model.CharacterDesign;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    ArrayList<Campaign> test = new ArrayList<>();

    @Test
    void testWriterInvalidFile() {
        try {
            Campaign c = new Campaign("Campaign Room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Campaign ch = new Campaign("Campaign Room");
            ch.addCharacter(new CharacterDesign("Clairisa", "Druid", "Teifling", 3));
            ch.addCharacter(new CharacterDesign("Bjone", "Paladin", "Giant", 3));
            test.add(ch);
            JsonWriter writer = new JsonWriter("./data/testWriterCharacterWorkroom.json");
            writer.open();
            writer.write(test);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCharacterWorkroom.json");
            test = reader.read();
            assertEquals("Campaign Room", ch.getName());
            ArrayList<CharacterDesign> characters = ch.getCampaign();
            assertEquals(2, characters.size());
            checkJson("Clairisa", "Druid", "Teifling", 3, characters.get(0));
            checkJson("Bjone", "Paladin", "Giant", 3, characters.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}