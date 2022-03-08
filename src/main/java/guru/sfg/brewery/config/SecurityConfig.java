package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestParamAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestParamAuthFilter restParamAuthFilter(AuthenticationManager authenticationManager) {
        RestParamAuthFilter filter = new RestParamAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(restParamAuthFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll() // do not user in production!
                            .antMatchers("/", "/webjars/**", "/resources/**").permitAll()
                            .antMatchers("/beers/find", "/beers*", "/beers/*").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
        // h2 console
        http.headers().frameOptions().sameOrigin();
    }


    //    @Override
    //    @Bean
    //    protected UserDetailsService userDetailsService() {
    //        UserDetails admin = User.withDefaultPasswordEncoder()
    //                .username("spring")
    //                .password("guru")
    //                .roles("ADMIN")
    //                .build();
    //
    //        UserDetails user = User.withDefaultPasswordEncoder()
    //                .username("user")
    //                .password("password")
    //                .roles("USER")
    //                .build();
    //
    //        return new InMemoryUserDetailsManager(admin, user);
    //    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //    @Override
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    //        auth.inMemoryAuthentication()
    //                .withUser("spring")
    //                .password("{bcrypt}$2a$10$ks2sof1/6HZy2F/2ifvvP.N.Wc8zZu8zc6NtWqEJE06rq4cW4onCa")
    //                .roles("ADMIN")
    //                .and()
    //                .withUser("user")
    //                .password("{sha256}aa8f824cdbdaa9e2c1800f1e22a825bd49518c2f878da75667c255216e5b71cfac466f648e538a92")
    //                .roles("USER");
    //
    //        auth.inMemoryAuthentication().withUser("scott").password("{bcrypt15}$2a$15$8XZT8a6wBke33sDd7taiJuSX63e.N1olpjUL1oOpPgSOlMfkLxoaO").roles("USER");
    //    }
}
