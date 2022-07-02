package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;
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
				AuthenticationException.builder()
						.userMessageTranslationKey(translationKey)
						.originalLocale(originalLocale)
						.build()
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void authenticationException_NullLocale_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";

		//when
		AuthenticationException exception = AuthenticationException.builder()
				.userMessageTranslationKey(translationKey)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AuthenticationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Authentication Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(Locale.UK));
	}

	@Test
	void authenticationException_NoLogMessageNoParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;

		//when
		AuthenticationException exception = AuthenticationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.build();

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
	void authenticationException_NoLogMessageWithParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String param1 = "Param1";
		int param2 = 1;

		//when
		AuthenticationException exception = AuthenticationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.translationParameter(param2)
				.translationParameter(param1)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AuthenticationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Authentication Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
		assertThat(exception.getTranslationParameters(), allOf(
				notNullValue(),
				instanceOf(List.class),
				containsInAnyOrder(param2, param1)
		));
	}

	@Test
	void authenticationException_WithLogMessageNoParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";

		//when
		AuthenticationException exception = AuthenticationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.logMessage(logMessage)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AuthenticationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}

	@Test
	void authenticationException_WithLogMessageWithParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";
		String param1 = "Param1";
		int param2 = 1;

		//when
		AuthenticationException exception = AuthenticationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.logMessage(logMessage)
				.translationParameter(param2)
				.translationParameter(param1)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AuthenticationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
		assertThat(exception.getTranslationParameters(), allOf(
				notNullValue(),
				instanceOf(List.class),
				containsInAnyOrder(param2, param1)
		));
	}
}