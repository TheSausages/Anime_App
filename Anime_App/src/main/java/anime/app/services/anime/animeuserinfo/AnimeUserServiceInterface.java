package anime.app.services.anime.animeuserinfo;

import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;

import java.util.List;

/**
 * Interface for an Anime User Information Service. Each implementation must use this interface.
 */
public interface AnimeUserServiceInterface {
    /**
     * Get anime user information for the currently authenticated user.
     * @param animeId Od of the Anime for which the information should be found.
     * @return Anime user information.
     */
    LocalUserAnimeInformationDTO getCurrentUserAnimeInfo(long animeId);

    /**
     * Update anime user information for the currently authenticated user and a given anime.
     * @param animeUserInfoDTO The anime user information that should be used for the update. Has information which anime should be updated.
     * @return the updated anime user information
     */
    LocalUserAnimeInformationDTO updateCurrentUserAnimeInfo(LocalUserAnimeInformationDTO animeUserInfoDTO);

    /**
     * Get 4 latest reviews of an Anime.
     * @param animeId Id of the anime for which we want the reviews
     * @return Collection of reviews for an Anime
     */
    List<LocalSimpleAnimeReviewDTO> get5LatestReviewsForAnime(long animeId);
}
