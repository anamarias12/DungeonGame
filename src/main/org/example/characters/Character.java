package main.org.example.characters;

import main.org.example.entities.Entity;

public abstract class Character extends Entity {
    public String name;
    public int exp;
    public int lvl;
    public int strength, charisma, dexterity;

    // constructor
    public Character(String name, int exp, int lvl, int maxLife, int maxMana) {
        super(maxLife, maxMana);
        this.name = name;
        this.exp = exp;
        this.lvl = lvl;
    }

    // getters & setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLvl() {
        return lvl;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public int getMaxLife() {
        return maxLife;
    }
    public void setMaxLife(int life) {
        this.maxLife = life;
    }
    public int getMaxMana() {
        return maxMana;
    }
    public void setMaxMana(int mana) {
        this.maxMana = mana;
    }

    // cand exp > 100
    public void levelUp() {
        lvl++;
        maxLife += 50;
        maxMana += 20;
        charisma += 10;
        strength += 10;
        dexterity += 10;
        exp = 0;
    }

    public String toString() {
        return getClass().getSimpleName() + " Stats: " +
                "Life = " + crtLife + "/" + maxLife + ", " +
                "Mana = " + crtMana + "/" + maxMana + "Exp = " + exp;
    }
}
