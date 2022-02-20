package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationExceptionTest {
	@Test
	void authenticationException_NullTranslationKey_Exception() {
		//given
		String translationKey = null;
		Locale originalLocale = Locale.US;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				new AuthenticationException(translationKey, originalLocale)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void authenticationException_NullLocale_Exception() {
		//given
		String translationKey = "Key";
		Locale originalLocale = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				new AuthenticationException(translationKey, originalLocale)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void authenticationException_NoLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;

		//when
		AuthenticationException exception = new AuthenticationException(translationKey, originalLocale);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AuthenticationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Authentication Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}

	@Test
	void authenticationException_WithLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";

		//when
		AuthenticationException exception = new AuthenticationException(translationKey, originalLocale, logMessage);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AuthenticationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}
}