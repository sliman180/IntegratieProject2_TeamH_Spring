package be.kdg.teamh.dtos.response;

import java.io.Serializable;

public class SpelkaartResponse implements Serializable {
    private int id;
    private int positie;
    private int cirkelsessie;
    private int kaart;

    public SpelkaartResponse() {
        //
    }

    public SpelkaartResponse(int id, int positie, int cirkelsessie, int kaart) {
        this.id = id;
        this.positie = positie;
        this.cirkelsessie = cirkelsessie;
        this.kaart = kaart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositie() {
        return positie;
    }

    public void setPositie(int positie) {
        this.positie = positie;
    }

    public int getCirkelsessie() {
        return cirkelsessie;
    }

    public void setCirkelsessie(int cirkelsessie) {
        this.cirkelsessie = cirkelsessie;
    }

    public int getKaart() {
        return kaart;
    }

    public void setKaart(int kaart) {
        this.kaart = kaart;
    }
}
