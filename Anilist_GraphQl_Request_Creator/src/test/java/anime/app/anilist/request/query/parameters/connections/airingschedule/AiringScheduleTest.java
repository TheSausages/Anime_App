package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.utils.QueryTitleAndParametersMatcher;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.airingschedule.AiringSchedule.airingScheduleTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AiringScheduleTest {

	@Test
	void getAiringScheduleStringWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedSchedule = TestUtils.getParameterStringSet("id");

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
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet("id");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().id().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(airingScheduleTitle, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_AiringAt_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet("airingAt");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().airingAt().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(airingScheduleTitle, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_TimeUntilAiring_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet("timeUntilAiring");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().timeUntilAiring().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(airingScheduleTitle, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_Episode_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet("episode");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().episode().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(airingScheduleTitle, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_MediaId_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet("mediaId");

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder().mediaId().build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(airingScheduleTitle, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_Media_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSetWithDivide(
					QueryTitleAndParametersMatcher.divider,
					"media {\nformat\n}"
			);

			//when
			AiringSchedule actualSchedule = AiringSchedule.getAiringScheduleBuilder()
					.media(new Media("media(id: ${id}) {\nformat\n}"))
					.build();

			//then
			assertThat(actualSchedule.getAiringScheduleString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(airingScheduleTitle, expectedString)
			));
		}

		@Test
		void airingScheduleBuilder_All_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
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
					containsTitleAndAllSetElements(airingScheduleTitle, expectedString)
			));
		}
	}
}