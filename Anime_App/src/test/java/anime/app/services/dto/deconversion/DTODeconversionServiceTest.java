package anime.app.services.dto.deconversion;

import anime.app.entities.database.anime.Anime;
import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.anime.Review;
import anime.app.entities.database.user.User;
import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTOId;
import anime.app.services.anime.anime.AnimeService;
import anime.app.services.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class DTODeconversionServiceTest {

    @Mock
    AnimeService animeService;

    @Mock
    UserService userService;

    @InjectMocks
    DTODeconversionService dtoDeconversionService;

    @Nested
    @DisplayName("Local User Anime Info")
    class LocalUserAnimeInfoAndItsIdTest {
        @Test
        void convertFromDTO_NullInfo_ThrowException() {
            //given
            LocalUserAnimeInformationDTO info = null;

            //when
            Exception exception = assertThrows(NullPointerException.class, () ->
                    dtoDeconversionService.convertFromDTO(info)
            );

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NullPointerException.class)
            ));
        }

        @Test
        void convertToDTO_CorrectLocalAnimeUserInfoWithBasicInfo_ReturnDTO() {
            //given
            long animeId = 1L;
            Anime anime = Anime.builder()
                    .id(Math.toIntExact(animeId))
                    .title("Some Title")
                    .build();
            doReturn(anime).when(animeService).getAnimeById(animeId);

            UUID userId = UUID.randomUUID();
            User user = User.builder()
                    .id(userId)
                    .username("Some Username")
                    .build();
            doReturn(user).when(userService).getUser(userId);

            LocalUserAnimeInformationDTO info = LocalUserAnimeInformationDTO.builder()
                    .id(LocalUserAnimeInformationDTOId.builder()
                            .animeId(animeId)
                            .userId(userId)
                            .build())
                    .status(LocalUserAnimeInformationDTO.StatusEnum.NO_STATUS)
                    .isFavourite(false)
                    .build();

            AnimeUserInfo expectedAnimeUserInfo = AnimeUserInfo.builder()
                    .id(AnimeUserInfo.AnimeUserInfoId.builder()
                            .user(user)
                            .anime(anime)
                            .build())
                    .status(AnimeUserInfo.AnimeUserStatus.NO_STATUS)
                    .favourite(false)
                    .build();

            //when
            AnimeUserInfo actualAnimeUserInfo = dtoDeconversionService.convertFromDTO(info);

            //then
            assertThat(actualAnimeUserInfo, allOf(
                    notNullValue(),
                    instanceOf(AnimeUserInfo.class),
                    equalTo(expectedAnimeUserInfo)
            ));
        }
    }

    @Nested
    @DisplayName("Local Anime Review")
    class LocalSimpleAnimeReviewTest {
        @Test
        void convertFromDTO_NullReview_ThrowException() {
            //given
            LocalSimpleAnimeReviewDTO info = null;

            //when
            Exception exception = assertThrows(NullPointerException.class, () ->
                    dtoDeconversionService.convertFromDTO(info)
            );

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NullPointerException.class)
            ));
        }

        @Test
        void convertToDTO_CorrectLocalSimpleAnimeReview_ReturnDTO() {
            //given
            long reviewId = 1;
            LocalSimpleAnimeReviewDTO dto = LocalSimpleAnimeReviewDTO.builder()
                    .id(reviewId)
                    .title("Title")
                    .text("Text")
                    .build();

            Review expectedReview = Review.builder()
                    .id((int) reviewId)
                    .title("Title")
                    .text("Text")
                    .build();

            //when
            Review actualReview = dtoDeconversionService.convertFromDTO(dto);

            //then
            assertThat(actualReview, allOf(
                    notNullValue(),
                    instanceOf(Review.class),
                    equalTo(expectedReview)
            ));
        }
    }
}