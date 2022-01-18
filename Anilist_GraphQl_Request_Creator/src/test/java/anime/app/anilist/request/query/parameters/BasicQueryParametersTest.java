package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BasicQueryParametersTest {

	@Test
	void getField__ReturnCorrectField() {
		//given
		BasicQueryParameters parameter = BasicQueryParameters.ID;
		String expectedField = "id";

		//when
		String actualField = parameter.getFieldName();

		//then
		assertThat(actualField, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedField)
		));
	}
}