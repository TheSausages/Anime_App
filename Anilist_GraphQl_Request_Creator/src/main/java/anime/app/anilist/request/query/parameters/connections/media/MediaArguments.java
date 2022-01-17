package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.media.MediaSort;
import anime.app.anilist.request.query.parameters.media.MediaType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class MediaArguments {
	private final String MediaArgumentsString;

	private MediaArguments(String MediaArgumentsString) {
		this.MediaArgumentsString = MediaArgumentsString;
	}

	public static MediaArgumentsBuilder getMediaArgumentsBuilder() {
		return new MediaArgumentsBuilder();
	}

	@Override
	public String toString() {
		return MediaArgumentsString;
	}

	public static final class MediaArgumentsBuilder {
		private final Set<ParameterString> mediaArguments = new OverwritingLinkedHashSet<>();

		public MediaArgumentsBuilder sortBy(MediaSort... sorts) {
			mediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket(CommonParameterFieldNames.SORT, Arrays.toString(sorts)));
			return this;
		}

		public MediaArgumentsBuilder type(MediaType type) {
			mediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket("type", type.name()));
			return this;
		}

		public MediaArgumentsBuilder onList() {
			return onList(true);
		}

		public MediaArgumentsBuilder onList(boolean onList) {
			mediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket("onList", onList));
			return this;
		}

		public MediaArgumentsBuilder page(int page) {
			mediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket(CommonParameterFieldNames.PAGE, page));
			return this;
		}

		public MediaArgumentsBuilder perPage(int perPage) {
			mediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket(CommonParameterFieldNames.PER_PAGE, perPage));
			return this;
		}

		public MediaArguments build() {
			if (mediaArguments.isEmpty()) {
				throw new IllegalStateException("Media Arguments should posses at least 1 parameter!");
			}

			return new MediaArguments(QueryParameterUtils.buildArguments(mediaArguments));
		}
	}
}
