package se233.chapter1.model.item;

public class Armor extends BasedEquipment {
    private int defense;
    private int resistance;

    public Armor(String name, int defense, int resistance, String imgpath) {
        super(name, imgpath);  // Call the constructor of BasedEquipment
        this.defense = defense;
        this.resistance = resistance;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    @Override
    public String toString() {
        return name;
    }
}
