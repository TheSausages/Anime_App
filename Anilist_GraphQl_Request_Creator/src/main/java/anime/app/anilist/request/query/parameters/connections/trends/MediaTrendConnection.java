package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class MediaTrendConnection {
	public final static String MEDIA_TRENDS_CONNECTION_TITLE = "mediaTrendsConnection";

	private final String mediaConnectionString;

	private MediaTrendConnection(String mediaConnectionString) {
		this.mediaConnectionString = mediaConnectionString;
	}

	public String getMediaConnectionWithoutFieldName() {
		return this.mediaConnectionString.substring(MEDIA_TRENDS_CONNECTION_TITLE.length() + 1);
	}

	public static MediaConnectionBuilder getMediaConnectionBuilder() {
		return new MediaConnectionBuilder();
	}

	public static final class MediaConnectionBuilder {
		private final Set<ParameterString> mediaTrendsConnection = new OverwritingLinkedHashSet<>();

		public MediaConnectionBuilder edges(MediaTrendEdge mediaTrendEdge) {
			mediaTrendsConnection.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.EDGES, mediaTrendEdge.getStudioEdgeWithoutFieldName()));
			return this;
		}

		public MediaConnectionBuilder nodes(MediaTrend mediaTrend) {
			mediaTrendsConnection.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.NODES, mediaTrend.getMediaTrendWithoutFieldName()));
			return this;
		}

		public MediaConnectionBuilder pageInfo(PageInfo pageInfo) {
			mediaTrendsConnection.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.PAGE_INFO, pageInfo.getPageInfoStringWithoutFieldName()));
			return this;
		}

		public MediaTrendConnection build() {
			if (mediaTrendsConnection.isEmpty()) {
				throw new IllegalStateException("Trends Connection should posses at least 1 parameter!");
			}

			return new MediaTrendConnection(QueryParameterUtils.buildStringField(
					MEDIA_TRENDS_CONNECTION_TITLE,
					mediaTrendsConnection
			));
		}
	}
}
