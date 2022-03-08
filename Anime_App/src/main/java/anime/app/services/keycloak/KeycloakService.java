package anime.app.services.keycloak;

import anime.app.configuration.properties.KeycloakUserServerProperties;
import anime.app.entities.AuthenticationToken;
import anime.app.exceptions.exceptions.AuthenticationException;
import anime.app.exceptions.exceptions.RegistrationException;
import anime.app.openapi.model.AuthenticationTokenDTO;
import anime.app.openapi.model.LoginCredentialsDTO;
import anime.app.openapi.model.RefreshTokenDTO;
import anime.app.openapi.model.RegistrationBodyDTO;
import anime.app.services.dto.conversion.DTOConversionServiceInterface;
import anime.app.utils.LocaleUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

/**
 * Default implementation for the {@link KeycloakServiceInterface} interface.
 */
@Log4j2
@Service
public class KeycloakService implements KeycloakServiceInterface {
	private final WebClient client;
	private final Keycloak keycloak;
	//private final UserServiceInterface userService;
	private final DTOConversionServiceInterface dtoConversion;
	private final KeycloakUserServerProperties keycloakProperties;

	@Autowired
	KeycloakService(KeycloakUserServerProperties keycloakProperties,
	                @Qualifier("keycloakWebClient") WebClient client,
	                Keycloak keycloak,
	                //UserServiceInterface userService,
	                DTOConversionServiceInterface dtoConversion) {
		this.client = client;
		this.keycloak = keycloak;
		//this.userService = userService;
		this.dtoConversion = dtoConversion;
		this.keycloakProperties = keycloakProperties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationTokenDTO login(LoginCredentialsDTO credentials) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		return client
				.post()
				.uri("/token")
				.headers(httpHeaders -> httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE)))
				.body(BodyInserters
						.fromFormData("client_id", keycloakProperties.getClient())
						.with("client_secret", keycloakProperties.getClientSecret())
						.with("scope", keycloakProperties.getScope())
						.with("username", credentials.getUsername())
						.with("password", credentials.getPassword())
						.with("grant_type", keycloakProperties.getGrantType().getLogin()))
				.retrieve()
				.bodyToMono(AuthenticationToken.class)
				.map(dtoConversion::convertToDTO)
				.doOnSuccess(s -> log.info("Log In was Successful for Username:" + credentials.getUsername()))
				.onErrorMap(throwable ->
						AuthenticationException.builder()
								.userMessageTranslationKey("authentication.login-not-successful")
								.originalLocale(originalLocale)
								.logMessage(String.format("Log in was not successful for User %s", credentials.getUsername()))
								.build())
				.block();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logout(RefreshTokenDTO logoutRequestBody, String accessToken) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		if (StringUtils.isBlank(logoutRequestBody.getRefreshToken()) || StringUtils.isBlank(accessToken)) {
			log.warn("Could not log out - missing information!");

			throw AuthenticationException.builder()
					.userMessageTranslationKey("authentication.credentials-wrong-structure")
					.originalLocale(originalLocale)
					.logMessage("The credentials had wrong structure")
					.build();
		}

		client
				.post()
				.uri("/logout")
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					httpHeaders.set("Authorization", accessToken);
				})
				.body(BodyInserters
						.fromFormData("client_id", keycloakProperties.getClient())
						.with("client_secret", keycloakProperties.getClientSecret())
						.with("refresh_token", logoutRequestBody.getRefreshToken()))
				.retrieve()
				.toBodilessEntity()
				.doOnSuccess(s -> log.info("Logged Out Successfully"))
				.onErrorMap(throwable ->
						AuthenticationException.builder()
								.userMessageTranslationKey("authentication.logout-not-successful")
								.originalLocale(originalLocale)
								.logMessage(String.format("Logout was not successful for user %s", ""))//userService.getUsernameOfCurrentUser()))))
								.build())
				.block();
	}

	/**
	 * {@inheritDoc}
	 *
	 * This implementation also adds user information to the database.
	 */
	@Override
	public AuthenticationTokenDTO register(RegistrationBodyDTO registrationBody) {
		log.info("Attempt registration for user: {}, with email: {}", registrationBody.getUsername(), registrationBody.getEmail());
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		if (!registrationBody.getPassword().equals(registrationBody.getMatchingPassword())) {
			throw AuthenticationException.builder()
					.userMessageTranslationKey("authentication.not-matching-passwords")
					.originalLocale(originalLocale)
					.logMessage(String.format("The Passwords did not match for new user with username %s", registrationBody.getUsername()))
					.build();
		}

		CredentialRepresentation password = new CredentialRepresentation();
		password.setTemporary(false);
		password.setType(CredentialRepresentation.PASSWORD);
		password.setValue(registrationBody.getPassword());

		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setUsername(registrationBody.getUsername());
		user.setEmail(registrationBody.getEmail());
		user.setCredentials(Collections.singletonList(password));

		Response response = keycloak.realm(keycloakProperties.getRealm())
				.users().create(user);

		if (response.getStatus() > 100 && response.getStatus() < 300) {
			log.info("Registration was successful. Attempt login for user: {}", registrationBody.getUsername());

			Optional<UserRepresentation> newUser = keycloak.realm(keycloakProperties.getRealm())
					.users().search(registrationBody.getUsername())
					.stream().filter(userRep -> userRep.getEmail().equalsIgnoreCase(registrationBody.getEmail())).findAny();

			if (newUser.isEmpty()) {
				throw RegistrationException.builder()
						.userMessageTranslationKey("authentication.after-registration-error")
						.originalLocale(originalLocale)
						.logMessage(String.format("User %s was register successfully, but couldn't be saved to the database", registrationBody.getUsername()))
						.build();
			}

			//userService.saveUser(new User(newUser.get().getId(), registrationBody.getUsername()));

			LoginCredentialsDTO login = LoginCredentialsDTO.builder()
					.username(registrationBody.getUsername())
					.password(registrationBody.getPassword())
					.build();

			return login(login);
		}

		if (response.getStatus() == 409) {
			throw RegistrationException.builder()
					.userMessageTranslationKey("authentication.registration-data-taken")
					.originalLocale(originalLocale)
					.logMessage(
							String.format("Data for user was already taken:\n username: %s,\n email: %s",
								registrationBody.getUsername(),
								registrationBody.getEmail()))
					.build();
		}

		throw AuthenticationException.builder()
				.userMessageTranslationKey("authentication.registration-error")
				.originalLocale(originalLocale)
				.logMessage(String.format("Registration was not successful for user %s", registrationBody.getUsername()))
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationTokenDTO refreshTokens(RefreshTokenDTO refreshTokenDTO) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		return client
				.post()
				.uri("/token")
				.headers(httpHeaders -> httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE)))
				.body(BodyInserters
						.fromFormData("client_id", keycloakProperties.getClient())
						.with("client_secret", keycloakProperties.getClientSecret())
						.with("scope", keycloakProperties.getScope())
						.with("refresh_token", refreshTokenDTO.getRefreshToken())
						.with("grant_type", keycloakProperties.getGrantType().getRefresh()))
				.retrieve()
				.bodyToMono(AuthenticationToken.class)
				.map(dtoConversion::convertToDTO)
				.doOnSuccess(s -> log.info("Tokens has been successful refreshed"))
				.onErrorMap(throwable ->
						AuthenticationException.builder()
								.userMessageTranslationKey("authentication.tokens-not-refreshed")
								.originalLocale(originalLocale)
								.logMessage(String.format("The refresh token of user %s did not work", ""))//userService.getUsernameOfCurrentUser())))
								.build())
				.block();
	}
}