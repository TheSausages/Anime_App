package anime.app.configuration.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * Configure a {@link AcceptHeaderLocaleResolver} locale resolver with a default {@link Locale#UK} locale.
 */
@Configuration
public class LocaleResolverConfiguration {

	//Primary is here to stop the default one from being created
	//Wont work without it
	@Primary
	@Bean
	LocaleResolver customLocaleResolver() {
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);

		return resolver;
	}
}
