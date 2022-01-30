package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ReviewArgumentsTest {

	@Test
	void getReviewArgumentsBuilder__ReturnValidBuilder() {
		//given

		//when
		ReviewArguments.ReviewArgumentsBuilder builder = ReviewArguments.getReviewArgumentsBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(ReviewArguments.ReviewArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Review Arguments Builder")
	class ReviewArgumentsBuilderTest {
		@Test
		void reviewArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> ReviewArguments.getReviewArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void reviewArgumentsBuilder_SortSingle_ReturnCorrectString() {
			//given
			ReviewSort[] sorts = new ReviewSort[] {ReviewSort.ID};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + Arrays.toString(sorts)
			);

			//when
			ReviewArguments actualArguments = ReviewArguments.getReviewArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getReviewArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void reviewArgumentsBuilder_SortMany_ReturnCorrectString() {
			//given
			ReviewSort[] sorts = new ReviewSort[] {ReviewSort.ID, ReviewSort.RATING};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + Arrays.toString(sorts)
			);

			//when
			ReviewArguments actualArguments = ReviewArguments.getReviewArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getReviewArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void reviewArgumentsBuilder_Limit_ReturnCorrectString() {
			//given
			int limit = 10;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"limit: " + limit
			);

			//when
			ReviewArguments actualArguments = ReviewArguments.getReviewArgumentsBuilder()
					.limit(limit)
					.build();

			//then
			assertThat(actualArguments.getReviewArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void reviewArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"page: " + page
			);

			//when
			ReviewArguments actualArguments = ReviewArguments.getReviewArgumentsBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getReviewArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void reviewArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 30;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"perPage: " + perPage
			);

			//when
			ReviewArguments actualArguments = ReviewArguments.getReviewArgumentsBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getReviewArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void reviewArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			int limit = 10;
			int page = 1;
			int perPage = 30;
			ReviewSort[] sorts = new ReviewSort[] {ReviewSort.ID, ReviewSort.RATING};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + Arrays.toString(sorts),
					"limit: " + limit,
					"page: " + page,
					"perPage: " + perPage
			);

			//when
			ReviewArguments actualArguments = ReviewArguments.getReviewArgumentsBuilder()
					.sort(sorts)
					.limit(limit)
					.page(page)
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getReviewArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}