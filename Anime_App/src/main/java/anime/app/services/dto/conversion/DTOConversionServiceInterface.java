package anime.app.services.dto.conversion;

import anime.app.entities.AuthenticationToken;
import anime.app.entities.database.anime.Anime;
import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.anime.Review;
import anime.app.entities.database.forum.Thread;
import anime.app.entities.database.forum.*;
import anime.app.entities.database.user.Achievement;
import anime.app.entities.database.user.User;
import anime.app.openapi.model.*;

import java.util.UUID;

/**
 * Interface for a DTO Conversion Service. Each implementation must use this interface.
 * <p>
 * Conversion methods for:
 * <ul>
 *     <li>Complete DTO objects (ex. {@link anime.app.openapi.model.CompleteUserDTO}) should be named <i>convertToDTO</i></li>
 *     <li>Simple DTO objects (ex. {@link anime.app.openapi.model.SimpleUserDTO}) should be named <i>convertToSimpleDTO</i></li>
 * </ul>
 */
public interface DTOConversionServiceInterface {

	//User

	/** Convert a {@link AuthenticationToken} into it's DTO */
	AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken);

	/** Convert an {@link Achievement} into it's DTO */
	AchievementDTO convertToDTO(Achievement achievement);

	/** Convert an {@link User} into it's simple DTO */
	SimpleUserDTO convertToSimpleDTO(User user);

	/** Convert an {@link User} into it's DTO */
	CompleteUserDTO convertToDTO(User user);

	//Anime

	/** Convert an {@link Anime} into it's DTO */
	LocalAnimeInformationDTO convertToDTO(Anime anime);

	/** Convert an {@link AnimeUserInfo} into it's DTO */
	LocalUserAnimeInformationDTO convertToDTO(AnimeUserInfo animeUserInfo);

	/** Convert an {@link AnimeUserInfo.AnimeUserInfoId} into it's DTO */
	LocalUserAnimeInformationDTOId convertToDTO(AnimeUserInfo.AnimeUserInfoId id);

	/** Convert an {@link anime.app.entities.database.anime.Review} into it's DTO */
	LocalSimpleAnimeReviewDTO convertToSimpleDTO(Review review);

	/** Convert an {@link anime.app.entities.database.anime.Review} into it's DTO */
	LocalDetailedAnimeReviewDTO convertToDTO(Review review);

	//Forum

	/** Convert a {@link ForumCategory} into it's DTO */
	ForumCategoryDTO convertToDTO(ForumCategory category);

	/** Convert a {@link Tag} into it's DTO */
	TagDTO convertToDTO(Tag tag);

	/** Convert an {@link Post} into it's DTO that contains additional information */
	PostWithThreadInformationDTO convertToDTOWithAdditionalInfo(Post post, UUID userId);

	/** Convert an {@link Post} into it's simple DTO */
	SimplePostDTO convertToSimpleDTO(Post post, UUID userId);

	/** Convert an {@link PostUserStatus} into it's DTO */
	PostUserStatusDTO convertToDTO(PostUserStatus status);

	/** Convert an {@link PostUserStatus.PostUserStatusId} into it's DTO */
	PostUserStatusDTOId convertToDTO(PostUserStatus.PostUserStatusId statusId);

	/** Convert an {@link Thread} into it's simple DTO */
	SimpleThreadDTO convertToSimpleDTO(Thread thread, UUID userId);

	/** Convert an {@link anime.app.entities.database.forum.ThreadUserStatus.ThreadUserStatusId} into it's DTO */
	ThreadUserStatusDTOId convertToDTO(ThreadUserStatus.ThreadUserStatusId id);

	/** Convert an {@link ThreadUserStatus} into it's DTO */
	ThreadUserStatusDTO convertToDTO(ThreadUserStatus status);
}
