package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthApiTest extends ApiTest
{
    @Test
    public void login() throws Exception
    {
        LoginRequest login = new LoginRequest("userone", "userone");

        http.perform(post("/auth/login", objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void login_verkeerdeGegevens() throws Exception
    {
        LoginRequest login = new LoginRequest("wrong", "wrong");

        http.perform(post("/auth/login", objectMapper.writeValueAsString(login)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void register() throws Exception
    {
        RegistratieRequest registratie = new RegistratieRequest("test", "password", "password");

        http.perform(post("/auth/register", objectMapper.writeValueAsString(registratie)))
            .andExpect(status().isCreated());
    }

    @Test
    public void register_reedsGeregistreerd() throws Exception
    {
        RegistratieRequest registratie = new RegistratieRequest("userone", "password", "password");

        http.perform(post("/auth/register", objectMapper.writeValueAsString(registratie)))
            .andExpect(status().isConflict());
    }

    @Test
    public void register_wachtwoordTeKort() throws Exception
    {
        RegistratieRequest registratie = new RegistratieRequest("test", "pass", "pass");

        http.perform(post("/auth/register", objectMapper.writeValueAsString(registratie)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void register_wachtwoordConfirmatieNietOvereenkomend() throws Exception
    {
        RegistratieRequest registratie = new RegistratieRequest("test", "password", "drowssap");

        http.perform(post("/auth/register", objectMapper.writeValueAsString(registratie)))
            .andExpect(status().isBadRequest());
    }
}
