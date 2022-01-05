package com.moscow.neighbours.backend.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.moscow.neighbours.backend.jwt.JwtAuthEntryPoint;
import com.moscow.neighbours.backend.jwt.auth.JwtAuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // MARK: - property

    private final UserDetailsService userDetailsService;

    private Algorithm algorithm;

    @Autowired
    private JwtAuthEntryPoint authEntryPoint;

    @Value("${app.config.webconfig.password-encoder-qualifier}")
    private String passwordEncoderQualifier;

    private PasswordEncoder passwordEncoder;

    // MARK: - methods

    @Autowired
    public void setAlgorithm(ApplicationContext context) {
        algorithm = context.getBean("algorithm", Algorithm.class);
    }

    @Autowired
    public void setPasswordEncoder(ApplicationContext context) {
        passwordEncoder = context.getBean(passwordEncoderQualifier, PasswordEncoder.class);
    }

    @Bean
    JwtAuthTokenFilter authorizationFilter() {
        return new JwtAuthTokenFilter(algorithm);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
