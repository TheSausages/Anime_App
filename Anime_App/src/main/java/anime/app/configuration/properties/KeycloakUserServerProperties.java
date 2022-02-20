package anime.app.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties used for connecting to the user server realm in keycloak. It uses the <i>keycloakrealm.userserver</i> prefix
 */
@ConfigurationProperties(prefix = "keycloakrealm.userserver")
@ConstructorBinding
@Getter
public class KeycloakUserServerProperties {
	private final String realm;
	private final String client;
	private final String url;
	private final String clientSecret;
	private final String scope;
	private final KeycloakGrantType grantType;

	public KeycloakUserServerProperties(String realm,
	                                    String client,
	                                    String url,
	                                    String clientSecret,
	                                    String scope,
	                                    KeycloakGrantType grantType) {
		this.realm = realm;
		this.client = client;
		this.url = url;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.grantType = grantType;
	}

	@ConstructorBinding
	@Getter
	public static class KeycloakGrantType {
		private final String login;
		private final String refresh;

		public KeycloakGrantType(String login,
		                         String refresh) {
			this.login = login;
			this.refresh = refresh;
		}
	}
}
