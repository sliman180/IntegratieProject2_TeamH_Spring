package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "messages")
public class Message {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private String datum;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Chat chat;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Gebruiker gebruiker;


    public Message() {
        //JPA
    }

    public Message(String tekst, String datum, Gebruiker gebruiker) {
        this.tekst = tekst;
        this.datum = datum;
        this.gebruiker = gebruiker;
    }


    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
