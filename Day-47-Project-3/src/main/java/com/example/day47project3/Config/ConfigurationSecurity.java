package com.example.day47project3.Config;

import com.example.day47project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                .and()

                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/employee/register", "/api/v1/customer/register").permitAll()
                .requestMatchers("/api/v1/employee/update", "/api/v1/employee/delete", "/api/v1/account/activate/{accountId}").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/customer/update", "/api/v1/customer/delete").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/create",
                        "/api/v1/account/get-account/{accountId}", "/api/v1/account/get-my"
                        , "/api/v1/account/delete/{accountId}",
                        "/api/v1/account/deposit/account-{accountId}/amount-{amount}"
                        , "/api/v1/account/withdraw/account-{accountId}/amount-{amount}",
                        "/api/v1/account/transfer/from-{sourceAccountId}/to-{destinationAccountId}/amount-{amount}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/activate/{accountId}"
                        , "/api/v1/user/get-all",
                        "/api/v1/account/ban/{accountId}",
                        "/api/v1/account/get-all",
                        "/api/v1/customer/get-all",
                        "/api/v1/employee/get-all").hasAuthority("ADMIN")
                .anyRequest().authenticated()

                .and()

                .logout().logoutUrl("/api/v1/logout").permitAll()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)

                .and()

                .httpBasic();
        return http.build();

    }
}
