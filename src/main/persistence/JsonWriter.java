package persistence;

import model.CharacterDesign;
import model.Campaign;
import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of campaign room to file
    public void write(ArrayList<Campaign> c) {
        JSONArray campaigns = new JSONArray();
        for (Campaign camp : c) {
            JSONObject json = camp.toJson();
            campaigns.put(json);
            EventLog.getInstance().logEvent(new Event(
                    camp.getName() + " was added to " + destination));
        }
        saveToFile(campaigns.toString(TAB));

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
