package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll() // do not user in production!
                            .antMatchers("/", "/webjars/**", "/resources/**").permitAll()
                            .antMatchers("/beers/find", "/beers*").hasAnyRole("USER", "CUSTOMER", "ADMIN")
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries").hasRole("USER")
                            .mvcMatchers("brewery/breweries").hasAnyRole("USER", "ADMIN")
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").hasAnyRole("USER", "CUSTOMER", "ADMIN");
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and().csrf().disable();
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
