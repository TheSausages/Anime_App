package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationExceptionTest {
	@Test
	void validationException_NullTranslationKey_Exception() {
		//given
		String translationKey = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				ValidationException.builder()
						.userMessageTranslationKey(translationKey)
						.build()
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void validationException_NoLogMessageNoParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";

		//when
		ValidationException exception = ValidationException.builder()
				.userMessageTranslationKey(translationKey)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(ValidationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Constraint Violation Exception, no message given"));
	}

	@Test
	void validationException_NoLogMessageWithParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		String param1 = "Param1";
		int param2 = 1;

		//when
		ValidationException exception = ValidationException.builder()
				.userMessageTranslationKey(translationKey)
				.translationParameter(param2)
				.translationParameter(param1)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(ValidationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Constraint Violation Exception, no message given"));
		assertThat(exception.getTranslationParameters(), allOf(
				notNullValue(),
				instanceOf(List.class),
				containsInAnyOrder(param2, param1)
		));
	}

	@Test
	void validationException_WithLogMessageNoParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		String logMessage = "Log Message";

		//when
		ValidationException exception = ValidationException.builder()
				.userMessageTranslationKey(translationKey)
				.logMessage(logMessage)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(ValidationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
	}

	@Test
	void validationException_WithLogMessageWithParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		String logMessage = "Log Message";
		String param1 = "Param1";
		int param2 = 1;

		//when
		ValidationException exception = ValidationException.builder()
				.userMessageTranslationKey(translationKey)
				.logMessage(logMessage)
				.translationParameter(param2)
				.translationParameter(param1)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(ValidationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getTranslationParameters(), allOf(
				notNullValue(),
				instanceOf(List.class),
				containsInAnyOrder(param2, param1)
		));
	}
}