package be.kdg.teamh.api;

import be.kdg.teamh.Application;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class ApiTest
{
    protected MockMvc http;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void setUp()
    {
        this.http = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    public MockHttpServletRequestBuilder get(String url)
    {
        return MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder post(String url, String json)
    {
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(json);
    }

    public MockHttpServletRequestBuilder put(String url, String json)
    {
        return MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(json);
    }

    public MockHttpServletRequestBuilder delete(String url)
    {
        return MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON);
    }

    public String getUserOneToken() throws Exception
    {
        MvcResult result = http.perform(post("/auth/login", objectMapper.writeValueAsString(new LoginRequest("userone", "userone")))).andReturn();

        return "Bearer " + objectMapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    public String getUserTwoToken() throws Exception
    {
        MvcResult result = http.perform(post("/auth/login", objectMapper.writeValueAsString(new LoginRequest("usertwo", "usertwo")))).andReturn();

        return "Bearer " + objectMapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    public String getNonExistingUserToken() throws Exception
    {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwIiwiZ2VicnVpa2Vyc25hYW0iOiJ3cm9uZyIsInJvbGxlbiI6W10sImlhdCI6MTQ1ODM5NzMyMX0.oisvjFAB6TX6M_qGmzqdDngvBDtN-zzkrgEbL5gD9ck";
    }
}
