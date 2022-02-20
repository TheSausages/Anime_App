package anime.app.services.i18n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class I18nServiceTest {

	@Mock
	MessageSource source;

	@InjectMocks
	I18nService service;

	@Test
	void getTranslation_NullPath_ThrowException() {
		//given
		String path = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				service.getTranslation(path)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void getTranslation_NullLocale_ThrowException() {
		//given
		String path = "path";
		Locale locale = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				service.getTranslation(path, locale)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void testTranslation_PathWithoutLocale_ReturnTranslation() {
		//given
		String path = "path";
		String expectedTranslation = "translation";
		doReturn(expectedTranslation).when(source).getMessage(eq(path), ArgumentMatchers.any(), eq(LocaleContextHolder.getLocale()));

		//when
		String actualTranslation = service.getTranslation(path);

		//then
		assertThat(actualTranslation, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedTranslation)
		));
	}

	@Test
	void testTranslation_PathWithLocale_ReturnTranslation() {
		//given
		String path = "path";
		String expectedTranslation = "translationLocale";
		Locale locale = Locale.CANADA;
		doReturn(expectedTranslation).when(source).getMessage(eq(path), ArgumentMatchers.any(), eq(locale));

		//when
		String actualTranslation = service.getTranslation(path, locale);

		//then
		assertThat(actualTranslation, allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedTranslation)
		));
	}
}