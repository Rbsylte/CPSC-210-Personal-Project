package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import model.Campaign;
import model.CharacterDesign;
import model.DiceRoller;
import persistence.JsonWriter;
import persistence.JsonReader;

public class DungeonsAndDragonsGUI {
    private static final String JSON_Campaign = "./data/campaigns.json";
    private ArrayList<Campaign> campaigns;     //Character ArrayList
    private JsonWriter jsonWriter = new JsonWriter("./data/campaign.json");
    private JsonReader jsonReader = new JsonReader("./data/campaign.json");

    //Constructor
    public DungeonsAndDragonsGUI() throws FileNotFoundException {
        campaigns = new ArrayList<>();
    }

    public Campaign createNewCampaign(String name, int characters) {
        Campaign camp = new Campaign(name);
        return camp;
    }

    //EFFECTS: creates a character
    public CharacterDesign createCharacter(String name, String classC, String raceC, int level) {
        CharacterDesign character = new CharacterDesign(name, classC, raceC, level);
        DiceRoller dice = new DiceRoller();
        return character;
    }

    public ArrayList<Campaign> getCampaignsArrayList() {
        return campaigns;
    }

    public void addCampaignsArrayList(Campaign campaign) {
        campaigns.add(campaign);
    }

    /*
    public void playCampaign() {
        Scanner sn = new Scanner(System.in);
        if (campaigns.size() == 0) {
            System.out.println("There are no campaigns to play");
            runApp();
        } else {
            System.out.println("\t What campaign would you like to play?");
            for (int i = 0; i < campaigns.size(); i++) {
                System.out.println("\t[" + i + "] " + campaigns.get(i));
            }
            int value = sn.nextInt();
            if (campaigns.get(value).getCampaign().size() == 0) {
                System.out.println("There are no characters in this campaign.");
                System.out.println("Deleting Campaign......");
                System.out.println();
                campaigns.remove(campaigns.get(value));
                runApp();
            }
            selectCharacter(campaigns.get(value));
        }
    } //Undone

     */

    public Campaign chooseCampaign() {
        Scanner sn = new Scanner(System.in);
        for (int i = 0; i < campaigns.size(); i++) {
            System.out.println("\t[" + i + "] " + campaigns.get(i));
        }
        Campaign campaign = campaigns.get(sn.nextInt());
        return campaign;
    }

