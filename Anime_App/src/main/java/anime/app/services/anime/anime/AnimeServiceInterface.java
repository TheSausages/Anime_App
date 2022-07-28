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

    /**
     * If a given Anime object doesn't exist, save it to the database.
     * If it exists, check if it needs to be updated, and do it if necessary.
     * The following fields are used to check for updates: ID, Average Episode Length, Title.
     * @param anime Object to be checked and/or saved
     */
    void updateDataForAnime(Anime anime);
}
