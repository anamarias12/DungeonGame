package main.org.example.characters;

import main.org.example.entities.Entity;
import main.org.example.entities.Spell;

public class Fire extends Spell {
    public Fire(int damage, int manaCost) {
        super(damage, manaCost);
    }
    @Override
    public void visit(Entity entity) {
        System.out.println("Fire ability used!");
        super.visit(entity);
    }
}
