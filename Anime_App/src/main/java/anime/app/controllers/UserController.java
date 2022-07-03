package anime.app.controllers;

import anime.app.openapi.api.UserApi;
import anime.app.openapi.model.CompleteUserDTO;
import anime.app.services.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.validation.Validator;
import java.util.UUID;

/**
 * Controller for all endpoints connected to user operations.
 */
@RestControllerWithBasePath
public class UserController implements UserApi {
    private final Validator validator;
    private final UserServiceInterface userService;

    @Autowired
    UserController(Validator validator, UserServiceInterface userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<CompleteUserDTO> getCurrentUserProfile() {
        CompleteUserDTO currUserProfile = userService.getCurrentUserInformation();
        validator.validate(currUserProfile);

        return ResponseEntity.ok(currUserProfile);
    }

    @Override
    public ResponseEntity<CompleteUserDTO> getUserProfileByUserId(UUID userId) {
        CompleteUserDTO userProfile = userService.getUserInformationById(userId);
        validator.validate(userProfile);

        return ResponseEntity.ok(userProfile);
    }
}
