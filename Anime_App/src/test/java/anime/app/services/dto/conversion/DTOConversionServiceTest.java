package anime.app.services.dto.conversion;

import anime.app.entities.database.anime.Anime;
import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.anime.Review;
import anime.app.entities.database.forum.Thread;
import anime.app.entities.database.forum.*;
import anime.app.entities.database.user.Achievement;
import anime.app.entities.database.user.User;
import anime.app.openapi.model.*;
import anime.app.services.icon.IconService;
import anime.app.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class DTOConversionServiceTest {

	@Mock
	IconService iconService;

	@InjectMocks
	DTOConversionService dtoConversionService;

	//User and Auth

	@Nested
	@DisplayName("User and Auth Conversion Tests")
	class UserAuthTests {
		@Nested
		@DisplayName("Authentication Token")
		class AuthenticationTokenTest {
			@Test
			void convertToDTO_NullAuthenticationToken_ThrowException() {
				//given
				anime.app.entities.AuthenticationToken token = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(token)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectAuthenticationToken_ReturnDTO() {
				//given
				anime.app.entities.AuthenticationToken token = anime.app.entities.AuthenticationToken.builder()
						.access_token("accessToken")
						.expires_in(1)
						.token_type("Bearer")
						.refresh_token("refreshToken")
						.build();

				AuthenticationTokenDTO expectedTokenDTO = AuthenticationTokenDTO.builder()
						.accessToken(token.getAccess_token())
						.expiresIn(token.getExpires_in())
						.tokenType(token.getToken_type())
						.refreshToken(token.getRefresh_token())
						.build();

				//when
				AuthenticationTokenDTO actualTokenDTO = dtoConversionService.convertToDTO(token);

				//then
				assertThat(actualTokenDTO, allOf(
						notNullValue(),
						instanceOf(AuthenticationTokenDTO.class),
						equalTo(expectedTokenDTO)
				));
			}
		}

		@Nested
		@DisplayName("Achievement")
		class AchievementTest {
			@Test
			void convertToDTO_NullAchievement_ThrowException() {
				//given
				Achievement achievement = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(achievement)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectAchievementInvalidPath_ReturnDTO() throws IOException {
				//given
				byte[] icon = TestUtils.getIcon("achievements/Default.png");
				Achievement achievement = Achievement.builder()
						.id(1)
						.name("name")
						.description("description")
						.iconPath("achievements/Default.png")
						.points(15)
						.build();
				AchievementDTO expectedDTO = AchievementDTO.builder()
						.id((long) achievement.getId())
						.name(achievement.getName())
						.description(achievement.getDescription())
						.icon(icon)
						.points(achievement.getPoints())
						.nrOfUsersPosses(0L)
						.build();
				doReturn(icon).when(iconService).getAchievementIcon(achievement);

				//when
				AchievementDTO actualDTO = dtoConversionService.convertToDTO(achievement);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(AchievementDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_CorrectAchievementValidPath_ReturnDTO() throws IOException {
				//given
				byte[] icon = TestUtils.getIcon("achievements/NrOfReviews-10.png");
				Achievement achievement = Achievement.builder()
						.id(1)
						.name("name")
						.description("description")
						.iconPath("achievements/NrOfReviews-10.png")
						.points(15)
						.build();
				AchievementDTO expectedDTO = AchievementDTO.builder()
						.id((long) achievement.getId())
						.name(achievement.getName())
						.description(achievement.getDescription())
						.icon(icon)
						.points(achievement.getPoints())
						.nrOfUsersPosses(0L)
						.build();
				doReturn(icon).when(iconService).getAchievementIcon(achievement);

				//when
				AchievementDTO actualDTO = dtoConversionService.convertToDTO(achievement);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(AchievementDTO.class),
						equalTo(expectedDTO)
				));
			}
		}

		@Nested
		@DisplayName("User")
		class UserTest {
			@Test
			void convertToSimpleDTO_NullUser_ThrowException() {
				//given
				User user = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToSimpleDTO(user)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToSimpleDTO_CorrectUser_ThrowException() {
				//given
				User user = User.builder()
						.id(UUID.randomUUID())
						.username("username")
						.build();
				SimpleUserDTO expectedDTO = SimpleUserDTO.builder()
						.userId(user.getId())
						.username(user.getUsername())
						.userType(SimpleUserDTO.UserTypeEnum.SIMPLEUSER)
						.build();

				//when
				SimpleUserDTO actualDTO = dtoConversionService.convertToSimpleDTO(user);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(SimpleUserDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_NullUser_ThrowException() {
				//given
				User user = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(user)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectUser_ThrowException() throws IOException {
				//given
				byte[] icon = TestUtils.getIcon("achievements/NrOfReviews-10.png");
				Achievement achievement = Achievement.builder()
						.id(1)
						.name("name")
						.description("description")
						.iconPath("achievements/NrOfReviews-10.png")
						.points(15)
						.build();
				doReturn(icon).when(iconService).getAchievementIcon(achievement);
				LocalDateTime time = LocalDateTime.now();
				User creator = User.builder()
						.id(UUID.randomUUID())
						.username("creator")
						.build();
				UUID userId = UUID.randomUUID();
				Thread thread = Thread.builder()
						.id(1)
						.title("title")
						.text("text")
						.status(Thread.ThreadStatus.OPEN)
						.nrOfPosts(1)
						.creation(time)
						.modification(time)
						.category(ForumCategory.builder()
								.id(1)
								.name("name")
								.description("description")
								.build())
						.creator(creator)
						.tags(Set.of(Tag.builder()
								.id(1)
								.name("name")
								.importance(Tag.TagImportance.HIGH)
								.color("rgb(0, 183, 255)")
								.build()))
						.threadUserStatuses(Set.of(ThreadUserStatus.builder()
								.id(ThreadUserStatus.ThreadUserStatusId.builder()
										.user(User.builder().id(userId).build())
										.thread(Thread.builder().id(1).build())
										.build())
								.blocking(true)
								.watching(false)
								.build()))
						.build();
				Post post = Post.builder()
						.id(1)
						.title("title")
						.text("text")
						.blocked(false)
						.status(Post.PostStatus.NO_PROBLEM)
						.nrOfPlus(1)
						.nrOfMinus(1)
						.nrOfReports(1)
						.creation(time)
						.modification(time)
						.creator(creator)
						.thread(thread)
						.postUserStatuses(Set.of(
								PostUserStatus.builder()
										.id(PostUserStatus.PostUserStatusId.builder()
												.user(User.builder().id(userId).build())
												.post(Post.builder().id(1).build())
												.build())
										.liked(false)
										.disliked(true)
										.reported(false)
										.build()
						))
						.build();
				AnimeUserInfo info = AnimeUserInfo.builder()
						.id(AnimeUserInfo.AnimeUserInfoId.builder()
								.user(User.builder().id(userId).build())
								.anime(Anime.builder().id(1).build())
								.build())
						.status(AnimeUserInfo.AnimeUserStatus.DROPPED)
						.watchStart(time.toLocalDate())
						.watchEnd(time.toLocalDate())
						.episodesSeen(1)
						.favourite(false)
						.grade(7)
						.modification(time)
						.review(Review.builder()
								.id(1)
								.text("text")
								.nrOfPlus(1)
								.nrOfMinus(1)
								.nrOfHelpful(1)
								.build())
						.build();
				User user = User.builder()
						.id(userId)
						.username("username")
						.threads(Set.of(thread))
						.achievements(Set.of(achievement))
						.posts(Set.of(post))
						.animeUserInfo(Set.of(info))
						.build();
				CompleteUserDTO expectedDTO = CompleteUserDTO.builder()
						.userId(userId)
						.username("username")
						.userType(SimpleUserDTO.UserTypeEnum.COMPLETEUSER)
						.threads(Set.of(dtoConversionService.convertToSimpleDTO(thread, userId)))
						.achievements(Set.of(dtoConversionService.convertToDTO(achievement)))
						.posts(Set.of(dtoConversionService.convertToDTOWithAdditionalInfo(post, userId)))
						.animeInfos(Set.of(dtoConversionService.convertToDTO(info)))
						.build();

				//when
				CompleteUserDTO actualDTO = dtoConversionService.convertToDTO(user);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(CompleteUserDTO.class),
						equalTo(expectedDTO)
				));
			}
		}
	}

	//Anime

	@Nested
	@DisplayName("Anime Conversion test")
	class AnimeConversionTests {
		@Nested
		@DisplayName("Anime")
		class AnimeTest {
			@Test
			void convertToDTO_NullAnime_ThrowException() {
				//given
				Anime anime = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(anime)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectAnime_ReturnDTO() {
				//given
				Anime anime = Anime.builder()
						.id(1)
						.averageScore(25.0)
						.averageEpisodeLength(25)
						.nrOfFavourites(5)
						.nrOfReviews(5)
						.nrOfScores(5)
						.build();

				LocalAnimeInformationDTO expectedDTO = LocalAnimeInformationDTO.builder()
						.animeId((long) anime.getId())
						.nrOfFavourites(anime.getNrOfFavourites())
						.nrOfReviews(anime.getNrOfReviews())
						.averageScore(anime.getAverageScore())
						.build();

				//when
				LocalAnimeInformationDTO actualDTO = dtoConversionService.convertToDTO(anime);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(LocalAnimeInformationDTO.class),
						equalTo(expectedDTO)
				));
			}
		}

		@Nested
		@DisplayName("Anime User Info")
		class AnimeUserInfoTest {
			@Test
			void convertToDTO_NullInfo_ThrowException() {
				//given
				AnimeUserInfo info = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(info)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectInfo_ReturnDTO() {
				//given
				UUID userId = UUID.randomUUID();
				LocalDateTime time = LocalDateTime.now();
				AnimeUserInfo info = AnimeUserInfo.builder()
						.id(AnimeUserInfo.AnimeUserInfoId.builder()
								.anime(Anime.builder().id(1).build())
								.user(User.builder().id(userId).build())
								.build())
						.status(AnimeUserInfo.AnimeUserStatus.DROPPED)
						.watchStart(time.toLocalDate())
						.watchEnd(time.toLocalDate())
						.episodesSeen(0)
						.favourite(false)
						.grade(0)
						.modification(time)
						.build();
				Review review = Review.builder()
						.id(1)
						.title("title")
						.text("text")
						.nrOfHelpful(1)
						.nrOfPlus(1)
						.nrOfMinus(1)
						.animeUserInfo(info)
						.build();
				info.setReview(review);
				LocalUserAnimeInformationDTO expectedDTO = LocalUserAnimeInformationDTO.builder()
						.id(dtoConversionService.convertToDTO(info.getId()))
						.status(LocalUserAnimeInformationDTO.StatusEnum.fromValue(info.getStatus().name()))
						.watchStartDate(info.getWatchStart())
						.watchEndDate(info.getWatchEnd())
						.nrOfEpisodesSeen(info.getEpisodesSeen())
						.isFavourite(info.isFavourite())
						.modification(info.getModification())
						.grade(info.getGrade())
						.review(dtoConversionService.convertToSimpleDTO(info.getReview()))
						.build();

				//when
				LocalUserAnimeInformationDTO actualDTO = dtoConversionService.convertToDTO(info);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(LocalUserAnimeInformationDTO.class),
						equalTo(expectedDTO)
				));
			}
		}

		@Nested
		@DisplayName("Anime User Info Id")
		class AnimeUserInfoIdTest {
			@Test
			void convertToDTO_NullInfo_ThrowException() {
				//given
				AnimeUserInfo.AnimeUserInfoId info = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(info)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectId_ReturnDTO() {
				//given
				UUID userID = UUID.randomUUID();
				AnimeUserInfo.AnimeUserInfoId infoId = AnimeUserInfo.AnimeUserInfoId.builder()
						.user(User.builder().id(userID).build())
						.anime(Anime.builder().id(1).build())
						.build();
				LocalUserAnimeInformationDTOId expectedDTO = LocalUserAnimeInformationDTOId.builder()
						.userId(userID)
						.animeId(1L)
						.build();

				//when
				LocalUserAnimeInformationDTOId actualDTO = dtoConversionService.convertToDTO(infoId);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(LocalUserAnimeInformationDTOId.class),
						equalTo(expectedDTO)
				));
			}
		}

		@Nested
		@DisplayName("Review")
		class ReviewTests {
			@Test
			void convertToSimpleDTO_NullReview_ThrowException() {
				//given
				Review review = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToSimpleDTO(review)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToSimpleDTO_CorrectReview_ReturnDTO() {
				//given
				UUID userId = UUID.randomUUID();
				Review review = Review.builder()
						.id(1)
						.title("title")
						.text("text")
						.nrOfHelpful(1)
						.nrOfMinus(1)
						.nrOfPlus(1)
						.build();
				LocalSimpleAnimeReviewDTO expectedDTO = LocalSimpleAnimeReviewDTO.builder()
						.id((long) review.getId())
						.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
						.title(review.getTitle())
						.text(review.getText())
						.nrOfUpvotes(review.getNrOfPlus())
						.nrOfHelpfull(review.getNrOfHelpful())
						.nrOfDownvotes(review.getNrOfMinus())
						.build();

				//when
				LocalSimpleAnimeReviewDTO actualDTO = dtoConversionService.convertToSimpleDTO(review);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(LocalSimpleAnimeReviewDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_NullReview_ThrowException() {
				//given
				Review review = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(review)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectReview_ReturnDTO() {
				//given
				UUID userId = UUID.randomUUID();
				Review review = Review.builder()
						.id(1)
						.title("title")
						.text("text")
						.nrOfHelpful(1)
						.nrOfMinus(1)
						.nrOfPlus(1)
						.animeUserInfo(AnimeUserInfo.builder()
								.id(AnimeUserInfo.AnimeUserInfoId.builder()
										.anime(Anime.builder().id(1).title("title").build())
										.user(User.builder().id(userId).build())
										.build())
								.build())
						.build();
				LocalDetailedAnimeReviewDTO expectedDTO = LocalDetailedAnimeReviewDTO.builder()
						.id((long) review.getId())
						.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.DETAILEDREVIEW)
						.title(review.getTitle())
						.text(review.getText())
						.nrOfUpvotes(review.getNrOfPlus())
						.nrOfHelpfull(review.getNrOfHelpful())
						.nrOfDownvotes(review.getNrOfMinus())
						.animeId(1L)
						.animeTitle("title")
						.build();

				//when
				LocalDetailedAnimeReviewDTO actualDTO = dtoConversionService.convertToDTO(review);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(LocalDetailedAnimeReviewDTO.class),
						equalTo(expectedDTO)
				));
			}
		}
	}

	//Forum

	@Nested
	@DisplayName("Forum Conversion Tests")
	class ForumConversionTests {
		@Nested
		@DisplayName("Forum Category")
		class ForumCategoryTest {
			@Test
			void convertToDTO_NullCategory_ThrowException() {
				//given
				ForumCategory category = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(category)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectCategory_ReturnDTO() {
				//given
				ForumCategory category = ForumCategory.builder()
						.id(1)
						.name("title")
						.description("description")
						.build();

				ForumCategoryDTO expectedCategoryDTO = ForumCategoryDTO.builder()
						.id((long) category.getId())
						.name(category.getName())
						.description(category.getDescription())
						.build();

				//when
				ForumCategoryDTO actualCategoryDTO = dtoConversionService.convertToDTO(category);

				//then
				assertThat(actualCategoryDTO, allOf(
						notNullValue(),
						instanceOf(ForumCategoryDTO.class),
						equalTo(expectedCategoryDTO)
				));
			}
		}

		@Nested
		@DisplayName("Tag")
		class TagTest {
			@Test
			void convertToDTO_NullTag_ThrowException() {
				//given
				Tag tag = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(tag)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectTag_ReturnDTO() {
				//given
				Tag tag = Tag.builder()
						.id(1)
						.name("name")
						.color("color")
						.importance(Tag.TagImportance.HIGH)
						.build();
				TagDTO expectedTagDTO = TagDTO.builder()
						.id((long) tag.getId())
						.name(tag.getName())
						.color(tag.getColor())
						.importance(TagImportance.fromValue(tag.getImportance().name()))
						.build();

				//when
				TagDTO actualTagDTO = dtoConversionService.convertToDTO(tag);

				//then
				assertThat(actualTagDTO, allOf(
						notNullValue(),
						instanceOf(TagDTO.class),
						equalTo(expectedTagDTO)
				));
			}
		}

		@Nested
		@DisplayName("Post")
		class PostTests {
			@Test
			void convertToDTOWithAdditionalInfo_NullPost_ThrowException() {
				//given
				Post post = null;
				UUID userId = UUID.randomUUID();

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTOWithAdditionalInfo(post, userId)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTOWithAdditionalInfo_NullUUID_ThrowException() {
				//given
				Post post = Post.builder().build();
				UUID userId = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTOWithAdditionalInfo(post, userId)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTOWithAdditionalInfo_CorrectPost_ReturnDTO() {
				//given
				LocalDateTime time = LocalDateTime.now();
				UUID userId = UUID.randomUUID();
				User user = User.builder().id(userId).build();
				PostUserStatus status = PostUserStatus.builder()
						.id(PostUserStatus.PostUserStatusId.builder()
								.post(Post.builder().id(1).build())
								.user(user)
								.build())
						.build();
				Post post = Post.builder()
						.id(1)
						.title("title")
						.text("text")
						.blocked(false)
						.status(Post.PostStatus.PENDING)
						.nrOfPlus(1)
						.nrOfMinus(1)
						.nrOfReports(5)
						.creation(time)
						.modification(time)
						.creator(user)
						.postUserStatuses(Set.of(status))
						.thread(Thread.builder().id(1).title("title").build())
						.build();
				PostWithThreadInformationDTO expectedDTO = PostWithThreadInformationDTO.builder()
						.post(SimplePostDTO.builder()
								.id((long) post.getId())
								.postComplexityType(SimplePostDTO.PostComplexityTypeEnum.SIMPLEPOST)
								.title(post.getTitle())
								.text(post.getText())
								.creation(post.getModification().toLocalDate())
								.modification(post.getModification().toLocalDate())
								.creator(dtoConversionService.convertToSimpleDTO(user))
								.nrOfLikes(post.getNrOfPlus())
								.nrOfDislikes(post.getNrOfMinus())
								.status(PostStatus.fromValue(post.getStatus().name()))
								.userStatus(dtoConversionService.convertToDTO(status))
								.build())
						.threadInformation(PostWithThreadInformationDTOThreadInformation.builder()
								.id(1L)
								.title("title")
								.build())
						.build();

				//when
				PostWithThreadInformationDTO actualDTO = dtoConversionService.convertToDTOWithAdditionalInfo(post, userId);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(PostWithThreadInformationDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToSimpleDTO_NullPost_ThrowException() {
				//given
				Post post = null;
				UUID userId = UUID.randomUUID();

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToSimpleDTO(post, userId)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToSimpleDTO_NullUUID_ThrowException() {
				//given
				Post post = Post.builder().build();
				UUID userId = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToSimpleDTO(post, userId)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToSimpleDTO_CorrectPost_ReturnDTO() {
				//given
				LocalDateTime time = LocalDateTime.now();
				UUID userId = UUID.randomUUID();
				User user = User.builder().id(userId).build();
				PostUserStatus status = PostUserStatus.builder()
						.id(PostUserStatus.PostUserStatusId.builder()
								.post(Post.builder().id(1).build())
								.user(user)
								.build())
						.build();
				Post post = Post.builder()
						.id(1)
						.title("title")
						.text("text")
						.blocked(false)
						.status(Post.PostStatus.PENDING)
						.nrOfPlus(1)
						.nrOfMinus(1)
						.nrOfReports(5)
						.creation(time)
						.modification(time)
						.creator(user)
						.postUserStatuses(Set.of(status))
						.thread(Thread.builder().id(1).title("title").build())
						.build();
				SimplePostDTO expectedDTO = SimplePostDTO.builder()
						.id((long) post.getId())
						.postComplexityType(SimplePostDTO.PostComplexityTypeEnum.SIMPLEPOST)
						.title(post.getTitle())
						.text(post.getText())
						.creation(post.getModification().toLocalDate())
						.modification(post.getModification().toLocalDate())
						.creator(dtoConversionService.convertToSimpleDTO(user))
						.nrOfLikes(post.getNrOfPlus())
						.nrOfDislikes(post.getNrOfMinus())
						.status(PostStatus.fromValue(post.getStatus().name()))
						.userStatus(dtoConversionService.convertToDTO(status))
						.build();

				//when
				SimplePostDTO actualDTO = dtoConversionService.convertToSimpleDTO(post, userId);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(SimplePostDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_NullPostUserStatus_ThrowException() {
				//given
				PostUserStatus status = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(status)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectPostUserStatus_ReturnDTO() {
				//given
				PostUserStatus.PostUserStatusId id = PostUserStatus.PostUserStatusId.builder()
						.user(User.builder().id(UUID.randomUUID()).build())
						.post(Post.builder().id(1).build())
						.build();
				PostUserStatus status = PostUserStatus.builder()
						.id(id)
						.reported(false)
						.liked(false)
						.disliked(false)
						.build();
				PostUserStatusDTO expectedDTO = PostUserStatusDTO.builder()
						.id(dtoConversionService.convertToDTO(id))
						.reported(status.isReported())
						.disliked(status.isDisliked())
						.liked(status.isLiked())
						.build();

				//when
				PostUserStatusDTO actualDTO = dtoConversionService.convertToDTO(status);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(PostUserStatusDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_NullId_ThrowException() {
				//given
				PostUserStatus.PostUserStatusId id = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(id)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectId_ReturnDTO() {
				//given
				UUID userId = UUID.randomUUID();
				PostUserStatus.PostUserStatusId id = PostUserStatus.PostUserStatusId.builder()
						.user(User.builder().id(userId).build())
						.post(Post.builder().id(1).build())
						.build();
				PostUserStatusDTOId expectedDTO = PostUserStatusDTOId.builder()
						.postId(1L)
						.userId(userId)
						.build();

				//when
				PostUserStatusDTOId actualDTO = dtoConversionService.convertToDTO(id);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(PostUserStatusDTOId.class),
						equalTo(expectedDTO)
				));
			}
		}

		@Nested
		@DisplayName("Thread")
		class ThreadTests {
			@Test
			void convertToSimpleDTO_NullPost_ThrowException() {
				//given
				Thread thread = null;
				UUID userId = UUID.randomUUID();

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToSimpleDTO(thread, userId)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToSimpleDTO_NullUUID_ThrowException() {
				//given
				Thread thread = Thread.builder().build();
				UUID userId = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToSimpleDTO(thread, userId)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToSimpleDTO_CorrectPost_ReturnDTO() {
				//given
				LocalDateTime time = LocalDateTime.now();
				UUID userId = UUID.randomUUID();
				User user = User.builder().id(userId).build();
				ForumCategory category = ForumCategory.builder()
						.id(1)
						.name("name")
						.description("description")
						.build();
				Tag tag = Tag.builder()
						.id(1)
						.name("name")
						.importance(Tag.TagImportance.HIGH)
						.color("rgb(0, 183, 255)")
						.build();
				ThreadUserStatus status = ThreadUserStatus.builder()
						.id(ThreadUserStatus.ThreadUserStatusId.builder()
								.user(user)
								.thread(Thread.builder().id(1).build())
								.build())
						.watching(false)
						.blocking(false)
						.build();
				Thread thread = Thread.builder()
						.id(1)
						.title("title")
						.text("text")
						.status(Thread.ThreadStatus.CLOSED)
						.nrOfPosts(1)
						.creation(time)
						.modification(time)
						.creator(user)
						.category(category)
						.tags(Set.of(tag))
						.threadUserStatuses(Set.of(status))
						.build();
				SimpleThreadDTO expectedDTO = SimpleThreadDTO.builder()
						.id((long) thread.getId())
						.threadComplexityType(SimpleThreadDTO.ThreadComplexityTypeEnum.SIMPLETHREAD)
						.title(thread.getTitle())
						.nrOfPosts(thread.getNrOfPosts())
						.status(ThreadStatus.fromValue(thread.getStatus().name()))
						.creation(thread.getCreation())
						.modification(thread.getModification())
						.creator(dtoConversionService.convertToSimpleDTO(user))
						.category(dtoConversionService.convertToDTO(category))
						.tags(List.of(dtoConversionService.convertToDTO(tag)))
						.userStatus(dtoConversionService.convertToDTO(status))
						.build();

				//when
				SimpleThreadDTO actualDTO = dtoConversionService.convertToSimpleDTO(thread, userId);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(SimpleThreadDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_NullId_ThrowException() {
				//given
				ThreadUserStatus.ThreadUserStatusId id = null;

				//when
				Exception exception = assertThrows(NullPointerException.class, () ->
						dtoConversionService.convertToDTO(id)
				);

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(NullPointerException.class)
				));
			}

			@Test
			void convertToDTO_CorrectId_ReturnDTO() {
				//given
				User user = User.builder().id(UUID.randomUUID()).build();
				ThreadUserStatus.ThreadUserStatusId id = ThreadUserStatus.ThreadUserStatusId.builder()
						.thread(Thread.builder().id(1).build())
						.user(user)
						.build();
				ThreadUserStatusDTOId expectedDTO = ThreadUserStatusDTOId.builder()
						.userId(user.getId())
						.threadId(1L)
						.build();

				//when
				ThreadUserStatusDTOId actualDTO = dtoConversionService.convertToDTO(id);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(ThreadUserStatusDTOId.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_NullThreadUserStatus_ThrowException() {
				//given
				User user = User.builder().id(UUID.randomUUID()).build();
				ThreadUserStatus.ThreadUserStatusId id = ThreadUserStatus.ThreadUserStatusId.builder()
						.thread(Thread.builder().id(1).build())
						.user(user)
						.build();
				ThreadUserStatus status = ThreadUserStatus.builder()
						.id(id)
						.blocking(false)
						.watching(true)
						.build();
				ThreadUserStatusDTO expectedDTO = ThreadUserStatusDTO.builder()
						.id(dtoConversionService.convertToDTO(id))
						.blocked(status.isBlocking())
						.watching(status.isWatching())
						.build();

				//when
				ThreadUserStatusDTO actualDTO = dtoConversionService.convertToDTO(status);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(ThreadUserStatusDTO.class),
						equalTo(expectedDTO)
				));
			}

			@Test
			void convertToDTO_CorrectThreadUserStatus_ReturnDTO() {
				//given
				User user = User.builder().id(UUID.randomUUID()).build();
				ThreadUserStatus.ThreadUserStatusId id = ThreadUserStatus.ThreadUserStatusId.builder()
						.thread(Thread.builder().id(1).build())
						.user(user)
						.build();
				ThreadUserStatusDTOId expectedDTO = ThreadUserStatusDTOId.builder()
						.userId(user.getId())
						.threadId(1L)
						.build();

				//when
				ThreadUserStatusDTOId actualDTO = dtoConversionService.convertToDTO(id);

				//then
				assertThat(actualDTO, allOf(
						notNullValue(),
						instanceOf(ThreadUserStatusDTOId.class),
						equalTo(expectedDTO)
				));
			}
		}
	}
}