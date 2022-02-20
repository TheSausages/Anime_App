package anime.app.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

/**
 * Small utility class containing methods connected to Locale.
 */
public interface LocaleUtils {
	/**
	 * Small method to get the Locale of the current Request.
	 * @return The Locale of the current request
	 */
	static Locale getCurrentRequestLocale() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getLocale();
	}
}
