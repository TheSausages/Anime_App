package anime.app.configuration.beans;

import anime.app.configuration.properties.AnilistProperties;
import anime.app.configuration.properties.KeycloakUserServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configure web client instances:
 * <ul>
 *     <li>The Anilist web client uses {@link anime.app.configuration.properties.AnilistProperties}</li>
 *     <li>The Keycloak web client uses {@link KeycloakUserServerProperties}</li>
 * </ul>
 * Besides a basic URL, no other settings are changed.
 */
@Configuration
public class WebClientsConfiguration {
	public final static String anilistWebClientBeanName = "anilistWebClient";
	public final static String keycloakWebClientBeanName = "keycloakWebClient";

	private final AnilistProperties anilistProperties;
	private final KeycloakUserServerProperties keycloakProperties;

	WebClientsConfiguration(AnilistProperties anilistProperties,
	                        KeycloakUserServerProperties keycloakProperties) {
		this.anilistProperties = anilistProperties;
		this.keycloakProperties = keycloakProperties;
	}

	@Bean(name = anilistWebClientBeanName)
	WebClient anilistWebClient() {
		return WebClient.create(anilistProperties.getApiUrl());
	}

	@Bean(name = keycloakWebClientBeanName)
	WebClient keycloakWebClient() {
		return WebClient.create(keycloakProperties.getUrl());
	}
}
