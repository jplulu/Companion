package com.lustermaniacs.companion.models;

import com.lustermaniacs.companion.repository.UserRepository;
import com.lustermaniacs.companion.service.MatchingService;
import com.lustermaniacs.companion.service.UsrDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsrDetailsService userDetailsService;
    @Autowired
    private MatchingService matchingService;
    @Autowired
    private UserRepository userRepository;

    @Component("ValidUserCheck")
    public class ValidUserCheck{
        public boolean hasPermission(String username, String principal) {
            Profile profile = userRepository.findByUsername(principal).getProfile();
            return username.equals(principal) || matchingService.getAllSysmatchUser(username).contains(profile);
        }
    }
    @Bean
    public AuthenticationProvider authProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/register");
    }

}
