package anime.app.services.anime.anime;

import anime.app.entities.database.anime.Anime;
import anime.app.openapi.model.LocalAnimeInformationDTO;

/**
 * Interface for an Anime Service. Each implementation must use this interface.
 */
public interface AnimeServiceInterface {
    /**
     * Get local Anime information using its id.
     * @param id The Anime id. It's the same id as in Anilist
     * @return Local information on an Anime
     */
    Anime getAnimeById(long id);

    /**
     * Get local Anime information using its id, and then transform it into it's DTO.
     * @param id The Anime id. It's the same id as in Anilist
     * @return Local information on an Anime in DTO form
     */
    LocalAnimeInformationDTO getAnimeDTOById(long id);
}
