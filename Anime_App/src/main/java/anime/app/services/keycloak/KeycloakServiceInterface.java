package anime.app.services.keycloak;

import anime.app.configuration.beans.WebClientsConfiguration;
import anime.app.openapi.model.AuthenticationTokenDTO;
import anime.app.openapi.model.LoginCredentialsDTO;
import anime.app.openapi.model.RefreshTokenDTO;
import anime.app.openapi.model.RegistrationBodyDTO;

/**
 * Interface for a Keycloak Service. Used to interact with the keycloak server. Each implementation must use this interface.
 * It's recommended to use {@link WebClientsConfiguration#keycloakWebClient()} in order to connect to keycloak.
 *
 * <p>
 *
 * The <i>request</i> parameter for each method is used to retrieve the {@link org.springframework.http.HttpHeaders#ACCEPT_LANGUAGE}
 * header from the request for a {@link anime.app.services.i18n.I18nServiceInterface} implementation to use.
 */
public interface KeycloakServiceInterface {
	/**
	 * Log in a given user in keycloak using its credentials.
	 * @param credentials Credentials used to log in a user.
	 * @return The tokens used to authenticate a user. Also has information for refreshing the access token.
	 */
	AuthenticationTokenDTO login(LoginCredentialsDTO credentials);

	/**
	 * Log out a user from keycloak.
	 * @param logoutRequestBody Information needed to log out a user from keycloak.
	 * @param accessToken The access token used to authenticate the user when connecting to the endpoint. Used for logging out.
	 */
	void logout(RefreshTokenDTO logoutRequestBody, String accessToken);

	/**
	 * Register a user in keycloak.
	 * @param registrationBody Data used to register a user
	 * @return The tokens used to authenticate a user. Also has information for refreshing the authentication token.
	 */
	AuthenticationTokenDTO register(RegistrationBodyDTO registrationBody);

	/**
	 * Refresh the authentication token and send the new token back.
	 * @param refreshTokenDTO Data used to refresh the access token
	 * @return New token for the user.
	 */
	AuthenticationTokenDTO refreshTokens(RefreshTokenDTO refreshTokenDTO);
}
