package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.media.MediaFormat;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.trends.MediaTrend.MEDIA_TREND_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaTrendTest {

	@Test
	void getStudioEdgeWithoutFieldName__ReturnCorrectString() {
		//given
		List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("mediaId");

		//when
		MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder()
				.mediaId()
				.build();

		//then
		assertThat(actualTrend.getMediaTrendWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedTrend)
		));
	}

	@Test
	void getMediaTrendBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaTrend.MediaTrendBuilder builder = MediaTrend.getMediaTrendBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaTrend.MediaTrendBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Trend Builder")
	class MediaTrendBuilderTest {
		@Test
		void mediaTrendBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaTrend.getMediaTrendBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaTrendBuilder_MediaId_ReturnCorrectString() {
			//given
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("mediaId");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().mediaId().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_Date_ReturnCorrectString() {
			//given
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("date");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().date().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_Trending_ReturnCorrectString() {
			//given
			List<String>expectedTrend = TestUtils.buildFieldParameterStringSet("trending");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().trending().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_AverageScore_ReturnCorrectString() {
			//given
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("averageScore");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().averageScore().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_Popularity_ReturnCorrectString() {
			//given
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("popularity");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().popularity().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_InProgress_ReturnCorrectString() {
			//given
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("inProgress");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().inProgress().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_Releasing_ReturnCorrectString() {
			//given
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("releasing");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().releasing().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_Episode_ReturnCorrectString() {
			//given
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet("episode");

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().episode().build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_Media_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet(
					QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.MEDIA, media.getRequestedMediaFields()).getField()
			);

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder().media(media).build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}

		@Test
		void mediaTrendBuilder_AllParameters_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedTrend = TestUtils.buildFieldParameterStringSet(
					"mediaId",
					"date",
					"trending",
					"averageScore",
					"popularity",
					"inProgress",
					"releasing",
					"episode",
					QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.MEDIA, media.getRequestedMediaFields()).getField()
			);

			//when
			MediaTrend actualTrend = MediaTrend.getMediaTrendBuilder()
					.mediaId()
					.date()
					.trending()
					.averageScore()
					.popularity()
					.inProgress()
					.releasing()
					.episode()
					.media(media)
					.build();

			//then
			assertThat(actualTrend.getMediaTrendString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TREND_TITLE, expectedTrend)
			));
		}
	}
}