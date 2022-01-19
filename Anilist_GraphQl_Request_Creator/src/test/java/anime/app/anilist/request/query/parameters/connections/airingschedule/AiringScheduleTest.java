package anime.app.anilist.request.query.parameters.connections.airingschedule;

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

import static anime.app.anilist.request.query.parameters.connections.airingschedule.AiringSchedule.AIRING_SCHEDULE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AiringScheduleTest {

	@Test
	void getAiringScheduleStringWithoutFieldName__ReturnCorrectString() {
		//given
		List<String> expectedSchedule = TestUtils.buildFieldParameterStringSet("id");

		//when
		String actualString = AiringSchedule.getAiringScheduleBuilder()
				.id()
				.build()
				.getAiringScheduleStringWithoutFieldName();

		//then
		assertThat(actualString, allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedSchedule)
		));
	}

	@Test
	void getAiringScheduleBuilder__ReturnValidBuilder() {
		//given

		//when
		AiringSchedule.AiringScheduleBuilder builder = AiringSchedule.getAiringScheduleBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(AiringSchedule.AiringScheduleBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Airing Schedule Builder")
	class AiringScheduleBuilderTest {
		@Test
		void airingScheduleBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> AiringSchedule.getAiringScheduleBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void airingScheduleBuilder_Id_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("id");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().id().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_TITLE, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_AiringAt_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("airingAt");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().airingAt().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_TITLE, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_TimeUntilAiring_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("timeUntilAiring");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().timeUntilAiring().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_TITLE, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_Episode_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("episode");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().episode().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_TITLE, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_MediaId_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("mediaId");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().mediaId().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_TITLE, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_Media_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedString = TestUtils.buildFieldParameterStringSet(
					QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.MEDIA, media.getRequestedMediaFields()).getField()
			);

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder()
					.media(media)
					.build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_TITLE, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_All_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet(
					"id",
					"airingAt",
					"timeUntilAiring",
					"episode",
					"mediaId"
			);

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder()
					.id()
					.airingAt()
					.timeUntilAiring()
					.episode()
					.mediaId()
					.build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_TITLE, expectedString)
			));
		}
	}
}