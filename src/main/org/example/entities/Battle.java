package main.org.example.entities;

public interface Battle {
    public void receiveDamage(int damage);
    //inregistrarea unei pierderi de viata
    //in fctie de cele 2 atribute secundare sansa de 50% injum

    public int getDamage();
    // damage pe care o entitate il aplica
    //in fctie de atribut principal sansa de 50% dublare
    //atributele sunt Strength, Dexterity, Charisma
}
