package anime.app.anilist.request.query.media;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateValue;
import anime.app.anilist.request.query.parameters.media.*;
import anime.app.anilist.request.utils.TestUtils;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static anime.app.anilist.request.query.media.Media.MEDIA_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithArgumentsAndParametersMatcher.containsTitleWithArgumentsAndParameters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaTest {
	private final ObjectMapper mapper =
			new ObjectMapper()
					.enable(SerializationFeature.INDENT_OUTPUT)
					.setDefaultPrettyPrinter(new DefaultPrettyPrinter())
					.registerModule(new JavaTimeModule())
					.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));

	@Test
	void getMediaArgumentBuilder__ReturnValidBuilder() {
		//given

		//when
		Media.MediaArgumentBuilder builder = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build());

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(Media.MediaArgumentBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Builder")
	class MediaBuilderTest {
		private final Field field = Field.getFieldBuilder().parameter(BasicQueryParameters.ID).build();
		private final Set<ParameterString> expectedField = TestUtils.buildFieldParameterStringSet(
				"id"
		);

		@Test
		void mediaBuilder_NoParameterSelected_ThrowException() {
			//given
			Field field = Field.getFieldBuilder().status().build();

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Media.getMediaArgumentBuilder(field).build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaBuilder_Id_ReturnCorrectString() {
			//given
			Field field = Field.getFieldBuilder().parameter(BasicQueryParameters.ID).build();
			int id = 135485;
			Map<String, Object> parameters = Map.of("id", id);
			Set<String> expectedQueryParameters = Set.of(
					"$id: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"id: $id"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).id(id).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(parameters)
			));
		}

		@Test
		void mediaBuilder_IdMal_ReturnCorrectString() {
			//given
			int idMal = 135485;
			Map<String, Object> parameters = Map.of("idMal", idMal);
			Set<String> expectedQueryParameters = Set.of(
					"$idMal: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"idMal: $idMal"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idMal(idMal).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(parameters)
			));
		}

		@Test
		void mediaBuilder_StartDate_ReturnCorrectString() {
			//given
			FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();
			Map<String, Object> expectedParameters = Map.of("startDate", fuzzyDateValue.getFuzzyDateNumber());
			Set<String> expectedQueryParameters = Set.of(
					"$startDate: FuzzyDateInt"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"startDate: $startDate"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).startDate(fuzzyDateValue).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_EndDate_ReturnCorrectString() {
			//given
			FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();
			Map<String, Object> expectedParameters = Map.of("endDate", fuzzyDateValue.getFuzzyDateNumber());
			Set<String> expectedQueryParameters = Set.of(
					"$endDate: FuzzyDateInt"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"endDate: $endDate"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).endDate(fuzzyDateValue).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Season_ReturnCorrectString() {
			//given
			MediaSeason season = MediaSeason.SPRING;
			Map<String, Object> expectedParameters = Map.of("season", season);
			Set<String> expectedQueryParameters = Set.of(
					"$season: MediaSeason"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"season: $season"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).season(season).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_CurrentSeason_ReturnCorrectString() {
			//given
			MediaSeason season = MediaSeason.getCurrentSeason();
			Map<String, Object> expectedParameters = Map.of("season", season);
			Set<String> expectedQueryParameters = Set.of(
					"$season: MediaSeason"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"season: $season"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).currentSeason().build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_SeasonYear_ReturnCorrectString() {
			//given
			int seasonYear = 2022;
			Map<String, Object> expectedParameters = Map.of("seasonYear", seasonYear);
			Set<String> expectedQueryParameters = Set.of(
					"$seasonYear: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"seasonYear: $seasonYear"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).seasonYear(seasonYear).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Type_ReturnCorrectString() {
			//given
			MediaType type = MediaType.ANIME;
			Map<String, Object> expectedParameters = Map.of("type", type);
			Set<String> expectedQueryParameters = Set.of(
					"$type: MediaType"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"type: $type"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).type(type).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Format_ReturnCorrectString() {
			//given
			MediaFormat format = MediaFormat.TV;
			Map<String, Object> expectedParameters = Map.of("format", format);
			Set<String> expectedQueryParameters = Set.of(
					"$format: MediaFormat"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"format: $format"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).format(format).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Status_ReturnCorrectString() {
			//given
			MediaStatus status = MediaStatus.FINISHED;
			Map<String, Object> expectedParameters = Map.of("status", status);
			Set<String> expectedQueryParameters = Set.of(
					"$status: MediaStatus"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"status: $status"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).status(status).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Episode_ReturnCorrectString() {
			//given
			int episodes = 12;
			Map<String, Object> expectedParameters = Map.of("episodes", episodes);
			Set<String> expectedQueryParameters = Set.of(
					"$episodes: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"episodes: $episodes"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).episodes(episodes).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Duration_ReturnCorrectString() {
			//given
			int duration = 12;
			Map<String, Object> expectedParameters = Map.of("duration", duration);
			Set<String> expectedQueryParameters = Set.of(
					"$duration: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"duration: $duration"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).duration(duration).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Chapters_ReturnCorrectString() {
			//given
			int chapters = 12;
			Map<String, Object> expectedParameters = Map.of("chapters", chapters);
			Set<String> expectedQueryParameters = Set.of(
					"$chapters: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"chapters: $chapters"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).chapters(chapters).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Volumes_ReturnCorrectString() {
			//given
			int volumes = 12;
			Map<String, Object> expectedParameters = Map.of("volumes", volumes);
			Set<String> expectedQueryParameters = Set.of(
					"$volumes: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"volumes: $volumes"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).volumes(volumes).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IsAdult_ReturnCorrectString() {
			//given
			boolean isAdult = true;
			Map<String, Object> expectedParameters = Map.of("isAdult", isAdult);
			Set<String> expectedQueryParameters = Set.of(
					"$isAdult: Boolean"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"isAdult: $isAdult"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).isAdult(isAdult).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Genre_ReturnCorrectString() {
			//given
			MediaGenre genre = MediaGenre.COMEDY;
			Map<String, Object> expectedParameters = Map.of("genre", genre);
			Set<String> expectedQueryParameters = Set.of(
					"$genre: String"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"genre: $genre"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).genre(genre).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Tag_ReturnCorrectString() {
			//given
			MediaTags tag = MediaTags.Meta;
			Map<String, Object> expectedParameters = Map.of("tag", tag);
			Set<String> expectedQueryParameters = Set.of(
					"$tag: String"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"tag: $tag"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).tag(tag).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_OnList_ReturnCorrectString() {
			//given
			boolean onList = true;
			Map<String, Object> expectedParameters = Map.of("onList", onList);
			Set<String> expectedQueryParameters = Set.of(
					"$onList: Boolean"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"onList: $onList"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).onList(onList).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_LicensedBy_ReturnCorrectString() {
			//given
			String licensedBy = "CloverWorks";
			Map<String, Object> expectedParameters = Map.of("licensedBy", licensedBy);
			Set<String> expectedQueryParameters = Set.of(
					"$licensedBy: String"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"licensedBy: $licensedBy"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).licensedBy(licensedBy).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_AverageScore_ReturnCorrectString() {
			//given
			int averageScore = 5;
			Map<String, Object> expectedParameters = Map.of("averageScore", averageScore);
			Set<String> expectedQueryParameters = Set.of(
					"$averageScore: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"averageScore: $averageScore"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).averageScore(averageScore).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Source_ReturnCorrectString() {
			//given
			MediaSource source = MediaSource.LIGHT_NOVEL;
			Map<String, Object> expectedParameters = Map.of("source", source);
			Set<String> expectedQueryParameters = Set.of(
					"$source: MediaSource"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"source: $source"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).source(source).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_CountryOfOriginIncorrectString_ReturnCorrectString() {
			//given
			Field field = Field.getFieldBuilder().status().build();

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalArgumentException.class,
					() -> Media.getMediaArgumentBuilder(field).countryOfOrigin("weirdCountryCode").build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalArgumentException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaBuilder_CountryOfOriginCorrectLowercaseString_ReturnCorrectString() {
			//given
			String countryOfOrigin = "pl";
			Map<String, Object> expectedParameters = Map.of("countryOfOrigin", countryOfOrigin.toUpperCase(Locale.ROOT));
			Set<String> expectedQueryParameters = Set.of(
					"$countryOfOrigin: CountryCode"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"countryOfOrigin: $countryOfOrigin"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).countryOfOrigin(countryOfOrigin).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_CountryOfOriginCorrectUppercaseString_ReturnCorrectString() {
			//given
			String countryOfOrigin = "PL";
			Map<String, Object> expectedParameters = Map.of("countryOfOrigin", countryOfOrigin);
			Set<String> expectedQueryParameters = Set.of(
					"$countryOfOrigin: CountryCode"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"countryOfOrigin: $countryOfOrigin"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).countryOfOrigin(countryOfOrigin).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Popularity_ReturnCorrectString() {
			//given
			int popularity = 60;
			Map<String, Object> expectedParameters = Map.of("popularity", popularity);
			Set<String> expectedQueryParameters = Set.of(
					"$popularity: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"popularity: $popularity"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).popularity(popularity).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_Search_ReturnCorrectString() {
			//given
			String search = "SearchString";
			Map<String, Object> expectedParameters = Map.of("search", search);
			Set<String> expectedQueryParameters = Set.of(
					"$search: String"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"search: $search"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).search(search).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdNot_ReturnCorrectString() {
			//given
			int idNot = 168943;
			Map<String, Object> expectedParameters = Map.of("id_not", idNot);
			Set<String> expectedQueryParameters = Set.of(
					"$id_not: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"id_not: $id_not"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idNot(idNot).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdInSingle_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953};
			Map<String, Object> expectedParameters = Map.of("id_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$id_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"id_in: $id_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdInMany_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953, 789654};
			Map<String, Object> expectedParameters = Map.of("id_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$id_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"id_in: $id_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdNotInSingle_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953};
			Map<String, Object> expectedParameters = Map.of("id_not_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$id_not_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"id_not_in: $id_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idNotIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdNotInMany_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953, 963258};
			Map<String, Object> expectedParameters = Map.of("id_not_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$id_not_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"id_not_in: $id_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idNotIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdMalNot_ReturnCorrectString() {
			//given
			int idNot = 168943;
			Map<String, Object> expectedParameters = Map.of("idMal_not", idNot);
			Set<String> expectedQueryParameters = Set.of(
					"$idMal_not: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"idMal_not: $idMal_not"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idMalNot(idNot).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdMalInSingle_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953};
			Map<String, Object> expectedParameters = Map.of("idMal_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$idMal_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"idMal_in: $idMal_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idMalIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdMalInMany_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953, 789654};
			Map<String, Object> expectedParameters = Map.of("idMal_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$idMal_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"idMal_in: $idMal_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idMalIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdMalNotInSingle_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953};
			Map<String, Object> expectedParameters = Map.of("idMal_not_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$idMal_not_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"idMal_not_in: $idMal_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idMalNotIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_IdMalNotInMany_ReturnCorrectString() {
			//given
			int[] ints = new int[] {264953, 963258};
			Map<String, Object> expectedParameters = Map.of("idMal_not_in", Arrays.toString(ints));
			Set<String> expectedQueryParameters = Set.of(
					"$idMal_not_in: [Int]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"idMal_not_in: $idMal_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).idMalNotIn(ints).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_StartDateGreater_ReturnCorrectString() {
			//given
			FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();
			Map<String, Object> expectedParameters = Map.of("startDate_greater", fuzzyDateValue.getFuzzyDateNumber());
			Set<String> expectedQueryParameters = Set.of(
					"$startDate_greater: FuzzyDateInt"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"startDate_greater: $startDate_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).startDateGreater(fuzzyDateValue).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_EndDateGreater_ReturnCorrectString() {
			//given
			FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();
			Map<String, Object> expectedParameters = Map.of("endDate_greater", fuzzyDateValue.getFuzzyDateNumber());
			Set<String> expectedQueryParameters = Set.of(
					"$endDate_greater: FuzzyDateInt"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"endDate_greater: $endDate_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).endDateGreater(fuzzyDateValue).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_StartDateLesser_ReturnCorrectString() {
			//given
			FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();
			Map<String, Object> expectedParameters = Map.of("startDate_lesser", fuzzyDateValue.getFuzzyDateNumber());
			Set<String> expectedQueryParameters = Set.of(
					"$startDate_lesser: FuzzyDateInt"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"startDate_lesser: $startDate_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).startDateLesser(fuzzyDateValue).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_EndDateLesser_ReturnCorrectString() {
			//given
			FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();
			Map<String, Object> expectedParameters = Map.of("endDate_lesser", fuzzyDateValue.getFuzzyDateNumber());
			Set<String> expectedQueryParameters = Set.of(
					"$endDate_lesser: FuzzyDateInt"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"endDate_lesser: $endDate_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).endDateLesser(fuzzyDateValue).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_FormatNot_ReturnCorrectString() {
			//given
			MediaFormat format = MediaFormat.TV;
			Map<String, Object> expectedParameters = Map.of("format_not", format);
			Set<String> expectedQueryParameters = Set.of(
					"$format_not: MediaFormat"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"format_not: $format_not"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).formatNot(format).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_FormatInSingle_ReturnCorrectString() {
			//given
			MediaFormat[] format = new MediaFormat[] {MediaFormat.TV};
			Map<String, Object> expectedParameters = Map.of("format_in", Arrays.toString(format));
			Set<String> expectedQueryParameters = Set.of(
					"$format_in: [MediaFormat]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"format_in: $format_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).formatIn(format).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_FormatInMany_ReturnCorrectString() {
			//given
			MediaFormat[] format = new MediaFormat[] {MediaFormat.TV, MediaFormat.OVA};
			Map<String, Object> expectedParameters = Map.of("format_in", Arrays.toString(format));
			Set<String> expectedQueryParameters = Set.of(
					"$format_in: [MediaFormat]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"format_in: $format_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).formatIn(format).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_FormatNotInSingle_ReturnCorrectString() {
			//given
			MediaFormat[] format = new MediaFormat[] {MediaFormat.TV};
			Map<String, Object> expectedParameters = Map.of("format_not_in", Arrays.toString(format));
			Set<String> expectedQueryParameters = Set.of(
					"$format_not_in: [MediaFormat]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"format_not_in: $format_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).formatNotIn(format).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_FormatNotInMany_ReturnCorrectString() {
			//given
			MediaFormat[] format = new MediaFormat[] {MediaFormat.TV, MediaFormat.OVA};
			Map<String, Object> expectedParameters = Map.of("format_not_in", Arrays.toString(format));
			Set<String> expectedQueryParameters = Set.of(
					"$format_not_in: [MediaFormat]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"format_not_in: $format_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).formatNotIn(format).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_StatusNot_ReturnCorrectString() {
			//given
			MediaStatus status = MediaStatus.FINISHED;
			Map<String, Object> expectedParameters = Map.of("status_not", status);
			Set<String> expectedQueryParameters = Set.of(
					"$status_not: MediaStatus"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"status_not: $status_not"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).statusNot(status).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_StatusInSignle_ReturnCorrectString() {
			//given
			MediaStatus[] status = new MediaStatus[] {MediaStatus.FINISHED};
			Map<String, Object> expectedParameters = Map.of("status_in", Arrays.toString(status));
			Set<String> expectedQueryParameters = Set.of(
					"$status_in: [MediaStatus]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"status_in: $status_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).statusIn(status).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_StatusInManyMany_ReturnCorrectString() {
			//given
			MediaStatus[] status = new MediaStatus[] {MediaStatus.FINISHED, MediaStatus.HIATUS};
			Map<String, Object> expectedParameters = Map.of("status_in", Arrays.toString(status));
			Set<String> expectedQueryParameters = Set.of(
					"$status_in: [MediaStatus]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"status_in: $status_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).statusIn(status).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_StatusNotInSignle_ReturnCorrectString() {
			//given
			MediaStatus[] status = new MediaStatus[] {MediaStatus.FINISHED};
			Map<String, Object> expectedParameters = Map.of("status_not_in", Arrays.toString(status));
			Set<String> expectedQueryParameters = Set.of(
					"$status_not_in: [MediaStatus]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"status_not_in: $status_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).statusNotIn(status).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_StatusNotInMany_ReturnCorrectString() {
			//given
			MediaStatus[] status = new MediaStatus[] {MediaStatus.FINISHED, MediaStatus.CANCELLED};
			Map<String, Object> expectedParameters = Map.of("status_not_in", Arrays.toString(status));
			Set<String> expectedQueryParameters = Set.of(
					"$status_not_in: [MediaStatus]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"status_not_in: $status_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).statusNotIn(status).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_EpisodeGreater_ReturnCorrectString() {
			//given
			int episodes = 12;
			Map<String, Object> expectedParameters = Map.of("episodes_greater", episodes);
			Set<String> expectedQueryParameters = Set.of(
					"$episodes_greater: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"episodes_greater: $episodes_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).episodesGreater(episodes).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_EpisodeLesser_ReturnCorrectString() {
			//given
			int episodes = 12;
			Map<String, Object> expectedParameters = Map.of("episodes_lesser", episodes);
			Set<String> expectedQueryParameters = Set.of(
					"$episodes_lesser: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"episodes_lesser: $episodes_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).episodesLesser(episodes).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_DurationGreater_ReturnCorrectString() {
			//given
			int duration = 12;
			Map<String, Object> expectedParameters = Map.of("duration_greater", duration);
			Set<String> expectedQueryParameters = Set.of(
					"$duration_greater: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"duration_greater: $duration_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).durationGreater(duration).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_DurationLesser_ReturnCorrectString() {
			//given
			int duration = 12;
			Map<String, Object> expectedParameters = Map.of("duration_lesser", duration);
			Set<String> expectedQueryParameters = Set.of(
					"$duration_lesser: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"duration_lesser: $duration_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).durationLesser(duration).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_ChaptersGreater_ReturnCorrectString() {
			//given
			int chapters = 12;
			Map<String, Object> expectedParameters = Map.of("chapters_greater", chapters);
			Set<String> expectedQueryParameters = Set.of(
					"$chapters_greater: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"chapters_greater: $chapters_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).chaptersGreater(chapters).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_ChaptersLesser_ReturnCorrectString() {
			//given
			int chapters = 12;
			Map<String, Object> expectedParameters = Map.of("chapters_lesser", chapters);
			Set<String> expectedQueryParameters = Set.of(
					"$chapters_lesser: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"chapters_lesser: $chapters_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).chaptersLesser(chapters).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_VolumesGreater_ReturnCorrectString() {
			//given
			int volumes = 12;
			Map<String, Object> expectedParameters = Map.of("volumes_greater", volumes);
			Set<String> expectedQueryParameters = Set.of(
					"$volumes_greater: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"volumes_greater: $volumes_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).volumesGreater(volumes).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_VolumesLesser_ReturnCorrectString() {
			//given
			int volumes = 12;
			Map<String, Object> expectedParameters = Map.of("volumes_lesser", volumes);
			Set<String> expectedQueryParameters = Set.of(
					"$volumes_lesser: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"volumes_lesser: $volumes_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).volumesLesser(volumes).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_GenreInSingle_ReturnCorrectString() {
			//given
			MediaGenre[] genre = new MediaGenre[] {MediaGenre.COMEDY};
			Map<String, Object> expectedParameters = Map.of("genre_in", Arrays.toString(genre));
			Set<String> expectedQueryParameters = Set.of(
					"$genre_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"genre_in: $genre_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).genreIn(genre).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_GenreInMany_ReturnCorrectString() {
			//given
			MediaGenre[] genre = new MediaGenre[] {MediaGenre.COMEDY, MediaGenre.MECHA};
			Map<String, Object> expectedParameters = Map.of("genre_in", Arrays.toString(genre));
			Set<String> expectedQueryParameters = Set.of(
					"$genre_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"genre_in: $genre_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).genreIn(genre).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_GenreNotInSingle_ReturnCorrectString() {
			//given
			MediaGenre[] genre = new MediaGenre[] {MediaGenre.COMEDY};
			Map<String, Object> expectedParameters = Map.of("genre_not_in", Arrays.toString(genre));
			Set<String> expectedQueryParameters = Set.of(
					"$genre_not_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"genre_not_in: $genre_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).genreNotIn(genre).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_GenreInNotMany_ReturnCorrectString() {
			//given
			MediaGenre[] genre = new MediaGenre[] {MediaGenre.COMEDY, MediaGenre.MECHA};
			Map<String, Object> expectedParameters = Map.of("genre_not_in", Arrays.toString(genre));
			Set<String> expectedQueryParameters = Set.of(
					"$genre_not_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"genre_not_in: $genre_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).genreNotIn(genre).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_TagInSingle_ReturnCorrectString() {
			//given
			MediaTags[] tags = new MediaTags[] {MediaTags.Meta};
			Map<String, Object> expectedParameters = Map.of("tag_in", Arrays.toString(tags));
			Set<String> expectedQueryParameters = Set.of(
					"$tag_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"tag_in: $tag_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).tagIn(tags).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_TagInMany_ReturnCorrectString() {
			//given
			MediaTags[] tags = new MediaTags[] {MediaTags.Meta, MediaTags.Go};
			Map<String, Object> expectedParameters = Map.of("tag_in", Arrays.toString(tags));
			Set<String> expectedQueryParameters = Set.of(
					"$tag_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"tag_in: $tag_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).tagIn(tags).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_TagNotInSingle_ReturnCorrectString() {
			//given
			MediaTags[] tags = new MediaTags[] {MediaTags.Meta};
			Map<String, Object> expectedParameters = Map.of("tag_not_in", Arrays.toString(tags));
			Set<String> expectedQueryParameters = Set.of(
					"$tag_not_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"tag_not_in: $tag_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).tagNotIn(tags).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_TagNotInMany_ReturnCorrectString() {
			//given
			MediaTags[] tags = new MediaTags[] {MediaTags.Meta, MediaTags.Go};
			Map<String, Object> expectedParameters = Map.of("tag_not_in", Arrays.toString(tags));
			Set<String> expectedQueryParameters = Set.of(
					"$tag_not_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"tag_not_in: $tag_not_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).tagNotIn(tags).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_LicensedByInSingle_ReturnCorrectString() {
			//given
			String[] licensedBy = new String[] {"CloverWorks"};
			Map<String, Object> expectedParameters = Map.of("licensedBy_in", Arrays.toString(licensedBy));
			Set<String> expectedQueryParameters = Set.of(
					"$licensedBy_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"licensedBy_in: $licensedBy_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).licensedByIn(licensedBy).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_LicensedByInMany_ReturnCorrectString() {
			//given
			String[] licensedBy = new String[] {"CloverWorks", "A1-Pictures"};
			Map<String, Object> expectedParameters = Map.of("licensedBy_in", Arrays.toString(licensedBy));
			Set<String> expectedQueryParameters = Set.of(
					"$licensedBy_in: [String]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"licensedBy_in: $licensedBy_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).licensedByIn(licensedBy).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_AverageScoreNot_ReturnCorrectString() {
			//given
			int averageScore = 5;
			Map<String, Object> expectedParameters = Map.of("averageScore_not", averageScore);
			Set<String> expectedQueryParameters = Set.of(
					"$averageScore_not: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"averageScore_not: $averageScore_not"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).averageScoreNot(averageScore).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_AverageScoreGreater_ReturnCorrectString() {
			//given
			int averageScore = 5;
			Map<String, Object> expectedParameters = Map.of("averageScore_greater", averageScore);
			Set<String> expectedQueryParameters = Set.of(
					"$averageScore_greater: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"averageScore_greater: $averageScore_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).averageScoreGreater(averageScore).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_AverageScoreLesser_ReturnCorrectString() {
			//given
			int averageScore = 5;
			Map<String, Object> expectedParameters = Map.of("averageScore_lesser", averageScore);
			Set<String> expectedQueryParameters = Set.of(
					"$averageScore_lesser: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"averageScore_lesser: $averageScore_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).averageScoreLesser(averageScore).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_PopularityNot_ReturnCorrectString() {
			//given
			int popularity = 60;
			Map<String, Object> expectedParameters = Map.of("popularity_not", popularity);
			Set<String> expectedQueryParameters = Set.of(
					"$popularity_not: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"popularity_not: $popularity_not"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).popularityNot(popularity).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_PopularityGreater_ReturnCorrectString() {
			//given
			int popularity = 60;
			Map<String, Object> expectedParameters = Map.of("popularity_greater", popularity);
			Set<String> expectedQueryParameters = Set.of(
					"$popularity_greater: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"popularity_greater: $popularity_greater"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).popularityGreater(popularity).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_PopularityLesser_ReturnCorrectString() {
			//given
			int popularity = 60;
			Map<String, Object> expectedParameters = Map.of("popularity_lesser", popularity);
			Set<String> expectedQueryParameters = Set.of(
					"$popularity_lesser: Int"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"popularity_lesser: $popularity_lesser"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).popularityLesser(popularity).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_SourceInSingle_ReturnCorrectString() {
			//given
			MediaSource[] source = new MediaSource[] {MediaSource.LIGHT_NOVEL};
			Map<String, Object> expectedParameters = Map.of("source_in", Arrays.toString(source));
			Set<String> expectedQueryParameters = Set.of(
					"$source_in: [MediaSource]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"source_in: $source_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).sourceIn(source).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_SourceInMany_ReturnCorrectString() {
			//given
			MediaSource[] source = new MediaSource[] {MediaSource.LIGHT_NOVEL, MediaSource.ANIME};
			Map<String, Object> expectedParameters = Map.of("source_in", Arrays.toString(source));
			Set<String> expectedQueryParameters = Set.of(
					"$source_in: [MediaSource]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"source_in: $source_in"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).sourceIn(source).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_SortSingle_ReturnCorrectString() {
			//given
			MediaSort[] sorts = new MediaSort[] {MediaSort.ID};
			Map<String, Object> expectedParameters = Map.of("sort", Arrays.toString(sorts));
			Set<String> expectedQueryParameters = Set.of(
					"$sort: [MediaSort]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: $sort"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).sort(sorts).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}

		@Test
		void mediaBuilder_SortInMany_ReturnCorrectString() {
			//given
			MediaSort[] sorts = new MediaSort[] {MediaSort.ID, MediaSort.SCORE};
			Map<String, Object> expectedParameters = Map.of("sort", Arrays.toString(sorts));
			Set<String> expectedQueryParameters = Set.of(
					"$sort: [MediaSort]"
			);
			Set<ParameterString> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: $sort"
			);


			//when
			Media media = Media.getMediaArgumentBuilder(field).sort(sorts).build();

			//then
			assertThat(media.getElementString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleWithArgumentsAndParameters(MEDIA_TITLE, expectedArguments, expectedField)
			));

			assertThat(media.getQueryParameters(), allOf(
					notNullValue(),
					instanceOf(Set.class),
					containsInAnyOrder(expectedQueryParameters.toArray())
			));

			assertThat(media.getVariables(), allOf(
					notNullValue(),
					instanceOf(Map.class),
					equalTo(expectedParameters)
			));
		}
	}
}