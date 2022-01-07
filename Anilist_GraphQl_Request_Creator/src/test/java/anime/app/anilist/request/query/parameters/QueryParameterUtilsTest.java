package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.common.ParameterString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class QueryParameterUtilsTest {
	@Test
	void buildString_NullTitle_ThrowException() {
		//given
		Set<ParameterString> parameterStrings = Set.of(ParameterString.fromString("element"));
		String title = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> QueryParameterUtils.buildString(title, parameterStrings)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void buildString_NullParameters_ThrowException() {
		//given
		Set<ParameterString> parameterStrings = null;
		String name =  "Title";

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> QueryParameterUtils.buildString(name, parameterStrings)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void buildString_SingleParameter_ReturnValidString() {
		//given
		Set<ParameterString> parameterStrings = Set.of(ParameterString.fromString("element"));
		String name =  "Title";
		String expectedString = "Title {\nelement\n}";

		//when
		String actualString = QueryParameterUtils.buildString(name, parameterStrings);

		//then
		assertThat(actualString, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedString)
		));
	}

	@Test
	void buildString_MultipleParameter_ReturnValidString() {
		//given
		Set<ParameterString> parameterStrings = Set.of(
				ParameterString.fromString("firstElement"),
				ParameterString.fromString("secondElement")
		);
		String title =  "Title";
		String expectedString = "Title {\nfirstElement\nsecondElement\n}";

		//when
		String actualString = QueryParameterUtils.buildString(title, parameterStrings);

		//then
		assertThat(actualString, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedString)
		));
	}
}