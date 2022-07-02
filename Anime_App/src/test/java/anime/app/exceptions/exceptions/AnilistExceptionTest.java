package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnilistExceptionTest {

	@Test
	void anilistException_NullTranslationKey_Exception() {
		//given
		String translationKey = null;
		Locale originalLocale = Locale.US;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				AnilistException.builder()
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
	void anilistException_NullLocale_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";

		//when
		AnilistException exception = AnilistException.builder()
				.userMessageTranslationKey(translationKey)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AnilistException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Anilist Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(Locale.UK));
	}

	@Test
	void anilistException_NoLogMessageNoParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;

		//when
		AnilistException exception = AnilistException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AnilistException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Anilist Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}

	@Test
	void anilistException_NoLogMessageWithParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String param1 = "Param1";
		int param2 = 1;

		//when
		AnilistException exception = AnilistException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.translationParameter(param1)
				.translationParameter(param2)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AnilistException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo("Anilist Exception, no message given"));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
		assertThat(exception.getTranslationParameters(), allOf(
				notNullValue(),
				instanceOf(List.class),
				containsInAnyOrder(param2, param1)
		));
	}

	@Test
	void anilistException_WithLogMessageNoParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";

		//when
		AnilistException exception = AnilistException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.logMessage(logMessage)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AnilistException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}

	@Test
	void anilistException_WithLogMessageWithParameters_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";
		String param1 = "Param1";
		int param2 = 1;

		//when
		AnilistException exception = AnilistException.builder()
				.userMessageTranslationKey(translationKey)
				.originalLocale(originalLocale)
				.logMessage(logMessage)
				.translationParameter(param2)
				.translationParameter(param1)
				.build();

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AnilistException.class)
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