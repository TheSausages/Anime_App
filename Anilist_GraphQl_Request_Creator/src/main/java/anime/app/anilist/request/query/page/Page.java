package anime.app.anilist.request.query.page;

import anime.app.anilist.request.query.common.QueryElement;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.PageInfo;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Page extends QueryElement {
	public static final String PAGE_TITLE = "Page";

	protected Page(String elementString, Set<String> queryParameters, Map<String, Object> variables) {
		super(elementString, queryParameters, variables);
	}

	private Page(int page, int perPage, Media media, PageInfo info) {
		super(
				QueryParameterUtils.buildStringField(
						PAGE_TITLE,
						Set.of(
								QueryParameterUtils.buildStringArguments(
										"page: $page",
										"perPage: $perPage"
								)
						),
						Set.of(
								Media.MEDIA_TITLE.toLowerCase(Locale.ROOT) + media.getRequestedMediaFieldsWithArguments(),
								info.getPageInfoString()
						)
				),
				combineIntoSet(media.getQueryParameters(), "$page: Int", "$perPage: Int"),
				combineIntoMap(media.getVariables(), Map.of("page", page, "perPage", perPage))
		);
	}

	private Page(int page, int perPage, Media media) {
		super(
				QueryParameterUtils.buildStringField(
						PAGE_TITLE,
						Set.of(
								QueryParameterUtils.buildStringArguments(
										"page: $page",
										"perPage: $perPage"
								)
						),
						Set.of(Media.MEDIA_TITLE.toLowerCase(Locale.ROOT) + media.getRequestedMediaFieldsWithArguments())
				),
				combineIntoSet(media.getQueryParameters(), "$page: Int", "$perPage: Int"),
				combineIntoMap(media.getVariables(), Map.of("page", page, "perPage", perPage))
		);
	}

	public static Page fromMediaAndPageInfo(int page, int perPage, Media media, PageInfo pageInfo) {
		Objects.requireNonNull(media, "Media cannot be null");
		Objects.requireNonNull(pageInfo, "PageInfo cannot be null");

		return new Page(page, perPage, media, pageInfo);
	}

	public static Page fromMedia(int page, int perPage, Media media) {
		Objects.requireNonNull(media, "Media cannot be null");

		return new Page(page, perPage, media);
	}

	public static Page fromMediaAndPageInfo(long page, int perPage, Media media, PageInfo pageInfo) {
		return fromMediaAndPageInfo(Math.toIntExact(page), perPage, media, pageInfo);
	}

	public static Page fromMedia(long page, int perPage, Media media) {
		return fromMedia(Math.toIntExact(page), perPage, media);
	}
}
