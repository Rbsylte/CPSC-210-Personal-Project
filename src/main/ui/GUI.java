package ui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class GUI {
    // Data Frames
    private JFrame dnd;
    private JFrame campaignFrame;
    private JFrame characterFrame;
    private JFrame optionClass;
    private JFrame optionRace;
    private JFrame characterStatBuilder;
    private JFrame viewCampaign;
    private JFrame viewCharacter;
    private JFrame displayAllCharacters;
    private JFrame calendarData;
    private JFrame calendar;
    private JFrame playCharacters;
    private JFrame playCampaign;
    private JFrame eventLog;
    //________________________________________________________________
    //Load other class methods
    DungeonsAndDragonsGUI dndApp = new DungeonsAndDragonsGUI();
    DiceRoller dice = new DiceRoller();
    Campaign cp = new Campaign("Start Up");
    //_______________________________________________________________
    //Other Data
    private String value;
    private JTextField textField;
    private static int count = 1;
    boolean multipleCharacters = false;
    boolean initialStart = true;
    //____________________________________________________________
    //Campaign Data
    private String campaignName;
    private int numOfCharactersCreation;
    private String characterName;
    private String className;
    private String raceName;
    private int level;
    private int strengthScore;
    private int dexterityScore;
    private int constitutionScore;
    private int intelligenceScore;
    private int wisdomScore;
    private int charismaScore;
    private int armorClass;
    private int health;
    //___________________________________________
    //Calender Data
    private String date;
    private String currentDate;
    //____________________________________________
    //List of Characters
    private ArrayList<CharacterDesign> barbarian = new ArrayList<>();
    private ArrayList<CharacterDesign> bard = new ArrayList<>();
    private ArrayList<CharacterDesign> cleric = new ArrayList<>();
    private ArrayList<CharacterDesign> fighter = new ArrayList<>();
    private ArrayList<CharacterDesign> monk = new ArrayList<>();
    private ArrayList<CharacterDesign> paladin = new ArrayList<>();
    private ArrayList<CharacterDesign> ranger = new ArrayList<>();
    private ArrayList<CharacterDesign> rogue = new ArrayList<>();
    private ArrayList<CharacterDesign> sorcerer = new ArrayList<>();
    private ArrayList<CharacterDesign> warlock = new ArrayList<>();
    private ArrayList<CharacterDesign> wizard = new ArrayList<>();
    private ArrayList<CharacterDesign> artificer = new ArrayList<>();
    private ArrayList<CharacterDesign> bloodHunter = new ArrayList<>();
    private ArrayList<CharacterDesign> other = new ArrayList<>();

    /*
     * MODIFIES: this
     * EFFECTS: initiates the starting window with animation and then starts program
     */
    public GUI() throws FileNotFoundException {
        JWindow window = new JWindow();
        window.setBounds(500, 300, 500, 500);
        JPanel iconPanel = new JPanel();
        window.getContentPane().add(iconPanel);
        iconPanel.setBackground(Color.BLACK);
        ImageIcon icons = new ImageIcon("windowIcon.png");
        Image image = icons.getImage();
        Image scaled = image.getScaledInstance(300, 250, java.awt.Image.SCALE_SMOOTH);
        icons = new ImageIcon(scaled);
        JLabel icon = new JLabel(icons);
        iconPanel.add(icon);
        JLabel loading = new JLabel(new ImageIcon("loading.gif"));
        iconPanel.add(loading);
        window.setVisible(true);
        window.getContentPane().add(BorderLayout.CENTER, iconPanel);
        try {
            Thread.sleep(8500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
        runGUI();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates the first menu of options
     */
    public void runGUI() {
        baseMenu();
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates the first menu of options
     */
    public void baseMenu() {
        dnd = new JFrame("Dungeons & Dragons");
        dnd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        closeLogTrace(dnd);
        dnd.setSize(600, 600);
        topBar(dnd);
        lowerPanel(dnd);
        menuButton(dnd);
        dnd.setVisible(true);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void closeLogTrace(JFrame frame) {
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                systemLogs();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: Creates the top layer of the menu that allows user to load and save data
     */
    public void topBar(JFrame frame) {
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        mb.add(m1);
        saveOrLoadData(m1);
        JMenu m2 = new JMenu("Event Log");
        mb.add(m2);
        eventLog(m2);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
    }

    private void saveOrLoadData(JMenu m1) {
        JMenuItem m11 = new JMenuItem("Load Data");
        m11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dndApp.loadWorkRoom();
            }
        });
        JMenuItem m12 = new JMenuItem("Save Data");
        m12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dndApp.saveWorkRoom();
            }
        });
        m1.add(m11);
        m1.add(m12);
    }

    private void eventLog(JMenu m2) {
        JMenuItem m21 = new JMenuItem("Print Event");
        m21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printLog();
                } catch (Exception d) {
                    JOptionPane.showMessageDialog(null, d.getMessage(), "System Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JMenuItem m22 = new JMenuItem("Clear All Events");
        m22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventLog.getInstance().clear();
            }
        });
        m2.add(m21);
        m2.add(m22);
    }

    private void printLog() {
        eventLog = new JFrame("Event Log");
        eventLog.setDefaultCloseOperation(eventLog.HIDE_ON_CLOSE);
        eventLog.setSize(800, 600);
        eventLog.setLocation(600, 0);
        eventList(eventLog);
        eventLog.setVisible(true);
    }

    private void eventList(JFrame eventLog) {
        JPanel events = new JPanel();
        printLogs(EventLog.getInstance(), events);
        eventLog.getContentPane().add(BorderLayout.CENTER, events);
    }

    private void printLogs(EventLog el, JPanel events) {
        for (Event next: el) {
            events.add(new JLabel(next.toString() + "\n"));
        }
    }

    private void systemLogs() {
        for (Event next: EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: Creates the bottom layer of the menu that allows user to save text
     */
    public void lowerPanel(JFrame frame) {
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        textField = new JTextField(20); // accepts up to 10 characters
        JButton send = simpleCreateButton("Save Text");
        send.setMnemonic(KeyEvent.VK_ENTER);
        saveButton(send, textField);
        panel.add(label); // Components Added using Flow Layout
        panel.add(textField);
        panel.add(send);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
    }

    /*
     * MODIFIES: Button, value
     * EFFECTS: Sets the string value to the text inside the textfield and resets textfield
     */
    private void saveButton(JButton send, JTextField textField) {
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = textField.getText();
                textField.setText("");
            }
        });
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: Creates the first menu base with all four options
     */
    public void menuButton(JFrame frame) {
        JPanel menuO = new JPanel();
        JButton campaign = createButton("Create Campaign", "campaign.jpg", 215, 211, 192);
        JButton playCamp = createButton("Play Campaign (In Development)", "play.jpg", 183, 206, 210);
        JButton viewCamp = createButton("View Campaign", "view.jpg", 215, 211, 192);
        JButton viewCal = createButton("View Calender (In Development)", "calendar.png", 0, 0, 0);
        menuO.add(campaign);
        campButton(campaign);
        menuO.add(playCamp);
        playButton(playCamp);
        menuO.add(viewCamp);
        viewCampaignGuIButton(viewCamp);
        menuO.add(viewCal);
        calendarButton(viewCal);
        frame.getContentPane().add(BorderLayout.CENTER, menuO);
    }

    /*
     * MODIFIES: button
     * EFFECTS: Creates a simple button of set size and font
     */
    private JButton simpleCreateButton(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setFont(new Font("Times New Roman", Font.BOLD, 12));
        button.setOpaque(true);
        return button;
    }

    /*
     * REQUIRES: height > 0, length > 0, size > 0
     * MODIFIES: button
     * EFFECTS: Creates a button of a given height, length and font size
     */
    private JButton sizeCreateButton(String name, int h, int l, int size) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setFont(new Font("Times New Roman", Font.BOLD, size));
        button.setPreferredSize(new Dimension(h, l));
        button.setOpaque(true);
        return button;
    }

    /*
     * REQUIRES: 0 <= r <= 255, 0 <= g <= 255, 0 <= b <= 255
     * MODIFIES: button
     * EFFECTS: Creates a button of set size with a logo and a background color
     */
    private JButton createButton(String name, String icon, int r, int g, int b) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        ImageIcon icons = new ImageIcon(icon);
        Image image = icons.getImage();
        Image scaled = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        icons = new ImageIcon(scaled);
        button.setIcon(icons);
        button.setIconTextGap(10);
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setFont(new Font("Times New Roman", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(500, 100));
        button.setBackground(Color.getHSBColor(r, g, b));
        button.setForeground(Color.getHSBColor(r, g, b));
        button.setOpaque(true);
        return button;
    }

    /*
     * MODIFIES: button
     * EFFECTS: opens the campaign menu and sets the previous menu to invisible
     */
    private void campButton(JButton campaign) {
        campaign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dnd.setVisible(false);
                campaignMenu();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates the create campaign menu with a campaign creation frame
     */
    public void campaignMenu() {
        campaignFrame = new JFrame("Create Campaign");
        campaignFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        closeLogTrace(campaignFrame);
        campaignFrame.setSize(600, 600);
        topBar(campaignFrame);
        campaignCreation(campaignFrame);
        lowerPanel(campaignFrame);
        campaignFrame.setVisible(true);
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: Creates a campaign creation menu that lets you set the name and number of characters
     */
    public void campaignCreation(JFrame campaignFrame) {
        JPanel campaignPanel = new JPanel();
        JButton campaignName = sizeCreateButton("What would you like the campaign name to be?", 600, 100, 25);
        campaignPanel.add(campaignName);
        JLabel answer1 = shownAnswer(campaignPanel);
        delayForAnswer(campaignName, answer1, "campName");
        JButton numOfCharacters = sizeCreateButton("How many characters are in your campaign", 600, 100, 25);
        campaignPanel.add(numOfCharacters);
        JLabel answer2 = shownAnswer(campaignPanel);
        delayForAnswer(numOfCharacters, answer2, "numCharacter");
        String nextLine = "____________________________________________________________________________________";
        JLabel blankSpace = new JLabel(nextLine);
        blankSpace.setSize(new Dimension(100, 100));
        campaignPanel.add(blankSpace);
        JButton cont = sizeCreateButton("Continue", 150, 50, 14);
        campaignPanel.add(cont);
        characterButton(cont);
        campaignFrame.getContentPane().add(BorderLayout.CENTER, campaignPanel);
    }

    /*
     * MODIFIES: button
     * EFFECTS: opens character frame and set the campaign frame to invisible
     */
    private void characterButton(JButton characterButton) {
        characterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campaignFrame.setVisible(false);
                characterGUI();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a create character JFrame
     */
    public void characterGUI() {
        characterFrame = new JFrame("Create Character");
        characterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        closeLogTrace(characterFrame);
        characterFrame.setSize(600, 600);
        topBar(characterFrame);
        characterCreation(characterFrame);
        lowerPanel(characterFrame);
        characterFrame.setVisible(true);
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: creates a character panel in JFrame
     */
    public void characterCreation(JFrame characterFrame) {
        JPanel characterPanel = new JPanel();
        characterQuestions(characterPanel);
        String nextLine = "____________________________________________________________________________________";
        JLabel blankSpace = new JLabel(nextLine);
        blankSpace.setSize(new Dimension(100, 100));
        characterPanel.add(blankSpace);
        JButton cont = sizeCreateButton("Continue", 150, 50, 14);
        characterPanel.add(cont);
        statsBuilderButton(cont);
        characterFrame.getContentPane().add(BorderLayout.CENTER, characterPanel);
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: Allows you to set the name, class, race and level
     */
    private void characterQuestions(JPanel characterPanel) {
        JButton characterName = sizeCreateButton("Name Of Character:", 600, 60, 25);
        characterPanel.add(characterName);
        JLabel answer1 = shownAnswer(characterPanel);
        delayForAnswer(characterName, answer1, "charName");
        JButton characterClass = sizeCreateButton("Class of Character:", 600, 60, 25);
        JButton classOptions = sizeCreateButton("Options", 600, 20, 12);
        characterPanel.add(characterClass);
        characterPanel.add(classOptions);
        optionClass(classOptions);
        JLabel answer2 = shownAnswer(characterPanel);
        delayForAnswer(characterClass, answer2, "charClass");
        JButton characterRace = sizeCreateButton("Race of Character:", 600, 60, 25);
        JButton raceOptions = sizeCreateButton("Options", 600, 20, 12);
        characterPanel.add(characterRace);
        characterPanel.add(raceOptions);
        optionRace(raceOptions);
        JLabel answer3 = shownAnswer(characterPanel);
        delayForAnswer(characterRace, answer3, "charRace");
        JButton characterLevel = sizeCreateButton("Level of Character:", 600, 60, 25);
        characterPanel.add(characterLevel);
        JLabel answer4 = shownAnswer(characterPanel);
        delayForAnswer(characterLevel, answer4, "charLevel");
    }

    /*
     * MODIFIES: button
     * EFFECTS: when clicked will display the race options
     */
    private void optionRace(JButton options) {
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRace();
            }
        });
    }

    /*
     * MODIFIES: button
     * EFFECTS: when clicked will display the class options
     */
    private void optionClass(JButton race) {
        race.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayClass();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: Displays the race options
     */
    public void displayRace() {
        optionRace = new JFrame("Races");
        optionRace.setDefaultCloseOperation(optionRace.HIDE_ON_CLOSE);
        optionRace.setSize(300, 300);
        optionRace.setLocation(600, 0);
        raceList(optionRace);
        optionRace.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Displays the class options
     */
    public void displayClass() {
        optionClass = new JFrame("Classes");
        optionClass.setDefaultCloseOperation(optionClass.HIDE_ON_CLOSE);
        optionClass.setSize(300, 300);
        optionClass.setLocation(600, 0);
        classList(optionClass);
        optionClass.setVisible(true);
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: adds the race options to a panel
     */
    private void raceList(JFrame optionRace) {
        JPanel optionPanel = new JPanel();
        optionPanel.add(new JLabel("Dragonborn, "));
        optionPanel.add(new JLabel("Dwarf, "));
        optionPanel.add(new JLabel("Elf, "));
        optionPanel.add(new JLabel("Gnome, "));
        optionPanel.add(new JLabel("Half-Elf,"));
        optionPanel.add(new JLabel("Elf, "));
        optionPanel.add(new JLabel("Halfling, "));
        optionPanel.add(new JLabel("Half-Orc, "));
        optionPanel.add(new JLabel("Human, "));
        optionPanel.add(new JLabel("Tiefling"));
        optionRace.getContentPane().add(BorderLayout.CENTER, optionPanel);
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: adds class options to panel
     */
    private void classList(JFrame optionClass) {
        JPanel optionPanel = new JPanel();
        optionPanel.add(new JLabel("Barbarian, "));
        optionPanel.add(new JLabel("Bard, "));
        optionPanel.add(new JLabel("Cleric, "));
        optionPanel.add(new JLabel("Druid, "));
        optionPanel.add(new JLabel("Fighter, "));
        optionPanel.add(new JLabel("Monk, "));
        optionPanel.add(new JLabel("Paladin, "));
        optionPanel.add(new JLabel("Ranger, "));
        optionPanel.add(new JLabel("Rogue, "));
        optionPanel.add(new JLabel("Sorcerer, "));
        optionPanel.add(new JLabel("Warlock, "));
        optionPanel.add(new JLabel("Wizard, "));
        optionPanel.add(new JLabel("Artificer, "));
        optionPanel.add(new JLabel("Blood Hunter"));
        optionClass.getContentPane().add(BorderLayout.CENTER, optionPanel);
    }

    /*
     * MODIFIES: button
     * EFFECTS: displays the stat builder dataframe while setting the character frame to invisiable
     */
    private void statsBuilderButton(JButton cont) {
        cont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statBuilderGUI();
                characterFrame.setVisible(false);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates the stat builder data frame
     */
    public void statBuilderGUI() {
        characterStatBuilder = new JFrame("Character Stats");
        characterStatBuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        closeLogTrace(characterStatBuilder);
        characterStatBuilder.setSize(600, 600);
        topBar(characterStatBuilder);
        characterStatCreation(characterStatBuilder);
        lowerPanel(characterStatBuilder);
        characterStatBuilder.setVisible(true);
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: allows character to initiate the stats of their character
     */
    public void characterStatCreation(JFrame characterStatBuilder) {
        JPanel statsPanel = new JPanel();
        JButton st = strengthOrder(statsPanel);
        JButton de = dexterityOrder(statsPanel);
        JButton co = constitutionOrder(statsPanel);
        JButton in = intelligenceOrder(statsPanel);
        JButton wi = wisdomOrder(statsPanel);
        JButton ch = charismaOrder(statsPanel);
        JLabel description = new JLabel();
        String s = "------------------";
        description.setText(s + " CLick The Buttons In The Order Above " + s);
        statsPanel.add(description);
        statButtonCreation(statsPanel, st, de, co, in, wi, ch);
        extraButtons(statsPanel);
        characterStatBuilder.getContentPane().add(BorderLayout.CENTER, statsPanel);
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: creates the stat button for each stat
     */
    private void statButtonCreation(JPanel statsPanel, JButton str, JButton dex, JButton con, JButton intt,
                                    JButton wis, JButton cha) {
        ArrayList<Integer> characterSixStats = dndApp.statGeneration(dice);
        JButton stat1 = sizeCreateButton((characterSixStats.get(0) + ""), 150, 50, 25);
        statButton(stat1, str, dex, con, intt, wis, cha);
        statsPanel.add(stat1);
        JButton stat2 = sizeCreateButton((characterSixStats.get(1) + ""), 150, 50, 25);
        statButton(stat2, str, dex, con, intt, wis, cha);
        statsPanel.add(stat2);
        JButton stat3 = sizeCreateButton((characterSixStats.get(2) + ""), 150, 50, 25);
        statButton(stat3, str, dex, con, intt, wis, cha);
        statsPanel.add(stat3);
        JButton stat4 = sizeCreateButton((characterSixStats.get(3) + ""), 150, 50, 25);
        statButton(stat4, str, dex, con, intt, wis, cha);
        statsPanel.add(stat4);
        JButton stat5 = sizeCreateButton((characterSixStats.get(4) + ""), 150, 50, 25);
        statButton(stat5, str, dex, con, intt, wis, cha);
        statsPanel.add(stat5);
        JButton stat6 = sizeCreateButton((characterSixStats.get(5) + ""), 150, 50, 25);
        statButton(stat6, str, dex, con, intt, wis, cha);
        statsPanel.add(stat6);

    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: allows user to set the armor class and health of the character
     */
    private void extraButtons(JPanel statPanel) {
        JButton armorClassButton = sizeCreateButton("Armor Class:", 600, 60, 25);
        statPanel.add(armorClassButton);
        JLabel answer1 = shownAnswer(statPanel);
        delayForAnswer(armorClassButton, answer1, "armorCLass");
        JButton maximumHealthButton = sizeCreateButton("Maximum Health Points:", 600, 60, 25);
        statPanel.add(maximumHealthButton);
        JLabel answer2 = shownAnswer(statPanel);
        delayForAnswer(maximumHealthButton, answer2, "health");
        if (numOfCharactersCreation > 1) {
            JButton next = sizeCreateButton("Next Character Continue", 600, 60, 25);
            nextCharacterButton(next);
            statPanel.add(next);
            numOfCharactersCreation--;
        } else {
            JButton next = sizeCreateButton("Back to Hub", 600, 60, 25);
            backToHubSave(next);
            statPanel.add(next);
        }
    }

    /*
     * MODIFIES: button
     * EFFECTS: creates a new campaign if the campaign hasn't been created or sets all new characters to the campaign
     */
    private void nextCharacterButton(JButton st) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (initialStart == true) {
                    cp = new Campaign(campaignName);
                }
                setCharacterStats(cp);
                dndApp.addCampaignsArrayList(cp);
                initialStart = false;
                multipleCharacters = true;
                characterStatBuilder.setVisible(false);
                characterGUI();
            }
        });
    }

    /*
     * MODIFIES: button
     * EFFECTS: if there isn't multple charactes in the campaign it sets the character stats and new campaign
     *          if not then it sets
     */
    private void backToHubSave(JButton st) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!multipleCharacters) {
                    Campaign cp = new Campaign(campaignName);
                    setCharacterStats(cp);
                    dndApp.addCampaignsArrayList(cp);
                } else {
                    setCharacterStats(cp);
                    initialStart = true;
                    multipleCharacters = false;
                }
                characterStatBuilder.setVisible(false);
                dnd.setVisible(true);
            }
        });
    } // CHeck this may because of bug

    /*
     * MODIFIES: Campaign
     * EFFECTS:sets the character to be defined by the initial stats and sends it to the campaign
     */
    private void setCharacterStats(Campaign cp) {
        CharacterDesign character = new CharacterDesign(characterName, className, raceName, level);
        character.setStrength(strengthScore);
        character.setDexterity(dexterityScore);
        character.setConstitution(constitutionScore);
        character.setIntelligence(intelligenceScore);
        character.setWisdom(wisdomScore);
        character.setCharisma(charismaScore);
        character.setAC(armorClass);
        character.setMaxHealth(health);
        cp.addCharacter(character);
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: creates strength button and adds to panel
     */
    private JButton strengthOrder(JPanel orderPanel) {
        JButton orders1 = sizeCreateButton("Str", 90, 50, 25);
        orderButton(orders1);
        orderPanel.add(orders1);
        return orders1;
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: creates dexterity button and adds to panel
     */
    private JButton dexterityOrder(JPanel orderPanel) {
        JButton orders = sizeCreateButton("Dex", 90, 50, 25);
        orderButton(orders);
        orderPanel.add(orders);
        return orders;
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: creates consitution button and adds to panel
     */
    private JButton constitutionOrder(JPanel orderPanel) {
        JButton orders = sizeCreateButton("Con", 90, 50, 25);
        orderButton(orders);
        orderPanel.add(orders);
        return orders;
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: creates intelligence button and adds to panel
     */
    private JButton intelligenceOrder(JPanel orderPanel) {
        JButton orders1 = sizeCreateButton("Int", 90, 50, 25);
        orderButton(orders1);
        orderPanel.add(orders1);
        return orders1;
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: creates wisdom button and adds to panel
     */
    private JButton wisdomOrder(JPanel orderPanel) {
        JButton orders = sizeCreateButton("Wis", 90, 50, 25);
        orderButton(orders);
        orderPanel.add(orders);
        return orders;
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: creates charisma button and adds to panel
     */
    private JButton charismaOrder(JPanel orderPanel) {
        JButton orders = sizeCreateButton("Cha", 90, 50, 25);
        orderButton(orders);
        orderPanel.add(orders);
        return orders;
    }

    /*
     * MODIFIES: button
     * EFFECTS: sets the button pressed to unpressible
     */
    private void orderButton(JButton st) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                st.setEnabled(false);
            }
        });
    }

    /*
     * MODIFIES: button
     * EFFECTS: Goes through the stat utton when pressed based on the list
     */
    private void statButton(JButton st, JButton str, JButton dex, JButton con, JButton intt, JButton wis, JButton cha) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = st.getText();
                statButtonActions1(st, intt, wis, cha);
                statButtonActions(st, str, dex, con);
                if (count == 7) {
                    count = 1;
                }
            }
        });
    }

    /*
     * MODIFIES: strengthScore, dexterityScore, consituitonScore
     * EFFECTS: modifies the scores depending on the count
     */
    private void statButtonActions(JButton st, JButton str, JButton dex, JButton con) {
        if (count == 1) {
            strengthScore = Integer.parseInt(value);
            str.doClick();
            st.setEnabled(false);
            count++;
        } else if (count == 2) {
            dexterityScore = Integer.parseInt(value);
            dex.doClick();
            st.setEnabled(false);
            count++;
        } else if (count == 3) {
            constitutionScore = Integer.parseInt(value);
            con.doClick();
            st.setEnabled(false);
            count++;
        }
    }

    /*
     * MODIFIES: intelligenceScore, wisdomScore, charismaScore
     * EFFECTS: modifies the scores depending on the count
     */
    private void statButtonActions1(JButton st, JButton intt, JButton wis, JButton cha) {
        if (count == 4) {
            intelligenceScore = Integer.parseInt(value);
            intt.doClick();
            st.setEnabled(false);
            count++;
        } else if (count == 5) {
            wisdomScore = Integer.parseInt(value);
            wis.doClick();
            st.setEnabled(false);
            count++;
        } else if (count == 6) {
            charismaScore = Integer.parseInt(value);
            cha.doClick();
            st.setEnabled(false);
            count++;
        }
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: Adds a JLabel that displaces the answers given
     */
    private JLabel shownAnswer(JPanel panel) {
        JLabel answer = new JLabel("Click Button Once Answer Is Saved");
        panel.add(answer);
        return answer;
    }

    /*
     * REQUIRES: key == campName, numCharacter, charName, charCLass, charLevel, armorClass, health
     * MODIFIES: button
     * EFFECTS: when button is pressed set the answer to the value
     */
    private void delayForAnswer(JButton campaign, JLabel answer, String key) {
        campaign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.setText(value);
                campaign.setEnabled(false);
                keyAccess(key);
            }
        });
    }

    /*
     * REQUIRES: key == campName, numCharacter, charName, charCLass, charLevel, armorClass, health
     * MODIFIES: campName, numCharacter, charName, charCLass, charLevel, armorClass, health
     * EFFECTS: sets the value to be equal to the respective values based on key
     */
    private void keyAccess(String key) {
        if (key.equals("campName")) {
            campaignName = value;
        } else if (key.equals("numCharacter")) {
            numOfCharactersCreation = Integer.parseInt(value);
        } else if (key.equals("charName")) {
            characterName = value;
        } else if (key.equals("charRace")) {
            raceName = value;
        } else if (key.equals("charClass")) {
            className = value;
        } else if (key.equals("charLevel")) {
            level = Integer.parseInt(value);
        } else if (key.equals("armorCLass")) {
            armorClass = Integer.parseInt(value);
        } else if (key.equals("health")) {
            health = Integer.parseInt(value);
        }
    }

    //Playing Characters from the campaign_________________________________________
    /*
     * MODIFIES: button
     * EFFECTS: creates the view campaign frame adn sets the initial frame to inviisible
     */
    private void playButton(JButton playCamp) {
        playCamp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dnd.setVisible(false);
                viewCampaign = new JFrame("Campaign List");
                viewCampaign.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(viewCampaign);
                viewCampaign.setSize(600, 600);
                topBar(viewCampaign);
                lowerPanel(viewCampaign);
                campaignPlayPanel(viewCampaign);
                viewCampaign.setVisible(true);
            }
        });
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: shows all campaigns
     */
    private void campaignPlayPanel(JFrame playCampaign) {
        JPanel campPanel = new JPanel();
        for (int i = 0; i < dndApp.getCampaignsArrayList().size(); i++) {
            JButton campaign = sizeCreateButton(dndApp.getCampaignsArrayList().get(i).getName(), 800, 100, 16);
            playingButton(campaign, i);
            campPanel.add(campaign);
        }
        JButton back = sizeCreateButton("Back To Hub", 600, 100, 24);
        backToHub(back, 1);
        campPanel.add(back);
        viewCampaign.getContentPane().add(BorderLayout.CENTER, campPanel);
    }

    //View Campaigns and Their Characters___________________________________________
    /*
     * MODIFIES: button
     * EFFECTS: sets the initial menu to inviisible and creates the view campaign frame
     */
    private void viewCampaignGuIButton(JButton viewCamp) {
        viewCamp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dnd.setVisible(false);
                viewCampaign = new JFrame("Campaign List");
                viewCampaign.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(viewCampaign);
                viewCampaign.setSize(600, 600);
                topBar(viewCampaign);
                lowerPanel(viewCampaign);
                campaignViewPanel(viewCampaign);
                viewCampaign.setVisible(true);
            }
        });
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: creates a new panel of all the campaigns
     */
    public void campaignViewPanel(JFrame viewCampaign) {
        JPanel campPanel = new JPanel();
        for (int i = 0; i < dndApp.getCampaignsArrayList().size(); i++) {
            JButton campaign = sizeCreateButton(dndApp.getCampaignsArrayList().get(i).getName(), 800, 100, 16);
            viewButton(campaign, i);
            campPanel.add(campaign);
        }
        JButton back = sizeCreateButton("Back To Hub", 600, 100, 24);
        backToHub(back, 1);
        campPanel.add(back);
        JButton allCharacterRace = new JButton("All Characters Layout");
        displayAllCharacters(allCharacterRace);
        campPanel.add(allCharacterRace);
        viewCampaign.getContentPane().add(BorderLayout.CENTER, campPanel);
    }

    /*
     * MODIFIES: button
     * EFFECTS: creates the display all character frame and sets the view campaign to false
     */
    private void displayAllCharacters(JButton allCharacter) {
        allCharacter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCampaign.setVisible(false);
                displayAllCharacters = new JFrame("Character Display");
                displayAllCharacters.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(displayAllCharacters);
                displayAllCharacters.setSize(600,600);
                topBar(displayAllCharacters);
                lowerPanel(displayAllCharacters);
                displayCharactersList(displayAllCharacters);
                resetArray();
                displayAllCharacters.setVisible(true);
            }
        });
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: checks all the characters in all campaigns and fits them to a class
     */
    public void displayCharactersList(JFrame displayAllCharacters) {
        JPanel displayPanel = new JPanel();
        for (int i = 0; i < dndApp.getCampaignsArrayList().size(); i++) {
            for (int j = 0; j < dndApp.getCampaignsArrayList().get(i).getSize(); j++) {
                CharacterDesign character = dndApp.getCampaignsArrayList().get(i).getCampaign().get(j);
                fitCharacterToClass1(character);
            }
        }
        display(displayPanel);
        JButton back = simpleCreateButton("Back to Hub");
        backToHub(back, 3);
        displayPanel.add(back);
        displayAllCharacters.getContentPane().add(BorderLayout.CENTER, displayPanel);
    }

    /*
     * MODIFIES: All Class arraylists
     * EFFECTS: Resets each arraylist so that it has no characts in it
     */
    public void resetArray() {
        barbarian = new ArrayList<>();
        bard = new ArrayList<>();
        cleric = new ArrayList<>();
        fighter = new ArrayList<>();
        monk = new ArrayList<>();
        paladin = new ArrayList<>();
        ranger = new ArrayList<>();
        rogue = new ArrayList<>();
        sorcerer = new ArrayList<>();
        warlock = new ArrayList<>();
        wizard = new ArrayList<>();
        artificer = new ArrayList<>();
        bloodHunter = new ArrayList<>();
        other = new ArrayList<>();
    }

    /*
     * MODIFIES: ArrayList of Class
     * EFFECTS: If the character has the class that fits the tag then add them to the arraylist
     */
    public void fitCharacterToClass1(CharacterDesign character) {
        if (character.getClassC().equals("Barbarian")) {
            barbarian.add(character);
        } else if (character.getClassC().equals("Bard")) {
            bard.add(character);
        } else if (character.getClassC().equals("cleric")) {
            cleric.add(character);
        } else if (character.getClassC().equals("fighter")) {
            fighter.add(character);
        } else if (character.getClassC().equals("monk")) {
            monk.add(character);
        } else if (character.getClassC().equals("paladin")) {
            paladin.add(character);
        } else if (character.getClassC().equals("ranger")) {
            ranger.add(character);
        } else if (character.getClassC().equals("rogue")) {
            rogue.add(character);
        } else if (character.getClassC().equals("sorcerer")) {
            cleric.add(character);
        } else if (character.getClassC().equals("warlock")) {
            warlock.add(character);
        } else {
            fitCharacterToClass2(character);
        }
    }

    /*
     * MODIFIES: ArrayList of Class
     * EFFECTS: If the character has the class that fits the tag then add them to the arraylist
     */
    public void fitCharacterToClass2(CharacterDesign character) {
        if (character.getClassC().equals("wizard")) {
            wizard.add(character);
        } else if (character.getClassC().equals("artificer")) {
            artificer.add(character);
        } else if (character.getClassC().equals("blood hunter")) {
            bloodHunter.add(character);
        } else {
            other.add(character);
        }

    }

    /*
     * MODIFIES:JPanel
     * EFFECTS: Adds the class tag and the names of the characters that fit the tag
     */
    private void display(JPanel displayPanel) {
        ArrayList<String> classes = new ArrayList<>(Arrays.asList("Barbarian", "Bard", "Cleric", "Fighter", "Monk",
                "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard", "Artificer", "Blood Hunter", "Other"));
        String s = "--------------------------------------";
        for (int i = 0; i < classes.size(); i++) {
            displayPanel.add(new JLabel(s + classes.get(i) + s));
            displayToken(i, displayPanel);
        }
    }

    /*
     * REQUIRES: i >= 0
     * MODIFIES: JPanel
     * EFFECTS: Adds the amount of labels for each class
     */
    private void displayToken(int i, JPanel displayPanel) {
        if (i == 0) {
            for (int k = 0; i < barbarian.size(); i++) {
                displayPanel.add(new JLabel(barbarian.get(k).getName()));
            }
        } else if (i == 1) {
            for (int k = 0; i < bard.size(); i++) {
                displayPanel.add(new JLabel(bard.get(k).getName()));
            }
        } else if (i == 2) {
            for (int k = 0; i < cleric.size(); i++) {
                displayPanel.add(new JLabel(cleric.get(k).getName()));
            }
        } else if (i == 3) {
            for (int k = 0; i < fighter.size(); i++) {
                displayPanel.add(new JLabel(fighter.get(k).getName()));
            }
        } else if (i == 4) {
            for (int k = 0; i < monk.size(); i++) {
                displayPanel.add(new JLabel(monk.get(k).getName()));
            }
        } else {
            displayToken2(i, displayPanel);
        }
    }

    /*
     * REQUIRES: i >= 5
     * MODIFIES: JPanel
     * EFFECTS: Adds the amount of labels for each class
     */
    private void displayToken2(int i, JPanel displayPanel) {
        if (i == 5) {
            for (int k = 0; i < paladin.size(); i++) {
                displayPanel.add(new JLabel(paladin.get(k).getName()));
            }
        } else if (i == 6) {
            for (int k = 0; i < ranger.size(); i++) {
                displayPanel.add(new JLabel(ranger.get(k).getName()));
            }
        } else if (i == 7) {
            for (int k = 0; i < rogue.size(); i++) {
                displayPanel.add(new JLabel(rogue.get(k).getName()));
            }
        } else if (i == 8) {
            for (int k = 0; i < sorcerer.size(); i++) {
                displayPanel.add(new JLabel(sorcerer.get(k).getName()));
            }
        } else {
            displayToken3(i, displayPanel);
        }
    }

    /*
     * REQUIRES: i >= 9
     * MODIFIES: JPanel
     * EFFECTS: Adds the amount of labels for each class
     */
    private void displayToken3(int i, JPanel displayPanel) {
        if (i == 9) {
            for (int k = 0; i < warlock.size(); i++) {
                displayPanel.add(new JLabel(warlock.get(k).getName()));
            }
        } else if (i == 10) {
            for (int k = 0; i < wizard.size(); i++) {
                displayPanel.add(new JLabel(wizard.get(k).getName()));
            }
        } else if (i == 11) {
            for (int k = 0; i < artificer.size(); i++) {
                displayPanel.add(new JLabel(artificer.get(k).getName()));
            }
        } else if (i == 12) {
            for (int k = 0; i < bloodHunter.size(); i++) {
                displayPanel.add(new JLabel(bloodHunter.get(k).getName()));
            }
        } else {
            for (int k = 0; i < other.size(); i++) {
                displayPanel.add(new JLabel(other.get(k).getName()));
            }
        }
    }

    /*
     * REQUIRES: id  be within the array list
     * MODIFIES: button
     * EFFECTS: creates a view character frame and sets the view campaign frame to false
     */
    private void viewButton(JButton viewCamp, int id) {
        viewCamp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCampaign.setVisible(false);
                viewCharacter = new JFrame("Character List");
                viewCharacter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(viewCharacter);
                viewCharacter.setSize(600, 600);
                topBar(viewCharacter);
                lowerPanel(viewCharacter);
                characterViewPanel(viewCharacter, id);
                viewCharacter.setVisible(true);
            }
        });
    }

    /*
     * REQUIRES: id be within the arraylist
     * MODIFIES: button
     * EFFECTS: creates the play campaign frame adn sets view campaign frame to false
     */
    private void playingButton(JButton viewCamp, int id) {
        viewCamp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCampaign.setVisible(false);
                playCampaign = new JFrame("Character List");
                playCampaign.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(playCampaign);
                playCampaign.setSize(600, 600);
                topBar(playCampaign);
                lowerPanel(playCampaign);
                characterPlayPanel(playCampaign, id);
                playCampaign.setVisible(true);
            }
        });
    }

    /*
     * REQUIRES: id be within the array list
     * MODIFIES: JFrame
     * EFFECTS: creates the buttons of all the campaign names
     */
    public void characterViewPanel(JFrame viewCampaign, int id) {
        JPanel charPanel = new JPanel();
        for (int j = 0; j < dndApp.getCampaignsArrayList().get(id).getSize(); j++) {
            JButton character = sizeCreateButton(
                    dndApp.getCampaignsArrayList().get(id).getCampaign().get(j).getName(), 200, 100, 12);
            charPanel.add(character);
        }
        JButton back = sizeCreateButton("Back To Hub", 600, 100, 24);
        backToHub(back, 1);
        charPanel.add(back);
        viewCampaign.getContentPane().add(BorderLayout.CENTER, charPanel);

    }

    /*
     * REQUIRES: id has to be within the character campaign array list
     * MODIFIES: JFrame
     * EFFECTS: creates a new panel with all the characters in the campaign
     */
    public void characterPlayPanel(JFrame viewCampaign, int id) {
        JPanel charPanel = new JPanel();
        for (int j = 0; j < dndApp.getCampaignsArrayList().get(id).getSize(); j++) {
            JButton character = sizeCreateButton(
                    dndApp.getCampaignsArrayList().get(id).getCampaign().get(j).getName(), 200, 100, 12);
            charPanel.add(character);
            playCharacter(character, dndApp.getCampaignsArrayList().get(id).getCampaign().get(j));

        }
        JButton back = sizeCreateButton("Back To Hub", 600, 100, 24);
        backToHub(back, 1);
        charPanel.add(back);
        viewCampaign.getContentPane().add(BorderLayout.CENTER, charPanel);

    }

    /*
     * MODIFIES: button
     * EFFECTS: sets the character frame to invisible adn initiates the play character frame
     */
    private void playCharacter(JButton st, CharacterDesign thisCharacter) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterFrame.setVisible(false);
                playCharacters = new JFrame("Character List");
                playCharacters.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(playCharacters);
                playCharacters.setSize(600, 600);
                topBar(playCharacters);
                lowerPanel(playCharacters);
                playingCharacter(playCharacters, thisCharacter);
                playCharacters.setVisible(true);

            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: Prints out to test if you are playing the character you choose
     */
    public void playingCharacter(JFrame playCharacter, CharacterDesign character) {
        System.out.println("Playing: " + character.getName());
    }

    /*
     * REQUIRES: key == 1 || key == 2 || key == 3
     * MODIFIES: JButton
     * EFFECTS: sets the current frame to invisible and goes back to the hub
     */
    private void backToHub(JButton st, int key) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (key == 1) {
                    viewCampaign.setVisible(false);
                } else if (key == 2) {
                    characterStatBuilder.setVisible(false);
                } else if (key == 3) {
                    displayAllCharacters.setVisible(false);
                }
                dnd.setVisible(true);
            }
        });
    }

    //Initiate Calendar_______________________________________________
    /*
     * MODIFIES: Button
     * EFFECTS: Initiates the calendar data JFrame adn sets the dnd frame to invisible
     */
    private void calendarButton(JButton playCamp) {
        playCamp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dnd.setVisible(false);
                calendarData = new JFrame("Calendar List");
                calendarData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(calendarData);
                calendarData.setSize(600, 600);
                topBar(calendarData);
                lowerPanel(calendarData);
                calendarDates(calendarData);
                calendarData.setVisible(true);
            }
        });
    }

    /*
     * MODIFIES: JFrame
     * EFFECTS: Allows user to set the current date and the date of your dnd game
     */
    public void calendarDates(JFrame calendarData) {
        JPanel calPanel = new JPanel();
        String s = "-------------------------";
        JLabel data = new JLabel(s + " What's Today's Date? " + s);
        calPanel.add(data);
        week(calPanel, true);
        JLabel datas = new JLabel(s + " What Data Is Your DND Game? " + s);
        calPanel.add(datas);
        week(calPanel, false);
        JButton next = sizeCreateButton("Calendar Diagram", 800, 100, 25);
        calPanel.add(next);
        calendarData.getContentPane().add(BorderLayout.CENTER, calPanel);
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: Creates button for ever day of the week
     */
    private void week(JPanel calPanel, Boolean d) {
        JButton monday = calendarOrder(calPanel, "Monday");
        JButton tuesday = calendarOrder(calPanel, "Tuesday");
        JButton wednesday = calendarOrder(calPanel, "Wednesday");
        JButton thursday = calendarOrder(calPanel, "Thursday");
        JButton friday = calendarOrder(calPanel, "Friday");
        JButton saturday = calendarOrder(calPanel, "Saturday");
        JButton sunday = calendarOrder(calPanel, "Sunday");
        accessData(monday, monday, tuesday, wednesday, thursday, friday, saturday, sunday, d);
        accessData(tuesday, monday, tuesday, wednesday, thursday, friday, saturday, sunday, d);
        accessData(wednesday, monday, tuesday, wednesday, thursday, friday, saturday, sunday, d);
        accessData(thursday, monday, tuesday, wednesday, thursday, friday, saturday, sunday, d);
        accessData(friday, monday, tuesday, wednesday, thursday, friday, saturday, sunday, d);
        accessData(saturday, monday, tuesday, wednesday, thursday, friday, saturday, sunday, d);
        accessData(sunday, monday, tuesday, wednesday, thursday, friday, saturday, sunday, d);
    }

    /*
     * MODIFIES: JPanel
     * EFFECTS: Creates a button of set size for the JPanel
     */
    private JButton calendarOrder(JPanel calendarPanel, String name) {
        JButton dataDate = sizeCreateButton(name, 80, 100, 16);
        calendarPanel.add(dataDate);
        return dataDate;
    }

    /*
     * MODIFIES: button
     * EFFECTS: if you click the button it will either set the current date or set the date depending on boolean
     */
    private void accessData(JButton st, JButton mon, JButton tue, JButton wed, JButton thurs, JButton fri, JButton sat,
                            JButton sun, Boolean d) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (d) {
                    setCurrentDate(st, mon, tue, wed, thurs, fri, sat, sun);
                } else if (!d) {
                    setDate(st, mon, tue, wed, thurs, fri, sat, sun);
                }
            }
        });
    }

    /*
     * MODIFIES: button
     * EFFECTS: Once one button is clicked it will set all other to unclickable
     */
    private void setCurrentDate(JButton st, JButton mon, JButton tue, JButton wed, JButton thurs, JButton fri,
                                JButton sat, JButton sun) {
        currentDate = st.getName();
        mon.setEnabled(false);
        tue.setEnabled(false);
        wed.setEnabled(false);
        thurs.setEnabled(false);
        fri.setEnabled(false);
        sat.setEnabled(false);
        sun.setEnabled(false);
    }

    /*
     * MODIFIES: button
     * EFFECTS: Once one button is clicked it sets all the others to unclickable
     */
    private void setDate(JButton st, JButton mon, JButton tue, JButton wed, JButton thurs, JButton fri, JButton sat,
                         JButton sun) {
        date = st.getName();
        cp.setCalendar(stringCalToInt(date));
        mon.setEnabled(false);
        tue.setEnabled(false);
        wed.setEnabled(false);
        thurs.setEnabled(false);
        fri.setEnabled(false);
        sat.setEnabled(false);
        sun.setEnabled(false);
    }

    private int stringCalToInt(String date) {
        if (Objects.equals(date, "Monday")) {
            return 1;
        } else if (Objects.equals(date, "Tuesday")) {
            return 2;
        } else if (Objects.equals(date, "Wednesday")) {
            return 3;
        } else if (Objects.equals(date, "Thursday")) {
            return 4;
        } else if (Objects.equals(date, "Friday")) {
            return 5;
        } else if (Objects.equals(date, "Saturday")) {
            return 6;
        } else if (Objects.equals(date, "Sunday")) {
            return 7;
        }
        return 0;
    }

    /*
     * MODIFIES: button
     * EFFECTS: Creates the calender dataframe and turns the calender data frame to invisible
     */
    private void initiateCalendar(JButton st) {
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendarData.setVisible(false);
                calendar = new JFrame("Calendar");
                calendar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                closeLogTrace(calendar);
                calendar.setSize(600, 600);
                topBar(calendar);
                lowerPanel(calendar);
                calendarCreate(calendar);
                calendar.setVisible(true);
            }
        });
    }

    /*
     * REQUIRES: Calender dataframe
     * MODIFIES: JFrame
     * EFFECTS: Creates a new calender panel
     */
    public void calendarCreate(JFrame calendar) {
        JPanel calendarPanel = new JPanel();
        calendar.getContentPane().add(BorderLayout.CENTER, calendarPanel);
    }
}
