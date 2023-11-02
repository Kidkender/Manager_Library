package vn.sparkminds.config;


import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    public SecurityFilterChain securityConfigration(HttpSecurity http) throws Exception {
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(t -> t.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(req -> req.requestMatchers(HttpMethod.POST, "/signup")
                        .permitAll().anyRequest().authenticated())
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.loginPage("/signin").permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // @Bean
    // public SecurityFilterChain securityConfigration(HttpSecurity http) throws Exception {
    // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors()
    // .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/signup")
    // .permitAll().anyRequest().authenticated().and()
    // .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
    // .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
    // .csrf().disable().formLogin(Customizer.withDefaults())
    // .httpBasic(Customizer.withDefaults());
    // return http.build();
    // }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users.username("user").password("password").roles("USER").build();
        UserDetails admin =
                users.username("admin").password("password").roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
