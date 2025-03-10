package main.org.example.entities;

import main.org.example.characters.Earth;
import main.org.example.characters.Fire;
import main.org.example.characters.Ice;

import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle, Element<Entity> {
    public ArrayList<Spell> abilities;
    public int crtLife, maxLife, crtMana, maxMana;
    protected boolean fire, ice, earth;

    // constructorul
    public Entity(int maxLife, int maxMana) {
        this.maxLife = maxLife;
        this.maxMana = maxMana;
        this.crtLife = maxLife;
        this.crtMana = maxMana;
        this.abilities = new ArrayList<>();
    }

    // getters & setters
    public int getMaxLife() {
        return maxLife;
    }
    public int getMaxMana() {
        return maxMana;
    }
    public void setCrtLife(int crtLife) {
        this.crtLife = crtLife;
    }
    public int getCrtLife() {
        return crtLife;
    }
    public void setCrtMana(int crtMana) {
        this.crtMana = crtMana;
    }
    public int getCrtMana() {
        return crtMana;
    }
    public boolean isFireImmune() {
        return fire;
    }
    public void setFireImmune(boolean fire) {
        this.fire = fire;
    }
    public boolean isIceImmune() {
        return ice;
    }
    public void setIceImmune(boolean ice) {
        this.ice = ice;
    }
    public boolean isEarthImmune() {
        return earth;
    }
    public void setEarthImmune(boolean earth) {
        this.earth = earth;
    }
    public ArrayList<Spell> getAbilities() {
        return abilities;
    }
    public void addAbility(Spell ability) {
        abilities.add(ability);
    }
    public void removeAbility(Spell ability) {
        abilities.remove(ability);
    }

    // fac functiile din interfata Battle
    @Override
    public abstract void receiveDamage(int damage);
    @Override
    public abstract int getDamage();
    // the damage that can be given

    // lifeRegen & manaRegen
    public void lifeRegen() {
        Random random = new Random();
        int addLife = random.nextInt(maxLife/2);
        crtLife += addLife;
        crtLife = Math.min(crtLife, maxLife);
    }
    public void manaRegen() {
        Random random = new Random();
        int addMana = random.nextInt(maxMana/2);
        crtMana += addMana;
        crtMana = Math.min(crtMana, maxMana);
    }

    // functie de folosire abilitate sau atac basic daca nu am abilitate
    public boolean useAbility(int index, Entity target){
        Spell ability = abilities.get(index);
        System.out.println("Selected ability: " + ability);

        if (crtMana < ability.getManaCost()) {
            System.out.println(getClass().getSimpleName() + " used default attack due to insufficient mana!");
            useDefaultAttack(target);
            return false;
        }

        crtMana -= ability.getManaCost();
        target.accept(ability);
        abilities.remove(index);

        return true;
    }

    public void useDefaultAttack(Entity target) {
        int damage = this.getDamage();
        target.receiveDamage(damage);
        System.out.println(getClass().getSimpleName() + " used default attack! [Damage: " + damage + "]");
    }

    // functie pentru a genera abilitati random
    public void generateRandomAbilities() {
        Random random = new Random();
        int numAbilities = random.nextInt(3) + 3;
        for (int i = 0; i < numAbilities; i++) {
            int damage = random.nextInt(31) + 10;
            int manaCost = random.nextInt(16) + 10;
            int type = random.nextInt(3);
            Spell spell;
            switch (type) {
                case 0 -> spell = new Fire(damage, manaCost);
                case 1 -> spell = new Ice(damage, manaCost);
                case 2 -> spell = new Earth(damage, manaCost);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
            abilities.add(spell);
        }
    }

    @Override
    // afisare informatii despre entitate
    public String toString() {
        return  " Stats: " +
                "Life = " + crtLife + "/" + maxLife + ", " +
                "Mana = " + crtMana + "/" + maxMana + ", " +
                "Fire Immune = " + fire + ", " +
                "Ice Immune = " + ice + ", " +
                "Earth Immune = " + earth;
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}