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
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Default implementation for the {@link DTODeconversionServiceInterface} interface.
 */
@Service
public class DTODeconversionService implements DTODeconversionServiceInterface {
    private final AnimeServiceInterface animeService;
    private final UserServiceInterface userService;

    @Autowired
    public DTODeconversionService(AnimeServiceInterface animeService, UserServiceInterface userService) {
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
        Objects.requireNonNull(animeUserInfoDTO, "Anime User Data cannot be null");

        int episodesSeen = Objects.isNull(animeUserInfoDTO.getNrOfEpisodesSeen()) ? 0 : animeUserInfoDTO.getNrOfEpisodesSeen();

        AnimeUserInfo info = AnimeUserInfo.builder()
                .id(convertFromDTO(animeUserInfoDTO.getId()))
                .status(AnimeUserInfo.AnimeUserStatus.valueOf(animeUserInfoDTO.getStatus().name()))
                .watchStart(animeUserInfoDTO.getWatchStartDate())
                .watchEnd(animeUserInfoDTO.getWatchEndDate())
                .episodesSeen(episodesSeen)
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(label = "Get Anime User Info ID using both id's to find the necessery elements", readOnly = true)
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
        Objects.requireNonNull(reviewDTO, "Review cannot be null");

        int nrOfHelpfull = Objects.nonNull(reviewDTO.getNrOfHelpfull()) ? reviewDTO.getNrOfHelpfull() : 0;
        int nrOfDownvotes = Objects.nonNull(reviewDTO.getNrOfDownvotes()) ? reviewDTO.getNrOfDownvotes() : 0;
        int nrOfUpvotes = Objects.nonNull(reviewDTO.getNrOfUpvotes()) ? reviewDTO.getNrOfUpvotes() : 0;

        return Review.builder()
                .id(Math.toIntExact(reviewDTO.getId()))
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .nrOfHelpful(nrOfHelpfull)
                .nrOfPlus(nrOfUpvotes)
                .nrOfMinus(nrOfDownvotes)
                .modification(reviewDTO.getModification())
                .build();
    }
}
