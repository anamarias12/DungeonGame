package main.org.example.entities;

import java.util.Random;

public class Enemy extends Entity {
    public Enemy() {
        super(new Random().nextInt(50) + 50, new Random().nextInt(30) + 20);
        Random random = new Random();
        this.fire = random.nextBoolean();
        this.ice = random.nextBoolean();
        this.earth = random.nextBoolean();
    }

    @Override
    public void receiveDamage(int damage) {
        Random random = new Random();
        if (random.nextBoolean()) {
            damage = damage / 2;
            System.out.println("Enemy got half damage!");
        }
        setCrtLife(getCrtLife() - damage);
    }

    @Override
    public int getDamage() {
        Random random = new Random();
        int damage = random.nextInt(5) + 5;
        if (random.nextBoolean()) {
            damage *= 2;
            System.out.println("Enemy's damage doubled!");
        }
        return damage;
    }
}

