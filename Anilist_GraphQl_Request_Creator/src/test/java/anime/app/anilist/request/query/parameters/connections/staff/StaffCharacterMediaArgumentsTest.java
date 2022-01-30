package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.parameters.connections.characters.CharacterSort;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StaffCharacterMediaArgumentsTest {

	@Test
	void getStaffCharacterMediaArgumentsBuilder() {
		//given

		//when
		StaffCharacterMediaArguments.StaffCharacterMediaArgumentsBuilder builder = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder();


		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StaffCharacterMediaArguments.StaffCharacterMediaArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Staff Character Media Arguments Builder")
	class StaffCharacterMediaArgumentsBuilderTest {
		@Test
		void staffCharacterMediaArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void staffCharacterMediaArgumentsBuilder_SortSingle_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + sorts
			);

			//when
			StaffCharacterMediaArguments actualArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getCharacterMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharactersArgumentsBuilder_SortMany_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID, CharacterSort.ROLE};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + sorts
			);

			//when
			StaffCharacterMediaArguments actualArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getCharacterMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharacterMediaArgumentsBuilder_OnListNoArgument_ReturnCorrectString() {
			//given
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"onList: true"
			);

			//when
			StaffCharacterMediaArguments actualArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.onList()
					.build();

			//then
			assertThat(actualArguments.getCharacterMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharacterMediaArgumentsBuilder_OnListWithArgument_ReturnCorrectString() {
			//given
			boolean onList = false;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"onList: " + onList
			);

			//when
			StaffCharacterMediaArguments actualArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.onList(onList)
					.build();

			//then
			assertThat(actualArguments.getCharacterMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharacterMediaArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"page: " + page
			);

			//when
			StaffCharacterMediaArguments actualArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getCharacterMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void staffCharacterMediaArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"perPage: " + perPage
			);

			//when
			StaffCharacterMediaArguments actualArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getCharacterMediaArgumentsString(), allOf(
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
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + sorts,
					"page: " + page,
					"perPage: " + perPage
			);

			//when
			StaffCharacterMediaArguments actualArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.sort(sorts)
					.page(page)
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getCharacterMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}