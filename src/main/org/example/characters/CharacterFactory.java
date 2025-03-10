package main.org.example.characters;

public class CharacterFactory {
    public static Character createCharacter(String profession, String name, int experience, int level) {
        switch (profession) {
            case "Warrior":
                return new Warrior(name, experience, level, 150, 50);
            case "Rogue":
                return new Rogue(name, experience, level, 100, 75);
            case "Mage":
                return new Mage(name, experience, level, 75, 100);
            default:
                return null;
        }
    }
}