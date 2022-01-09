package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class MediaConnection {
	public final static String mediaConnectionTitle = "mediaConnection";

	private final String mediaConnectionString;

	private MediaConnection(String mediaConnectionString) {
		this.mediaConnectionString = mediaConnectionString;
	}

	public String getMediaConnectionWithoutFieldName() {
		return this.mediaConnectionString.substring(mediaConnectionTitle.length() + 1);
	}

	public static MediaConnectionBuilder getMediaConnectionBuilder() {
		return new MediaConnectionBuilder();
	}

	public static final class MediaConnectionBuilder {
		private final Set<ParameterString> mediaConnections = new OverwritingLinkedHashSet<>();

		public MediaConnectionBuilder edge(MediaEdge mediaEdge) {
			mediaConnections.add(new ParameterString("edges " + mediaEdge.getMediaEdgeWithoutFieldName()));
			return this;
		}

		public MediaConnectionBuilder nodes(Media media) {
			mediaConnections.add(ParameterString.fromString("nodes " + media.getRequestedMediaFields()));
			return this;
		}

		public MediaConnectionBuilder pageInfo(PageInfo pageInfo) {
			mediaConnections.add(ParameterString.fromString(pageInfo.getPageInfoString()));
			return this;
		}

		public MediaConnection build() {
			if (mediaConnections.isEmpty()) {
				throw new IllegalStateException("Media Connection should posses at least 1 parameter!");
			}

			return new MediaConnection(QueryParameterUtils.buildQueryFieldElementString(
					mediaConnectionTitle,
					mediaConnections
			));
		}
	}
}
