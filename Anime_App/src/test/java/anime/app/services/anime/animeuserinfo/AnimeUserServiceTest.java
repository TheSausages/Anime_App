package anime.app.services.anime.animeuserinfo;

import anime.app.entities.database.anime.Anime;
import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.anime.Review;
import anime.app.entities.database.user.User;
import anime.app.exceptions.exceptions.AuthenticationException;
import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTOId;
import anime.app.repositories.anime.AnimeUserInfoRepository;
import anime.app.services.anime.anime.AnimeService;
import anime.app.services.dto.conversion.DTOConversionService;
import anime.app.services.dto.deconversion.DTODeconversionService;
import anime.app.services.icon.IconService;
import anime.app.services.user.UserService;
import anime.app.utils.UserAuthorizationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimeUserServiceTest {
    @Mock
    AnimeUserInfoRepository animeUserInfoRepository;

    @Mock
    AnimeService animeService;

    @Mock
    UserService userService;

    @Mock
    IconService iconService;

    @Mock
    DTODeconversionService dtoDeconversionService;

    @Spy
    DTOConversionService dtoConversionService = new DTOConversionService(iconService);

    @InjectMocks
    AnimeUserService animeUserService;

    @Nested
    @DisplayName("Get Current User Anime Info")
    class GetCurrentUserAnimeInfoTest {
        @Test
        void getCurrentUserAnimeInfo_UserNotLoggedIn_ThrowException() {
            //given
            long animeId = 1;

            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::checkIfLoggedInOrThrow).thenThrow(AuthenticationException.builder()
                        .userMessageTranslationKey("authentication.authentication-needed")
                        .build());

                //when
                AuthenticationException exception = assertThrows(AuthenticationException.class, () -> animeUserService.getCurrentUserAnimeInfo(animeId));

                //then
                assertThat(exception, allOf(
                        notNullValue(),
                        instanceOf(AuthenticationException.class)
                ));
            }
        }

        @Test
        void getCurrentUserAnimeInfo_UserLoggedIn_ReturnCorrectData() {
            //given
            long animeId = 1;
            Anime anime = Anime.builder().id((int) animeId).build();
            doReturn(anime).when(animeService).getAnimeById(animeId);

            UUID userId = UUID.randomUUID();
            User user = User.builder()
                    .id(userId)
                    .username("Some Username")
                    .build();
            doReturn(user).when(userService).getCurrentUser();

            AnimeUserInfo info = AnimeUserInfo.builder()
                    .id(AnimeUserInfo.AnimeUserInfoId.builder()
                            .user(user)
                            .anime(anime)
                            .build())
                    .status(AnimeUserInfo.AnimeUserStatus.NO_STATUS)
                    .episodesSeen(0)
                    .favourite(false)
                    .build();
            doReturn(Optional.of(info)).when(animeUserInfoRepository).findById(any());

            LocalUserAnimeInformationDTO expectedResult = LocalUserAnimeInformationDTO.builder()
                    .id(LocalUserAnimeInformationDTOId.builder()
                            .userId(userId)
                            .animeId(animeId)
                            .build())
                    .status(LocalUserAnimeInformationDTO.StatusEnum.NO_STATUS)
                    .isFavourite(false)
                    .nrOfEpisodesSeen(0)
                    .build();

            //when
            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {

                //when
                LocalUserAnimeInformationDTO actualResult = animeUserService.getCurrentUserAnimeInfo(animeId);

                //then
                assertThat(actualResult, allOf(
                        notNullValue(),
                        instanceOf(LocalUserAnimeInformationDTO.class),
                        equalTo(expectedResult)
                ));

                verify(animeUserInfoRepository, times(1)).findById(any());
            }
        }
    }

    @Nested
    @DisplayName("Update current user anime info")
    class UpdateCurrentUserAnimeInfoTest {
        @Test
        void updateCurrentUserAnimeInfo_UserNotLoggedIn_ThrowException() {
            //given
            LocalUserAnimeInformationDTO updatingInfoDTO = LocalUserAnimeInformationDTO.builder()
                    .id(LocalUserAnimeInformationDTOId.builder()
                            .animeId(1L)
                            .userId(UUID.randomUUID())
                            .build())
                    .status(LocalUserAnimeInformationDTO.StatusEnum.COMPLETED)
                    .nrOfEpisodesSeen(12)
                    .isFavourite(true)
                    .grade(8)
                    .modification(LocalDateTime.now())
                    .watchStartDate(LocalDate.now().minus(5, ChronoUnit.DAYS))
                    .watchEndDate(LocalDate.now())
                    .build();

            //when
            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::checkIfLoggedInOrThrow).thenThrow(AuthenticationException.builder()
                        .userMessageTranslationKey("authentication.authentication-needed")
                        .build());

                //when
                AuthenticationException exception = assertThrows(AuthenticationException.class, () -> animeUserService.updateCurrentUserAnimeInfo(updatingInfoDTO));

                //then
                assertThat(exception, allOf(
                        notNullValue(),
                        instanceOf(AuthenticationException.class)
                ));
            }
        }

        @Test
        void updateCurrentUserAnimeInfo_CorrectUpdatingInfoPreviousInfoDoesNotExistsUserLoggedIn_ReturnCorrectData() {
            //given
            long animeId = 1;
            Anime anime = Anime.builder()
                    .id((int) animeId)
                    .title("Some Title")
                    .averageEpisodeLength(20)
                    .build();
            doReturn(anime).when(animeService).getAnimeById(animeId);

            UUID userId = UUID.randomUUID();
            User user = User.builder()
                    .id(userId)
                    .username("Some Username")
                    .build();
            doReturn(user).when(userService).getCurrentUser();

            doReturn(Optional.empty()).when(animeUserInfoRepository).findById(any());

            LocalUserAnimeInformationDTOId updatingInfoDTOId = LocalUserAnimeInformationDTOId.builder()
                    .animeId(animeId)
                    .userId(userId)
                    .build();
            LocalUserAnimeInformationDTO updatingInfoDTO = LocalUserAnimeInformationDTO.builder()
                    .id(updatingInfoDTOId)
                    .status(LocalUserAnimeInformationDTO.StatusEnum.COMPLETED)
                    .nrOfEpisodesSeen(12)
                    .isFavourite(true)
                    .grade(8)
                    .modification(LocalDateTime.now())
                    .watchStartDate(LocalDate.now().minus(5, ChronoUnit.DAYS))
                    .watchEndDate(LocalDate.now())
                    .build();
            AnimeUserInfo.AnimeUserInfoId updatingInfoId = AnimeUserInfo.AnimeUserInfoId.builder()
                    .user(user)
                    .anime(anime)
                    .build();
            AnimeUserInfo updatingInfo = AnimeUserInfo.builder()
                    .id(updatingInfoId)
                    .status(AnimeUserInfo.AnimeUserStatus.COMPLETED)
                    .episodesSeen(12)
                    .favourite(true)
                    .grade(8)
                    .modification(LocalDateTime.now())
                    .watchStart(LocalDate.now().minus(5, ChronoUnit.DAYS))
                    .watchEnd(LocalDate.now())
                    .build();
            doReturn(updatingInfo).when(animeUserInfoRepository).saveAndFlush(updatingInfo);
            doReturn(updatingInfo).when(dtoDeconversionService).convertFromDTO(updatingInfoDTO);
            doReturn(updatingInfoId).when(dtoDeconversionService).convertFromDTO(updatingInfoDTOId);

            //when
            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::getIdOfCurrentUser).thenReturn(userId);

                //when
                LocalUserAnimeInformationDTO actualResult = animeUserService.updateCurrentUserAnimeInfo(updatingInfoDTO);

                //then
                assertThat(actualResult, allOf(
                        notNullValue(),
                        instanceOf(LocalUserAnimeInformationDTO.class),
                        samePropertyValuesAs(updatingInfoDTO, "modification")
                ));

                assertThat(user.getWatchTime(), equalTo(240));
                assertThat(anime.getAverageScore(), equalTo(8d));
                assertThat(anime.getNrOfScores(), equalTo(1));
                assertThat(anime.getNrOfFavourites(), equalTo(1));

                verify(animeUserInfoRepository, times(1)).findById(any());
            }
        }

        @Test
        void updateCurrentUserAnimeInfo_CorrectUpdatingInfoPreviousInfoExistsUserLoggedIn_ReturnCorrectData() {
            //given
            long animeId = 1;
            Anime anime = Anime.builder()
                    .id((int) animeId)
                    .title("Some Title")
                    .averageEpisodeLength(20)
                    .averageScore(6)
                    .nrOfScores(1)
                    .build();
            doReturn(anime).when(animeService).getAnimeById(animeId);

            UUID userId = UUID.randomUUID();
            User user = User.builder()
                    .id(userId)
                    .username("Some Username")
                    .watchTime(100)
                    .build();
            doReturn(user).when(userService).getCurrentUser();

            AnimeUserInfo existingInfo = AnimeUserInfo.builder()
                    .id(AnimeUserInfo.AnimeUserInfoId.builder()
                            .anime(anime)
                            .user(user)
                            .build())
                    .status(AnimeUserInfo.AnimeUserStatus.WATCHING)
                    .episodesSeen(5)
                    .watchStart(LocalDate.now().minus(5, ChronoUnit.DAYS))
                    .modification(LocalDateTime.now().minus(5, ChronoUnit.DAYS))
                    .build();
            doReturn(Optional.of(existingInfo)).when(animeUserInfoRepository).findById(any());

            LocalUserAnimeInformationDTOId updatingInfoDTOId = LocalUserAnimeInformationDTOId.builder()
                    .animeId(animeId)
                    .userId(userId)
                    .build();
            LocalUserAnimeInformationDTO updatingInfoDTO = LocalUserAnimeInformationDTO.builder()
                    .id(updatingInfoDTOId)
                    .status(LocalUserAnimeInformationDTO.StatusEnum.COMPLETED)
                    .nrOfEpisodesSeen(12)
                    .isFavourite(true)
                    .grade(8)
                    .modification(LocalDateTime.now())
                    .watchStartDate(LocalDate.now().minus(5, ChronoUnit.DAYS))
                    .watchEndDate(LocalDate.now())
                    .build();
            AnimeUserInfo.AnimeUserInfoId updatingInfoId = AnimeUserInfo.AnimeUserInfoId.builder()
                    .user(user)
                    .anime(anime)
                    .build();
            AnimeUserInfo updatingInfo = AnimeUserInfo.builder()
                    .id(updatingInfoId)
                    .status(AnimeUserInfo.AnimeUserStatus.COMPLETED)
                    .episodesSeen(12)
                    .favourite(true)
                    .grade(8)
                    .modification(LocalDateTime.now())
                    .watchStart(LocalDate.now().minus(5, ChronoUnit.DAYS))
                    .watchEnd(LocalDate.now())
                    .build();
            doReturn(updatingInfo).when(animeUserInfoRepository).saveAndFlush(updatingInfo);
            doReturn(updatingInfo).when(dtoDeconversionService).convertFromDTO(updatingInfoDTO);
            doReturn(updatingInfoId).when(dtoDeconversionService).convertFromDTO(updatingInfoDTOId);

            //when
            try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
                mockedUtils.when(UserAuthorizationUtils::getIdOfCurrentUser).thenReturn(userId);

                //when
                LocalUserAnimeInformationDTO actualResult = animeUserService.updateCurrentUserAnimeInfo(updatingInfoDTO);

                //then
                assertThat(actualResult, allOf(
                        notNullValue(),
                        instanceOf(LocalUserAnimeInformationDTO.class),
                        equalTo(updatingInfoDTO)
                ));

                assertThat(user.getWatchTime(), equalTo(240));
                assertThat(anime.getAverageScore(), equalTo(7d));
                assertThat(anime.getNrOfScores(), equalTo(2));
                assertThat(anime.getNrOfFavourites(), equalTo(1));

                verify(animeUserInfoRepository, times(1)).findById(any());
            }
        }
    }

    @Nested
    @DisplayName("5 Latest reviews for Anime")
    class LatestReviewsForAnime {
        @Test
        void get5LatestReviewsForAnime_EmptyList_ReturnCorrectData() {
            //given
            long animeId = 1;
            doReturn(Collections.emptyList()).when(animeUserInfoRepository).findTop5ByReviewIsNotNullAndId_Anime_IdIsOrderByReview_ModificationDesc((int) animeId);

            //when
            List<LocalSimpleAnimeReviewDTO> result = animeUserService.get5LatestReviewsForAnime(animeId);

            //then
            assertThat(result, allOf(
                    notNullValue(),
                    emptyCollectionOf(LocalSimpleAnimeReviewDTO.class)
            ));
        }

        @Test
        void get5LatestReviewsForAnime_ReturnSingleElement_ReturnCorrectData() {
            //given
            long animeId = 1;
            LocalDateTime time = LocalDateTime.now();
            doReturn(List.of(
                    AnimeUserInfo.builder()
                            .id(AnimeUserInfo.AnimeUserInfoId.builder()
                                    .anime(Anime.builder()
                                            .id(1)
                                            .title("Some Title")
                                            .build())
                                    .user(User.builder()
                                            .id(UUID.randomUUID())
                                            .username("Username")
                                            .build())
                                    .build())
                            .review(Review.builder()
                                    .id(1)
                                    .title("Some Title")
                                    .text("Some Text")
                                    .modification(time)
                                    .build())
                            .build()
            )).when(animeUserInfoRepository).findTop5ByReviewIsNotNullAndId_Anime_IdIsOrderByReview_ModificationDesc((int) animeId);

            LocalSimpleAnimeReviewDTO expectedResult = LocalSimpleAnimeReviewDTO.builder()
                    .id(1L)
                    .title("Some Title")
                    .text("Some Text")
                    .modification(time)
                    .nrOfDownvotes(0)
                    .nrOfHelpfull(0)
                    .nrOfUpvotes(0)
                    .reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
                    .build();

            //when
            List<LocalSimpleAnimeReviewDTO> result = animeUserService.get5LatestReviewsForAnime(animeId);

            //then
            assertThat(result, allOf(
                    notNullValue(),
                    containsInRelativeOrder(expectedResult)
            ));
        }

        @Test
        void get5LatestReviewsForAnime_ReturnManyElements_ReturnCorrectData() {
            //given
            long animeId = 1;
            LocalDateTime firstTime = LocalDateTime.now();
            LocalDateTime secondTime = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
            List<AnimeUserInfo> returnedFromDB = new java.util.ArrayList<>(List.of(
                    AnimeUserInfo.builder()
                            .id(AnimeUserInfo.AnimeUserInfoId.builder()
                                    .anime(Anime.builder()
                                            .id(1)
                                            .title("Some Title")
                                            .build())
                                    .user(User.builder()
                                            .id(UUID.randomUUID())
                                            .username("Username")
                                            .build())
                                    .build())
                            .review(Review.builder()
                                    .id(1)
                                    .title("Some Title")
                                    .text("Some Text")
                                    .modification(firstTime)
                                    .build())
                            .build(),
                    AnimeUserInfo.builder()
                            .id(AnimeUserInfo.AnimeUserInfoId.builder()
                                    .anime(Anime.builder()
                                            .id(1)
                                            .title("Some Title")
                                            .build())
                                    .user(User.builder()
                                            .id(UUID.randomUUID())
                                            .username("Username2")
                                            .build())
                                    .build())
                            .review(Review.builder()
                                    .id(2)
                                    .title("Some Title 2")
                                    .text("Some Text 2")
                                    .modification(secondTime)
                                    .build())
                            .build()
            ));
            returnedFromDB.sort(Comparator.comparing((AnimeUserInfo i) -> i.getReview().getModification()).reversed());

            doReturn(returnedFromDB).when(animeUserInfoRepository).findTop5ByReviewIsNotNullAndId_Anime_IdIsOrderByReview_ModificationDesc((int) animeId);

            LocalSimpleAnimeReviewDTO expectedFirstResult = LocalSimpleAnimeReviewDTO.builder()
                    .id(1L)
                    .title("Some Title")
                    .text("Some Text")
                    .modification(firstTime)
                    .nrOfDownvotes(0)
                    .nrOfHelpfull(0)
                    .nrOfUpvotes(0)
                    .reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
                    .build();
            LocalSimpleAnimeReviewDTO expectedSecondResult = LocalSimpleAnimeReviewDTO.builder()
                    .id(2L)
                    .title("Some Title 2")
                    .text("Some Text 2")
                    .modification(secondTime)
                    .nrOfDownvotes(0)
                    .nrOfHelpfull(0)
                    .nrOfUpvotes(0)
                    .reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
                    .build();

            //when
            List<LocalSimpleAnimeReviewDTO> result = animeUserService.get5LatestReviewsForAnime(animeId);

            //then
            assertThat(result, allOf(
                    notNullValue(),
                    containsInRelativeOrder(expectedFirstResult, expectedSecondResult)
            ));
        }
    }
}