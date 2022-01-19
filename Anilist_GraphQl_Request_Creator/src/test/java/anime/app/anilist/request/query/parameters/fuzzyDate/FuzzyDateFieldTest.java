package anime.app.anilist.request.query.parameters.fuzzyDate;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FuzzyDateFieldTest {

	@Test
	void getFuzzyDateFieldBuilder_NoParameter_ReturnCorrectBuilder() {
		//given

		//when
		FuzzyDateField.FuzzyDateFieldBuilder builder = FuzzyDateField.getFuzzyDateFieldBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(FuzzyDateField.FuzzyDateFieldBuilder.class)
		));
	}

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
	void getFuzzyDateFieldBuilder_ValidParameter_ReturnCorrectBuilder() {
		//given
		FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;

		//when
		FuzzyDateField.FuzzyDateFieldBuilder builder = FuzzyDateField.getFuzzyDateFieldBuilder(parameter);

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(FuzzyDateField.FuzzyDateFieldBuilder.class)
		));
	}

	@Test
	void getFuzzyDateStringWithoutFieldName_ValidParameter_ReturnCorrectString() {
		//given
		FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;
		Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("year");

		//when
		FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter).year().build();

		//then
		assertThat(actualFuzzyDateField.getFuzzyDateStringWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedFuzzyDateField)
		));
	}

	@Nested
	@DisplayName("Test Fuzzy Date Field Builder")
	class FuzzyDateFieldBuilderTest {
		@Test
		void fuzzyDateFieldBuilder_NoParameterSelected_ThrowException() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;

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
		void fuzzyDateFieldBuilder_YearWithoutParameter_ReturnValidString() {
			//given
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("year");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.year()
					.build();

			//then
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_YearWithParameter_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("year");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.year()
					.build();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(parameter.getFieldName(), expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_MonthWithoutParameter_ReturnValidString() {
			//given
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("month");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.month()
					.build();

			//then
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_MonthWithParameter_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("month");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.month()
					.build();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(parameter.getFieldName(), expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_DayWithoutParameter_ReturnValidString() {
			//given
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("day");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.day()
					.build();

			//then
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_DayWithParameter_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("day");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.day()
					.build();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(parameter.getFieldName(), expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_AllUsingSingleMethodWithoutParameter_ReturnValidString() {
			//given
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("year", "month", "day");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.allAndBuild();

			//then
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_AllUsingSingleMethodWithParameter_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("year", "month", "day");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
					.allAndBuild();

			//then
			assertThat(actualFuzzyDateField.getParameter(), equalTo(parameter));
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(parameter.getFieldName(), expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_AllUsingMultipleMethodsWithoutParameter_ReturnValidString() {
			//given
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("year", "month", "day");

			//when
			FuzzyDateField actualFuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.year()
					.month()
					.day()
					.build();

			//then
			assertThat(actualFuzzyDateField.getFuzzyDateString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedFuzzyDateField)
			));
		}

		@Test
		void fuzzyDateFieldBuilder_AllUsingMultipleMethodsWithParameter_ReturnValidString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.DATE_OF_BIRTH;
			Set<ParameterString> expectedFuzzyDateField = TestUtils.getParameterStringSetField("year", "month", "day");

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
					containsTitleAndAllSetElements(parameter.getFieldName(), expectedFuzzyDateField)
			));
		}
	}
}