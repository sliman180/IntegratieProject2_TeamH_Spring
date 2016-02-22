package be.kdg.teamh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
            .withUser("user").password("user").roles("USER").and()
            .withUser("admin").password("admin").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.httpBasic()
            .and().authorizeRequests()
            .antMatchers(HttpMethod.GET, "/hoofdthemas/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/hoofdthemas").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/hoofdthemas/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/hoofdthemas/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/organisaties/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/organisaties").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/organisaties/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/organisaties/**").hasRole("ADMIN")
            .and().csrf().disable();
    }
}
