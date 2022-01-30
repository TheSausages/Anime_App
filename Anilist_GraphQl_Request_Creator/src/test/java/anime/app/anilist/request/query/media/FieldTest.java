package anime.app.anilist.request.query.media;

import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.query.parameters.connections.airingschedule.AiringScheduleArguments;
import anime.app.anilist.request.query.parameters.connections.airingschedule.AiringScheduleConnection;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterArguments;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterConnection;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.connections.recommendation.RecommendationArguments;
import anime.app.anilist.request.query.parameters.connections.recommendation.RecommendationConnection;
import anime.app.anilist.request.query.parameters.connections.reviews.ReviewArguments;
import anime.app.anilist.request.query.parameters.connections.reviews.ReviewConnection;
import anime.app.anilist.request.query.parameters.connections.staff.StaffArguments;
import anime.app.anilist.request.query.parameters.connections.staff.StaffConnection;
import anime.app.anilist.request.query.parameters.connections.studio.StudioConnection;
import anime.app.anilist.request.query.parameters.connections.studio.StudioSort;
import anime.app.anilist.request.query.parameters.connections.trends.MediaTrendConnection;
import anime.app.anilist.request.query.parameters.connections.trends.MediaTrendsArguments;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import anime.app.anilist.request.query.parameters.media.MediaExternalLinks;
import anime.app.anilist.request.query.parameters.media.MediaRank;
import anime.app.anilist.request.query.parameters.media.MediaStreamingEpisodes;
import anime.app.anilist.request.query.parameters.media.MediaTitle;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.media.Field.FIELD_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FieldTest {

	@Test
	void getFieldBuilder__ReturnValidBuilder() {
		//given

		//when
		Field.FieldBuilder builder = Field.getFieldBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(Field.FieldBuilder.class)
		));
	}

	@Test
	void getFieldWithoutFieldName__ReturnCorrectString() {
		//given
		BasicQueryParameters parameters = BasicQueryParameters.ID;
		List<String> expectedField = TestUtils.buildFieldParameterStringSet(
				parameters.getFieldName()
		);

		//when
		Field actualField = Field.getFieldBuilder()
				.parameter(BasicQueryParameters.ID)
				.build();

		//then
		assertThat(actualField.getFieldWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedField)
		));
	}

	@Nested
	@DisplayName("Test Field Builder")
	class FieldBuilderTest {
		@Test
		void fieldBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Field.getFieldBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void fieldBuilder_Parameter_ReturnCorrectString() {
			//given
			BasicQueryParameters parameters = BasicQueryParameters.ID;
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					parameters.getFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.parameter(BasicQueryParameters.ID)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_Title_ReturnCorrectString() {
			//given
			MediaTitle title = MediaTitle.getMediaTitleBuilder().englishLanguage().build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					title.getTitleLanguages()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.title(title)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_Trailer_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"trailer {\nid\nsite\nthumbnail\n}"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.trailer()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_CoverImage_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"coverImage {\nextraLarge\nlarge\nmedium\ncolor\n}"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.coverImage()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_Tags_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"tags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.tags()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_NextAiringEpisode_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"nextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.nextAiringEpisode()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StatusNoParameter_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"status(version: 2)"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.status()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StatusWithParameter_ReturnCorrectString() {
			//given
			int status = 1;
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"status(version: " + status + ")"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.status(status)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_Description_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"description(asHtml: false)"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.description()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_DescriptionAsHtmlNoParameter_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"description(asHtml: true)"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.descriptionAsHtml()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_DescriptionAsHtmlWithParameter_ReturnCorrectString() {
			//given
			boolean asHtml = false;
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"description(asHtml: " + asHtml + ")"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.descriptionAsHtml(asHtml)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_SourceNoParameter_ReturnCorrectString() {
			//given
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"source(version: 3)"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.source()
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_SourceWithParameter_ReturnCorrectString() {
			//given
			int source = 1;
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"source(version: " + source + ")"
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.source(source)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_ExternalLinks_ReturnCorrectString() {
			//given
			MediaExternalLinks links = MediaExternalLinks.getMediaExternalLinkBuilder()
					.id()
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					links.getExternalLink()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.externalLinks(links)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_Ranking_ReturnCorrectString() {
			//given
			MediaRank rank = MediaRank.getMediaRankBuilder().id().build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					rank.getRank()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.ranking(rank)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StartDateNullParameter_ReturnCorrectString() {
			//given
			FuzzyDateField fuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder()
					.allAndBuild();

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Field.getFieldBuilder().startDate(fuzzyDateField).build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void fieldBuilder_StartDateIncorrectParameter_ReturnCorrectString() {
			//given
			FuzzyDateField fuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.DATE_OF_DEATH)
					.allAndBuild();

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Field.getFieldBuilder().startDate(fuzzyDateField).build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void fieldBuilder_StartDateCorrectParameter_ReturnCorrectString() {
			//given
			FuzzyDateField fuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.START_DATE)
					.allAndBuild();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					fuzzyDateField.getFuzzyDateString()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.startDate(fuzzyDateField)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_EndDateNullParameter_ReturnCorrectString() {
			//given
			FuzzyDateField fuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder()
					.allAndBuild();

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Field.getFieldBuilder().endDate(fuzzyDateField).build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void fieldBuilder_EndDateIncorrectParameter_ReturnCorrectString() {
			//given
			FuzzyDateField fuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.DATE_OF_DEATH)
					.allAndBuild();

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Field.getFieldBuilder().endDate(fuzzyDateField).build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void fieldBuilder_EndDateCorrectParameter_ReturnCorrectString() {
			//given
			FuzzyDateField fuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.END_DATE)
					.allAndBuild();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					fuzzyDateField.getFuzzyDateString()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.endDate(fuzzyDateField)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StreamingEpisodes_ReturnCorrectString() {
			//given
			MediaStreamingEpisodes episodes = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder()
					.title()
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					episodes.getStreamingEpisode()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.streamingEpisodes(episodes)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_Relations_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"relations " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.relations(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_CharactersWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			CharacterConnection connection = CharacterConnection.getCharacterConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"characters " + connection.getCharacterConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.characters(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_CharactersWithArguments_ReturnCorrectString() {
			//given
			CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
					.page(1)
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			CharacterConnection connection = CharacterConnection.getCharacterConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"characters" + arguments.getCharacterArgumentsString() + " " + connection.getCharacterConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.characters(connection, arguments)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StaffWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			StaffConnection connection = StaffConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"staff " + connection.getStaffConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.staff(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StaffWithArguments_ReturnCorrectString() {
			//given
			StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
					.perPage(1)
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			StaffConnection connection = StaffConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"staff" + arguments.getStaffArgumentsString() + " " + connection.getStaffConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.staff(connection, arguments)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StudioWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			StudioConnection connection = StudioConnection.getStudioConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"studios " + connection.getStudioConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.studios(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StudioWithSortSingle_ReturnCorrectString() {
			//given
			StudioSort[] sorts = new StudioSort[] {StudioSort.ID};
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			StudioConnection connection = StudioConnection.getStudioConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"studios(sort: " + sorts +") " + connection.getStudioConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.studios(connection, sorts)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_StudioWithSortMany_ReturnCorrectString() {
			//given
			StudioSort[] sorts = new StudioSort[] {StudioSort.ID, StudioSort.NAME};
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			StudioConnection connection = StudioConnection.getStudioConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"studios(sort: " + sorts +") " + connection.getStudioConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.studios(connection, sorts)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_AiringScheduleWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"airingSchedule " + connection.getAiringScheduleConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.airingSchedule(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_AiringScheduleWithArguments_ReturnCorrectString() {
			//given
			AiringScheduleArguments arguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
					.notYetAired()
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"airingSchedule" + arguments.getAiringScheduleArgumentsString() + " " + connection.getAiringScheduleConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.airingSchedule(connection, arguments)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_TrendsWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			MediaTrendConnection connection =MediaTrendConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"trends " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.trends(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_TrendsWithArguments_ReturnCorrectString() {
			//given
			MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.releasing()
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			MediaTrendConnection connection =MediaTrendConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"trends" + arguments.getMediaTrendsArgumentsString() + " " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.trends(connection, arguments)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_ReviewsWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			ReviewConnection connection = ReviewConnection.getReviewConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"reviews " + connection.getReviewConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.reviews(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_ReviewsWithArguments_ReturnCorrectString() {
			//given
			ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
					.perPage(1)
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			ReviewConnection connection = ReviewConnection.getReviewConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"reviews" + arguments.getReviewArgumentsString() + " " + connection.getReviewConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.reviews(connection, arguments)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_RecommendationWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			RecommendationConnection connection = RecommendationConnection.getRecommendationConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"recommendation " + connection.getRecommendationConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.recommendation(connection)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_RecommendationWithArguments_ReturnCorrectString() {
			//given
			RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
					.perPage(1)
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			RecommendationConnection connection = RecommendationConnection.getRecommendationConnectionBuilder()
					.pageInfo(info)
					.build();
			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					"recommendation" + arguments.getRecommendationArgumentsString() + " " + connection.getRecommendationConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.recommendation(connection, arguments)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}

		@Test
		void fieldBuilder_AllParameters_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();

			int source = 1;
			boolean asHtml = false;
			int status = 1;
			BasicQueryParameters parameters = BasicQueryParameters.AVERAGE_SCORE;

			MediaTitle title = MediaTitle.getMediaTitleBuilder().romajiLanguage().build();

			MediaExternalLinks links = MediaExternalLinks.getMediaExternalLinkBuilder()
					.id()
					.build();

			MediaRank rank = MediaRank.getMediaRankBuilder().id().build();

			FuzzyDateField startDateFuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.START_DATE)
					.allAndBuild();

			FuzzyDateField endDateFuzzyDateField = FuzzyDateField
					.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.END_DATE)
					.allAndBuild();

			MediaStreamingEpisodes episodes = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder()
					.site()
					.build();

			MediaConnection relationConnection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();

			CharacterArguments characterArguments = CharacterArguments.getCharacterArgumentsBuilder()
					.page(1)
					.build();
			CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
					.pageInfo(info)
					.build();

			StaffArguments staffArguments = StaffArguments.getStaffArgumentsBuilder()
					.perPage(1)
					.build();
			StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();

			StudioSort[] sorts = new StudioSort[] {StudioSort.ID, StudioSort.NAME};
			StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
					.pageInfo(info)
					.build();

			AiringScheduleArguments airingScheduleArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
					.notYetAired()
					.build();
			AiringScheduleConnection airingScheduleConnection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
					.pageInfo(info)
					.build();

			MediaTrendsArguments trendArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.releasing()
					.build();
			MediaTrendConnection trendConnection =MediaTrendConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();

			RecommendationArguments recommendationArguments = RecommendationArguments.getRecommendationArgumentBuilder()
					.perPage(1)
					.build();
			RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
					.pageInfo(info)
					.build();

			ReviewArguments reviewArguments = ReviewArguments.getReviewArgumentsBuilder()
					.perPage(1)
					.build();
			ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
					.pageInfo(info)
					.build();

			List<String> expectedField = TestUtils.buildFieldParameterStringSet(
					parameters.getFieldName(),
					title.getTitleLanguages(),
					"trailer {\nid\nsite\nthumbnail\n}",
					"coverImage {\nextraLarge\nlarge\nmedium\ncolor\n}",

					//Tags is left out, bcs the testing method doesn't work well when the ParameterString field (here 'description' in tags) exists 2 times
					"tags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}",
					"nextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}",
					"status(version: " + status + ")",
					"description(asHtml: false)",
					"source(version: " + source + ")",
					links.getExternalLink(),
					rank.getRank(),
					startDateFuzzyDateField.getFuzzyDateString(),
					endDateFuzzyDateField.getFuzzyDateString(),
					episodes.getStreamingEpisode(),
					"relations " + relationConnection.getMediaConnectionWithoutFieldName(),
					"characters" + characterArguments.getCharacterArgumentsString() + " " + characterConnection.getCharacterConnectionWithoutFieldName(),
					"staff" + staffArguments.getStaffArgumentsString() + " " + staffConnection.getStaffConnectionWithoutFieldName(),
					"studios(sort: " + sorts +") " + studioConnection.getStudioConnectionWithoutFieldName(),
					"airingSchedule" + airingScheduleArguments.getAiringScheduleArgumentsString() + " " + airingScheduleConnection.getAiringScheduleConnectionWithoutFieldName(),
					"trends" + trendArguments.getMediaTrendsArgumentsString() + " " + trendConnection.getMediaConnectionWithoutFieldName(),
					"reviews" + reviewArguments.getReviewArgumentsString() + " " + reviewConnection.getReviewConnectionWithoutFieldName(),
					"recommendation" + recommendationArguments.getRecommendationArgumentsString() + " " + recommendationConnection.getRecommendationConnectionWithoutFieldName()
			);

			//when
			Field actualField = Field.getFieldBuilder()
					.parameter(parameters)
					.title(title)
					.trailer()
					.coverImage()
					.tags()
					.nextAiringEpisode()
					.status(status)
					.descriptionAsHtml(asHtml)
					.source(source)
					.externalLinks(links)
					.ranking(rank)
					.startDate(startDateFuzzyDateField)
					.endDate(endDateFuzzyDateField)
					.streamingEpisodes(episodes)
					.relations(relationConnection)
					.characters(characterConnection, characterArguments)
					.staff(staffConnection, staffArguments)
					.studios(studioConnection, sorts)
					.airingSchedule(airingScheduleConnection, airingScheduleArguments)
					.trends(trendConnection, trendArguments)
					.reviews(reviewConnection, reviewArguments)
					.recommendation(recommendationConnection, recommendationArguments)
					.build();

			//then
			assertThat(actualField.getField(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(FIELD_TITLE, expectedField)
			));
		}
	}
}