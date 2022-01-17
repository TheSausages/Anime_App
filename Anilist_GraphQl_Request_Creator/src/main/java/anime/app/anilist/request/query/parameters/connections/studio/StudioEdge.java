package anime.app.anilist.request.query.parameters.connections.studio;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class StudioEdge {
	public static final String STUDIO_EDGE_TITLE = "studioEdge";

	private final String studioEdgeString;

	private StudioEdge(String studioEdgeString) {
		this.studioEdgeString = studioEdgeString;
	}

	public String getStudioEdgeWithoutFieldName() {
		return this.studioEdgeString.substring(STUDIO_EDGE_TITLE.length() + 1);
	}

	public static StudioEdgeBuilder getStudioEdgedBuilder() {
		return new StudioEdgeBuilder();
	}

	@Override
	public String toString() {
		return studioEdgeString;
	}

	public static final class StudioEdgeBuilder {
		private final Set<ParameterString> studioEdge = new OverwritingLinkedHashSet<>();

		public StudioEdgeBuilder node(Studio studio) {
			studioEdge.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODE, studio.getStudioWithoutFieldName()));
			return this;
		}

		public StudioEdgeBuilder id() {
			studioEdge.add(ParameterString.fromString("id"));
			return this;
		}

		public StudioEdgeBuilder isMain() {
			studioEdge.add(ParameterString.fromString("isMain"));
			return this;
		}

		public StudioEdgeBuilder favouriteOrder() {
			studioEdge.add(ParameterString.fromString("favouriteOrder"));
			return this;
		}

		public StudioEdge build() {
			if (studioEdge.isEmpty()) {
				throw new IllegalStateException("Studio Edge should posses at least 1 parameter!");
			}

			return new StudioEdge(QueryParameterUtils.buildFieldElement(
					STUDIO_EDGE_TITLE,
					studioEdge
			));
		}
	}
}
