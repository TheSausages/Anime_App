package anime.app.anilist.request.query.page;

import anime.app.anilist.request.query.common.QueryElement;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.PageInfo;

import java.util.Map;
import java.util.Set;

public class Page extends QueryElement {
	public static final String PAGE_TITLE = "Page";

	protected Page(String elementString, Set<String> queryParameters, Map<String, Object> variables) {
		super(elementString, queryParameters, variables);
	}

	public Page(int page, int perPage, Media media, PageInfo info) {
		super(
				QueryParameterUtils.combineIntoField(
						PAGE_TITLE,
						QueryParameterUtils.buildArguments(
								"page: $page",
								"perPage: $perPage"
						),
						media.toString() + "\n" + info.getPageInfoString()
				).getField(),
				Set.of("$page: int", "$perPage: Int"),
				Map.of("page", page, "perPage", perPage)
		);
	}

	public Page(int page, int perPage, Media media) {
		super(
				QueryParameterUtils.combineIntoField(
						PAGE_TITLE,
						QueryParameterUtils.buildArguments(
								"page: $page",
								"perPage: $perPage"
						),
						media.toString()
				).getField(),
				Set.of("$page: int", "$perPage: Int"),
				Map.of("page", page, "perPage", perPage)
		);
	}
}