    //EFFECTS: prints all characters
    public CharacterDesign chooseCharacter(ArrayList<CharacterDesign> characters) {
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < characters.size(); i++) {
            System.out.println("\t[" + i + "] " + characters.get(i));
        }
        CharacterDesign character = characters.get(s.nextInt());
        return character;
    }

    //EFFECTS: user input to create a date, and find when is the next date based on the date
    public void calendar() {
        Scanner s = new Scanner(System.in);
        System.out.println("\t [1] Set Date");
        System.out.println("\t [2] Check next date");
        int value = s.nextInt();
        if (value == 1) {
            System.out.println("\t What campaign would you like to set the date for?");
            Campaign camp = chooseCampaign();
            System.out.println("\t What date would you like to set?");
            camp.setCalendar(s.nextInt());
        } else if (value == 2) {
            System.out.println("\t What campaign would you like to see the next date for?");
            Campaign camp = chooseCampaign();
            System.out.println("What day is it today?");
            System.out.println(camp.checkDate(s.nextInt()) + " days until next game.");
        }
    }

    //EFFECTS: user input based on character action page 1
    public void playCharacterPage1(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        int value = s.nextInt();
        s.nextLine();
        while (value != 0) {
            if (value == 1) {
                rollDice(camp, character, dice);
            } else if (value == 2) {
                initative(camp, character, dice);
            } else if (value == 3) {
                sleepOrHealOrDamage(camp, character, dice);
            } else if (value == 4) {
                inventory(camp, character, dice);
            } else if (value == 5) {
                playCharacterPage2(camp, character, dice);
            }
            value = s.nextInt();
        }
        playCharacterPage1(camp, character, dice);
    }

    //MODIFIES: inventory
    //EFFECTS: user input to find, add or remove stuff from inventory
    public void inventory(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        System.out.println("\t [1] Access Inventory");
        System.out.println("\t [2] Add Item");
        System.out.println("\t [3] Remove Item");
        System.out.println("\t [0] Leave Inventory");
        int value = s.nextInt();
        if (value == 1) {
            character.viewInventory();
        } else if (value == 2) {
            System.out.println("\t What object would you like to be added to your inventory?");
            String object = s.nextLine();
            character.addObject(object);
        } else if (value == 3) {
            System.out.println("\t What object would you like to be removed to your inventory?");
            character.viewInventory();
            String object = s.nextLine();
            character.addObject(object);
        } else if (value == 0) {
            playCharacterPage1(camp, character, dice);
        }
    }

    //EFFECTS: roll a random dice based on user input
    public void rollDice(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        int value = s.nextInt();
        while (value != 0) {
            if (value == 1) {
                System.out.println("Rolled: " + dice.rolld4());
            } else if (value == 2) {
                System.out.println("Rolled: " + dice.rolld6());
            } else if (value == 3) {
                System.out.println("Rolled: " + dice.rolld8());
            } else if (value == 4) {
                System.out.println("Rolled: " + dice.rolld12());
            } else if (value == 5) {
                System.out.println("Rolled: " + dice.rolld20());
            } else if (value == 6) {
                System.out.println("Rolled: " + dice.rolld100());
            }
            if (value == 0) {
                playCharacterPage1(camp, character, dice);
            }
            value = s.nextInt();
        }
    }


    //MODIFIES: enimesKilled, ComradesLost
    //EFFECTS: prints initative and user input for enimes killed and comrades lost
    public void initative(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        System.out.println("Roll Initative: " + rollInitative(character, dice));
        System.out.println("How many enemies did you kill?");
        character.enemiesKilled(s.nextInt());
        System.out.println("How many comrades did you lose?");
        character.comradesLost(s.nextInt());
        playCharacterPage1(camp, character, dice);
    }

    //EFFECTS: user input to sleep heal or damage
    public void sleepOrHealOrDamage(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        System.out.println("\t Would you like to sleep/heal/damage your character");
        System.out.println("\t [1] Sleep");
        System.out.println("\t [2] Heal");
        System.out.println("\t [3] Damage");
        System.out.println("\t [0] Exit");
        int value = s.nextInt();
        if (value == 1) {
            sleep(camp, character, dice);
        } else if (value == 2) {
            heal(camp, character, dice);
        } else if (value == 3) {
            damage(camp, character, dice);
        } else if (value == 0) {
            playCharacterPage1(camp, character, dice);
        }
    }

    //MODIFIES: health
    //EFFECTS: heal your character based on user input
    public void sleep(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        System.out.println("\t Is it a full rest or half rest");
        System.out.println("\t [1] Full Rest");
        System.out.println("\t [2] Half Rest");
        System.out.println("\t [0] Exit");
        int value = s.nextInt();
        if (value == 1) {
            character.fullRest();
            System.out.println("Your character is at max health");
            sleep(camp, character, dice);
        } else if (value == 2) {
            System.out.println("How much does your character heal by?");
            character.heal(s.nextInt());
            System.out.println("Your character has: " + character.getHealth() + "hp");
            sleep(camp, character, dice);
        } else if (value == 0) {
            sleepOrHealOrDamage(camp, character, dice);
        }
    }

    //MODIFIES: health
    //EFFECTS: heal your character based on user input
    public void heal(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        System.out.println("How much does your character heal by?");
        character.heal(s.nextInt());
        System.out.println("Your character has: " + character.getHealth() + "hp");
        sleepOrHealOrDamage(camp, character, dice);
    }

    //MODIFIES: health
    //EFFECTS: damage character based on user input and roll death saving dice if health < 0
    public void damage(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        System.out.println("How much damage your character take?");
        int damage = s.nextInt();
        int currentHealth = character.getHealth();
        int max = character.getMaxHealth();
        character.damage(damage);
        System.out.println("Your character gets damaged by: " + damage);
        System.out.println("Your current health is: " + character.getHealth());
        if (damage >= (currentHealth + max)) {
            deadCharacter(camp, character);
        } else if (character.getHealth() <= 0) {
            System.out.println("Your character is knocked out");
            System.out.println("Rolling death saving throws");
            deathSavingThrows(camp, character, dice);
        }
        sleepOrHealOrDamage(camp, character, dice);
    }

    //EFFECTS: roll death saving to see if character has survied
    public void deathSavingThrows(Campaign camp, CharacterDesign character, DiceRoller dice) {
        int saves = 0;
        int fails = 0;
        while (saves < 3 && fails < 3) {
            int roll = dice.rolld20();
            int savingThrow = roll + character.statModifier(character.getConstitution());
            System.out.println("Death Saving Throw: " + savingThrow);
            if (roll == 20) {
                saves += 2;
            } else if (roll == 1) {
                fails += 2;
            } else if (savingThrow >= 10) {
                saves += 1;
            } else if (savingThrow < 10) {
                fails += 1;
            }
        }
        if (fails > saves) {
            deadCharacter(camp, character);
        } else if (saves > fails) {
            System.out.println("You have survived");
            character.setHealth(1);
        }
    }

    //MODIFIES: characterDesignArrayList
    //EFFECTS: if character dies then remove character from arraylist
    public void deadCharacter(Campaign camp, CharacterDesign character) {
        System.out.println("Your character is dead");
        camp.getCampaign().remove(character);
    }

    //EFFECTS: print page 2 of character actions
    public void characterActionsPage2() {
        System.out.println("\t [1] Character Stats");
        System.out.println("\t [2] View All Stats");
        System.out.println("\t [3] Level Up");
        System.out.println("\t [4] Page 1");
        System.out.println("\t [9] Delete Character");
        System.out.println("\t [0] Leave Character");
    }

    //EFFECTS: access the page 2 options
    public void playCharacterPage2(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        characterActionsPage2();
        int value = s.nextInt();;
        s.nextLine();
        playCharacterPage2Options(value, camp, character, dice);
    }

    //EFFECTS: character options based on user input
    public void playCharacterPage2Options(int value, Campaign camp, CharacterDesign character, DiceRoller dice) {
        while (value != 0) {
            if (value == 1) {
                allCharacterStats(character);
                playCharacterPage2(camp, character, dice);
            } else if (value == 2) {
                allStats1(character);
                playCharacterPage2(camp, character, dice);
            } else if (value == 3) {
                levelStat(camp, character, dice);
            } else if (value == 4) {
                playCharacterPage1(camp, character, dice);
            } else if (value == 9) {
                camp.getCampaign().remove(character);
                System.out.println(character.getName() + " has been deleted");
            } else if (value == 0) {
                System.out.println("Test");
            }
        }
    }

    //EFFECTS: print user common stats and saving throws
    public void allCharacterStats(CharacterDesign character) {
        System.out.println(character.characterStats());
        System.out.println(character.savingThrows());
    }

    //EFFECTS: print possible ability stats
    public void allStats1(CharacterDesign character) {
        System.out.println("Proficiency: " + character.getProficiency(character.getLevel()));
        System.out.println("Acrobatics: " + character.statModifier(character.getDexterity()));
        System.out.println("Animal Handling: " + character.statModifier(character.getWisdom()));
        System.out.println("Arcana: " + character.statModifier(character.getIntelligence()));
        System.out.println("Athletics: " + character.statModifier(character.getStrength()));
        System.out.println("Deception: "  + character.statModifier(character.getCharisma()));
        System.out.println("History: " + character.statModifier(character.getIntelligence()));
        System.out.println("Insight: " + character.statModifier(character.getWisdom()));
        System.out.println("Intimidation: "  + character.statModifier(character.getCharisma()));
        System.out.println("Investigation: " + character.statModifier(character.getIntelligence()));
        System.out.println("Medicine: " + character.statModifier(character.getWisdom()));
        System.out.println("Nature: " + character.statModifier(character.getIntelligence()));
        System.out.println("Perception: " + character.statModifier(character.getWisdom()));
        System.out.println("Performance: "  + character.statModifier(character.getCharisma()));
        System.out.println("Persuasion: "  + character.statModifier(character.getCharisma()));
        System.out.println("Religion: "  + character.statModifier(character.getIntelligence()));
        System.out.println("Sleight of Hand: " + character.statModifier(character.getDexterity()));
        System.out.println("Stealth: " + character.statModifier(character.getDexterity()));
        System.out.println("Survival: " + character.statModifier(character.getWisdom()));
        System.out.println("Enemies Killed: " + character.getEnemiesSlay());
        System.out.println("Comrades Lost: " + character.getComradesLost());
    }

    //MODIFIES: level
    //EFFECTS: change or view level based on user input
    public void levelStat(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        System.out.println("\t [1] View Level");
        System.out.println("\t [2] Level Up");
        System.out.println("\t [0] Back");
        int value = s.nextInt();
        if (value == 1) {
            System.out.println(character.getName() + " is level " + character.getLevel());
        } else if (value == 2) {
            System.out.println(character.getName() + " is level " + character.getLevel());
            System.out.println("Level Up...");
            character.levelUp();
            System.out.println(character.getName() + " is level " + character.getLevel());
        } else if (value == 0) {
            playCharacterPage2(camp, character, dice);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates stats for each of the 6 characteristics
     */
    public ArrayList<Integer> statGeneration(DiceRoller dice) {
        ArrayList<Integer> stats = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int sum = 0;
            ArrayList<Integer> rolls = new ArrayList<>();
            for (int k = 0; k < 4; k++) {
                rolls.add(dice.rolld6());
            }
            int index = 0;
            int lowestValue = Integer.MAX_VALUE;
            for (int j = 0; j < 4; j++) {
                if (rolls.get(j) < lowestValue) {
                    index = j;
                    lowestValue = rolls.get(j);
                }
            }
            rolls.remove(index);
            for (int roll: rolls) {
                sum += roll;
            }
            stats.add(sum);
        }
        return stats;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-20 and also the stat modifier for the players dexterity
     */
    public int rollInitative(CharacterDesign character, DiceRoller dice) {
        return (dice.rolld20() + character.statModifier(character.getDexterity()));
    }

    // EFFECTS: saves the workroom to file
    public void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(campaigns);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_Campaign);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadWorkRoom() {
        try {
            campaigns = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_Campaign);
        }
    }
}
