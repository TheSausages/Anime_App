package anime.app.services.i18n;

import java.util.Locale;

/**
 * Interface for an I18n Service. Each implementation must use this interface.
 */
public interface I18nServiceInterface {
	/**
	 * Get a translation using a selected path. This method uses the locale set from the {@link org.springframework.http.HttpHeaders#ACCEPT_LANGUAGE} header.
	 * @param path       Path to the translation
	 * @param parameters Parameters used in the translation, can be empty
	 * @return Translation with parameters inserted in order
	 */
	String getTranslation(String path, Object... parameters);

	/**
	 * Variant of {@link #getTranslation(String, Object...)}. When the error is from a request to another site (ex. anilist) the headers would be from there, so in order to get
	 * the correct locale we need to insert the correct locale by hand.
	 * header from the request for a {@link anime.app.services.i18n.I18nServiceInterface} implementation to use.
	 * @param path       Path to the translation
	 * @param locale    Locale from original request, used for messages
	 * @param parameters Parameters used in the translation, can be empty
	 * @return Translation with parameters inserted in order
	 */
	String getTranslation(String path, Locale locale, Object... parameters);
}
