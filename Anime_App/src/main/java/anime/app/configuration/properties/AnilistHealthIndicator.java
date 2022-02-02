package anime.app.configuration.properties;

import anime.app.anilist.request.query.Query;
import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.configuration.beans.WebClientsConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

/**
 * Health indicator for the Anilist GraphQl api. Checks its availability using {@link AnilistHealthIndicator#ANILIST_STATUS_QUERY}.
 * Used in the 'health' actuator endpoint.
 */
@Component
public class AnilistHealthIndicator implements HealthIndicator {
	private static final BodyInserter<JsonNode, ReactiveHttpOutputMessage> ANILIST_STATUS_QUERY = BodyInserters.fromValue(
			Query.buildQuery(
					Media.getMediaArgumentBuilder(Field.getFieldBuilder().source().build()).id(1).build()
			)
	);

	private final WebClient anilistWebClient;
	private final AnilistProperties anilistProperties;

	@Autowired
	AnilistHealthIndicator(@Qualifier(WebClientsConfiguration.anilistWebClientBeanName) WebClient anilistWenClient, AnilistProperties anilistProperties) {
		this.anilistWebClient = anilistWenClient;
		this.anilistProperties = anilistProperties;
	}

	@Override
	public Health health() {
		Health.Builder status = anilistWebClient
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
				})
				.body(ANILIST_STATUS_QUERY)
				.retrieve()
				.toBodilessEntity()
				.map(check -> check.getStatusCode().isError() ? Health.outOfService() : Health.up())
				.block();

		if (Objects.isNull(status)) status = Health.unknown();

		return status
				.withDetail("service", "Anilist GraphQl Service")
				.withDetail("URL used", anilistProperties.getApiUrl())
				.withDetail("date", LocalDateTime.now())
				.build();
	}
}
