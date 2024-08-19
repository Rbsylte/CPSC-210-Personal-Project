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

public class DungeonsAndDragonsApp {
    private static final String JSON_Campaign = "./data/campaigns.json";
    private ArrayList<Campaign> campaigns;     //Character ArrayList
    private JsonWriter jsonWriter = new JsonWriter("./data/campaign.json");
    private JsonReader jsonReader = new JsonReader("./data/campaign.json");

    //Constructor
    public DungeonsAndDragonsApp() throws FileNotFoundException {
        campaigns = new ArrayList<>();
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        Scanner s = new Scanner(System.in);
        options();
        int value = s.nextInt();
        if (value == 1) {
            createNewCampaign();
            runApp();
        } else if (value == 2) {
            playCampaign();
            runApp();
        } else if (value == 3) {
            System.out.println(campaigns);
            runApp();
        } else if (value == 4) {
            saveOrLoad();
            runApp();
        } else if (value == 5) {
            calendar();
            runApp();
        } else if (value == 0) {
            System.out.println("Thank You!!");
        }
    }

    //EFFECTS: prints character options
    public void options() {
        System.out.println("What would you like to do?");
        System.out.println("\t [1] Create Campaign");
        System.out.println("\t [2] Play Campaign");
        System.out.println("\t [3] View Campaigns");
        System.out.println("\t [4] Save/Load Data");
        System.out.println("\t [5] View Calendar");
        System.out.println("\t [0] Close");
    }

