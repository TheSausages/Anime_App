package anime.app.services.i18n;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Default implementation for the {@link I18nServiceInterface} interface.
 */
@Service
@Log4j2
public class I18nService implements I18nServiceInterface {
	private final MessageSource source;

	@Autowired
	I18nService(MessageSource source) {
		this.source = source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTranslation(@NonNull String path, Object... parameters) {
		return getTranslation(path, LocaleContextHolder.getLocale(), parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTranslation(@NonNull String path, @NonNull Locale locale, Object... parameters) {
		log.info("Get message for locale {} with path {}", locale, path);

		return source.getMessage(path, parameters, locale);
	}
}
