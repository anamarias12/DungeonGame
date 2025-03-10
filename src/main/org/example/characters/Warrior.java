package main.org.example.characters;

import java.util.Random;

public class Warrior extends Character {
    public Warrior(String name, int exp, int lvl, int maxLife, int maxMana) {
        super(name, exp, lvl, maxLife, maxMana);
        this.strength = 10; // high strength for warrior
        this.charisma = 0;
        this.dexterity = 0;
        this.fire = true; // immune to fire
    }

    @Override
    public void receiveDamage(int damage) {
        if(charisma + dexterity >= 25) {
            damage = damage / 2;
            System.out.println("Warrior got half damage!");
            print_atributes();
        }
        // the damage that is lost
        crtLife -= damage;
        if(crtLife < 0) crtLife = 0;
    }

    @Override
    public int getDamage() {
        Random random = new Random();
        int damage = strength;
        if (random.nextBoolean()) {
            if(strength >= 25) {
                damage = damage * 2;
                System.out.println("Warrior's damage doubled due to high strength!");
            }
        }
        return damage;
    }

    public void print_atributes() {
        System.out.println("Strength: " + strength + " Charisma: " + charisma + " Dexterity: " + dexterity);
    }
}
