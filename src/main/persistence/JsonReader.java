package persistence;

import model.Campaign;
import model.CharacterDesign;
import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;
    ArrayList<Campaign> list = new ArrayList<>();

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Campaign> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return  parseCampaign(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses campaign from JSON object and returns it
    private ArrayList<Campaign> parseCampaign(JSONArray jsonArray) {
        Campaign cp = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String name = jsonObject.getString("name");
            cp = new Campaign(name);
            addCharacters(cp, jsonObject);
            list.add(cp);
            EventLog.getInstance().logEvent(new Event(
                    cp.getName() + " was added from " + source));
        }
        return list;
    }

    // MODIFIES: ch
    // EFFECTS: parses character from JSON object and adds them to Character
    private void addCharacters(Campaign ch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("character");
        for (Object json : jsonArray) {
            JSONObject nextChar = (JSONObject) json;
            addChar(ch, nextChar);
        }
    }

    // MODIFIES: ch
    // EFFECTS: parses character from JSON object and adds it to workroom
    private void addChar(Campaign ch, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String classC = jsonObject.getString("class");
        String raceC = jsonObject.getString("race");
        int level = jsonObject.getInt("level");
        CharacterDesign character = new CharacterDesign(name, classC, raceC, level);
        character.setStrength(jsonObject.getInt("strength"));
        character.setDexterity(jsonObject.getInt("dexterity"));
        character.setConstitution(jsonObject.getInt("constitution"));
        character.setIntelligence(jsonObject.getInt("intelligence"));
        character.setWisdom(jsonObject.getInt("wisdom"));
        character.setCharisma(jsonObject.getInt("charisma"));
        character.setHealth(jsonObject.getInt("health"));
        character.setMaxHealth(jsonObject.getInt("maxHealth"));
        character.setComradesLost(jsonObject.getInt("comradesLost"));
        character.setEnemiesSlay(jsonObject.getInt("enemiesSlay"));
        character.setAC(jsonObject.getInt("armourClass"));
        character.setInventory(inventory(jsonObject));
        ch.addCharacter(character);
    }

    private ArrayList inventory(JSONObject jsonObject) {
        ArrayList<String> inv = new ArrayList<String>();
        JSONArray j = jsonObject.getJSONArray("inventory");
        if (j != null) {
            for (int i = 0; i < j.length();i++) {
                inv.add(j.getString(i));
            }
        }
        return inv;
    }
}
