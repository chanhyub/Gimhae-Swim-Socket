package com.alijas.gimhaeswimsocket.security;

import com.alijas.gimhaeswimsocket.exception.JwtAuthenticationEntryPoint;
import com.alijas.gimhaeswimsocket.exception.UnauthorizedExceptionFilter;
import com.alijas.gimhaeswimsocket.exception.handler.JwtAccessDeniedHandler;
import com.alijas.gimhaeswimsocket.security.SecurityConstants;
import com.alijas.gimhaeswimsocket.security.SecurityFilter;
import com.alijas.gimhaeswimsocket.security.SecurityTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
// @EnableMethodSecurity
public class SecurityConfig {

    // TODO :: 오류 뜨면 파스칼 표기법 사용
    @Value("${spring.datasource.driver-class-name}")
    private String springDatasourceDriverClassName;

    private final SecurityTokenProvider provider;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        SecurityFilter filter = new SecurityFilter(provider);
        http
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                        .accessDeniedHandler(new JwtAccessDeniedHandler())
                )
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(
                                        new MvcRequestMatcher(introspector,"/login"),
                                        new MvcRequestMatcher(introspector,"/docs/**")
                                ).permitAll()
                                .anyRequest().hasAnyAuthority("REFEREE", "ADMIN")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .httpBasic(withDefaults())
                .sessionManagement((session) ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource())) // Bean 기본 이름이 corsConfigurationSource
//                .authorizeHttpRequests((authorize) ->
//                        authorize
//                                .requestMatchers(new MvcRequestMatcher(introspector,"/h2-console")).permitAll()
//                                .requestMatchers(new MvcRequestMatcher(introspector,"/login")).permitAll()
//                                .anyRequest().hasAuthority("REFEREE")
//                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new UnauthorizedExceptionFilter(), filter.getClass());

//        if (springDatasourceDriverClassName.equals("org.h2.Driver")) {
//            // h2 관련 옵션
//            http.authorizeHttpRequests(config -> config
////                    .requestMatchers(PathRequest.toH2Console())
//                    .requestMatchers(new MvcRequestMatcher(introspector,"/h2-console/**"))
//                    .permitAll());
//        }
//        http.authorizeHttpRequests(config -> config.anyRequest().permitAll());
        return http.build();
        }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowedOrigins(
            List.of(
                    "http://localhost",
                    "http://localhost:3000/",
                    "http://localhost:8080"
            )
        );

        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader(SecurityConstants.TOKEN_HEADER);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
