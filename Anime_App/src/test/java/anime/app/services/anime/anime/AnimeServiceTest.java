package anime.app.services.anime.anime;

import anime.app.entities.database.anime.Anime;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.openapi.model.LocalAnimeInformationDTO;
import anime.app.repositories.anime.AnimeRepository;
import anime.app.services.dto.conversion.DTOConversionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @Mock
    AnimeRepository animeRepository;

    @Spy
    DTOConversionService conversionService = new DTOConversionService();

    @InjectMocks
    AnimeService animeService;

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
    void getAnimeById_AnimeExists_ThrowException() {
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
    }

    @Test
    void getAnimeDTOById_AnimeExists_ThrowException() {
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
    }
}