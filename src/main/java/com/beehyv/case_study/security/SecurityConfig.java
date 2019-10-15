package com.beehyv.case_study.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("myUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    LoginSuccess loginSuccess;
    @Autowired
    LoginFailure loginFailure;
    @Autowired
    LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/home", "/getProfile/*", "/updateProfile").hasAnyAuthority("USER", "ADMIN")
//                .antMatchers("/login").anonymous() //TODO how to make sure only logged out users can access login page?
                .antMatchers("/**").permitAll() // check /* and /**
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccess)
                .failureHandler(loginFailure)
                .permitAll()
                .and().logout().logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf().disable(); //TODO determine if csrf is useful or harmful
    }


}
