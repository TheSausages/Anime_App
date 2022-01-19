package anime.app.anilist.request.query.parameters.connections.recommendation;

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

import static anime.app.anilist.request.query.parameters.connections.recommendation.Recommendation.RECOMMENDATION_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RecommendationTest {

	@Test
	void getRecommendationStringWithoutFieldName__ReturnCorrectString() {
		//given
		List<String> expectedRecommendation = TestUtils.buildFieldParameterStringSet(
				"id"
		);

		//when
		Recommendation actualRecommendation = Recommendation.getRecommendationBuilder()
				.id()
				.build();

		//then
		assertThat(actualRecommendation.getRecommendationStringWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedRecommendation)
		));
	}

	@Test
	void getRecommendationBuilder__ReturnValidBuilder() {
		//given

		//when
		Recommendation.RecommendationBuilder builder = Recommendation.getRecommendationBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(Recommendation.RecommendationBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Recommendation Builder")
	class RecommendationBuilderTest {
		@Test
		void recommendationBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Recommendation.getRecommendationBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void recommendationBuilder_Id_ReturnCorrectString() {
			//given
			List<String>expectedRecommendation = TestUtils.buildFieldParameterStringSet(
					"id"
			);

			//when
			Recommendation actualRecommendation = Recommendation.getRecommendationBuilder()
					.id()
					.build();

			//then
			assertThat(actualRecommendation.getRecommendationString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_TITLE, expectedRecommendation)
			));
		}

		@Test
		void recommendationBuilder_Rating_ReturnCorrectString() {
			//given
			List<String> expectedRecommendation = TestUtils.buildFieldParameterStringSet(
					"rating"
			);

			//when
			Recommendation actualRecommendation = Recommendation.getRecommendationBuilder()
					.rating()
					.build();

			//then
			assertThat(actualRecommendation.getRecommendationString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_TITLE, expectedRecommendation)
			));
		}

		@Test
		void recommendationBuilder_Media_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedRecommendation = TestUtils.buildFieldParameterStringSet(
					"media " + media.getRequestedMediaFields()
			);

			//when
			Recommendation actualRecommendation = Recommendation.getRecommendationBuilder()
					.media(media)
					.build();

			//then
			assertThat(actualRecommendation.getRecommendationString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_TITLE, expectedRecommendation)
			));
		}

		@Test
		void recommendationBuilder_MediaRecommendation_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedRecommendation = TestUtils.buildFieldParameterStringSet(
					"mediaRecommendation " + media.getRequestedMediaFields()
			);

			//when
			Recommendation actualRecommendation = Recommendation.getRecommendationBuilder()
					.mediaRecommendation(media)
					.build();

			//then
			assertThat(actualRecommendation.getRecommendationString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_TITLE, expectedRecommendation)
			));
		}

		@Test
		void recommendationBuilder_AnilistUser_ReturnCorrectString() {
			//given
			AnilistUser user = AnilistUser.getUserBuilder().id().build();
			List<String> expectedRecommendation = TestUtils.buildFieldParameterStringSet(
					"user " + user.getAnilistUserWithoutFieldName()
			);

			//when
			Recommendation actualRecommendation = Recommendation.getRecommendationBuilder()
					.user(user)
					.build();

			//then
			assertThat(actualRecommendation.getRecommendationString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_TITLE, expectedRecommendation)
			));
		}

		@Test
		void recommendationBuilder_AllParameters_ReturnCorrectString() {
			//given
			AnilistUser user = AnilistUser.getUserBuilder().id().build();
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedRecommendation = TestUtils.buildFieldParameterStringSet(
					"id",
					"rating",
					"media " + media.getRequestedMediaFields(),
					"mediaRecommendation " + media.getRequestedMediaFields(),
					"user " + user.getAnilistUserWithoutFieldName()
			);

			//when
			Recommendation actualRecommendation = Recommendation.getRecommendationBuilder()
					.id()
					.rating()
					.media(media)
					.mediaRecommendation(media)
					.user(user)
					.build();

			//then
			assertThat(actualRecommendation.getRecommendationString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_TITLE, expectedRecommendation)
			));
		}
	}
}