package com.ad.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** One of the following 2 approaches should be implemented for configuring in-memory authentication by exposing UserDetailsService **/
    /** FIRST APPROACH **/
//    @Bean //This @Bean annotation is mandatory
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails student = User.builder()
//                        .username("student")
//                        .password(passwordEncoder.encode("pws"))
//                        .roles("STUDENT")
//                        .build();
//
//        UserDetails teacher = User.builder()
//                .username("teacher")
//                .password(passwordEncoder.encode("pwt"))
//                .roles("TEACHER")
//                .build();
//
//        return new InMemoryUserDetailsManager(student, teacher);
//    }

    /** SECOND APPROACH - more readable and convenient for custom UserDetailsService that can be exposed **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("student")
                    .password(passwordEncoder.encode("pws"))
                    .roles("STUDENT")
                    .and()
                .withUser("teacher")
                    .password(passwordEncoder.encode("pwt"))
                    .roles("TEACHER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/spring-security-memory-auth/greetings").permitAll()
                .antMatchers("/spring-security-memory-auth/greetings/class").hasAnyRole("STUDENT", "TEACHER")
                .antMatchers(HttpMethod.GET,"/spring-security-memory-auth/greetings/teacher").hasRole("TEACHER")
                .anyRequest().authenticated()
                .and()
            .formLogin() //if this is not added then '/login' resource itself will be Unauthorized/Forbidden
                .and()
            .httpBasic(); //this is added to send Basic Auth requests using Postman
//                .and()
//            .csrf().disable(); // see OneNote for more details
    }
}