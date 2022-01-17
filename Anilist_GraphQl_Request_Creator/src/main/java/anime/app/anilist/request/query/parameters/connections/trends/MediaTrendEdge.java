package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Objects;

@Getter
public class MediaTrendEdge {
	public static final String MEDIA_TREND_EDGE_TITLE = "mediaTrendEdge";

	private final String studioEdgeString;

	public MediaTrendEdge(MediaTrend mediaTrend) {
		Objects.requireNonNull(mediaTrend, "Media trend cannot be null");

		this.studioEdgeString = QueryParameterUtils.combineIntoField(MEDIA_TREND_EDGE_TITLE, mediaTrend.getMediaTrendWithoutFieldName()).getField();
	}

	public static MediaTrendEdge fromMediaTrend(MediaTrend trend) {
		return new MediaTrendEdge(trend);
	}

	public String getStudioEdgeWithoutFieldName() {
		return this.studioEdgeString.substring(MEDIA_TREND_EDGE_TITLE.length() + 1);
	}
}
