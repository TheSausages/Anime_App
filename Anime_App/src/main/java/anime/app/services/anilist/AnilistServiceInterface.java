package anime.app.services.anilist;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.query.parameters.media.MediaTitle;
import anime.app.openapi.model.AnilistPageDTO;

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
	 * Retrieve the current season Anime.
	 * @return The current season Anime in an Anilist Query JSON tree
	 */
	AnilistPageDTO getTopAiring(long pageNumber);
}
