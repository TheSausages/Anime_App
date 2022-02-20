package anime.app.controllers;

import anime.app.openapi.api.AuthApi;
import anime.app.openapi.model.AuthenticationTokenDTO;
import anime.app.openapi.model.LoginCredentialsDTO;
import anime.app.openapi.model.RefreshTokenDTO;
import anime.app.openapi.model.RegistrationBodyDTO;
import anime.app.services.keycloak.KeycloakServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.validation.Validator;

/**
 * Controller for all endpoints connected to authenticating the user.
 */
@RestControllerWithBasePath
public class AuthController implements AuthApi {
	private final Validator validator;
	private final KeycloakServiceInterface keycloakService;

	@Autowired
	AuthController(Validator validator, KeycloakServiceInterface keycloakService) {
		this.validator = validator;
		this.keycloakService = keycloakService;
	}

	@Override
	public ResponseEntity<AuthenticationTokenDTO> login(LoginCredentialsDTO loginCredentialsDTO) {
		AuthenticationTokenDTO authenticationToken = keycloakService.login(loginCredentialsDTO);
		validator.validate(authenticationToken);

		return ResponseEntity.ok(authenticationToken);
	}

	@Override
	public ResponseEntity<Void> logout(String authorization, RefreshTokenDTO refreshTokenDTO) {
		keycloakService.logout(refreshTokenDTO, authorization);

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<AuthenticationTokenDTO> refreshAccessToken(RefreshTokenDTO refreshTokenDTO) {
		AuthenticationTokenDTO authenticationToken = keycloakService.refreshTokens(refreshTokenDTO);
		validator.validate(authenticationToken);

		return ResponseEntity.ok(authenticationToken);
	}

	@Override
	public ResponseEntity<AuthenticationTokenDTO> registerUser(RegistrationBodyDTO registrationBodyDTO) {
		AuthenticationTokenDTO authenticationToken = keycloakService.register(registrationBodyDTO);
		validator.validate(authenticationToken);

		return ResponseEntity.ok(authenticationToken);
	}
}
