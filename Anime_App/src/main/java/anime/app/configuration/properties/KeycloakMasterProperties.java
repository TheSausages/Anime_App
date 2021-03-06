package anime.app.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties used for connecting to the master realm in keycloak. It uses the <i>keycloakrealm.master</i> prefix
 */
@ConfigurationProperties(prefix = "keycloakrealm.master")
@ConstructorBinding
@Getter
public class KeycloakMasterProperties {
	private final String url;
	private final String realm;
	private final String username;
	private final String password;
	private final String client;
	private final String clientSecret;

	public KeycloakMasterProperties(String url,
	                                String realm,
	                                String username,
	                                String password,
	                                String client,
	                                String clientSecret) {
		this.url = url;
		this.realm = realm;
		this.username = username;
		this.password = password;
		this.client = client;
		this.clientSecret = clientSecret;
	}
}
