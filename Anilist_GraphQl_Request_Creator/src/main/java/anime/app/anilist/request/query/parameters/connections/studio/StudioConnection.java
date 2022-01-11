package anime.app.anilist.request.query.parameters.connections.studio;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class StudioConnection {
	public static final String studioConnectionTitle = "studioConnection";

	private final String studioConnectionString;

	private StudioConnection(String studioConnectionString) {
		this.studioConnectionString = studioConnectionString;
	}

	public String getStudioConnectionWithoutFieldName() {
		return this.studioConnectionString.substring(studioConnectionTitle.length() + 1);
	}

	public static StudioConnectionBuilder getStudioConnectionBuilder() {
		return new StudioConnectionBuilder();
	}

	@Override
	public String toString() {
		return studioConnectionString;
	}

	public static final class StudioConnectionBuilder {
		private final Set<ParameterString> studioConnection = new OverwritingLinkedHashSet<>();

		public StudioConnectionBuilder edges(StudioEdge studioEdge) {
			studioConnection.add(ParameterString.fromString("edges " + studioEdge.getStudioEdgeWithoutFieldName()));
			return this;
		}

		public StudioConnectionBuilder nodes(Studio studio) {
			studioConnection.add(ParameterString.fromString("nodes " + studio.getStudioWithoutFieldName()));
			return this;
		}

		public StudioConnectionBuilder pageInfo(PageInfo pageInfo) {
			studioConnection.add(ParameterString.fromString(pageInfo.getPageInfoString()));
			return this;
		}

		public StudioConnection build() {
			if (studioConnection.isEmpty()) {
				throw new IllegalStateException("Studio Connection should posses at least 1 parameter!");
			}

			return new StudioConnection(QueryParameterUtils.buildQueryFieldElementString(
					studioConnectionTitle,
					studioConnection
			));
		}
	}
}
