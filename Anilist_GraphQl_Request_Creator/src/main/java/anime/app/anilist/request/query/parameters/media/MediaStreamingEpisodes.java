package anime.app.anilist.request.query.parameters.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MediaStreamingEpisodes {
	public static final String streamingEpisodesTitle = "streamingEpisodes";

	private final String streamingEpisode;

	@Override
	public String toString() {
		return streamingEpisode;
	}

	public static MediaStreamingEpisodesBuilder getMediaStreamingEpisodesBuilder() {
		return new MediaStreamingEpisodesBuilder();
	}

	public final static class MediaStreamingEpisodesBuilder {
		private final Set<ParameterString> mediaStreamingEpisode = new OverwritingLinkedHashSet<>();

		public MediaStreamingEpisodesBuilder title() {
			mediaStreamingEpisode.add(ParameterString.fromString("title"));
			return this;
		}

		public MediaStreamingEpisodesBuilder url() {
			mediaStreamingEpisode.add(ParameterString.fromString("url"));
			return this;
		}

		public MediaStreamingEpisodesBuilder site() {
			mediaStreamingEpisode.add(ParameterString.fromString("site"));
			return this;
		}

		public MediaStreamingEpisodesBuilder thumbnail() {
			mediaStreamingEpisode.add(ParameterString.fromString("thumbnail"));
			return this;
		}

		public MediaStreamingEpisodes build() {
			if (mediaStreamingEpisode.isEmpty()) {
				throw new IllegalStateException("Streaming Episodes should posses at least 1 parameter!");
			}

			return new MediaStreamingEpisodes(QueryParameterUtils.buildQueryFieldElementString(
					streamingEpisodesTitle,
					mediaStreamingEpisode
			));
		}

	}
}
