package xyz.toway.emarket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.toway.emarket.security.JwtFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/graphql/**").permitAll()
                .antMatchers("/image/download/**").permitAll()
                .antMatchers("/security/open").permitAll()
                .antMatchers("/security/login").permitAll()
                .antMatchers("/security/register").permitAll()
                .antMatchers("/image/upload/**").permitAll()  //todo replace with hasRole("ADMIN") or just remove
                .antMatchers("/image/test/**").permitAll()  //todo remove
                .antMatchers("/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .build();
    }
}
