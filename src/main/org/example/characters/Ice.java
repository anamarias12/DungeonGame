package main.org.example.characters;

import main.org.example.entities.Entity;
import main.org.example.entities.Spell;

public class Ice extends Spell {
    public Ice(int damage, int manaCost) {
        super(damage, manaCost);
    }
    @Override
    public void visit(Entity entity) {
        System.out.println("Ice ability used!");
        super.visit(entity);
    }
}
