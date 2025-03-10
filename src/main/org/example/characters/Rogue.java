package main.org.example.characters;

import java.util.Random;

public class Rogue extends Character {
    public Rogue(String name, int exp, int lvl, int maxLife, int maxMana) {
        super(name, exp, lvl, maxLife, maxMana);
        this.strength = 0;
        this.charisma = 0;
        this.dexterity = 10; // high dexterity for rogue
        this.earth = true; // immune to earth
    }

    @Override
    public void receiveDamage(int damage) {
        if(strength + charisma >= 25) {
            damage = damage / 2;
            System.out.println("Rogue got half damage!");
            print_atributes();
        }
        // the damage that is lost
        crtLife -= damage;
        if(crtLife < 0) crtLife = 0;
    }

    @Override
    public int getDamage() {
        Random random = new Random();
        int damage = dexterity;
        if (random.nextBoolean()) {
            if(dexterity >= 25) {
                damage = damage * 2;
                System.out.println("Rogue's damage doubled due to high dexterity!");
            }
        }
        return damage;
    }

    public void print_atributes() {
        System.out.println("Strength: " + strength + " Charisma: " + charisma + " Dexterity: " + dexterity);
    }
}
