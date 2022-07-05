package anime.app.services.dto.deconversion;

import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.anime.Review;
import anime.app.openapi.model.LocalAnimeInformationDTO;
import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTOId;

/**
 * Interface for a DTO Deconversion Service. Each implementation must use this interface.
 */
public interface DTODeconversionServiceInterface {
    /**
     * Deconvert a {@link LocalUserAnimeInformationDTO} to {@link LocalAnimeInformationDTO}.
     * @param animeUserInfoDTO DTO to be deconverted
     * @return A new Info object.
     */
    AnimeUserInfo convertFromDTO(LocalUserAnimeInformationDTO animeUserInfoDTO);

    /**
     * Deconvert a {@link LocalUserAnimeInformationDTOId} to {@link AnimeUserInfo.AnimeUserInfoId}.
     * @param localUserAnimeInformationDTOId DTO to be deconverted
     * @return A new Info object.
     */
    AnimeUserInfo.AnimeUserInfoId convertFromDTO(LocalUserAnimeInformationDTOId localUserAnimeInformationDTOId);

    /**
     * Deconvert a {@link LocalSimpleAnimeReviewDTO} to {@link Review}.
     * @param reviewDTO DTO to be deconverted
     * @return A new Review with the Id added.
     */
    Review convertFromDTO(LocalSimpleAnimeReviewDTO reviewDTO);
}
