package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class MediaConnection {
	public final static String MEDIA_CONNECTION_TITLE = "mediaConnection";

	private final String mediaConnectionString;

	private MediaConnection(String mediaConnectionString) {
		this.mediaConnectionString = mediaConnectionString;
	}

	public String getMediaConnectionWithoutFieldName() {
		return this.mediaConnectionString.substring(MEDIA_CONNECTION_TITLE.length() + 1);
	}

	@Override
	public String toString() {
		return mediaConnectionString;
	}

	public static MediaConnectionBuilder getMediaConnectionBuilder() {
		return new MediaConnectionBuilder();
	}

	public static final class MediaConnectionBuilder {
		private final Set<ParameterString> mediaConnections = new OverwritingLinkedHashSet<>();

		public MediaConnectionBuilder edge(MediaEdge mediaEdge) {
			mediaConnections.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.EDGES, mediaEdge.getMediaEdgeWithoutFieldName()));
			return this;
		}

		public MediaConnectionBuilder nodes(Media media) {
			mediaConnections.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODES, media.getRequestedMediaFields()));
			return this;
		}

		public MediaConnectionBuilder pageInfo(PageInfo pageInfo) {
			mediaConnections.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.PAGE_INFO, pageInfo.getPageInfoStringWithoutFieldName()));
			return this;
		}

		public MediaConnection build() {
			if (mediaConnections.isEmpty()) {
				throw new IllegalStateException("Media Connection should posses at least 1 parameter!");
			}

			return new MediaConnection(QueryParameterUtils.buildFieldElement(
					MEDIA_CONNECTION_TITLE,
					mediaConnections
			));
		}
	}
}
