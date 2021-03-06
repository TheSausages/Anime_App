package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RecommendationArgumentsTest {

	@Test
	void getRecommendationArgumentBuilder__ReturnValidBuilder() {
		//given

		//when
		RecommendationArguments.RecommendationArgumentsBuilder builder = RecommendationArguments.getRecommendationArgumentBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(RecommendationArguments.RecommendationArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Recommendation Argument Builder")
	class RecommendationArgumentsBuilderTest {
		@Test
		void recommendationArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> RecommendationArguments.getRecommendationArgumentBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void recommendationArgumentsBuilder_SortSingle_ReturnCorrectString() {
			//given
			RecommendationSort[] sorts = new RecommendationSort[] {RecommendationSort.ID};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + sorts
			);

			//when
			RecommendationArguments actualArguments = RecommendationArguments.getRecommendationArgumentBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getRecommendationArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void recommendationArgumentsBuilder_SortMany_ReturnCorrectString() {
			//given
			RecommendationSort[] sorts = new RecommendationSort[] {RecommendationSort.ID, RecommendationSort.RATING};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + sorts
			);

			//when
			RecommendationArguments actualArguments = RecommendationArguments.getRecommendationArgumentBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getRecommendationArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void recommendationArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"page: " + page
			);

			//when
			RecommendationArguments actualArguments = RecommendationArguments.getRecommendationArgumentBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getRecommendationArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void recommendationArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"perPage: " + perPage
			);

			//when
			RecommendationArguments actualArguments = RecommendationArguments.getRecommendationArgumentBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getRecommendationArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void recommendationArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			RecommendationSort[] sorts = new RecommendationSort[] {RecommendationSort.ID, RecommendationSort.RATING};
			int page = 1;
			int perPage = 10;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + sorts,
					"page: " + page,
					"perPage: " + perPage
			);

			//when
			RecommendationArguments actualArguments = RecommendationArguments.getRecommendationArgumentBuilder()
					.sort(sorts)
					.page(page)
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getRecommendationArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}