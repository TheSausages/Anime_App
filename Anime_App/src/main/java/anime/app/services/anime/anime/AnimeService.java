package anime.app.services.anime.anime;

import anime.app.entities.database.anime.Anime;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.openapi.model.LocalAnimeInformationDTO;
import anime.app.repositories.anime.AnimeRepository;
import anime.app.services.dto.conversion.DTOConversionServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation for the {@link AnimeServiceInterface} interface.
 */
@Service
@Log4j2
public class AnimeService implements AnimeServiceInterface {
    private final AnimeRepository animeRepository;
    private final DTOConversionServiceInterface conversionService;

    @Autowired
    AnimeService(AnimeRepository animeRepository, DTOConversionServiceInterface conversionService) {
        this.animeRepository = animeRepository;
        this.conversionService = conversionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(label = "Get an anime by it's id", readOnly = true)
    public Anime getAnimeById(long id) {
        log.info("Get anime with id: {}", id);

        return animeRepository.findById((int) id)
                .orElseThrow(() -> NotFoundException.builder()
                        .userMessageTranslationKey("anime.no-anime-with-id")
                        .translationParameter(id)
                        .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(label = "Get anime by it's id and transform it into the DTO", readOnly = true)
    public LocalAnimeInformationDTO getAnimeDTOById(long id) {
        return conversionService.convertToDTO(getAnimeById(id));
    }
}
