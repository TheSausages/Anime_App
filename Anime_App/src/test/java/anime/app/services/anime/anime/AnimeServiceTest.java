package anime.app.services.anime.anime;

import anime.app.entities.database.anime.Anime;
import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.user.User;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.openapi.model.LocalAnimeInformationDTO;
import anime.app.repositories.anime.AnimeRepository;
import anime.app.services.dto.conversion.DTOConversionService;
import anime.app.services.icon.IconService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static anime.app.utils.AdditionalVerificationModes.once;
import static anime.app.utils.AdditionalVerificationModes.twice;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @Mock
    AnimeRepository animeRepository;

    @Mock
    IconService iconService;

    @Spy
    DTOConversionService conversionService = new DTOConversionService(iconService);

    @InjectMocks
    AnimeService animeService;

    @Nested
    @DisplayName("Tests for getting an Anime by it's id")
    class GetUserByIdTest {
        @Test
        void getAnimeById_NoSuchAnime_ThrowException() {
            //given
            int id = 1;
            doReturn(Optional.empty()).when(animeRepository).findById(id);

            //when
            NotFoundException exception = assertThrows(NotFoundException.class, () -> animeService.getAnimeById(id));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NotFoundException.class)
            ));

            assertThat(exception.getMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));

            assertThat(exception.getLogMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));
        }

        @Test
        void getAnimeById_AnimeExists_ReturnCorrectData() {
            //given
            int id = 1;
            Anime expectedAnime = Anime.builder()
                    .id(id)
                    .nrOfScores(15)
                    .nrOfFavourites(15)
                    .averageScore(15)
                    .nrOfReviews(15)
                    .averageEpisodeLength(25)
                    .build();
            doReturn(Optional.of(expectedAnime)).when(animeRepository).findById(id);

            //when
            Anime actualAnime = animeService.getAnimeById(id);

            //then
            assertThat(actualAnime, allOf(
                    notNullValue(),
                    instanceOf(Anime.class),
                    equalTo(expectedAnime)
            ));
        }
    }

    @Nested
    @DisplayName("Get Anime DTO by it's id")
    class GetAnimeDTOByIDTest {
        @Test
        void getAnimeDTOById_NoSuchAnime_ThrowException() {
            //given
            int id = 1;
            doReturn(Optional.empty()).when(animeRepository).findById(id);

            //when
            NotFoundException exception = assertThrows(NotFoundException.class, () -> animeService.getAnimeDTOById(id));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NotFoundException.class)
            ));

            assertThat(exception.getMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));

            assertThat(exception.getLogMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));

            verify(conversionService, never()).convertToDTO(ArgumentMatchers.any(Anime.class));
        }

        @Test
        void getAnimeDTOById_AnimeExists_ReturnCorrectData() {
            //given
            int id = 1;
            Anime anime = Anime.builder()
                    .id(id)
                    .nrOfScores(15)
                    .nrOfFavourites(15)
                    .averageScore(15)
                    .nrOfReviews(15)
                    .averageEpisodeLength(25)
                    .build();
            doReturn(Optional.of(anime)).when(animeRepository).findById(id);
            LocalAnimeInformationDTO expectedDTO = conversionService.convertToDTO(anime);

            //when
            LocalAnimeInformationDTO actualDTO = animeService.getAnimeDTOById(id);

            //then
            assertThat(actualDTO, allOf(
                    notNullValue(),
                    instanceOf(LocalAnimeInformationDTO.class),
                    equalTo(expectedDTO)
            ));

            // One time in given section, once in test
            verify(conversionService, twice()).convertToDTO(anime);
        }
    }

    @Nested
    @DisplayName("Save a new Anime instance if it doesn't exist")
    class UpdateDataForAnimeTest {
        @Test
        void updateDataForAnime_DataIsCurrent_DontSavePassedObject() {
            //given
            Anime anime = Anime.builder()
                    .id(1)
                    .title("Some Title")
                    .averageEpisodeLength(5)
                    .build();
            doReturn(true).when(animeRepository).existsByAnilistData(anime);

            //when
            animeService.updateDataForAnime(anime);

            //then
            verify(animeRepository, never()).save(any());
        }

        @Test
        void updateDataForAnime_DataIsNotCurrentButExists_SavePassedObject() {
            //given
            int animeId = 1;
            Anime existing = Anime.builder()
                    .id(animeId)
                    .title("Some Title 2")
                    .averageEpisodeLength(20)
                    .build();
            Anime checking = Anime.builder()
                    .id(animeId)
                    .title("Some Title")
                    .averageEpisodeLength(10)
                    .build();
            doReturn(false).when(animeRepository).existsByAnilistData(checking);
            doReturn(Optional.of(existing)).when(animeRepository).findById(animeId);

            User user = User.builder()
                    .id(UUID.randomUUID())
                    .username("Username")
                    .watchTime(40)
                    .build();
            AnimeUserInfo info = AnimeUserInfo.builder()
                    .id(AnimeUserInfo.AnimeUserInfoId.builder()
                            .user(user)
                            .anime(existing)
                            .build())
                    .episodesSeen(2)
                    .build();
            existing.setAnimeUserInfos(Set.of(info));

            ArgumentCaptor<Anime> animeCaptor = ArgumentCaptor.forClass(Anime.class);

            //when
            animeService.updateDataForAnime(checking);

            //then
            verify(animeRepository, once()).save(animeCaptor.capture());

            assertThat(animeCaptor.getAllValues(), iterableWithSize(1));

            Anime savedAnime = animeCaptor.getValue();
            assertThat(savedAnime, allOf(
                    notNullValue(),
                    equalTo(checking)
            ));

            assertThat(user.getWatchTime(), equalTo(20));
        }

        @Test
        void updateDataForAnime_DataIsNotCurrentAndDoesntExists_SavePassedObject() {
            //given
            int animeId = 1;
            Anime checking = Anime.builder()
                    .id(animeId)
                    .title("Some Title")
                    .averageEpisodeLength(10)
                    .build();
            doReturn(false).when(animeRepository).existsByAnilistData(checking);
            doReturn(Optional.empty()).when(animeRepository).findById(animeId);

            ArgumentCaptor<Anime> animeCaptor = ArgumentCaptor.forClass(Anime.class);

            //when
            animeService.updateDataForAnime(checking);

            //then
            verify(animeRepository, once()).save(animeCaptor.capture());

            assertThat(animeCaptor.getAllValues(), iterableWithSize(1));

            Anime savedAnime = animeCaptor.getValue();
            assertThat(savedAnime, allOf(
                    notNullValue(),
                    equalTo(checking)
            ));
        }
    }
}