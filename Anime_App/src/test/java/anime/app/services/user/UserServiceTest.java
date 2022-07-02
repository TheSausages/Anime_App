package anime.app.services.user;

import anime.app.entities.database.user.User;
import anime.app.exceptions.exceptions.AuthenticationException;
import anime.app.openapi.model.CompleteUserDTO;
import anime.app.repositories.user.UserRepository;
import anime.app.services.dto.conversion.DTOConversionService;
import anime.app.services.icon.IconService;
import anime.app.utils.UserAuthorizationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    IconService iconService;

    @Spy
    DTOConversionService conversionService = new DTOConversionService(iconService);

    @InjectMocks
    UserService userService;

    @Nested
    @DisplayName("Get Current User Information")
    class GetCurrentUserInformationTest {
        @Test
        void getCurrentUserInformation_UserLoggedIn_ReturnCorrectData() {
            //given
            UUID userId = UUID.randomUUID();
            User user = User.builder()
                    .id(userId)
                    .username("SomeUsername")
                    .achievements(emptySet())
                    .animeUserInfo(emptySet())
                    .achievements(emptySet())
                    .posts(emptySet())
                    .threads(emptySet())
                    .build();
            doReturn(Optional.of(user)).when(userRepository).findById(userId);
            CompleteUserDTO expectedDTO = conversionService.convertToDTO(user);


            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::getIdOfCurrentUser).thenReturn(userId);

                //when
                CompleteUserDTO actualDTO = userService.getCurrentUserInformation();

                //then
                assertThat(actualDTO, allOf(
                        notNullValue(),
                        instanceOf(CompleteUserDTO.class),
                        equalTo(expectedDTO)
                ));

                // One time in given section, once in test
                verify(conversionService, times(2)).convertToDTO(user);
            }
        }

        @Test
        void getCurrentUserInformation_UserNotLoggedIn_ThrowException() {
            //given

            //when
            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::getIdOfCurrentUser).thenThrow(
                        AuthenticationException.builder()
                                .userMessageTranslationKey("authentication.authentication-needed")
                                .build());

                //when
                AuthenticationException exception = assertThrows(AuthenticationException.class, () -> userService.getCurrentUserInformation());

                //then
                assertThat(exception, allOf(
                        notNullValue(),
                        instanceOf(AuthenticationException.class)
                ));

                verify(conversionService, times(0)).convertToDTO(ArgumentMatchers.any(User.class));
            }
        }
    }

    @Nested
    @DisplayName("Tests fot getting user information by it's id")
    class GetUserInformationByIdTest {
        @Test
        void getUserInformationById_UserExists_ReturnCorrectData() {
            //given
            UUID userId = UUID.randomUUID();
            User user = User.builder()
                    .id(userId)
                    .username("SomeUsername")
                    .achievements(emptySet())
                    .animeUserInfo(emptySet())
                    .achievements(emptySet())
                    .posts(emptySet())
                    .threads(emptySet())
                    .build();
            doReturn(Optional.of(user)).when(userRepository).findById(userId);
            CompleteUserDTO expectedDTO = conversionService.convertToDTO(user);

            //when
            CompleteUserDTO actualDTO = userService.getUserInformationById(userId);

            //then
            assertThat(actualDTO, allOf(
                    notNullValue(),
                    instanceOf(CompleteUserDTO.class),
                    equalTo(expectedDTO)
            ));

            verify(conversionService, times(2)).convertToDTO(user);
        }

        @Test
        void getUserInformationById_UserDoesntExists_ThrowException() {
            //given
            UUID userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(userId);

            //when
            AuthenticationException exception = assertThrows(AuthenticationException.class, () -> userService.getUserInformationById(userId));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(AuthenticationException.class)
            ));

            verify(conversionService, times(0)).convertToDTO(ArgumentMatchers.any(User.class));
        }

        @Test
        void getUserInformationById_NullUUID_ThrowException() {
            //given
            UUID userId = null;

            //when
            NullPointerException exception = assertThrows(NullPointerException.class, () -> userService.getUserInformationById(userId));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NullPointerException.class)
            ));

            verify(conversionService, times(0)).convertToDTO(ArgumentMatchers.any(User.class));
        }
    }

    @Nested
    @DisplayName("Tests fot getting a current user")
    class GetCurrentUserTest {
        @Test
        void getCurrentUser_UserExists_ReturnCorrectData() {
            //given
            UUID userId = UUID.randomUUID();
            User expectedUser = User.builder()
                    .id(userId)
                    .username("SomeUsername")
                    .achievements(emptySet())
                    .animeUserInfo(emptySet())
                    .achievements(emptySet())
                    .posts(emptySet())
                    .threads(emptySet())
                    .build();
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(userId);

            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::getIdOfCurrentUser).thenReturn(userId);

                //when
                User actualDTO = userService.getCurrentUser();

                //then
                assertThat(actualDTO, allOf(
                        notNullValue(),
                        instanceOf(User.class),
                        equalTo(expectedUser)
                ));
            }
        }

        @Test
        void getCurrentUser_UserNotLoggedIn_ThrowException() {
            //given

            //when
            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::getIdOfCurrentUser).thenThrow(
                        AuthenticationException.builder()
                                .userMessageTranslationKey("authentication.authentication-needed")
                                .build());

                //when
                AuthenticationException exception = assertThrows(AuthenticationException.class, () -> userService.getCurrentUser());

                //then
                assertThat(exception, allOf(
                        notNullValue(),
                        instanceOf(AuthenticationException.class)
                ));
            }
        }
    }

    @Nested
    @DisplayName("Tests fot getting a user by it's id")
    class GetUserByIdTest {
        @Test
        void getUserInformationById_UserExists_ReturnCorrectData() {
            //given
            UUID userId = UUID.randomUUID();
            User expectedUser = User.builder()
                    .id(userId)
                    .username("SomeUsername")
                    .achievements(emptySet())
                    .animeUserInfo(emptySet())
                    .achievements(emptySet())
                    .posts(emptySet())
                    .threads(emptySet())
                    .build();
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(userId);

            //when
            User actualDTO = userService.getUser(userId);

            //then
            assertThat(actualDTO, allOf(
                    notNullValue(),
                    instanceOf(User.class),
                    equalTo(expectedUser)
            ));
        }

        @Test
        void getUserInformationById_UserDoesntExists_ThrowException() {
            //given
            UUID userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(userId);

            //when
            AuthenticationException exception = assertThrows(AuthenticationException.class, () -> userService.getUserInformationById(userId));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(AuthenticationException.class)
            ));
        }

        @Test
        void getUserInformationById_NullUUID_ThrowException() {
            //given
            UUID userId = null;

            //when
            NullPointerException exception = assertThrows(NullPointerException.class, () -> userService.getUserInformationById(userId));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NullPointerException.class)
            ));
        }
    }

    @Nested
    @DisplayName("Test for user saving")
    class SaveUserTest {
        @Test
        void saveUser_NullUser_ThrowException() {
            //given
            User user = null;

            //when
            NullPointerException exception = assertThrows(NullPointerException.class, () -> userService.saveUser(user));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NullPointerException.class)
            ));
        }

        @Test
        void saveUser_CorrectUser_SaveAndReturnCorrectUser() {
            //given
            User expectedUser = User.builder().id(UUID.randomUUID()).username("SomeUsername").build();
            doReturn(expectedUser).when(userRepository).save(expectedUser);

            //when
            User actualUser  = userService.saveUser(expectedUser);

            //then
            assertThat(actualUser, allOf(
                    notNullValue(),
                    instanceOf(User.class),
                    equalTo(expectedUser)
            ));
        }
    }
}