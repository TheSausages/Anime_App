package anime.app.services.dto.deconversion;

import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.anime.Review;
import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTOId;
import anime.app.services.anime.anime.AnimeServiceInterface;
import anime.app.services.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Default implementation for the {@link DTODeconversionServiceInterface} interface.
 */
@Service
public class DTODeconversionService implements DTODeconversionServiceInterface {
    private final AnimeServiceInterface animeService;
    private final UserServiceInterface userService;

    @Autowired
    DTODeconversionService(AnimeServiceInterface animeService, UserServiceInterface userService) {
        this.animeService = animeService;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     *
     * This implementation also adds the ID.
     */
    @Override
    public AnimeUserInfo convertFromDTO(LocalUserAnimeInformationDTO animeUserInfoDTO) {
        AnimeUserInfo info = AnimeUserInfo.builder()
                .id(convertFromDTO(animeUserInfoDTO.getId()))
                .status(AnimeUserInfo.AnimeUserStatus.valueOf(animeUserInfoDTO.getStatus().name()))
                .watchStart(animeUserInfoDTO.getWatchStartDate())
                .watchEnd(animeUserInfoDTO.getWatchEndDate())
                .episodesSeen(animeUserInfoDTO.getNrOfEpisodesSeen())
                .favourite(animeUserInfoDTO.getIsFavourite())
                .grade(animeUserInfoDTO.getGrade())
                .modification(animeUserInfoDTO.getModification())
                .build();

        if (Objects.nonNull(animeUserInfoDTO.getReview())) {
            Review review = convertFromDTO(animeUserInfoDTO.getReview());

            review.setAnimeUserInfo(info);
            info.setReview(review);
        }

        return info;
    }

    @Override
    public AnimeUserInfo.AnimeUserInfoId convertFromDTO(LocalUserAnimeInformationDTOId localUserAnimeInformationDTOId) {
        return AnimeUserInfo.AnimeUserInfoId.builder()
                .user(userService.getUser(localUserAnimeInformationDTOId.getUserId()))
                .anime(animeService.getAnimeById(localUserAnimeInformationDTOId.getAnimeId()))
                .build();
    }

    /**
     * {@inheritDoc}
     *
     * This implementation doesn't add a reference to an AnimeUserInfo.
     */
    @Override
    public Review convertFromDTO(LocalSimpleAnimeReviewDTO reviewDTO) {
        return Review.builder()
                .id(Math.toIntExact(reviewDTO.getId()))
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .nrOfHelpful(reviewDTO.getNrOfHelpfull())
                .nrOfPlus(reviewDTO.getNrOfUpvotes())
                .nrOfMinus(reviewDTO.getNrOfDownvotes())
                .modification(reviewDTO.getModification())
                .build();
    }
}
