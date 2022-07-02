package anime.app.services.user;

import anime.app.entities.database.user.User;
import anime.app.openapi.model.CompleteUserDTO;

import java.util.UUID;

/**
 * Interface for a User Service. Each implementation must use this interface.
 */
public interface UserServiceInterface {
    /**
     * Get complete information about the currently authenticated User. Can only be used when a user is authenticated.
     * @return Complete authenticated user information
     */
    CompleteUserDTO getCurrentUserInformation();

    /**
     * Get complete information about a given User.
     * @param userId Id of the searched user
     * @return Complete user information
     */
    CompleteUserDTO getUserInformationById(UUID userId);

    /**
     * Get the currently authenticated user.
     * @return Current user
     */
    User getCurrentUser();

    /**
     * Get the user by the user id.
     * @param userId Id of the searched user
     * @return Current user
     */
    User getUser(UUID userId);

    /**
     * Save a new user to the non-keycloak database.
     * @param user the user to be saved
     * @return The saved user.
     */
    User saveUser(User user);
}
