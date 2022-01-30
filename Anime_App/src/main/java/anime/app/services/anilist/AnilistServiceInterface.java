package anime.app.services.anilist;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.query.parameters.media.MediaTitle;
import anime.app.openapi.model.AnilistPageDTO;
import anime.app.openapi.model.AnimeQueryDTO;
import anime.app.openapi.model.DetailedAnimeDTO;

/**
 * Interface for an Anilist Service. Each implementation must use this interface.
 * <p>
 * The anilist api information are available at:
 * <ul>
 *     <li>Api documentation: <a href="https://anilist.github.io/ApiV2-GraphQL-Docs/">https://anilist.github.io/ApiV2-GraphQL-Docs/</a></li>
 *     <li>Query tester: <a href="https://anilist.co/graphiql">https://anilist.co/graphiql</a></li>
 * </ul>
 */
public interface AnilistServiceInterface {
	PageInfo defaultPageInfo = PageInfo.getPageInfoBuilder()
			.total()
			.currentPage()
			.lastPage()
			.hasNextPage()
			.perPage()
			.build();

	Field defaultBasicField = Field.getFieldBuilder()
			.parameter(BasicQueryParameters.ID)
			.title(MediaTitle.getMediaTitleBuilder()
					.englishLanguageStylized()
					.nativeLanguageStylized()
					.romajiLanguageStylized()
					.build())
			.coverImage()
			.build();

	/**
	 * Retrieve the current season (currently airing) Anime.
	 * @param pageNumber Which page should be retrieved from the ranking
	 * @return The current season Anime in an Anilist Page DTO
	 */
	AnilistPageDTO getTopAiring(long pageNumber);

	/**
	 * Retrieve a page from the Top Anime Movies ranking.
	 * @param pageNumber Which page should be retrieved from the ranking
	 * @return Anime Movies in an Anilist Page DTO
	 */
	AnilistPageDTO getTopAnimeMovies(long pageNumber);

	/**
	 * Retrieve a page from the Top Anime of all time ranking.
	 * @param pageNumber Which page should be retrieved from the ranking
	 * @return Top Anime, ranked by score, in an Anilist Page DTO
	 */
	AnilistPageDTO getTopAnimeOfAllTime(long pageNumber);

	/**
	 * Get a page from the list of Anime that meet the {@link AnimeQueryDTO} query conditions.
	 * @param query Query used to search the anime list
	 * @param pageNumber Which page should be retrieved from the Anime list
	 * @return Anime that meet conditions in an Anilist Page DTO
	 */
	AnilistPageDTO searchUsingQuery(AnimeQueryDTO query, long pageNumber);

	/**
	 * Retrieve detailed Anime information using the id.
	 * @param id Anilist anime id used to get detailed information
	 * @return Detailed information about an Anime in a {@link DetailedAnimeDTO} object
	 */
	DetailedAnimeDTO getAnimeById(long id);
}
