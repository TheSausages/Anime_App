package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AiringScheduleArgumentsTest {

	@Test
	void getAiringScheduleArgumentsBuilder__ReturnValidBuilder() {
		//given

		//when
		AiringScheduleArguments.AiringScheduleArgumentsBuilder builder = AiringScheduleArguments.getAiringScheduleArgumentsBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(AiringScheduleArguments.AiringScheduleArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Airing Schedule Builder")
	class AiringScheduleBuilderTest {
		@Test
		void airingScheduleArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> AiringScheduleArguments.getAiringScheduleArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void airingScheduleArgumentsBuilder_NotYetAiredWithoutParameter_ReturnCorrectString() {
			//given
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("notYetAired: true");

			//when
			AiringScheduleArguments actualArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
					.notYetAired()
					.build();

			//then
			assertThat(actualArguments.getAiringScheduleArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void airingScheduleArgumentsBuilder_NotYetAiredWithParameter_ReturnCorrectString() {
			//given
			boolean notYetAired = false;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("notYetAired: " + notYetAired);

			//when
			AiringScheduleArguments actualArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
					.notYetAired(notYetAired)
					.build();

			//then
			assertThat(actualArguments.getAiringScheduleArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void airingScheduleArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("page: " + page);

			//when
			AiringScheduleArguments actualArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getAiringScheduleArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void airingScheduleArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 30;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("perPage: " + perPage);

			//when
			AiringScheduleArguments actualArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getAiringScheduleArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void airingScheduleArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			boolean notYetAired = true;
			int page = 1;
			int perPage = 30;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"notYetAired: " + notYetAired,
					"page: " + page,
					"perPage: " + perPage
			);

			//when
			AiringScheduleArguments actualArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
					.notYetAired(notYetAired)
					.page(page)
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getAiringScheduleArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}