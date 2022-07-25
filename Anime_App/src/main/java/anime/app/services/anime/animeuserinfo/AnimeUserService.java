package anime.app.services.anime.animeuserinfo;

import anime.app.entities.database.anime.Anime;
import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.user.User;
import anime.app.exceptions.exceptions.DataConflictException;
import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import anime.app.repositories.anime.AnimeUserInfoRepository;
import anime.app.services.anime.anime.AnimeServiceInterface;
import anime.app.services.dto.conversion.DTOConversionServiceInterface;
import anime.app.services.dto.deconversion.DTODeconversionServiceInterface;
import anime.app.services.user.UserServiceInterface;
import anime.app.utils.UserAuthorizationUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link AnimeUserServiceInterface} interface.
 */
@Log4j2
@Service
public class AnimeUserService implements AnimeUserServiceInterface {
    private final DTOConversionServiceInterface dtoConversionService;
    private final DTODeconversionServiceInterface dtoDeconversionService;
    private final AnimeUserInfoRepository animeUserInfoRepository;
    private final AnimeServiceInterface animeService;
    private final UserServiceInterface userService;

    @Autowired
    AnimeUserService(
            DTOConversionServiceInterface dtoConversionService,
            DTODeconversionServiceInterface dtoDeconversionService,
            AnimeUserInfoRepository animeUserInfoRepository,
            AnimeServiceInterface animeService,
            UserServiceInterface userService
    ) {
        this.dtoConversionService = dtoConversionService;
        this.dtoDeconversionService = dtoDeconversionService;
        this.animeUserInfoRepository = animeUserInfoRepository;
        this.animeService = animeService;
        this.userService = userService;
    }

    @Override
    @Transactional(label = "Get user anime info for the current user using tha anime's id", readOnly = true)
    public LocalUserAnimeInformationDTO getCurrentUserAnimeInfo(long animeId) {
        UserAuthorizationUtils.checkIfLoggedInOrThrow();

        AnimeUserInfo.AnimeUserInfoId id = AnimeUserInfo.AnimeUserInfoId
                .builder()
                .user(userService.getCurrentUser())
                .anime(animeService.getAnimeById(animeId))
                .build();

        log.info("Get user anime information for a user with id: " + animeId);

        return dtoConversionService.convertToDTO(
                animeUserInfoRepository.findById(id)
                        .orElseGet(() -> AnimeUserInfo.builder()
                                .id(id)
                                .modification(LocalDateTime.now())
                                .build())
        );
    }

    @Override
    @Transactional(label = "Update anime user info for the current user")
    public LocalUserAnimeInformationDTO updateCurrentUserAnimeInfo(LocalUserAnimeInformationDTO animeUserInfoDTO) {
        UserAuthorizationUtils.checkIfLoggedInOrThrow();

        User currentUser = userService.getCurrentUser();
        if (Objects.isNull(animeUserInfoDTO.getId().getUserId()) || !currentUser.getId().equals(animeUserInfoDTO.getId().getUserId())) {
            throw DataConflictException.builder()
                    .userMessageTranslationKey("anime.tried-to-update-another-users-data")
                    .build();
        }

        AnimeUserInfo.AnimeUserInfoId infoId = dtoDeconversionService.convertFromDTO(animeUserInfoDTO.getId());
        Anime anime = animeService.getAnimeById(animeUserInfoDTO.getId().getAnimeId());

        AnimeUserInfo updatedAnimeUserInfo = animeUserInfoRepository.findById(infoId)
                .map(currentAnimeUserInfo -> {
                    int oldWatchTime = currentAnimeUserInfo.getEpisodesSeen() * anime.getAverageEpisodeLength();
                    int newWatchTime = animeUserInfoDTO.getNrOfEpisodesSeen() * anime.getAverageEpisodeLength();
                    currentUser.setWatchTime(currentUser.getWatchTime() + newWatchTime - oldWatchTime);

                    if (Objects.nonNull(currentAnimeUserInfo.getGrade())) {
                        anime.updateAverageForUpdatedScore(animeUserInfoDTO.getGrade(), currentAnimeUserInfo.getGrade());
                    } else {
                        anime.updateAverageForNewScore(animeUserInfoDTO.getGrade());
                    }

                    if (currentAnimeUserInfo.isFavourite() != animeUserInfoDTO.getIsFavourite()) {
                        if (currentAnimeUserInfo.isFavourite()) {
                            anime.decrementFavourites();
                        } else {
                            anime.incrementFavourites();
                        }
                    }

                    return dtoDeconversionService.convertFromDTO(animeUserInfoDTO);
                })
                .orElseGet(() -> {
                    int newWatchTime = animeUserInfoDTO.getNrOfEpisodesSeen() * anime.getAverageEpisodeLength();
                    currentUser.setWatchTime(currentUser.getWatchTime() + newWatchTime);

                    anime.updateAverageForNewScore(animeUserInfoDTO.getGrade());

                    if (animeUserInfoDTO.getIsFavourite()) {
                        anime.incrementFavourites();
                    }

                    return dtoDeconversionService.convertFromDTO(animeUserInfoDTO);
                });

        // SaveAndFlush to get updated modification dates
        AnimeUserInfo upd = animeUserInfoRepository.saveAndFlush(updatedAnimeUserInfo);

        return dtoConversionService.convertToDTO(upd);
    }

    @Override
    @Transactional(label = "Get 5 latest anime reviews using tha anime's id", readOnly = true)
    public List<LocalSimpleAnimeReviewDTO> get5LatestReviewsForAnime(long animeId) {
        return animeUserInfoRepository.findTop5ByReviewIsNotNullAndId_Anime_IdIsOrderByReview_ModificationDesc((int) animeId)
                .stream()
                .map(AnimeUserInfo::getReview)
                .map(dtoConversionService::convertToSimpleDTO)
                .collect(Collectors.toList());
    }
}
