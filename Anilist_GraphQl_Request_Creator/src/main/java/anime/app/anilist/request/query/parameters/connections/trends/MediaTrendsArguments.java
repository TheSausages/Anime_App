package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class MediaTrendsArguments {
	private final String mediaTrendsArgumentsString;

	private MediaTrendsArguments(String mediaTrendsArgumentsString) {
		this.mediaTrendsArgumentsString = mediaTrendsArgumentsString;
	}

	public static MediaTrendsArgumentsBuilder getMediaTrendsArgumentsBuilder() {
		return new MediaTrendsArgumentsBuilder();
	}

	@Override
	public String toString() {
		return mediaTrendsArgumentsString;
	}

	public static final class MediaTrendsArgumentsBuilder {
		private final Set<ParameterString> trendsArguments = new OverwritingLinkedHashSet<>();

		public MediaTrendsArgumentsBuilder sort(MediaTrendSort... sorts) {
			trendsArguments.add(ParameterString.fromString("sort: " + Arrays.toString(sorts)));
			return this;
		}

		public MediaTrendsArgumentsBuilder releasing() {
			trendsArguments.add(ParameterString.fromString("releasing: true"));
			return this;
		}

		public MediaTrendsArgumentsBuilder releasing(boolean releasing) {
			trendsArguments.add(ParameterString.fromString("releasing: " + releasing));
			return this;
		}

		public MediaTrendsArgumentsBuilder page(int page) {
			trendsArguments.add(ParameterString.fromString("page: " + page));
			return this;
		}

		public MediaTrendsArgumentsBuilder perPage(int perPage) {
			trendsArguments.add(ParameterString.fromString("perPage: " + perPage));
			return this;
		}

		public MediaTrendsArguments build() {
			if (trendsArguments.isEmpty()) {
				throw new IllegalStateException("Trends Arguments should posses at least 1 parameter!");
			}

			return new MediaTrendsArguments(QueryParameterUtils.buildQueryFieldElementArgumentsString(
					trendsArguments
			));
		}
	}
}
