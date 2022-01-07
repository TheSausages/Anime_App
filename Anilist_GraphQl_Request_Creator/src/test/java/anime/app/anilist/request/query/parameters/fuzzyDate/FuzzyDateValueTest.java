package anime.app.anilist.request.query.parameters.fuzzyDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FuzzyDateValueTest {

	@Test
	void getFuzzyDateValueBuilder() {
		//given

		//when
		FuzzyDateValue.FuzzyDateValueBuilder builder = FuzzyDateValue.getFuzzyDateValueBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(FuzzyDateValue.FuzzyDateValueBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Fuzzy Date Value Builder")
	class FuzzyDateValueBuilderTest {
		@Nested
		@DisplayName("Test Exceptions for Fuzzy Date Value Builder")
		class FuzzyDateValueBuilderExceptionTest {
			@Test
			void fuzzyDateValueBuilder_NoParameterSelected_ThrowException() {
				//given

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder().build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_YearTooSmall_ReturnValidString() {
				//given
				int year = 1;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder().year(year).build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_YearTooBig_ReturnValidString() {
				//given
				int year = 123456789;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder().year(year).build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_MonthTooSmall_ReturnValidString() {
				//given
				int year = 2022;
				int month = -1;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder()
								.year(year)
								.month(month)
								.build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_MonthTooBig_ReturnValidString() {
				//given
				int year = 2022;
				int month = 13;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder()
								.year(year)
								.month(month)
								.build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_DayTooSmall_ReturnValidString() {
				//given
				int year = 2022;
				int month = 10;
				int day  = -1;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder()
								.year(year)
								.month(month)
								.day(day)
								.build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_DayTooBig_ReturnValidString() {
				//given
				int year = 2022;
				int month = 10;
				int day  = 45;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder()
								.year(year)
								.month(month)
								.day(day)
								.build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_DayTooBigForNonLeapYear_ReturnValidString() {
				//given
				int year = 2021;
				int month = 2;
				int day  = 29;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder()
								.year(year)
								.month(month)
								.day(day)
								.build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}

			@Test
			void fuzzyDateValueBuilder_DayTooBigForLeapYear_ReturnValidString() {
				//given
				int year = 2020;
				int month = 2;
				int day  = 30;

				//when
				Exception thrownException = Assertions.assertThrows(
						IllegalStateException.class,
						() -> FuzzyDateValue.getFuzzyDateValueBuilder()
								.year(year)
								.month(month)
								.day(day)
								.build()
				);

				//then
				assertThat(thrownException, instanceOf(IllegalStateException.class));
				assertThat(thrownException.getMessage(), notNullValue());
			}
		}

		@Test
		void fuzzyDateValueBuilder_Year_ReturnValidString() {
			//given
			int year = 2022;
			int expectedValue = 20220000;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_CurrentYear_ReturnValidString() {
			//given
			int year = LocalDateTime.now().getYear();
			int expectedValue = year * 10000;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.currentYear()
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_MonthUsingNumber_ReturnValidString() {
			//given
			int year = 2022;
			int month = 8;
			int expectedValue = 20220800;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.month(month)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_MonthUsingObject_ReturnValidString() {
			//given
			int year = 2022;
			int expectedValue = 20221200;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.month(Month.DECEMBER)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_CurrentMonth_ReturnValidString() {
			//given
			int year = 2022;
			//Add the current month value to the number
			int expectedValue = 20220000 + (LocalDateTime.now().getMonthValue() * 100);

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.currentMonth()
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_Day_ReturnValidString() {
			//given
			int year = 2022;
			int month = 8;
			int day = 15;
			int expectedValue = 20220815;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.month(month)
					.day(day)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_MaxDayWhenLeapYear_ReturnValidString() {
			//given
			int year = 2020;
			int month = 2;
			int day = 29;
			int expectedValue = 20200229;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.month(month)
					.day(day)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_MaxDayWhenNoLeapYear_ReturnValidString() {
			//given
			int year = 2021;
			int month = 2;
			int day = 28;
			int expectedValue = 20210228;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.month(month)
					.day(day)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_CurrentDay_ReturnValidString() {
			//given
			int year = 2022;
			int month = 8;
			int day = LocalDateTime.now().getDayOfMonth();
			int expectedValue = 20220800 + day;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.year(year)
					.month(month)
					.currentDay()
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_FromLocalDateTime_ReturnValidString() {
			//given
			LocalDateTime dateTime = LocalDateTime.of(2021, 10, 23, 0, 0);
			int expectedValue = 20211023;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(dateTime)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_FromLocalDate_ReturnValidString() {
			//given
			LocalDate dateTime = LocalDate.of(2021, 10, 23);
			int expectedValue = 20211023;

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(dateTime)
					.build();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}

		@Test
		void fuzzyDateValueBuilder_Now_ReturnValidString() {
			//given
			//because we don't use time at all, we can just use the LocalDate
			LocalDate dateTime = LocalDate.now();
			int expectedValue = dateTime.getYear() * 10000 + dateTime.getMonthValue()  * 100 + dateTime.getDayOfMonth();

			//when
			FuzzyDateValue actualValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();

			//then
			assertThat(actualValue.getFuzzyDateNumber(), allOf(
					notNullValue(),
					instanceOf(int.class),
					equalTo(expectedValue)
			));
		}
	}
}