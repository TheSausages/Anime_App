package anime.app.anilist.request.query.parameters.fuzzyDate;

import anime.app.anilist.request.query.parameters.media.MediaRank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class FuzzyDateFieldTest {
	@Test
	void getFuzzyDateFieldBuilder_NullParameter_ThrowException() {
		//given
		FuzzyDateFieldParameter parameter = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void getFuzzyDateFieldBuilder_ValidParameter_ThrowException() {
		//given
		FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;

		//when
		FuzzyDateField.FuzzyDateFieldBuilder builder = FuzzyDateField.getFuzzyDateFieldBuilder(parameter);

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(FuzzyDateField.FuzzyDateFieldBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Rank Builder")
	class MediaRankBuilderTest {
		@Test
		void fuzzyDateFieldBuilder_NoParameterSelected_ThrowException() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> FuzzyDateField.getFuzzyDateFieldBuilder(parameter).build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void fuzzyDateFieldBuilder_Year_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;
			String expectedFuzzyDateField = "dateOfBirth {\nyear\n}";

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.year()
					.build();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_Month_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;
			String expectedFuzzyDateField = "dateOfBirth {\nmonth\n}";

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.month()
					.build();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_Day_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;
			String expectedFuzzyDateField = "dateOfBirth {\nday\n}";

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.day()
					.build();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_AllUsingSingleMethod_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;
			String expectedFuzzyDateField = "dateOfBirth {\nyear\nmonth\nday\n}";

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.allAndBuild();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_AllUsingMultipleMethods_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;
			String expectedFuzzyDateField = "dateOfBirth {\nyear\nmonth\nday\n}";

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.year()
					.month()
					.day()
					.build();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedFuzzyDateField)
			));
		}
	}
}