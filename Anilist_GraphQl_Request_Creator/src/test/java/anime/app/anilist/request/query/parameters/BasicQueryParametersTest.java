package anime.app.anilist.request.query.parameters;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BasicQueryParametersTest {

	@Test
	void getType_DefaultParameterType_ReturnSameValue() {
		//given
		String expectedValue = "MediaFormat";
		BasicQueryParameters basicParam = BasicQueryParameters.format;

		//when
		String value = basicParam.getType();

		//then
		assertThat(value, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedValue)
		));
	}

	@Test
	void getType_Int_ReturnCorrectValue() {
		//given
		String expectedValue = "int";
		BasicQueryParameters basicParam = BasicQueryParameters.averageScore;

		//when
		String value = basicParam.getType();

		//then
		assertThat(value, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedValue)
		));
	}

	@Test
	void getType_Boolean_ReturnCorrectValue() {
		//given
		String expectedValue = "int";
		BasicQueryParameters basicParam = BasicQueryParameters.averageScore;

		//when
		String value = basicParam.getType();

		//then
		assertThat(value, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedValue)
		));
	}

	@Test
	void getType_IntArray_ReturnCorrectValue() {
		//given
		String expectedValue = "int";
		BasicQueryParameters basicParam = BasicQueryParameters.averageScore;

		//when
		String value = basicParam.getType();

		//then
		assertThat(value, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedValue)
		));
	}
}