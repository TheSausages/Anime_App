package anime.app.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration. Uses the Spring Security OAuth2 implementation for checking the tokens. Log in/out and
 * registration is handled using the {@link anime.app.openapi.api.AuthApi} implementation with the corresponding service.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
@Log4j2
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${api.default-path}")
	private String defaultApiPath;

	/**
	 * Configure the HttpSecurity:
	 * <ul>
	 *     <li>List of authenticated and public endpoints in the documentation</li>
	 *     <li>Disable CSRF</li>
	 *     <li>Enable Cors configured using a {@link CorsConfigurationSource} bean</li>
	 *     <li>All paths not explicitly marked as permitted for all require authentication</li>
	 *     <li>Any Requests need authentication</li>
	 * </ul>
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
					.disable()
				.cors()
					.and()
				.httpBasic()
					.disable()
				.headers()
					.frameOptions().sameOrigin()
					.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.authorizeRequests()
					.mvcMatchers(addDefaultPath("/auth/login"), addDefaultPath("/auth/register"), addDefaultPath("/anime/**"), addDefaultPath("/user/**")).permitAll()
					.mvcMatchers(addDefaultPath("/**")).authenticated()
					.anyRequest().authenticated()
					.and()
				.oauth2ResourceServer()
					.jwt()
		;
	}

	/**
	 * Default CORS configuration allowing both static content and local server
	 * @return CORS configuration
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8180", "http://localhost:3000", "http://192.168.0.245:3000", "http://localhost:8080", "http://192.168.0.245:8080"));
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "OPTIONS", "DELETE", "PUT"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION, HttpHeaders.ACCEPT_LANGUAGE));
		configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * Small Method to add the default API path to a selected string
	 * @param path The path
	 * @return The path with the default API path added
	 */
	private String addDefaultPath(String path) { return defaultApiPath + path; }
}