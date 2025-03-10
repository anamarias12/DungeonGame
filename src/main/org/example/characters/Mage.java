package main.org.example.characters;

import java.util.Random;

public class Mage extends Character {
    public Mage(String name, int exp, int lvl, int maxLife, int maxMana) {
        super(name, exp, lvl, maxLife, maxMana);
        this.strength = 0;
        this.charisma = 10; // high charisma for mage
        this.dexterity = 0;
        this.ice = true; // immune to ice
    }

    @Override
    public void receiveDamage(int damage) {
        if(strength + dexterity >= 25) {
            damage = damage / 2;
            System.out.println("Mage got half damage!");
            print_atributes();
        }
        // the damage that is lost
        crtLife -= damage;
        if(crtLife < 0) crtLife = 0;
    }

    @Override
    public int getDamage() {
        Random random = new Random();
        int damage = charisma;
        if (random.nextBoolean()) {
            if(charisma >= 25) {
                damage = damage * 2;
                System.out.println("Mage's damage doubled due to high charisma!");
            }
        }
        return damage;
    }

    public void print_atributes() {
        System.out.println("Strength: " + strength + " Charisma: " + charisma + " Dexterity: " + dexterity);
    }
}
