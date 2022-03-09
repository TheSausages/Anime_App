package anime.app.utils;

import anime.app.exceptions.exceptions.AuthenticationException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

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

        return Objects.nonNull(authentication) && authentication.isAuthenticated();
    }

    /**
     * Check if the request is from an authenticated user or anonymous.
     * @throws AuthenticationException When the user isn't logged in
     */
    static void checkIfLoggedInOrThrow() {
        if (!checkIfLoggedIn())
            throw AuthenticationException.builder()
                    .userMessageTranslationKey("authentication.authentication-needed")
                    .originalLocale(LocaleContextHolder.getLocale())
                    .build();
    }

    /**
     * Return the id of the currently authenticated user - for keycloak it will be the ID (or UUID) of the User on the Keycloak Server.
     * If the user is not logged in, this method will throw an exception.
     * @see #checkIfLoggedInOrThrow()
     * @return Id of the currently authenticated user in form of UUID
     */
    static String getIdOfCurrentUser() {
        checkIfLoggedInOrThrow();

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
