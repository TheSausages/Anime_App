package anime.app.services.user;

import anime.app.entities.database.user.User;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.openapi.model.CompleteUserDTO;
import anime.app.repositories.user.UserRepository;
import anime.app.services.dto.conversion.DTOConversionServiceInterface;
import anime.app.utils.UserAuthorizationUtils;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation for the {@link UserServiceInterface} interface.
 */
@Service
@Log4j2
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final DTOConversionServiceInterface dtoConversion;

    @Autowired
    UserService(UserRepository userRepository, DTOConversionServiceInterface dtoConversion) {
        this.userRepository = userRepository;
        this.dtoConversion = dtoConversion;
    }

    @Override
    @Transactional(label = "Get user information using the currently authenticated user", readOnly = true)
    public CompleteUserDTO getCurrentUserInformation() {
        return getUserInformationById(UserAuthorizationUtils.getIdOfCurrentUser());
    }

    @Override
    @Transactional(label = "Get user information using it's id", readOnly = true)
    public CompleteUserDTO getUserInformationById(@NonNull UUID userId) {
        return dtoConversion.convertToDTO(getUser(userId));
    }

    @Override
    @Transactional(label = "Get a user using the currently authenticated user", readOnly = true)
    public User getCurrentUser() {
        return getUser(UserAuthorizationUtils.getIdOfCurrentUser());
    }

    @Override
    @Transactional(label = "Get a user using it's id", readOnly = true)
    public User getUser(@NonNull UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.builder()
                        .userMessageTranslationKey("user.user-not-found")
                        .translationParameter(userId)
                        .logMessage("User with id " + userId + "could not be found in the database")
                        .build());
    }

    @Override
    @Transactional(label = "Get a user using it's id")
    public User saveUser(@NonNull User user) {
        log.info("Save user {} with id {}", user.getUsername(), user.getId());

        return userRepository.save(user);
    }
}
