package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationExceptionTest {
	@Test
	void registrationException_NullTranslationKey_Exception() {
		//given
		String translationKey = null;
		Locale originalLocale = Locale.US;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				new RegistrationException(translationKey, originalLocale)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void registrationException_NullLocale_Exception() {
		//given
		String translationKey = "Key";
		Locale originalLocale = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				new RegistrationException(translationKey, originalLocale)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void registrationException_NoLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;

		//when
		RegistrationException exception = new RegistrationException(translationKey, originalLocale);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(RegistrationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Registration Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}

	@Test
	void registrationException_WithLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";

		//when
		RegistrationException exception = new RegistrationException(translationKey, originalLocale, logMessage);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(RegistrationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}
}