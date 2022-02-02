package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

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
				new ValidationException(translationKey)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void validationException_NoLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";

		//when
		ValidationException exception = new ValidationException(translationKey);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(ValidationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Constraint Violation Exception, no message given"));
	}

	@Test
	void validationException_WithLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		String logMessage = "Log Message";

		//when
		ValidationException exception = new ValidationException(translationKey, logMessage);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(ValidationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
	}
}