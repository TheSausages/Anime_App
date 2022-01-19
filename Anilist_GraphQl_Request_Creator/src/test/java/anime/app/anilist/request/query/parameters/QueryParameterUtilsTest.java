package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.common.ParameterString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class QueryParameterUtilsTest {
	@Nested
	@DisplayName("Query Field Element String Test")
	class QueryFieldElementStringTests {
		@Test
		void buildQueryFieldElementString_NullTitle_ThrowException() {
			//given
			Set<ParameterString> parameterStrings = Set.of(ParameterString.fromString("element"));
			String title = null;

			//when
			Exception thrownException = Assertions.assertThrows(
					NullPointerException.class,
					() -> QueryParameterUtils.buildStringField(title, parameterStrings)
			);

			//then
			assertThat(thrownException, instanceOf(NullPointerException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void buildQueryFieldElementString_NullParameters_ThrowException() {
			//given
			Set<ParameterString> parameterStrings = null;
			String name =  "Title";

			//when
			Exception thrownException = Assertions.assertThrows(
					NullPointerException.class,
					() -> QueryParameterUtils.buildStringField(name, parameterStrings)
			);

			//then
			assertThat(thrownException, instanceOf(NullPointerException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void buildQueryFieldElementString_SingleParameter_ReturnValidString() {
			//given
			Set<ParameterString> parameterStrings = Set.of(ParameterString.fromString("element"));
			String title =  "Title";

			//when
			String actualString = QueryParameterUtils.buildStringField(title, parameterStrings);

			//then
			assertThat(actualString, allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(title, parameterStrings)
			));
		}

		@Test
		void buildQueryFieldElementString_MultipleParameter_ReturnValidString() {
			//given
			Set<ParameterString> parameterStrings = Set.of(
					ParameterString.fromString("firstElement"),
					ParameterString.fromString("secondElement")
			);
			String title =  "Title";

			//when
			String actualString = QueryParameterUtils.buildStringField(title, parameterStrings);

			//then
			assertThat(actualString, allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(title, parameterStrings)
			));
		}
	}

	@Nested
	@DisplayName("Query Field Element String Test")
	class QueryFieldElementArgumentsStringTests {
		@Test
		void buildString_NullArguments_ThrowException() {
			//given
			Set<ParameterString> argumentStrings = null;

			//when
			Exception thrownException = Assertions.assertThrows(
					NullPointerException.class,
					() -> QueryParameterUtils.buildStringArguments(argumentStrings)
			);

			//then
			assertThat(thrownException, instanceOf(NullPointerException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void buildString_SingleArgument_ReturnValidString() {
			//given
			Set<ParameterString> argumentStrings = Set.of(ParameterString.fromString("element: true"));

			//when
			String actualString = QueryParameterUtils.buildStringArguments(argumentStrings);

			//then
			assertThat(actualString, allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(argumentStrings)
			));
		}

		@Test
		void buildString_MultipleArgument_ReturnValidString() {
			//given
			Set<ParameterString> argumentStrings = Set.of(
					ParameterString.fromString("firstElement: true"),
					ParameterString.fromString("secondElement: 5")
			);

			//when
			String actualString = QueryParameterUtils.buildStringArguments(argumentStrings);

			//then
			assertThat(actualString, allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(argumentStrings)
			));
		}
	}
}