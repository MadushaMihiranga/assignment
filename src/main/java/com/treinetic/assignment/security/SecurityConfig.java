package com.treinetic.assignment.security;

import com.treinetic.assignment.security.filter.CustomAuthenticationFilter;
import com.treinetic.assignment.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.csrf().disable();
        /*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login","/api/v1/user/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(POST,"/api/v1/user").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/v1/user").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PATCH,"/api/v1/user/update/name/**").hasAnyAuthority("ROLE_STUDENT");
        http.authorizeRequests().antMatchers(PATCH,"/api/v1/user/approve/**").hasAnyAuthority("ROLE_ADMIN");*/
      //  http.authorizeRequests().antMatchers("/swagger-ui/**/**","/api-docs").permitAll();
       // http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().anyRequest().permitAll(); //test only
       // http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
