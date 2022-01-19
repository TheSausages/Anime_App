package anime.app.anilist.request.query.parameters.connections.staff;

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

class StaffArgumentsTest {

	@Test
	void getStaffArgumentsBuilder__ReturnValidBuilder() {
		//given

		//when
		StaffArguments.StaffArgumentsBuilder builder = StaffArguments.getStaffArgumentsBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StaffArguments.StaffArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Staff Arguments Builder")
	class StaffArgumentsBuilderTest {
		@Test
		void staffArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StaffArguments.getStaffArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void staffArgumentsBuilder_SortSingle_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + Arrays.toString(sorts)
			);

			//when
			StaffArguments actualArguments = StaffArguments.getStaffArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getStaffArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffArgumentsBuilder_SortMultiple_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ID_DESC};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + Arrays.toString(sorts)
			);

			//when
			StaffArguments actualArguments = StaffArguments.getStaffArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getStaffArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"page: " + page
			);

			//when
			StaffArguments actualArguments = StaffArguments.getStaffArgumentsBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getStaffArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"perPage: " + perPage
			);

			//when
			StaffArguments actualArguments = StaffArguments.getStaffArgumentsBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getStaffArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			int page = 1;
			int perPage = 30;
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ID_DESC};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"page: " + page,
					"perPage: " + perPage,
					"sort: " + Arrays.toString(sorts)
			);

			//when
			StaffArguments actualArguments = StaffArguments.getStaffArgumentsBuilder()
					.page(page)
					.perPage(perPage)
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getStaffArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}