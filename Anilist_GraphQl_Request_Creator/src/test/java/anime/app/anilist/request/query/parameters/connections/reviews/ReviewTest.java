package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.common.AnilistUser;
import anime.app.anilist.request.query.parameters.media.MediaFormat;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.reviews.Review.REVIEW_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ReviewTest {

	@Test
	void getReviewWithoutFieldName__ReturnCorrectString() {
		//given
		List<String> expectedReview = TestUtils.buildFieldParameterStringSet("id");

		//when
		Review actualReview = Review.getReviewBuilder()
				.id()
				.build();

		//then
		assertThat(actualReview.getReviewWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedReview)
		));
	}

	@Test
	void getReviewBuilder__ReturnValidBuilder() {
		//given

		//when
		Review.ReviewBuilder builder = Review.getReviewBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(Review.ReviewBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Review Builder")
	class ReviewBuilderTest {
		@Test
		void reviewBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Review.getReviewBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void reviewBuilder_Id_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"id"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.id()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_UserId_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"userId"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.userId()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_MediaId_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"mediaId"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.mediaId()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_MediaType_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"mediaType"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.mediaType()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Summary_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"summary"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.summary()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Body_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"body"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.body()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_BodyAsHtmlNoParameter_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"body(asHtml: true)"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.bodyAsHtml()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_BodyAsHtmlWithParameter_ReturnCorrectString() {
			//given
			boolean asHtml = false;
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"body(asHtml: " + asHtml + ")"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.bodyAsHtml(asHtml)
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Rating_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"rating"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.rating()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_RatingAmount_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"ratingAmount"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.ratingAmount()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Score_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"score"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.score()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_IsPrivate_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"private"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.isPrivate()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_SiteUrl_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"siteUrl"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.siteUrl()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_CreatedAt_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"createdAt"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.createdAt()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_UpdatedAt_ReturnCorrectString() {
			//given
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"updatedAt"
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.updatedAt()
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_User_ReturnCorrectString() {
			//given
			AnilistUser anilistUser = AnilistUser.getUserBuilder().id().build();
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"user " + anilistUser.getAnilistUserWithoutFieldName()
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.user(anilistUser)
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Media_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"media " +media.getRequestedMediaFields()
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.media(media)
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}

		@Test
		void reviewBuilder_AllParameters_ReturnCorrectString() {
			//given
			AnilistUser anilistUser = AnilistUser.getUserBuilder().id().build();
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedReview = TestUtils.buildFieldParameterStringSet(
					"id",
					"userId",
					"mediaId",
					"mediaType",
					"summary",
					"body(asHtml: true)",
					"rating",
					"ratingAmount",
					"score",
					"private",
					"siteUrl",
					"createdAt",
					"updatedAt",
					"user " + anilistUser.getAnilistUserWithoutFieldName(),
					"media " +media.getRequestedMediaFields()
			);

			//when
			Review actualReview = Review.getReviewBuilder()
					.id()
					.userId()
					.mediaId()
					.mediaType()
					.summary()
					.bodyAsHtml()
					.rating()
					.ratingAmount()
					.score()
					.isPrivate()
					.siteUrl()
					.createdAt()
					.updatedAt()
					.user(anilistUser)
					.media(media)
					.build();

			//then
			assertThat(actualReview.getReviewString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_TITLE, expectedReview)
			));
		}
	}
}