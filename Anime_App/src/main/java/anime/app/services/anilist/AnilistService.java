package anime.app.services.anilist;

import anime.app.anilist.request.query.Query;
import anime.app.anilist.request.query.common.QueryElement;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.page.Page;
import anime.app.anilist.request.query.parameters.media.MediaSeason;
import anime.app.anilist.request.query.parameters.media.MediaSort;
import anime.app.anilist.request.query.parameters.media.MediaType;
import anime.app.exceptions.exceptions.AnilistException;
import anime.app.openapi.model.AnilistPageDTO;
import anime.app.openapi.model.AnilistResponseDTOPage;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

/**
 * Default implementation for the {@link AnilistServiceInterface} interface.
 */
@Service
public class AnilistService implements AnilistServiceInterface {
	private final WebClient client;

	AnilistService(@Qualifier("anilistWebClient") WebClient anilistWenClient) {
		this.client = anilistWenClient;
	}

	/**
	 * {@inheritDoc}
	 *
	 * This implementation sorts the Anime from the most popular to least
	 */
	@Override
	public AnilistPageDTO getTopAiring(long pageNumber) {
		Locale originalLocale = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getLocale();

		Page page = Page.fromMediaAndPageInfo(
				pageNumber,
				49,
				Media.getMediaArgumentBuilder(defaultBasicField)
						.season(MediaSeason.getCurrentSeason())
						.seasonYear(LocalDate.now().getYear())
						.type(MediaType.ANIME)
						.sort(MediaSort.POPULARITY_DESC)
						.build(),
				defaultPageInfo
		);

		return client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
				})
				.body(buildBodyInsert(page))
				.retrieve()
				.onStatus(HttpStatus::isError, ex -> {
					throw new AnilistException(
							"anime.anilist-server-no-response",
							originalLocale
					);
				})
				.bodyToMono(AnilistResponseDTOPage.class)
				.flatMap(response -> evaluateResponse(response, originalLocale))
				.block()
				.getData()
				.getPage();
	}

	private <T> Mono<T> evaluateResponse(T response, Locale originalLocale) {
		if (Objects.isNull(response)) {
			return Mono.error(new AnilistException(
					"anime.anilist-server-no-response",
					originalLocale
			));
		} else {
			return Mono.just(response);
		}
	}

	private BodyInserter<JsonNode, ReactiveHttpOutputMessage> buildBodyInsert(QueryElement element) {
		return BodyInserters.fromValue(Query.buildQuery(element));
	}
}
