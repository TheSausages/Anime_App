package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterSort;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StaffCharactersArgumentsTest {

	@Test
	void getStaffCharactersArgumentsBuilder__ReturnValidBuilder() {
		//given

		//when
		StaffCharactersArguments.StaffCharactersArgumentsBuilder builder = StaffCharactersArguments.getStaffCharactersArgumentsBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StaffCharactersArguments.StaffCharactersArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Staff Characters Arguments Builder")
	class StaffCharactersArgumentsBuilderTest {
		@Test
		void staffCharactersArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StaffCharactersArguments.getStaffCharactersArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void staffCharactersArgumentsBuilder_SortSingle_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID};
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"sort: " + Arrays.toString(sorts)
			);

			//when
			StaffCharactersArguments actualArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharactersArgumentsBuilder_SortMany_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID, CharacterSort.ROLE};
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"sort: " + Arrays.toString(sorts)
			);

			//when
			StaffCharactersArguments actualArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharactersArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"page: " + page
			);

			//when
			StaffCharactersArguments actualArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharactersArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 1;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"perPage: " + perPage
			);

			//when
			StaffCharactersArguments actualArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharactersArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID, CharacterSort.ROLE};
			int page = 1;
			int perPage = 1;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"sort: " + Arrays.toString(sorts),
					"page: " + page,
					"perPage: " + perPage
			);

			//when
			StaffCharactersArguments actualArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
					.sort(sorts)
					.page(page)
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}