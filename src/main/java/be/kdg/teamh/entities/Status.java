package be.kdg.teamh.entities;

public enum Status {
    OPEN("Open"),
    GESLOTEN("Gesloten"),
    BEEINDIGD("Beëindigd");

    private final String naam;

    Status(String naam) {
        this.naam = naam;
    }

    public boolean equalsNaam(String naam) {
        return naam != null && this.naam.equals(naam);
    }

    public String toString() {
        return this.naam;
    }
}
