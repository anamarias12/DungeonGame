package main.org.example.entities;

import main.org.example.characters.*;
import main.org.example.JsonInput;
import main.org.example.characters.Character;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame implements ActionListener {
    private final ArrayList<Account> playerAccounts;
    private Grid gameMap;
    Account currentAccount;
    private Character currentCharacter;
    private Enemy currentEnemy;
    public int room = 1;

    // singleton instance
    private static Game instance;
    // constructor
    public Game() {
        this.playerAccounts = JsonInput.deserializeAccounts();
        this.gameMap = null;
    }
    // method to get the singleton instance
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // LOGIN PAGE
    JPasswordField password;
    JTextField username;
    JLabel labelPassword, labelUsername, message, labelTitle;
    JButton button;
    JCheckBox show;
    // set up login page
    private void setUpLoginGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setTitle("League of Warriors - Authentication");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("src/main/org/example/images/castle.jpg");
        Image backgroundImageScaled = backgroundImage.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(backgroundImageScaled));
        background.setBounds(0, 0, 1000, 800);
        this.setContentPane(background);

        labelTitle = new JLabel("Welcome to League of Warriors!", JLabel.CENTER);
        labelTitle.setBounds(100, 100, 800, 60);
        labelTitle.setFont(new Font("Old English Text MT", Font.BOLD, 50));
        labelTitle.setForeground(Color.WHITE);

        labelUsername = new JLabel("Email:");
        labelUsername.setBounds(375, 250, 250, 30);
        labelUsername.setFont(new Font("Times New Roman", Font.BOLD, 18));
        labelUsername.setForeground(Color.WHITE);

        labelPassword = new JLabel("Password:");
        labelPassword.setBounds(375, 330, 250, 30);
        labelPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
        labelPassword.setForeground(Color.WHITE);

        message = new JLabel("", JLabel.CENTER);
        message.setBounds(300, 600, 400, 30);
        message.setFont(new Font("Times New Roman", Font.BOLD, 16));
        message.setForeground(Color.WHITE);

        username = new JTextField();
        username.setBounds(375, 280, 250, 40);
        username.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        password = new JPasswordField();
        password.setBounds(375, 360, 250, 40);
        password.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        show = new JCheckBox("Show password");
        show.setBounds(375, 410, 250, 30);
        show.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        show.setForeground(Color.WHITE);
        show.setOpaque(false);
        show.addActionListener(this);

        button = new JButton("Sign in");
        button.setBounds(425, 470, 150, 40);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(new Color(139, 69, 19)); // Dark brown
        button.setForeground(Color.WHITE);
        button.setFocusPainted(true);
        button.addActionListener(this);

        this.add(labelTitle);
        this.add(labelUsername);
        this.add(username);
        this.add(labelPassword);
        this.add(password);
        this.add(show);
        this.add(button);
        this.add(message);

        this.setVisible(true);
    }
    // authenticate user
    private Account authenticate(String username, String password) {
        for (Account account : playerAccounts) {
            if (account.getInformation().getCredentials().getEmail().equals(username) &&
                    account.getInformation().getCredentials().getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }
    // LOGIN PAGE

    // CHARACTER SELECTION PAGE
    ImageIcon imageWarrior = new ImageIcon(new ImageIcon("src/main/org/example/images/warrior.jpg")
            .getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
    ImageIcon imageMage = new ImageIcon(new ImageIcon("src/main/org/example/images/mage.jpg")
            .getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
    ImageIcon imageRogue = new ImageIcon(new ImageIcon("src/main/org/example/images/rogue.jpg")
            .getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
    private JComboBox<String> charactersCombo;
    private JLabel labelPic;
    private JButton startButton;
    // set up character selection page
    private void setUpCharacterSelection() {
        this.getContentPane().removeAll();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setTitle("Character Selection Page");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(new ImageIcon("src/main/org/example/images/wall.jpg")
                .getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        this.setContentPane(backgroundLabel);
        this.setLayout(null);

        JLabel charactersLabel = new JLabel("Choose your character:");
        charactersLabel.setBounds(0, 50, 1000, 60);
        charactersLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        charactersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        charactersLabel.setForeground(Color.WHITE);

        charactersCombo = new JComboBox<>();
        charactersCombo.setBounds(350, 150, 300, 40);
        charactersCombo.addActionListener(this);
        charactersCombo.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        charactersCombo.setBackground(new Color(255, 223, 186));
        charactersCombo.setForeground(new Color(0, 0, 0));
        charactersCombo.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2));

        labelPic = new JLabel();
        labelPic.setBounds(350, 200, 300, 400);
        labelPic.setHorizontalAlignment(SwingConstants.CENTER);
        labelPic.setBorder(BorderFactory.createLineBorder(Color.BLACK, 8));
        labelPic.setBackground(Color.WHITE);
        labelPic.setOpaque(true);

        charactersCombo.addActionListener(_ -> {
            String selectedCharacter = (String) charactersCombo.getSelectedItem();
            switch (selectedCharacter) {
                case "Warrior" -> labelPic.setIcon(imageWarrior);
                case "Mage" -> labelPic.setIcon(imageMage);
                case "Rogue" -> labelPic.setIcon(imageRogue);
            }
        });

        startButton = new JButton("Start Game");
        startButton.setBounds(425, 620, 150, 40);
        startButton.addActionListener(this);
        startButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        startButton.setBackground(new Color(139, 69, 19));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(true);

        for (Character character : currentAccount.getCharacters()) {
            charactersCombo.addItem(character.getName());
        }

        this.add(charactersLabel);
        this.add(charactersCombo);
        this.add(labelPic);
        this.add(startButton);

        this.setVisible(true);
    }
    // CHARACTER SELECTION PAGE

    // GAME PAGE
    JPanel characterPanel;
    private JLabel characterStatsLabel;
    private JLabel characterImageLabel;
    private JTable gameMapVisual;
    private GameTableModel tableModel;
    private JButton northButton, southButton, westButton, eastButton;
    // set up game page
    private void setUpGame() {
        this.getContentPane().removeAll();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setTitle("Medieval Adventure");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        if (currentCharacter.exp >= 100) {
            currentCharacter.levelUp();
        }

        this.getContentPane().setBackground(new Color(139, 69, 19));

        tableModel = new GameTableModel(gameMap);
        gameMapVisual = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gameMapVisual.setRowHeight(40);
        gameMapVisual.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gameMapVisual.setBackground(new Color(210, 180, 140)); // Tan
        gameMapVisual.setShowGrid(true);
        gameMapVisual.setGridColor(Color.BLACK);
        gameMapVisual.setTableHeader(null);
        gameMapVisual.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        gameMapVisual.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Cell cellType = (Cell) value;
                if (cellType.getCellEntityType() == CellEntityType.PLAYER) {
                    cell.setBackground(new Color(85, 107, 47));
                    setText("Player");
                } else if (!cellType.getVisited()) {
                    cell.setBackground(new Color(184, 134, 11));
                    setText("");
                } else {
                    cell.setBackground(new Color(245, 245, 220));
                    setText("");
                    if (cellType.getCellEntityType() == CellEntityType.SANCTUARY) {
                        setText("Sanctuary");
                    }
                }

                cell.setForeground(Color.BLACK);
                cell.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                return cell;
            }
        });

        JScrollPane scrollPane = new JScrollPane(gameMapVisual);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 5));
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel movementPanel = new JPanel(new GridBagLayout());
        movementPanel.setBackground(new Color(139, 69, 19));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        northButton = createStyledButton("North");
        southButton = createStyledButton("South");
        westButton = createStyledButton("West");
        eastButton = createStyledButton("East");

        gbc.gridx = 1; gbc.gridy = 0;
        movementPanel.add(northButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        movementPanel.add(westButton, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        movementPanel.add(eastButton, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        movementPanel.add(southButton, gbc);

        this.add(movementPanel, BorderLayout.SOUTH);

        characterPanel = new JPanel();
        characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
        characterPanel.setBackground(new Color(139, 69, 19));
        characterPanel.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 5));

        characterImageLabel = new JLabel();
        characterImageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        characterPanel.add(characterImageLabel);

        characterStatsLabel = new JLabel();
        characterStatsLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        characterStatsLabel.setForeground(Color.WHITE);
        characterStatsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        characterPanel.add(characterStatsLabel);

        this.add(characterPanel, BorderLayout.WEST);

        updateCharacterPage();

        this.setVisible(true);
    }
    // style buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        button.setBackground(new Color(210, 180, 140));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));
        button.addActionListener(this);
        return button;
    }
    private void moveCharacter(String direction) {
        switch (direction) {
            case "N": gameMap.setCrtCell(gameMap.goNorth()); break;
            case "S": gameMap.setCrtCell(gameMap.goSouth()); break;
            case "W": gameMap.setCrtCell(gameMap.goWest()); break;
            case "E": gameMap.setCrtCell(gameMap.goEast()); break;
            default: throw new IllegalArgumentException("Invalid direction!");
        }
        tableModel.updateGrid(gameMap);
        gameMapVisual.repaint();
    }
    // GAME PAGE

    // SANCTUARY PAGE
    JButton continueButton;
    JButton yesButton;
    JButton noButton;
    public void setUpSanctuaryPage() {
        this.getContentPane().removeAll();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setTitle("Sanctuary Page");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(new ImageIcon("src/main/org/example/images/sanctuary.jpg")
                .getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        this.setContentPane(backgroundLabel);
        this.setLayout(null);

        Font medievalFont = new Font("Times New Roman", Font.BOLD, 24);

        JLabel sanctuaryMessageLabel = new JLabel("You find a sanctuary and feel reinvigorated!");
        sanctuaryMessageLabel.setBounds(0, 50, 1000, 50);
        sanctuaryMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sanctuaryMessageLabel.setFont(medievalFont);
        sanctuaryMessageLabel.setForeground(Color.WHITE);

        JLabel newStatsLabel = new JLabel("New Stats: " + currentCharacter.toString());
        newStatsLabel.setBounds(0, 650, 1000, 30);
        newStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newStatsLabel.setFont(medievalFont);
        newStatsLabel.setForeground(Color.WHITE);

        yesButton = new JButton("Yes");
        yesButton.setBounds(350, 350, 150, 50);
        yesButton.setFont(medievalFont);
        yesButton.setForeground(Color.WHITE);
        yesButton.setBackground(new Color(34, 139, 34));
        yesButton.setFocusPainted(false);
        yesButton.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));
        yesButton.addActionListener(_ -> {
            gameMap.setUse(true);
            currentCharacter.lifeRegen();
            currentCharacter.manaRegen();
            setUpGame();
        });

        noButton = new JButton("No");
        noButton.setBounds(500, 350, 150, 50);
        noButton.setFont(medievalFont);
        noButton.setForeground(Color.WHITE);
        noButton.setBackground(new Color(255, 0, 0));
        noButton.setFocusPainted(false);
        noButton.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));
        noButton.addActionListener(_ -> {
            gameMap.setUse(false);
            setUpGame();
        });

        this.add(sanctuaryMessageLabel);
        this.add(newStatsLabel);
        this.add(yesButton);
        this.add(noButton);

        this.setVisible(true);
    }
    // SANCTUARY PAGE

    // FIGHT AND ABILITIES PAGE
    JButton attackButton;
    JButton abilityButton;
    JLabel playerStatsLabel;
    JLabel enemyStatsLabel;
    JLabel fightMessageLabel;
    ImageIcon enemyImage;
    int enemies = 0;
    public void setUpFight(Enemy enemy, boolean makeNewImage) {
        currentEnemy = enemy;
        if (makeNewImage) {
            enemyImage = new ImageIcon("src/main/org/example/images/" + (int) (Math.random() * 3) + ".jpg");
        }

        this.getContentPane().removeAll();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setTitle("Battle Page");
        this.setLocationRelativeTo(null);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(new ImageIcon("src/main/org/example/images/battlefield.jpg")
                .getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        this.setContentPane(backgroundLabel);
        this.setLayout(null);

        Font medievalFont = new Font("Times New Roman", Font.BOLD, 20);

        JPanel playerImagePanel = new JPanel();
        playerImagePanel.setBounds(150, 100, 220, 320);
        playerImagePanel.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));
        playerImagePanel.setBackground(new Color(0, 0, 0, 0));
        playerImagePanel.setLayout(new BorderLayout());

        JLabel playerImageLabel = new JLabel();
        playerImageLabel.setIcon(
                currentCharacter instanceof Warrior ? imageWarrior
                        : currentCharacter instanceof Mage ? imageMage
                        : imageRogue
        );
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerImagePanel.add(playerImageLabel, BorderLayout.CENTER);

        JPanel enemyImagePanel = new JPanel();
        enemyImagePanel.setBounds(650, 100, 220, 320);
        enemyImagePanel.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0), 3));
        enemyImagePanel.setBackground(new Color(0, 0, 0, 0));
        enemyImagePanel.setLayout(new BorderLayout());

        JLabel enemyImageLabel = new JLabel();
        enemyImageLabel.setIcon(enemyImage);
        enemyImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enemyImagePanel.add(enemyImageLabel, BorderLayout.CENTER);

        JPanel playerStatsPanel = new JPanel();
        playerStatsPanel.setBounds(150, 450, 250, 100);
        playerStatsPanel.setBackground(new Color(34, 139, 34));
        playerStatsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        playerStatsPanel.setLayout(new BorderLayout());

        playerStatsLabel = new JLabel();
        playerStatsLabel.setFont(medievalFont);
        playerStatsLabel.setForeground(Color.WHITE);
        playerStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateStatsLabel(playerStatsLabel, currentCharacter);
        playerStatsPanel.add(playerStatsLabel, BorderLayout.CENTER);

        JPanel enemyStatsPanel = new JPanel();
        enemyStatsPanel.setBounds(650, 450, 250, 100);
        enemyStatsPanel.setBackground(new Color(139, 0, 0)); // Dark Red
        enemyStatsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        enemyStatsPanel.setLayout(new BorderLayout());

        enemyStatsLabel = new JLabel();
        enemyStatsLabel.setFont(medievalFont);
        enemyStatsLabel.setForeground(Color.WHITE);
        enemyStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateStatsLabel(enemyStatsLabel, enemy);
        enemyStatsPanel.add(enemyStatsLabel, BorderLayout.CENTER);

        fightMessageLabel = new JLabel("", SwingConstants.CENTER);
        fightMessageLabel.setBounds(300, 50, 500, 30);
        fightMessageLabel.setFont(medievalFont);
        fightMessageLabel.setForeground(Color.YELLOW);

        attackButton = createStyledButton("Attack");
        attackButton.setBounds(350, 600, 150, 40);
        abilityButton = createStyledButton("Abilities");
        abilityButton.setBounds(500, 600, 150, 40);

        attackButton.addActionListener(this);
        abilityButton.addActionListener(this);

        this.add(playerImagePanel);
        this.add(enemyImagePanel);
        this.add(playerStatsPanel);
        this.add(enemyStatsPanel);
        this.add(fightMessageLabel);
        this.add(attackButton);
        this.add(abilityButton);

        this.setVisible(true);
    }
    JButton backButton;
    private void setUpAbilitiesPage(Character crtCharacter) {
        this.getContentPane().removeAll();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setTitle("Abilities");
        this.setLocationRelativeTo(null);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(new ImageIcon("src/main/org/example/images/battlefield.jpg")
                .getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        this.setContentPane(backgroundLabel);
        this.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Choose Your Ability", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(headerLabel, BorderLayout.NORTH);

        JPanel abilitiesPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        abilitiesPanel.setOpaque(false); // Make the panel transparent

        String[] abilityTypes = {"Fire", "Ice", "Earth"};
        String[] images = {
                "src/main/org/example/images/fire.jpg",
                "src/main/org/example/images/ice.jpg",
                "src/main/org/example/images/earth.jpg"
        };

        for (int i = 0; i < abilityTypes.length; i++) {
            String type = abilityTypes[i];
            String imagePath = images[i];

            JPanel abilityPanel = new JPanel();
            abilityPanel.setLayout(new BorderLayout());
            abilityPanel.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));
            abilityPanel.setBackground(new Color(0, 0, 0, 120));

            JLabel abilityImageLabel = new JLabel();
            ImageIcon abilityImage = new ImageIcon(new ImageIcon(imagePath)
                    .getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
            abilityImageLabel.setIcon(abilityImage);
            abilityImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel abilityDetailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            abilityDetailsPanel.setOpaque(false);

            boolean hasAbilities = false;

            for (int j = 0; j < crtCharacter.getAbilities().size(); j++) {
                Spell ability = crtCharacter.getAbilities().get(j);
                if ((type.equals("Fire") && ability instanceof Fire) ||
                        (type.equals("Ice") && ability instanceof Ice) ||
                        (type.equals("Earth") && ability instanceof Earth)) {
                    hasAbilities = true;

                    JButton abilityUseButton = new JButton(type + ": Damage " + ability.getDamage() + ", Mana " + ability.getManaCost());
                    abilityUseButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    abilityUseButton.setForeground(Color.WHITE);
                    abilityUseButton.setBackground(new Color(139, 69, 19));
                    abilityUseButton.setFocusPainted(false);
                    abilityUseButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                    int abilityIndex = j;
                    abilityUseButton.addActionListener(_ -> {
                        try {
                            crtCharacter.useAbility(abilityIndex, currentEnemy);
                            handleTurn(crtCharacter, currentEnemy, playerStatsLabel, enemyStatsLabel);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage());
                        }
                    });
                    abilityDetailsPanel.add(abilityUseButton);
                }
            }

            if (!hasAbilities) {
                JLabel noAbilitiesLabel = new JLabel("No abilities", SwingConstants.CENTER);
                noAbilitiesLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
                noAbilitiesLabel.setForeground(Color.WHITE);
                abilityDetailsPanel.add(noAbilitiesLabel);
            }

            abilityPanel.add(abilityImageLabel, BorderLayout.CENTER);
            abilityPanel.add(abilityDetailsPanel, BorderLayout.SOUTH);
            abilitiesPanel.add(abilityPanel);
        }

        this.add(abilitiesPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(139, 69, 19));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));
        backButton.addActionListener(this);

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setOpaque(false);
        backButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        backButtonPanel.add(backButton);
        this.add(backButtonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
    private void handleTurn(Character crtCharacter, Enemy enemy, JLabel playerStatsLabel, JLabel enemyStatsLabel) {
        setUpFight(currentEnemy, false);
        updateStatsLabel(playerStatsLabel, crtCharacter);
        updateStatsLabel(enemyStatsLabel, enemy);
        playerStatsLabel.repaint();
        enemyStatsLabel.repaint();

        if (enemy.getCrtLife() <= 0) {
            enemy.setCrtLife(0);
            fightMessageLabel.setText("Enemy defeated! You win!");
            JOptionPane.showMessageDialog(this, "Enemy defeated! You win!");
            crtCharacter.crtLife = Math.min(crtCharacter.crtLife * 2, crtCharacter.maxLife);
            crtCharacter.crtMana = crtCharacter.maxMana;
            Random random = new Random();
            crtCharacter.exp += random.nextInt(25);
            setUpGame();
            enemies++;
            return;
        }

        Random random = new Random();
        boolean useAbility = random.nextBoolean();
        if (!enemy.getAbilities().isEmpty() && useAbility) {
            int abilityIndex = random.nextInt(enemy.getAbilities().size());
            Spell chosenAbility = enemy.getAbilities().get(abilityIndex);
            if (enemy.getCrtMana() >= chosenAbility.getManaCost()) {
                enemy.useAbility(abilityIndex, crtCharacter);
                fightMessageLabel.setText("Enemy used " + chosenAbility + "!");
                updateStatsLabel(playerStatsLabel, crtCharacter);
                updateStatsLabel(enemyStatsLabel, enemy);
                playerStatsLabel.repaint();
                enemyStatsLabel.repaint();
            } else {
                enemy.useDefaultAttack(crtCharacter);
                fightMessageLabel.setText("Enemy used a default attack!");
                updateStatsLabel(playerStatsLabel, crtCharacter);
                updateStatsLabel(enemyStatsLabel, enemy);
                playerStatsLabel.repaint();
                enemyStatsLabel.repaint();
            }
        } else {
            enemy.useDefaultAttack(crtCharacter);
            fightMessageLabel.setText("Enemy used a default attack!");
            updateStatsLabel(playerStatsLabel, crtCharacter);
            updateStatsLabel(enemyStatsLabel, enemy);
            playerStatsLabel.repaint();
            enemyStatsLabel.repaint();
        }

        updateStatsLabel(playerStatsLabel, crtCharacter);
        updateStatsLabel(enemyStatsLabel, enemy);
        playerStatsLabel.repaint();
        enemyStatsLabel.repaint();

        if (crtCharacter.getCrtLife() <= 0) {
            crtCharacter.setCrtLife(0);
            fightMessageLabel.setText("You have been defeated!");
            JOptionPane.showMessageDialog(this, "You have been defeated! Game over!");
            setUpCharacterSelection();
        }
    }
    // FIGHT AND ABILITIES PAGE

    // character/ enemy stats updates
    private void updateCharacterPage() {
        if (currentCharacter != null) {
            characterImageLabel.setIcon(currentCharacter instanceof Warrior ? imageWarrior
                    : currentCharacter instanceof Mage ? imageMage
                    : imageRogue);
            String stats = "<html>Name: " + currentCharacter.getName() + "<br>" +
                    "Level: " + currentCharacter.getLvl() + "<br>" +
                    "Health: " + currentCharacter.getCrtLife() + "<br>" +
                    "Mana: " + currentCharacter.getCrtMana() + "<br>" +
                    "Exp: " + currentCharacter.getExp() +"</html>";
            characterStatsLabel.setText(stats);
        }
    }
    private void updateStatsLabel(JLabel statsLabel, Character character) {
        statsLabel.setText( "<html>Your Stats:<br>" +"Life: " + character.getCrtLife() + "<br>" +
                "Mana: " + character.getCrtMana() + "<br>" +
                "Exp: " + character.getExp() + "</html>");
    }
    private void updateStatsLabel(JLabel statsLabel, Enemy enemy) {
        statsLabel.setText("<html>Enemy Stats:<br>" +
                "Life: " + enemy.getCrtLife() + "<br>" +
                "Mana: " + enemy.getCrtMana() + "</html>");
    }

    // PORTAL PAGE
    public void portalPage() {
        this.getContentPane().removeAll();
        this.repaint();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setTitle("Portal Page");
        this.setLocationRelativeTo(null);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(new ImageIcon("src/main/org/example/images/gate.jpg")
                .getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        this.setContentPane(backgroundLabel);
        this.setLayout(null);

        JLabel title = new JLabel("You have reached the portal! Next level: " + (room + 1), JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBounds(200, 20, 600, 50);
        this.add(title);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        infoPanel.setBounds(150, 100, 700, 500);
        infoPanel.setBackground(new Color(0, 0, 0, 150));
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(184, 134, 11), 3),
                "Character Info",
                0,
                0,
                new Font("Times New Roman", Font.BOLD, 20),
                Color.WHITE
        ));

        JLabel photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(JLabel.CENTER);
        photoLabel.setVerticalAlignment(JLabel.CENTER);
        if (currentCharacter != null) {
            if (currentCharacter instanceof Warrior) {
                photoLabel.setIcon(imageWarrior);
            } else if (currentCharacter instanceof Mage) {
                photoLabel.setIcon(imageMage);
            } else if (currentCharacter instanceof Rogue) {
                photoLabel.setIcon(imageRogue);
            }
        }
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        photoLabel.setPreferredSize(new Dimension(300, 400));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        infoPanel.add(photoLabel, gbc);

        JPanel statsPanel = new JPanel(new GridLayout(5, 1, 5, 15));
        statsPanel.setOpaque(false);

        JLabel classLabel = new JLabel("Class: " + (currentCharacter != null ? currentCharacter.getClass().getSimpleName() : "N/A"), JLabel.CENTER);
        JLabel levelLabel = new JLabel("Level: " + (currentCharacter != null ? currentCharacter.getLvl() : "N/A"), JLabel.CENTER);
        JLabel expLabel = new JLabel("Experience: " + (currentCharacter != null ? currentCharacter.getExp() : "N/A"), JLabel.CENTER);
        JLabel manaLabel = new JLabel("Mana: " + (currentCharacter != null ? currentCharacter.getCrtMana() : "N/A"), JLabel.CENTER);
        JLabel enemiesLabel = new JLabel("Enemies Killed: " + enemies, JLabel.CENTER);

        JLabel[] labels = {classLabel, levelLabel, expLabel, manaLabel, enemiesLabel};
        for (JLabel label : labels) {
            label.setFont(new Font("Times New Roman", Font.BOLD, 22));
            label.setForeground(Color.WHITE);
            statsPanel.add(label);
        }

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        infoPanel.add(statsPanel, gbc);

        this.add(infoPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        buttonPanel.setBounds(350, 600, 300, 100);
        buttonPanel.setOpaque(false);

        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        continueButton.setForeground(Color.WHITE);
        continueButton.setBackground(new Color(139, 69, 19));
        continueButton.setFocusPainted(false);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        continueButton.setPreferredSize(new Dimension(120, 40));
        continueButton.addActionListener(_ -> {
            this.getContentPane().removeAll();
            this.repaint();
            advanceToNextLevel();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(139, 69, 19));
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.addActionListener(_ -> {
            this.dispose();
            System.exit(0);
        });

        buttonPanel.add(continueButton);
        buttonPanel.add(exitButton);
        this.add(buttonPanel);

        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }
    private void advanceToNextLevel() {
        room++;
        currentCharacter.exp += room * 5;
        currentCharacter.crtMana = currentCharacter.maxMana;
        currentCharacter.crtLife = currentCharacter.maxLife;
        gameMap = Grid.generateGrid(this);
        gameMap.setCrtCharacter(currentCharacter);
        setUpGame();
    }
    // PORTAL PAGE

    // actions performed
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == button) {
            String userText = username.getText();
            String pwdText = new String(password.getPassword());
            Account account = authenticate(userText, pwdText);
            if (account != null) {
                currentAccount = account;
                message.setText("Welcome, " + account.getInformation().getName() + "!");
                setUpCharacterSelection();
            } else {
                message.setText("Invalid username or password.");
                username.setText("");
                password.setText("");
            }
        }
        if (evt.getSource() == show) {
            password.setEchoChar(show.isSelected() ? (char) 0 : '*');
        }
        if (evt.getSource() == charactersCombo) {
            String selectedName = (String) charactersCombo.getSelectedItem();
            for (Character character : currentAccount.getCharacters()) {
                if (character.getName().equals(selectedName)) {
                    currentCharacter = character;
                    currentCharacter.setCrtLife(currentCharacter.getMaxLife());
                    currentCharacter.setCrtMana(currentCharacter.getMaxMana());
                    labelPic.setIcon(currentCharacter instanceof Warrior ? imageWarrior
                            : currentCharacter instanceof Mage ? imageMage
                            : imageRogue);
                    break;
                }
            }
        }
        if (evt.getSource() == startButton) {
            if (currentCharacter != null) {
                gameMap = Grid.generateGrid(this);
                gameMap.setCrtCharacter(currentCharacter);
                setUpGame();
            }
        }
        if (evt.getSource() == northButton) {
            moveCharacter("N");
        }
        if (evt.getSource() == southButton) {
            moveCharacter("S");
        }
        if (evt.getSource() == westButton) {
            moveCharacter("W");
        }
        if (evt.getSource() == eastButton) {
            moveCharacter("E");
        }
        if (evt.getSource() == continueButton) {
            setUpGame();
        }
        if (evt.getSource() == attackButton) {
            currentCharacter.useDefaultAttack(currentEnemy);
            try {
                updateStatsLabel(playerStatsLabel, currentCharacter);
                updateStatsLabel(enemyStatsLabel, currentEnemy);
                handleTurn(currentCharacter, currentEnemy, playerStatsLabel, enemyStatsLabel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (evt.getSource() == abilityButton) {
            setUpAbilitiesPage(currentCharacter);
        }
        if (evt.getSource() == backButton) {
            setUpFight(currentEnemy, false);
        }
        if (evt.getSource() == yesButton) {
            currentCharacter.lifeRegen();
            currentCharacter.manaRegen();
            System.out.println("Sanctuary used.");
        } else if (evt.getSource() == noButton) {
            System.out.println("Sanctuary not used.");
            //
        }
    }

    public static void run() {
        Game game = Game.getInstance();
        game.setUpLoginGUI();
    }
}
