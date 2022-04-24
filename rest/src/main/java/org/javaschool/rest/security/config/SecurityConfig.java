package org.javaschool.rest.security.config;

import org.javaschool.rest.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder n = new BCryptPasswordEncoder();
        System.out.println(n.encode("1234"));
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean(name = "userDetailService")
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests().antMatchers(HttpMethod.GET, "/employee/**", "/department/**", "/position/**", "/type/**").hasAnyRole("USER", "ADMINISTRATOR", "MODERATOR")
                .and()
                .authorizeRequests().antMatchers("/position/**", "/type/**").hasAnyRole("ADMINISTRATOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/employee/**", "/department/**").hasAnyRole("ADMINISTRATOR", "MODERATOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/employee/**", "/department/**").hasAnyRole("ADMINISTRATOR", "MODERATOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/employee/**", "/department/**").hasAnyRole("ADMINISTRATOR", "MODERATOR")
                .and()
                .authorizeRequests().antMatchers("/login").permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

}
