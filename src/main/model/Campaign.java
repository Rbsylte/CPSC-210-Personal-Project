package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;

public class Campaign implements Writable {
    private ArrayList<CharacterDesign> campaignArrayList;
    private String name;
    private int date;

    public Campaign(String name) {
        this.name = name;
        this.campaignArrayList = new ArrayList<>();
        this.date = 0;
        EventLog.getInstance().logEvent(new Event(name + " Campaign Was Created"));
    }

    public int getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CharacterDesign> getCampaign() {
        return campaignArrayList;
    }

    public void addCharacter(CharacterDesign character) {
        campaignArrayList.add(character);
        EventLog.getInstance().logEvent(new Event(character.getName() + "  is added to " + getName()));
    }

    public void setCalendar(int date) {
        this.date = date;
        EventLog.getInstance().logEvent(new Event("DND Date has been set")); //Fix to display date
    }

    private String getDateString(int date) {
        if (date == 1) {
            return "Monday";
        } else if (date == 2) {
            return "Tuesday";
        } else if (date == 3) {
            return "Wednesday";
        } else if (date == 4) {
            return "Thursday";
        } else if (date == 5) {
            return "Friday";
        } else if (date == 6) {
            return "Saturday";
        } else if (date == 7) {
            return "Sunday";
        }
        return "";
    }

    public int getSize() {
        return campaignArrayList.size();
    }

    /*
     * REQUIRES: currentDate >= 1 && currentDate <= 7
     * MODIFIES: this
     * EFFECTS: returns how many days until the next dnd session
     */
    public int checkDate(int currentDate) {
        if (currentDate < getDate()) {
            return getDate() - currentDate;
        } else {
            return 7 - currentDate + getDate();
        }
    }

    public String toString() {
        return getName();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("character", charactersToJson());
        return json;
    }

    private JSONArray charactersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CharacterDesign character : campaignArrayList) {
            jsonArray.put(character.toJson());
        }
        return jsonArray;
    }

}
