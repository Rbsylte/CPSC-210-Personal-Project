package persistence;

import model.Campaign;
import model.CharacterDesign;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {
    ArrayList<Campaign> test = new ArrayList<>();


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            test = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            test = reader.read();
        } catch (IOException e) {
            fail("IOException");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        testWith();
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            test = reader.read();
            ArrayList<CharacterDesign> characters = test.get(0).getCampaign();
            assertEquals(2, test.get(0).getSize());
            checkJson("Clairisa", "Druid", "Teifling", 3, characters.get(0));
            checkJson("Bjone", "Paladin", "Giant", 3, characters.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    void testWith() {
        try{
            Campaign ch = new Campaign("Campaign Room");
            ch.addCharacter(new CharacterDesign("Clairisa", "Druid", "Teifling", 3));
            ch.addCharacter(new CharacterDesign("Bjone", "Paladin", "Giant", 3));
            test.add(ch);
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralWorkroom.json");
            writer.open();
            writer.write(test);
            writer.close();
        } catch (Exception e){

        }

    }
}