package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;

import java.util.ArrayList;
import java.util.List;

public class OrganisatieService {

    List<Organisatie> organisaties;

    public OrganisatieService() {
        this.organisaties = new ArrayList<>();

    }

    public void addOrganisatie(Organisatie organisatie) {
        organisaties.add(organisatie);
    }

    public Organisatie getOrganisatie(int id) {
       try{
            return organisaties.get(id);
        }catch(IndexOutOfBoundsException ie){
            return null;
        }
        /*
        if(organisaties.size()<1) {
            return null;
        }else{
            return organisaties.get(id);
        }
        */
    }
}
