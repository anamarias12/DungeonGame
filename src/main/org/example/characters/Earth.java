package main.org.example.characters;

import main.org.example.entities.Entity;
import main.org.example.entities.Spell;

public class Earth extends Spell {
    public Earth(int damage, int manaCost) {
        super(damage, manaCost);
    }
    @Override
    public void visit(Entity entity) {
        System.out.println("Earth ability used!");
        super.visit(entity);
    }
}
