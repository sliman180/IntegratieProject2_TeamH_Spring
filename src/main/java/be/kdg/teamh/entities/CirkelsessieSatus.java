package be.kdg.teamh.entities;

public enum CirkelsessieSatus {

    OPEN("Open"),
    GESLOTEN("Gesloten"),
    BEEINDIGD("Beëindig");


    private final String naam;

    CirkelsessieSatus(String s) {
        naam = s;
    }

    public boolean equalsNaam(String naam) {
        return (naam == null) ? false : this.naam.equals(naam);
    }


    public String toString() {
        return this.naam;
    }
}
