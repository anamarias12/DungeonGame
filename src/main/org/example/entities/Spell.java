package main.org.example.entities;

import main.org.example.characters.Earth;
import main.org.example.characters.Fire;
import main.org.example.characters.Ice;

public abstract class Spell implements Visitor<Entity> {
    private int damage;
    private int manaCost;

    public Spell(int damage, int manaCost) {
        this.damage = damage;
        this.manaCost = manaCost;
    }

    // getters & setters
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public int getDamage() {
        return damage;
    }
    public int getManaCost() {
        return manaCost;
    }

    // informatii
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Damage: " + damage + ", Mana Cost: " + manaCost + "]";
    }

    @Override
    public void visit(Entity entity) {
        if ((this instanceof Fire && entity.isFireImmune()) ||
                (this instanceof Ice && entity.isIceImmune()) ||
                (this instanceof Earth && entity.isEarthImmune())) {
            System.out.println(entity.getClass().getSimpleName() + " is immune to " + this.getClass().getSimpleName());
        } else {
            entity.receiveDamage(damage);
            System.out.println(this.getClass().getSimpleName() + " dealt " + damage + " damage to " + entity.getClass().getSimpleName());
        }
    }
}
