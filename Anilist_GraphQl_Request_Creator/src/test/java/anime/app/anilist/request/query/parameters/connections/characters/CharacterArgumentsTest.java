package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.ParameterString;
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

class CharacterArgumentsTest {

	@Test
	void getCharacterArgumentsBuilder__ReturnValidBuilder() {
		//given

		//when
		CharacterArguments.CharacterArgumentsBuilder builder = CharacterArguments.getCharacterArgumentsBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(CharacterArguments.CharacterArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Character Arguments Builder")
	class CharacterArgumentsBuilderTest {
		@Test
		void characterArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> CharacterArguments.getCharacterArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void characterArgumentsBuilder_CharacterSortSingle_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID};
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("sort: " + Arrays.toString(sorts));

			//when
			CharacterArguments actualArguments = CharacterArguments.getCharacterArgumentsBuilder()
					.sortBy(sorts)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void characterArgumentsBuilder_CharacterSortMany_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID, CharacterSort.ROLE};
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"sort: " + Arrays.toString(sorts)
			);

			//when
			CharacterArguments actualArguments = CharacterArguments.getCharacterArgumentsBuilder()
					.sortBy(sorts)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void characterArgumentsBuilder_CharacterRole_ReturnCorrectString() {
			//given
			CharacterRole role = CharacterRole.MAIN;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("role: " + role.name());

			//when
			CharacterArguments actualArguments = CharacterArguments.getCharacterArgumentsBuilder()
					.role(role)
					.build();

			//then
			assertThat(actualArguments.getCharacterArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void characterArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("page: " + page);

			//when
			CharacterArguments actualArguments = CharacterArguments.getCharacterArgumentsBuilder()
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
		void characterArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 1;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("perPage: " + perPage);

			//when
			CharacterArguments actualArguments = CharacterArguments.getCharacterArgumentsBuilder()
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
		void characterArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			CharacterSort[] sorts = new CharacterSort[] {CharacterSort.ID, CharacterSort.ROLE};
			CharacterRole role = CharacterRole.MAIN;
			int page = 1;
			int perPage = 1;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"sort: " + Arrays.toString(sorts),
					"role: " + role.name(),
					"page: " + page,
					"perPage: " + perPage
			);

			//when
			CharacterArguments actualArguments = CharacterArguments.getCharacterArgumentsBuilder()
					.sortBy(sorts)
					.role(role)
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