package be.kdg.teamh.dtos.request;

import be.kdg.teamh.entities.Status;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CirkelsessieInvitatieRequest implements Serializable
{
    @NotNull
    private String email;

    public CirkelsessieInvitatieRequest()
    {
        //
    }

    public CirkelsessieInvitatieRequest(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
