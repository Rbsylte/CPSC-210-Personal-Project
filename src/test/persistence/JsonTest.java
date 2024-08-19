package persistence;

import model.Campaign;
import model.CharacterDesign;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class JsonTest {
    protected void checkJson(String name, String classC, String raceC, int level, CharacterDesign character) {
        assertEquals(name, character.getName());
        assertEquals(classC, character.getClassC());
        assertEquals(raceC, character.getRaceC());
        assertEquals(level, character.getLevel());
    }
}
