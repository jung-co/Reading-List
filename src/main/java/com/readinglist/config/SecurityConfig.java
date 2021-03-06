package com.readinglist.config;

import com.readinglist.domain.Reader;
import com.readinglist.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").access("hasRole('READER')")
                .antMatchers("/h2-console/*").permitAll()
                .antMatchers("/**").permitAll()
            .and()

            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true");
        http.headers().frameOptions().disable();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Reader> userDetails = readerRepository.findById(username);
                return userDetails.orElseThrow(() -> new UsernameNotFoundException("????????? ?????? ??????"));
            }
        };
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username)
//                    throws UsernameNotFoundException {
//                Optional<Reader> userDetails = readerRepository.findById(username);
//                return userDetails.orElseThrow(() -> new UsernameNotFoundException("????????? ?????? ??????"));
//            }
//        });
//    }
}
