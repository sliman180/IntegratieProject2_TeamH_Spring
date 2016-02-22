package be.kdg.teamh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by S on 19-2-2016.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("greg").password("turnquist").roles("USER").and()
                .withUser("user").password("user").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/hoofdthemas").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/hoofdthemas/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/hoofdthemas").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/hoofdthemas/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/hoofdthemas/**").hasRole("ADMIN").and().csrf().disable();
    }
}
