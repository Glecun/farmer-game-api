package com.glecun.farmergameapi;

import com.glecun.farmergameapi.domain.CheckUserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final CheckUserCredentials checkUserCredentials;

   public WebSecurityConfig(@Autowired CheckUserCredentials checkUserCredentials) {
      this.checkUserCredentials = checkUserCredentials;
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
              .cors().and().csrf().disable()
              .httpBasic().and()
              .authorizeRequests()
                  .antMatchers( "/").permitAll()
                  .antMatchers( HttpMethod.GET, "/status").permitAll()
                  .antMatchers( HttpMethod.POST, "/sign-up").permitAll()
              .anyRequest()
                  .authenticated();
   }

   @Bean
   CorsConfigurationSource corsConfigurationSource() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
      return source;
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(checkUserCredentials).passwordEncoder(new BCryptPasswordEncoder());
   }

}
