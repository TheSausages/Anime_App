package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnilistExceptionTest {

	@Test
	void animistException_NullTranslationKey_Exception() {
		//given
		String translationKey = null;
		Locale originalLocale = Locale.US;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				new AnilistException(translationKey, originalLocale)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void anilistException_NoLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;

		//when
		AnilistException exception = new AnilistException(translationKey, originalLocale);

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
	void anilistException_WithLogMessage_CorrectExceptionCreation() {
		//given
		String translationKey = "Some key";
		Locale originalLocale = Locale.US;
		String logMessage = "Log Message";

		//when
		AnilistException exception = new AnilistException(translationKey, originalLocale, logMessage);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(AnilistException.class)
		));
		assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
		assertThat(exception.getLogMessage(), equalTo(logMessage));
		assertThat(exception.getOriginalLocale(), equalTo(originalLocale));
	}
}