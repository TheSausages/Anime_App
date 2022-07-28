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
import anime.app.services.icon.IconServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link DTOConversionServiceInterface} interface.
 */
@Service
public class DTOConversionService implements DTOConversionServiceInterface {
	private final IconServiceInterface iconService;

	public DTOConversionService(IconServiceInterface iconService) {
		this.iconService = iconService;
	}

	//User

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken) {
		Objects.requireNonNull(authenticationToken, "Authorization token cannot be null");

		return AuthenticationTokenDTO.builder()
				.accessToken(authenticationToken.getAccess_token())
				.expiresIn(authenticationToken.getExpires_in())
				.refreshToken(authenticationToken.getRefresh_token())
				.tokenType(authenticationToken.getToken_type())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AchievementDTO convertToDTO(Achievement achievement) {
		Objects.requireNonNull(achievement, "Achievement cannot be null");

		long nrOfUsers = Objects.isNull(achievement.getUsers()) ? 0 : achievement.getUsers().size();

		return AchievementDTO.builder()
				.id((long) achievement.getId())
				.name(achievement.getName())
				.description(achievement.getDescription())
				.icon(iconService.getAchievementIcon(achievement))
				.points(achievement.getPoints())
				.nrOfUsersPosses(nrOfUsers)
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimpleUserDTO convertToSimpleDTO(User user) {
		Objects.requireNonNull(user, "User cannot be null");

		return SimpleUserDTO.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.userType(SimpleUserDTO.UserTypeEnum.SIMPLEUSER)
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompleteUserDTO convertToDTO(User user) {
		Objects.requireNonNull(user, "User cannot be null");

		return CompleteUserDTO.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.userType(SimpleUserDTO.UserTypeEnum.COMPLETEUSER)
				.threads(user.getThreads()
						.stream()
						.limit(6)
						.map(x -> convertToSimpleDTO(x, user.getId()))
						.collect(Collectors.toSet())
				)
				.achievements(user.getAchievements()
						.stream()
						.limit(6)
						.map(this::convertToDTO)
						.collect(Collectors.toSet())
				)
				.animeInfos(user.getAnimeUserInfo()
						.stream()
						.limit(6)
						.map(this::convertToDTO)
						.collect(Collectors.toSet())
				)
				.posts(user.getPosts()
						.stream()
						.limit(6)
						.map(pos -> convertToDTOWithAdditionalInfo(pos, user.getId()))
						.collect(Collectors.toSet())
				)
				.build();
	}

	//Anime

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalAnimeInformationDTO convertToDTO(Anime anime) {
		Objects.requireNonNull(anime, "Anime cannot be null");

		return LocalAnimeInformationDTO.builder()
				.animeId((long) anime.getId())
				.title(anime.getTitle())
				.averageScore(anime.getAverageScore())
				.nrOfFavourites(anime.getNrOfFavourites())
				.nrOfReviews(anime.getNrOfReviews())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalUserAnimeInformationDTO convertToDTO(AnimeUserInfo animeUserInfo) {
		Objects.requireNonNull(animeUserInfo, "Anime cannot be null");

		LocalSimpleAnimeReviewDTO reviewDTO = Objects.isNull(animeUserInfo.getReview()) ? null : convertToSimpleDTO(animeUserInfo.getReview());

		return LocalUserAnimeInformationDTO.builder()
				.id(convertToDTO(animeUserInfo.getId()))
				.status(LocalUserAnimeInformationDTO.StatusEnum.fromValue(animeUserInfo.getStatus().name()))
				.watchStartDate(animeUserInfo.getWatchStart())
				.watchEndDate(animeUserInfo.getWatchEnd())
				.nrOfEpisodesSeen(animeUserInfo.getEpisodesSeen())
				.isFavourite(animeUserInfo.isFavourite())
				.modification(animeUserInfo.getModification())
				.grade(animeUserInfo.getGrade())
				.review(reviewDTO)
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalUserAnimeInformationDTOId convertToDTO(AnimeUserInfo.AnimeUserInfoId id) {
		Objects.requireNonNull(id, "Anime User Info Id cannot be null");

		return LocalUserAnimeInformationDTOId.builder()
				.animeId((long) id.getAnime().getId())
				.userId(id.getUser().getId())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalSimpleAnimeReviewDTO convertToSimpleDTO(Review review) {
		Objects.requireNonNull(review, "Review cannot be null");

		return LocalSimpleAnimeReviewDTO.builder()
				.id((long) review.getId())
				.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
				.title(review.getTitle())
				.text(review.getText())
				.nrOfUpvotes(review.getNrOfPlus())
				.nrOfDownvotes(review.getNrOfMinus())
				.nrOfHelpfull(review.getNrOfHelpful())
				.modification(review.getModification())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalDetailedAnimeReviewDTO convertToDTO(Review review) {
		Objects.requireNonNull(review, "Review cannot be null");

		return LocalDetailedAnimeReviewDTO.builder()
				.id((long) review.getId())
				.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.DETAILEDREVIEW)
				.title(review.getTitle())
				.text(review.getText())
				.nrOfUpvotes(review.getNrOfPlus())
				.nrOfDownvotes(review.getNrOfMinus())
				.nrOfHelpfull(review.getNrOfHelpful())
				.modification(review.getModification())
				.animeId((long) review.getAnimeUserInfo().getId().getAnime().getId())
				.animeTitle(review.getAnimeUserInfo().getId().getAnime().getTitle())
				.build();
	}

	//Forum

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumCategoryDTO convertToDTO(ForumCategory category) {
		Objects.requireNonNull(category, "Category cannot be null");

		return ForumCategoryDTO.builder()
				.id((long) category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagDTO convertToDTO(Tag tag) {
		Objects.requireNonNull(tag, "Tag cannot be null");

		return TagDTO.builder()
				.id((long) tag.getId())
				.name(tag.getName())
				.importance(TagImportance.fromValue(tag.getImportance().name()))
				.color(tag.getColor())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PostWithThreadInformationDTO convertToDTOWithAdditionalInfo(Post post, UUID userId) {
		Objects.requireNonNull(post, "Post cannot be null");
		Objects.requireNonNull(userId, "User id cannot be null");

		return PostWithThreadInformationDTO.builder()
				.post(convertToSimpleDTO(post, userId))
				.threadInformation(PostWithThreadInformationDTOThreadInformation.builder()
						.id((long) post.getThread().getId())
						.title(post.getThread().getTitle())
						.build())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimplePostDTO convertToSimpleDTO(Post post, UUID userId) {
		Objects.requireNonNull(post, "Post cannot be null");
		Objects.requireNonNull(userId, "User id cannot be null");

		PostUserStatusDTO status = Objects.nonNull(post.getPostUserStatuses()) ?
				post.getPostUserStatuses()
						.stream()
						.filter(st -> st.getId().getUser().getId().equals(userId))
						.findFirst()
						.map(this::convertToDTO)
						.orElse(PostUserStatusDTO.builder()
								.id(PostUserStatusDTOId.builder()
										.postId((long) post.getId())
										.userId(userId)
										.build())
								.liked(false)
								.disliked(false)
								.reported(false)
								.build())
				:
				PostUserStatusDTO.builder()
						.id(PostUserStatusDTOId.builder()
								.postId((long) post.getId())
								.userId(userId)
								.build())
						.liked(false)
						.disliked(false)
						.reported(false)
						.build();

		return SimplePostDTO.builder()
				.id((long) post.getId())
				.postComplexityType(SimplePostDTO.PostComplexityTypeEnum.SIMPLEPOST)
				.title(post.getTitle())
				.text(post.getText())
				.creation(post.getCreation().toLocalDate())
				.modification(post.getModification().toLocalDate())
				.creator(convertToSimpleDTO(post.getCreator()))
				.nrOfLikes(post.getNrOfPlus())
				.nrOfDislikes(post.getNrOfMinus())
				.status(PostStatus.fromValue(post.getStatus().name()))
				.userStatus(status)
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PostUserStatusDTO convertToDTO(PostUserStatus status) {
		Objects.requireNonNull(status, "Status cannot be null");

		return PostUserStatusDTO.builder()
				.id(convertToDTO(status.getId()))
				.liked(status.isLiked())
				.disliked(status.isDisliked())
				.reported(status.isReported())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PostUserStatusDTOId convertToDTO(PostUserStatus.PostUserStatusId statusId) {
		Objects.requireNonNull(statusId, "Status Id cannot be null");

		return PostUserStatusDTOId.builder()
				.userId(statusId.getUser().getId())
				.postId((long) statusId.getPost().getId())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimpleThreadDTO convertToSimpleDTO(Thread thread, UUID userId) {
		Objects.requireNonNull(thread, "Thread cannot be null");
		Objects.requireNonNull(userId, "User id cannot be null");

		ThreadUserStatusDTO status = Objects.nonNull(thread.getThreadUserStatuses()) ?
				thread.getThreadUserStatuses()
						.stream()
						.filter(st -> st.getId().getUser().getId().equals(userId))
						.findFirst()
						.map(this::convertToDTO)
						.orElse(ThreadUserStatusDTO.builder()
								.id(ThreadUserStatusDTOId.builder()
										.threadId((long) thread.getId())
										.userId(userId)
										.build())
								.watching(false)
								.blocked(false)
								.build())
				:
				ThreadUserStatusDTO.builder()
						.id(ThreadUserStatusDTOId.builder()
								.threadId((long) thread.getId())
								.userId(userId)
								.build())
						.watching(false)
						.blocked(false)
						.build();

		return SimpleThreadDTO.builder()
				.id((long) thread.getId())
				.title(thread.getTitle())
				.threadComplexityType(SimpleThreadDTO.ThreadComplexityTypeEnum.SIMPLETHREAD)
				.nrOfPosts(thread.getNrOfPosts())
				.status(ThreadStatus.fromValue(thread.getStatus().name()))
				.creation(thread.getCreation())
				.modification(thread.getModification())
				.creator(convertToSimpleDTO(thread.getCreator()))
				.category(convertToDTO(thread.getCategory()))
				.tags(thread.getTags().stream().map(this::convertToDTO).collect(Collectors.toList()))
				.userStatus(status)
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ThreadUserStatusDTOId convertToDTO(ThreadUserStatus.ThreadUserStatusId id) {
		Objects.requireNonNull(id, "ThreadUserStatusId cannot be null");

		return ThreadUserStatusDTOId.builder()
				.userId(id.getUser().getId())
				.threadId((long) id.getThread().getId())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ThreadUserStatusDTO convertToDTO(ThreadUserStatus status) {
		Objects.requireNonNull(status, "Status cannot be null");

		return ThreadUserStatusDTO.builder()
				.id(convertToDTO(status.getId()))
				.blocked(status.isBlocking())
				.watching(status.isWatching())
				.build();
	}
}
