package com.ad.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder; //bean defined below

    /** One of the following 2 approaches should be implemented for configuring in-memory authentication using UserDetailsService **/
    /** FIRST APPROACH **/
    @Bean //This @Bean annotation is mandatory
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                        .username("user_a")
                        .password(passwordEncoder.encode("pw1"))
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    /** SECOND APPROACH - more suitable for custom UserDetailsService that can be injected into this configuration **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user_b")
                .password(passwordEncoder.encode("pw2"))
                .roles("USER");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}