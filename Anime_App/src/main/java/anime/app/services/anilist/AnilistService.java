package anime.app.services.anilist;

import anime.app.anilist.request.query.Query;
import anime.app.anilist.request.query.common.QueryElement;
import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.page.Page;
import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import anime.app.anilist.request.query.parameters.connections.characters.Character;
import anime.app.anilist.request.query.parameters.connections.characters.*;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.connections.media.MediaEdge;
import anime.app.anilist.request.query.parameters.connections.staff.Staff;
import anime.app.anilist.request.query.parameters.connections.staff.StaffArguments;
import anime.app.anilist.request.query.parameters.connections.staff.StaffConnection;
import anime.app.anilist.request.query.parameters.connections.staff.StaffSort;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateValue;
import anime.app.anilist.request.query.parameters.media.*;
import anime.app.configuration.beans.WebClientsConfiguration;
import anime.app.exceptions.exceptions.AnilistException;
import anime.app.openapi.model.*;
import anime.app.utils.LocaleUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Service;
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
@Log4j2
public class AnilistService implements AnilistServiceInterface {
	private final WebClient client;

	AnilistService(@Qualifier(WebClientsConfiguration.anilistWebClientBeanName) WebClient anilistWenClient) {
		this.client = anilistWenClient;
	}

	/**
	 * {@inheritDoc}
	 *
	 * This implementation sorts the Anime from the most popular to least.
	 */
	@Override
	public AnilistPageDTO getTopAiring(long pageNumber) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

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
				.flatMap(response -> evaluateResponse(
						response,
						originalLocale,
						String.format("Successfully requested page %s of top airing anime", pageNumber)
				))
				.block()
				.getData()
				.getPage();
	}

	/**
	 * {@inheritDoc}
	 *
	 * This implementation sorts the Anime Movies by score (from highest to lowest).
	 */
	@Override
	public AnilistPageDTO getTopAnimeMovies(long pageNumber) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		Page page = Page.fromMediaAndPageInfo(
				pageNumber,
				49,
				Media.getMediaArgumentBuilder(defaultBasicField)
						.format(MediaFormat.MOVIE)
						.type(MediaType.ANIME)
						.sort(MediaSort.SCORE_DESC)
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
				.flatMap(response -> evaluateResponse(
						response,
						originalLocale,
						String.format("Successfully requested page %s of top anime movies", pageNumber)
				))
				.block()
				.getData()
				.getPage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnilistPageDTO getTopAnimeOfAllTime(long pageNumber) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		Page page = Page.fromMediaAndPageInfo(
				pageNumber,
				49,
				Media.getMediaArgumentBuilder(defaultBasicField)
						.type(MediaType.ANIME)
						.sort(MediaSort.SCORE_DESC)
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
				.flatMap(response -> evaluateResponse(
						response,
						originalLocale,
						String.format("Successfully requested page %s of top anime of all time", pageNumber)
				))
				.block()
				.getData()
				.getPage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnilistPageDTO searchUsingQuery(AnimeQueryDTO query, long pageNumber) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		Media.MediaArgumentBuilder media = Media.getMediaArgumentBuilder(defaultBasicField)
				.type(MediaType.ANIME)
				.sort(MediaSort.SCORE_DESC);

		if (Objects.nonNull(query.getTitle()) && !query.getTitle().isBlank()) {
			media.search(query.getTitle());
		}

		if (Objects.nonNull(query.getSeason())) {
			media.season(MediaSeason.valueOf(query.getSeason().getValue()));
		}

		if (Objects.nonNull(query.getSeasonYear())) {
			media.seasonYear(query.getSeasonYear());
		}

		if (Objects.nonNull(query.getStatus())) {
			media.status(MediaStatus.valueOf(query.getStatus().getValue()));
		}

		if (Objects.nonNull(query.getFormat())) {
			media.format(MediaFormat.valueOf(query.getFormat().getValue()));
		}

		if (Objects.nonNull(query.getMaxNrOfEpisodes())) {
			media.episodesLesser(query.getMaxNrOfEpisodes());
		}

		if (Objects.nonNull(query.getMinNrOfEpisodes())) {
			media.episodesGreater(query.getMinNrOfEpisodes());
		}

		if (Objects.nonNull(query.getMaxAverageScore())) {
			media.averageScoreLesser(query.getMaxAverageScore());
		}

		if (Objects.nonNull(query.getMinAverageScore())) {
			media.averageScoreGreater(query.getMinAverageScore());
		}

		if (Objects.nonNull(query.getMaxStartDate())) {
			media.startDateLesser(FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMaxStartDate())
					.build());
		}

		if (Objects.nonNull(query.getMinStartDate())) {
			media.startDateGreater(FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMinStartDate())
					.build());
		}

		if (Objects.nonNull(query.getMaxEndDate())) {
			media.endDateLesser(FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMaxEndDate())
					.build());
		}

		if (Objects.nonNull(query.getMinEndDate())) {
			media.endDateGreater(FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMinEndDate())
					.build());
		}

		Page page = Page.fromMediaAndPageInfo(
				pageNumber,
				49,
				media.build(),
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
				.flatMap(response -> evaluateResponse(
						response,
						originalLocale,
						String.format("Successfully requested page %s using query %s", pageNumber, query)
				))
				.block()
				.getData()
				.getPage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DetailedAnimeDTO getAnimeById(long id) {
		Locale originalLocale = LocaleUtils.getCurrentRequestLocale();

		Field field = Field.getFieldBuilder()
				.parameter(BasicQueryParameters.ID)
				.parameter(BasicQueryParameters.SEASON)
				.parameter(BasicQueryParameters.SEASON_YEAR)
				.parameter(BasicQueryParameters.EPISODES)
				.parameter(BasicQueryParameters.DURATION)
				.parameter(BasicQueryParameters.GENRES)
				.parameter(BasicQueryParameters.AVERAGE_SCORE)
				.parameter(BasicQueryParameters.FORMAT)
				.parameter(BasicQueryParameters.TYPE)
				.parameter(BasicQueryParameters.FAVOURITES)
				.parameter(BasicQueryParameters.IS_ADULT)
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguageStylized()
						.romajiLanguageStylized()
						.nativeLanguageStylized()
						.build())
				.status()
				.coverImage()
				.descriptionAsHtml()
				.source(2)
				.startDate(FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.START_DATE).allAndBuild())
				.endDate(FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.END_DATE).allAndBuild())
				.nextAiringEpisode()
				.relations(MediaConnection.getMediaConnectionBuilder()
						.edge(MediaEdge.getMediaEdgeBuilder()
								.node(Media.getMediaArgumentBuilder(Field.getFieldBuilder()
												.parameter(BasicQueryParameters.ID)
												.parameter(BasicQueryParameters.TYPE)
												.title(MediaTitle.getMediaTitleBuilder()
														.englishLanguageStylized()
														.romajiLanguageStylized()
														.nativeLanguageStylized()
														.build())
												.coverImage()
												.status()
												.build())
										.sort(MediaSort.SCORE)
										.build())
								.relationType(2)
								.build())
						.build())
				.characters(CharacterConnection.getCharacterConnectionBuilder()
								.edges(CharacterEdge.getCharacterEdgeBuilder()
										.node(Character.getCharacterBuilder()
												.id()
												.name()
												.image()
												.build())
										.id()
										.role()
										.name()
										.voiceActors(Staff.getStaffBuilder()
												.name()
												.image()
												.languageV2()
												.build())
										.build())
								.pageInfo(defaultPageInfo)
								.build(),
						CharacterArguments.getCharacterArgumentsBuilder()
								.sortBy(CharacterSort.ROLE)
								.perPage(16)
								.build())
				.staff(StaffConnection.getMediaConnectionBuilder()
								.nodes(Staff.getStaffBuilder()
										.name()
										.image()
										.primaryOccupations()
										.build())
								.pageInfo(defaultPageInfo)
								.build(),
						StaffArguments.getStaffArgumentsBuilder()
								.sort(StaffSort.RELEVANCE)
								.perPage(16)
								.build())
				.build();

		Media media = Media.getMediaArgumentBuilder(field)
				.id(Math.toIntExact(id))
				.build();

		DetailedAnilistAnimeInformationDTO anilistInfo = client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
				})
				.body(buildBodyInsert(media))
				.retrieve()
				.onStatus(HttpStatus::isError, ex -> {
					throw new AnilistException(
							"anime.anilist-server-no-response",
							originalLocale,
							ex.body((clientHttpResponse, context) -> clientHttpResponse.getBody().toString())
					);
				})
				.bodyToMono(AnilistResponseDTOMedia.class)
				.flatMap(response -> evaluateResponse(
						response,
						originalLocale,
						String.format("Successfully requested anime with id %d", id)
				))
				.block()
				.getData()
				.getMedia();

		return DetailedAnimeDTO.builder()
				.externalInformation(anilistInfo)
				.build();
	}

	/**
	 * Small method used to evaluate the Anilist response.
	 * @param response The Anilist Response
	 * @param originalLocale The Locale of the original request
	 * @param <T> The class of the response
	 * @return If no error occurred, return the response
	 */
	private <T> Mono<T> evaluateResponse(T response, Locale originalLocale, String successLogMessage) {
		if (Objects.isNull(response)) {
			return Mono.error(new AnilistException(
					"anime.anilist-server-no-response",
					originalLocale
			));
		} else {
			log.info(successLogMessage);

			return Mono.just(response);
		}
	}

	/**
	 * Small method to build a {@link BodyInserter} for an Anilist Request.
	 * @param element The element to be used for the creation of {@link BodyInserter}
	 * @return {@link BodyInserter} for the request
	 */
	private BodyInserter<JsonNode, ReactiveHttpOutputMessage> buildBodyInsert(QueryElement element) {
		return BodyInserters.fromValue(Query.buildQuery(element));
	}
}
