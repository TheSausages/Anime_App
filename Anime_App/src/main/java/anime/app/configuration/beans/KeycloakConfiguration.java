package anime.app.configuration.beans;

import anime.app.configuration.properties.KeycloakMasterProperties;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a {@link Keycloak} instance as a bean using {@link anime.app.configuration.properties.KeycloakMasterProperties}.
 */
@Configuration
public class KeycloakConfiguration {
	private final KeycloakMasterProperties keycloakProperties;

	KeycloakConfiguration(KeycloakMasterProperties keycloakProperties) {
		this.keycloakProperties = keycloakProperties;
	}

	@Bean
	Keycloak getKeycloak() {
		return KeycloakBuilder.builder()
				.serverUrl(keycloakProperties.getUrl())
				.realm(keycloakProperties.getRealm())
				.grantType(OAuth2Constants.PASSWORD)
				.username(keycloakProperties.getUsername())
				.password(keycloakProperties.getPassword())
				.clientId(keycloakProperties.getClient())
				.clientSecret(keycloakProperties.getClientSecret())
				.resteasyClient(
						new ResteasyClientBuilder()
								.connectionPoolSize(10)
								.build()
				).build();
	}
}
