package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;
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
				RegistrationException.builder()
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
	void registrationException_NullLocale_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";

		//when
		RegistrationException exception = RegistrationException.builder()
				.userMessageTranslationKey(translationKey)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(RegistrationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Registration Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(Locale.UK));
	}

	@Test
	void registrationException_NoLogMessageNoParams_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;

		//when
		RegistrationException exception = RegistrationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.build();

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
	void registrationException_NoLogMessageWithParams_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String param1 = "Param1";
		int param2 = 1;

		//when
		RegistrationException exception = RegistrationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.translationParameter(param2)
				.translationParameter(param1)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(RegistrationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Registration Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
		assertThat(exception.getTranslationParameters(), allOf(
				notNullValue(),
				instanceOf(List.class),
				containsInAnyOrder(param2, param1)
		));
	}

	@Test
	void registrationException_WithLogMessageNoParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";

		//when
		RegistrationException exception = RegistrationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.logMessage(logMessage)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(RegistrationException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}

	@Test
	void registrationException_WithLogMessageWithParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";
		String param1 = "Param1";
		int param2 = 1;

		//when
		RegistrationException exception = RegistrationException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.logMessage(logMessage)
				.translationParameter(param2)
				.translationParameter(param1)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(RegistrationException.class)
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