package anime.app.anilist.request.query.parameters.connections.studio;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.media.MediaArguments;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import lombok.Getter;

import java.util.Set;

@Getter
public class Studio {
	public static final String STUDIO_TITLE = "studio";

	private final String studioString;

	private Studio(String studioString) {
		this.studioString = studioString;
	}

	public String getStudioWithoutFieldName() {
		return this.studioString.substring(STUDIO_TITLE.length() + 1);
	}

	public static StudioBuilder getStudioBuilder() {
		return new StudioBuilder();
	}

	@Override
	public String toString() {
		return studioString;
	}

	public static final class StudioBuilder {
		private final Set<ParameterString> studio = new OverwritingLinkedHashSet<>();

		public StudioBuilder id() {
			studio.add(ParameterString.fromString("id"));
			return this;
		}

		public StudioBuilder name() {
			studio.add(ParameterString.fromString("name"));
			return this;
		}

		public StudioBuilder isAnimationStudio() {
			studio.add(ParameterString.fromString("isAnimationStudio"));
			return this;
		}

		public StudioBuilder media(MediaConnection mediaConnection) {
			studio.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.MEDIA, mediaConnection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public StudioBuilder media(MediaConnection mediaConnection, MediaArguments mediaArguments) {
			studio.add(QueryParameterUtils.combineIntoStringField(
					CommonParameterFieldNames.MEDIA,
					mediaArguments.getMediaArgumentsString(),
					mediaConnection.getMediaConnectionWithoutFieldName()
			));
			return this;
		}

		public StudioBuilder siteUrl() {
			studio.add(ParameterString.fromString("siteUrl"));
			return this;
		}

		public StudioBuilder favourites() {
			studio.add(ParameterString.fromString("favourites"));
			return this;
		}

		public Studio build() {
			if (studio.isEmpty()) {
				throw new IllegalStateException("Studio should posses at least 1 parameter!");
			}

			return new Studio(QueryParameterUtils.buildStringField(
					STUDIO_TITLE,
					studio
			));
		}
	}
}