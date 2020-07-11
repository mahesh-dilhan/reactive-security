package com.reactive.auth.jwtauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@SpringBootApplication
public class JwtAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationApplication.class, args);
	}


	@Bean
	MapReactiveUserDetailsService mapReactiveUserDetailsService(){
		UserDetails mahesh = User.withUsername("mahesh")
				.password(passwordEncoder().encode("pwd"))
				.roles("USER","ADMIN")
				.build();
		UserDetails shawn = User.withUsername("shawn")
				.password(passwordEncoder().encode("pwd"))
				.roles("USER")
				.build();
		return new MapReactiveUserDetailsService(mahesh,shawn);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	SecurityWebFilterChain springWebFilterChain(
			ServerHttpSecurity http,
			JwtTokenProvider tokenProvider) {
		return http
				.csrf(it -> it.disable())

				/**********************************
				 * BASIC - Allow All Traffic	  *
				 **********************************/
//				.httpBasic()
//				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
//				.and()
//				.authorizeExchange()
//				.anyExchange().permitAll()
//				.and()
//				.build();

				/*******************************************
				 * BASIC - Apply Security for             *
				 * path -person, and restric greet/       *
				 *******************************************/

//				.httpBasic()
//				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
//				.and()
//				.authorizeExchange()
//				.pathMatchers("/person/**").authenticated()
//				.and()
//				.authorizeExchange()
//				.pathMatchers("/greet/**").permitAll()
//				.and()
//				.build();

				/*******************************************
				 * JWT - Authentication                   *
				 * path -person, and restric greet/       *
				 *******************************************/

				.httpBasic(it -> it.disable())
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.authorizeExchange(it -> it
						.pathMatchers("/auth/token").permitAll()
						.anyExchange().authenticated()
				)
				.addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
				.build();
	}



	@Bean
	CorsWebFilter corsWebFilter() {
		var corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("localhost"));
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsWebFilter(source);
	}
	@Bean
	public ReactiveAuthenticationManager reactiveAuthenticationManager(MapReactiveUserDetailsService userDetailsService,
																	   PasswordEncoder passwordEncoder) {
		var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
		authenticationManager.setPasswordEncoder(passwordEncoder);
		return authenticationManager;
	}

}