    public void createNewCampaign() {
        Scanner sn = new Scanner(System.in);
        System.out.println("\t What is the campaign name?");
        Campaign camp = new Campaign(sn.nextLine());
        System.out.println("\t How many characters are in your campaign?");
        int value = sn.nextInt();
        for (int i = 0; i < value - 1; i++) {
            camp.addCharacter(create());
            System.out.println("\t Next Character.....");
        }
        camp.addCharacter(create());
        campaigns.add(camp); // Why does this not work?
    }

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
    }

    //EFFECTS: creates a character
    public CharacterDesign create() {
        Scanner n = new Scanner(System.in);
        System.out.println("\t What is your characters name?");
        String name = n.nextLine();
        String classC = createClass();
        String raceC = createRace();
        int level = createLevel();
        CharacterDesign character = new CharacterDesign(name, classC, raceC, level);
        DiceRoller dice = new DiceRoller();
        statGeneration(character, dice);
        return character;
    }

    //EFFECTS: user input class
    public String createClass() {
        Scanner s = new Scanner(System.in);
        String classC = "none";
        System.out.println("\t [1] Set Class");
        System.out.println("\t [2] Class List");
        int value = s.nextInt();
        s.nextLine();
        if (value == 1) {
            System.out.println("\t What is your characters class?");
            classC = s.nextLine();
        } else if (value == 2) {
            System.out.print("Barbarian, Bard, Cleric, Druid, Fighter, Monk, Paladin, Ranger, ");
            System.out.println("Rogue, Sorcerer, Warlock, Wizard, Artificer, Blood Hunter");
            System.out.println("What is your characters class?");
            classC = s.nextLine();
        }
        return classC;
    }

    //EFFECTS: user input race
    public String createRace() {
        Scanner s = new Scanner(System.in);
        String raceC = "none";
        System.out.println("\t [1] Set Race");
        System.out.println("\t [2] Race List");
        int value = s.nextInt();
        s.nextLine();
        if (value == 1) {
            System.out.println("\t What is your characters race?");
            raceC = s.nextLine();
        } else if (value == 2) {
            System.out.println("Dragonborn,  Dwarf, Elf, Gnome, Half-Elf, Elf, Halfling, Half-Orc, Human, Tiefling ");
            System.out.println("What is your characters class?");
            raceC = s.nextLine();
        }
        return raceC;
    }

    //EFFECTS: user input level
    public int createLevel() {
        Scanner s = new Scanner(System.in);
        int level = 0;
        System.out.println("\t What level is your character?");
        System.out.println("\t [1] Level 1");
        System.out.println("\t [2] Level 3");
        System.out.println("\t [3] Custom Level");
        int value = s.nextInt();
        s.nextLine();
        if (value == 1) {
            level = 1;
        } else if (value == 2) {
            level = 3;
        } else if (value == 3) {
            System.out.println("\t What is the custom level?");
            level = s.nextInt();
        }
        return level;
    }

    public Campaign chooseCampaign() {
        Scanner sn = new Scanner(System.in);
        for (int i = 0; i < campaigns.size(); i++) {
            System.out.println("\t[" + i + "] " + campaigns.get(i));
        }
        Campaign campaign = campaigns.get(sn.nextInt());
        return campaign;
    }

    //EFFECTS: find character from arraylist
    public void selectCharacter(Campaign camp) {
        Scanner s = new Scanner(System.in);
        ArrayList<CharacterDesign> characters = camp.getCampaign();
        System.out.println("\t What character would you like to play?");
        for (int i = 0; i < characters.size(); i++) {
            System.out.println("\t[" + i + "] " + characters.get(i));
        }
        CharacterDesign character = characters.get(s.nextInt());
        DiceRoller dice = new DiceRoller();
        playCharacterPage1(camp, character, dice);
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
            calendarOption();
            camp.setCalendar(s.nextInt());
        } else if (value == 2) {
            System.out.println("\t What campaign would you like to see the next date for?");
            Campaign camp = chooseCampaign();
            System.out.println("What day is it today?");
            calendarOption();
            System.out.println(camp.checkDate(s.nextInt()) + " days until next game.");
        }
    }

    //EFFECTS: prints all day options
    public void calendarOption() {
        System.out.println("\t [1] Monday");
        System.out.println("\t [2] Tuesday");
        System.out.println("\t [3] Wednesday");
        System.out.println("\t [4] Thursday");
        System.out.println("\t [5] Friday");
        System.out.println("\t [6] Saturday");
        System.out.println("\t [7] Sunday");
    }

    //EFFECTS: prints all character actions
    public void characterActionsPage1() {
        System.out.println("\t [1] Roll Dice");
        System.out.println("\t [2] Roll Initative");
        System.out.println("\t [3] Sleep/Heal/Damage");
        System.out.println("\t [4] View Inventory");
        System.out.println("\t [5] Page 2");
    }

    //EFFECTS: user input based on character action page 1
    public void playCharacterPage1(Campaign camp, CharacterDesign character, DiceRoller dice) {
        Scanner s = new Scanner(System.in);
        characterActionsPage1();
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
        diceOptions();
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
            diceOptions();
            if (value == 0) {
                playCharacterPage1(camp, character, dice);
            }
            value = s.nextInt();
        }
    }

    //EFFECTS: print all dice options
    public void diceOptions() {
        System.out.println("\t [1] Roll 1d4");
        System.out.println("\t [2] Roll 1d6");
        System.out.println("\t [3] Roll 1d8");
        System.out.println("\t [4] Roll 1d12");
        System.out.println("\t [5] Roll 1d20");
        System.out.println("\t [6] Roll 1d100");
        System.out.println("\t [0] End");
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
        runApp();
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
                runApp();
            } else if (value == 0) {
                runApp();
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

    public Boolean setStats1(CharacterDesign character) {
        Scanner s = new Scanner(System.in);
        System.out.println("\t Set Strength");
        int strength = s.nextInt();
        s.nextLine();
        character.setStrength(strength);
        System.out.println("\t Set Dexterity");
        int dexterity = s.nextInt();
        s.nextLine();
        character.setDexterity(dexterity);
        System.out.println("\t Set Constitution");
        int constitution = s.nextInt();
        s.nextLine();
        character.setConstitution(constitution);
        return true;
    }

    public boolean setStats2(CharacterDesign character) {
        Scanner s = new Scanner(System.in);
        System.out.println("\t Set Intelligence");
        int intelligence = s.nextInt();
        s.nextLine();
        character.setIntelligence(intelligence);
        System.out.println("\t Set Wisdom");
        int wisdom = s.nextInt();
        s.nextLine();
        character.setWisdom(wisdom);
        System.out.println("\t Set Charisma");
        int charisma = s.nextInt();
        s.nextLine();
        character.setCharisma(charisma);
        return true;
    }

    /*
     * REQUIRES: Arraylist of stats
     * MODIFIES: this
     * EFFECTS: sets the character stats, sets max health, sets armor class
     */
    public boolean rollStats(ArrayList<Integer> stats, CharacterDesign character) {
        Scanner s = new Scanner(System.in);
        System.out.println("Rolling 7 4d6 and dropping the lowest roll...");
        int index = 0;
        for (int i = 0; i < 7; i++) {
            int lowestValue = 0;
            if (stats.get(i) < lowestValue) {
                index = i;
            }
        }
        stats.remove(index);
        System.out.println(stats);
        setStats1(character);
        setStats2(character);
        System.out.println("What is your character max health?");
        character.setHealth(s.nextInt());
        System.out.println("What is your character armor class?");
        character.setAC(s.nextInt());
        return true;
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates stats for each of the 6 characteristics
     */
    public boolean statGeneration(CharacterDesign character, DiceRoller dice) {
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
        rollStats(stats, character);
        return true;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns a random number from 1-20 and also the stat modifier for the players dexterity
     */
    public int rollInitative(CharacterDesign character, DiceRoller dice) {
        return (dice.rolld20() + character.statModifier(character.getDexterity()));
    }

    public void saveOrLoad() {
        Scanner sn = new Scanner(System.in);
        System.out.println("\t [1] Save Data");
        System.out.println("\t [2] Load Data");
        int value = sn.nextInt();
        if (value == 1) {
            saveWorkRoom();
        }
        if (value == 2) {
            loadWorkRoom();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    public void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(campaigns);
            jsonWriter.close();
            for (int i = 0; i < campaigns.size(); i++) {
                System.out.println("Saved " + campaigns.get(i).getName() + " to " + JSON_Campaign);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_Campaign);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadWorkRoom() {
        try {
            campaigns = jsonReader.read();
            for (int i = 0; i < campaigns.size(); i++) {
                System.out.println("Loaded " + campaigns.get(i).getName() + " from " + JSON_Campaign);
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_Campaign);
        }
    }

}
