package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SpelkaartRequest implements Serializable {
    @NotNull
    private int positie;

    @NotNull
    private int cirkelsessie;

    @NotNull
    private int kaart;

    public SpelkaartRequest() {
        //
    }

    public SpelkaartRequest(int cirkelsessie, int kaart) {
        this.cirkelsessie = cirkelsessie;
        this.kaart = kaart;
    }

    public int getPositie() {
        return positie;
    }

    public void setPositie(int positie) {
        this.positie = positie;
    }

    public int getKaart() {
        return kaart;
    }

    public void setKaart(int kaart) {
        this.kaart = kaart;
    }

    public int getCirkelsessie() {
        return cirkelsessie;
    }

    public void setCirkelsessie(int cirkelsessie) {
        this.cirkelsessie = cirkelsessie;
    }
}
