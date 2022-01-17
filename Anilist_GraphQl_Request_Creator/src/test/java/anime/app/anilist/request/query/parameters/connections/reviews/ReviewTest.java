package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.AnilistUser;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.reviews.Review.reviewTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ReviewTest {

	@Test
	void getReviewWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField("id");

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
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_UserId_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_MediaId_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_MediaType_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Summary_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Body_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_BodyAsHtmlNoParameter_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_BodyAsHtmlWithParameter_ReturnCorrectString() {
			//given
			boolean asHtml = false;
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Rating_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_RatingAmount_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Score_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_IsPrivate_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_SiteUrl_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_CreatedAt_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_UpdatedAt_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_User_ReturnCorrectString() {
			//given
			AnilistUser anilistUser = AnilistUser.getUserBuilder().id().build();
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_Media_ReturnCorrectString() {
			//given
			Media media = new Media("media(id: ${id}) {\nformat\n}");
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}

		@Test
		void reviewBuilder_AllParameters_ReturnCorrectString() {
			//given
			AnilistUser anilistUser = AnilistUser.getUserBuilder().id().build();
			Media media = new Media("media(id: ${id}) {\nformat\n}");
			Set<ParameterString> expectedReview = TestUtils.getParameterStringSetField(
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
					containsTitleAndAllSetElements(reviewTitle, expectedReview)
			));
		}
	}
}