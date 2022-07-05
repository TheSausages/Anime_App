package anime.app.utils;

import anime.app.exceptions.exceptions.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.UUID;

/**
 * Small utility class containing methods connected to User Authorization.
 */
public interface UserAuthorizationUtils {
    /**
     * Check if the request is from an authenticated user or anonymous.
     * @return true - the user is authenticated, false - it's anonymous
     */
    static boolean checkIfLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Objects.nonNull(authentication) && authentication.isAuthenticated() && !"anonymousUser".equalsIgnoreCase(authentication.getName());
    }

    /**
     * Check if the request is from an authenticated user or anonymous.
     * @throws AuthenticationException When the user isn't logged in
     */
    static void checkIfLoggedInOrThrow() {
        if (!checkIfLoggedIn())
            throw AuthenticationException.builder()
                    .userMessageTranslationKey("authentication.authentication-needed")
                    .build();
    }

    /**
     * Return the id of the currently authenticated user - for keycloak it will be the ID (or UUID) of the User on the Keycloak Server.
     * If the user is not logged in, this method will throw an exception.
     * @see #checkIfLoggedInOrThrow()
     * @return Id of the currently authenticated user in form of UUID
     */
    static UUID getIdOfCurrentUser() {
        checkIfLoggedInOrThrow();

        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
